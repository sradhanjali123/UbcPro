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
import com.ubc.pojo.Gallery;

@Path("/gallery")
public class GalleryController {
	@POST
	@Path("/createupdategallery")
	public Response createupdateGallery(String request) {
		Gallery requestGallery = new Gson().fromJson(request, Gallery.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestGallery.getGalleryId()!=null)
			{
		    eManager.getTransaction().begin();
		    Gallery searchGallery = eManager.find(Gallery.class, requestGallery.getGalleryId());
			if(searchGallery!=null) {
				if(requestGallery.getImageDesc()!=null && requestGallery.getImageDesc().length()>0) {
					searchGallery.setImageDesc(requestGallery.getImageDesc());
				}
				if(requestGallery.getImageTitle()!=null && requestGallery.getImageTitle().length()>0) {
					searchGallery.setImageTitle(requestGallery.getImageTitle());
				}
				if(requestGallery.getImagePriority()!=null && requestGallery.getImagePriority().length()>0) {
					searchGallery.setImagePriority(requestGallery.getImagePriority());
				}
				if(requestGallery.getImageUri()!=null && requestGallery.getImageUri().length()>0 ) {
					searchGallery.setImageUri(requestGallery.getImageUri());
				}
				
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchGallery).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Banner Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				eManager.persist(requestGallery);
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestGallery).toString())
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
	@Path("/gallerydetails")
	public Response getAllGallery() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();
			//String sql = "SELECT * FROM GALLERIA";
			//Query query = eManager.createNativeQuery(sql, Gallery.class);
			//List<Gallery> slu = query.getResultList();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<Gallery> query = builder.createQuery(Gallery.class);
		    Root<Gallery> variableRoot = query.from(Gallery.class);
		    query.select(variableRoot);
		    List<Gallery> slu = eManager.createQuery(query).getResultList();		
			ResponseGalleryList galleryList = new ResponseGalleryList();
			galleryList.setGalleryData(slu);
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), galleryList).toString())
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
	@Path("/deletegallery")
	public Response deleteGallery(String request) {
		Gallery galleryUse = new Gson().fromJson(request, Gallery.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  Gallery searchGallery = eManager.find(Gallery.class, galleryUse.getGalleryId());
		    eManager.remove(searchGallery);
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
	@Path("/findgallery")
	public Response findBannerById(String request) {
		Gallery galleryUse = new Gson().fromJson(request, Gallery.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from GALLERIA where GALLERY_ID = :gid";
			//Query query = eManager.createNativeQuery(sql, Gallery.class);
			//query.setParameter("gid", galleryUse.getGalleryId());
			//Gallery bandet = (Gallery) query.setMaxResults(1).getSingleResult();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Gallery> criteriaQuery = criteriaBuilder.createQuery(Gallery.class);
			Root<Gallery> achieveRoot = criteriaQuery.from(Gallery.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("galleryId"),  galleryUse.getGalleryId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			Gallery bandet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), bandet).toString())
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
