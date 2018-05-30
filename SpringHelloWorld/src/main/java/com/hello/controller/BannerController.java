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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.ubc.pojo.Banner;
import com.ubc.pojo.BasicResponseObject;



@Path("/banners")
public class BannerController {
	public static Logger logger = LogManager.getLogger(BannerController.class);
	@POST
	@Path("/createupdatebanner")
	public Response createupdateBanner(String request) {
		Banner requestBanner = new Gson().fromJson(request, Banner.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestBanner.getBannerId()!=null)
			{
		    eManager.getTransaction().begin();
		    Banner searchBanner = eManager.find(Banner.class, requestBanner.getBannerId());
			if(searchBanner!=null) {
				if(requestBanner.getBannerImagePath()!=null && requestBanner.getBannerImagePath().length()>0) {
					searchBanner.setBannerImagePath(requestBanner.getBannerImagePath());
				}
				if(requestBanner.getBannerText()!=null && requestBanner.getBannerText().length()>0) {
					searchBanner.setBannerText(requestBanner.getBannerText());
				}
				if(requestBanner.getVideoPath()!=null && requestBanner.getVideoPath().length()>0) {
					searchBanner.setVideoPath(requestBanner.getVideoPath());
				}
				if(requestBanner.getBannerPriority()!=null ) {
					searchBanner.setBannerPriority(requestBanner.getBannerPriority());
				}
				
				eManager.getTransaction().commit();
				logger.info("Banner updated");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchBanner).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Banner Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				eManager.persist(requestBanner);
				eManager.getTransaction().commit();
				logger.info("Banner created");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestBanner).toString())
						.header("Content-Type", "application/json").build();
			}
		} catch (NamingException ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (PersistenceException ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (Exception ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		}
	}

	@GET
	@Path("/bannersdetails")
	public Response getAllBanner() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//long userId = new Random().nextLong();
			// requestUser.setUserId(userId);
			eManager.getTransaction().begin();
			//String sql = "SELECT * FROM BANNERDETAILS";
			//Query query = eManager.createNativeQuery(sql, Banner.class);
			//List<Banner> slu = query.getResultList();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<Banner> query = builder.createQuery(Banner.class);
		    Root<Banner> variableRoot = query.from(Banner.class);
		    query.select(variableRoot);
		    List<Banner> slu = eManager.createQuery(query).getResultList();		
			ResponseBannerList bannerList = new ResponseBannerList();
			bannerList.setBannerrs(slu);
			logger.info("Banner details");
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), bannerList).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (PersistenceException ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (Exception ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		}
		
	}


	@POST
	@Path("/deletebanner")
	public Response deleteBanner(String request) {
		Banner bannerUse = new Gson().fromJson(request, Banner.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		Banner searchBanner = eManager.find(Banner.class, bannerUse.getBannerId());
		    eManager.remove(searchBanner);
			eManager.getTransaction().commit();
			logger.info("Banner deleted");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), null).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		logger.error("Response sending failed!");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}
	@POST
	@Path("/findbanner")
	public Response findBannerById(String request) {
		Banner bannerUse = new Gson().fromJson(request, Banner.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from BANNERDETAILS where BANNER_ID = :bid";
			//Query query = eManager.createNativeQuery(sql, Banner.class);
			//query.setParameter("bid", bannerUse.getBannerId());
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Banner> criteriaQuery = criteriaBuilder.createQuery(Banner.class);
			Root<Banner> achieveRoot = criteriaQuery.from(Banner.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("bannerId"), bannerUse.getBannerId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			Banner bandet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			logger.info("Banner found");
			//Banner bandet = (Banner) query.setMaxResults(1).getSingleResult();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), bandet).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			logger.error("Response sending failed!");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		logger.error("Response sending failed!");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}

}
