package com.hello.controller;
import io.sentry.*;
import java.util.ArrayList;

import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;
import com.ubc.pojo.BasicResponseObject;
import com.ubc.pojo.MetaDataa;

@Path("/metadatas")
public class MetaDataController {
	@POST
	@Path("/createupdatemetadata")
	public Response createupdateMetadata(String request) {
		MetaDataa requestMetadata = new Gson().fromJson(request, MetaDataa.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestMetadata.getContentid()!=null)
			{
		    eManager.getTransaction().begin();
		    MetaDataa searchMetadata = eManager.find(MetaDataa.class, requestMetadata.getContentid());
			if(searchMetadata!=null) {
				if(requestMetadata.getMeta()!=null && requestMetadata.getMeta().length()>0) {
					searchMetadata.setMeta(requestMetadata.getMeta());
				}
				if(requestMetadata.getKeywords()!=null && requestMetadata.getKeywords().length()>0) {
					searchMetadata.setKeywords(requestMetadata.getKeywords());
				}
				if(requestMetadata.getMenuId()!=null) {
					searchMetadata.setMenuId(requestMetadata.getMenuId());
				}
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchMetadata).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Metadata Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				eManager.persist(requestMetadata);
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestMetadata).toString())
						.header("Content-Type", "application/json").build();
			}
		} catch (NamingException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (PersistenceException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		}
	}

	@GET
	@Path("/metadatadetails")
	public Response getAllMetadata() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();
			//String sql = "SELECT * FROM METADATADESC";
			//Query query = eManager.createNativeQuery(sql, MetaDataa.class);
			//List<MetaDataa> slu = query.getResultList();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<MetaDataa> query = builder.createQuery(MetaDataa.class);
		    Root<MetaDataa> variableRoot = query.from(MetaDataa.class);
		    query.select(variableRoot);
		    List<MetaDataa> slu = eManager.createQuery(query).getResultList();	
			ResponseMetaDataList metadataList = new ResponseMetaDataList();
			metadataList.setMetadatas(slu);
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), metadataList).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (PersistenceException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		}
		
	}


	@POST
	@Path("/deletemetadata")
	public Response deleteMetadata(String request) {
		MetaDataa metadataUse = new Gson().fromJson(request, MetaDataa.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  MetaDataa searchMetadata = eManager.find(MetaDataa.class, metadataUse.getContentid());
		    eManager.remove(searchMetadata);
			eManager.getTransaction().commit();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), null).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}

	@POST
	@Path("/findmetadata")
	public Response findMetadataById(String request) {
		MetaDataa metaUse = new Gson().fromJson(request, MetaDataa.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from METADATADESC where META_ID = :mid";
			//Query query = eManager.createNativeQuery(sql, MetaDataa.class);
			//query.setParameter("mid", metaUse.getContentid());
			//MetaDataa metdet = (MetaDataa) query.setMaxResults(1).getSingleResult();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<MetaDataa> criteriaQuery = criteriaBuilder.createQuery(MetaDataa.class);
			Root<MetaDataa> achieveRoot = criteriaQuery.from(MetaDataa.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("contentId"),   metaUse.getContentid()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			MetaDataa metdet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), metdet).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}
	@POST
	@Path("/findmenumetadata")
	public Response findMetadataByMenuId(String request) {
		MetaDataa metaUse = new Gson().fromJson(request, MetaDataa.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from METADATADESC where MENU_ID = :mid";
			//Query query = eManager.createNativeQuery(sql, MetaDataa.class);
			//query.setParameter("mid", metaUse.getMenuId());
			//MetaDataa metdet = (MetaDataa) query.setMaxResults(1).getSingleResult();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<MetaDataa> criteriaQuery = criteriaBuilder.createQuery(MetaDataa.class);
			Root<MetaDataa> achieveRoot = criteriaQuery.from(MetaDataa.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("menuId"),   metaUse.getMenuId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			MetaDataa metdet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), metdet).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}
}
