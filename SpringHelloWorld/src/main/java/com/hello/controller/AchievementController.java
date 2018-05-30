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

import com.ubc.pojo.Achievement;
import com.ubc.pojo.BasicResponseObject;


@Path("/achievements")
public class AchievementController {
	public static Logger logger = LogManager.getLogger(AchievementController.class);
	@POST
	@Path("/createupdateachievement")
	public Response createupdateAchieve(String request) {
		Achievement requestAchieve = new Gson().fromJson(request, Achievement.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestAchieve.getAchieveId()!=null)
			{
		    eManager.getTransaction().begin();
		    Achievement searchAchieve = eManager.find(Achievement.class, requestAchieve.getAchieveId());
			if(searchAchieve!=null) {
				if(requestAchieve.getAchieveText()!=null && requestAchieve.getAchieveText().length()>0) {
					searchAchieve.setAchieveText(requestAchieve.getAchieveText());
				}
				if(requestAchieve.getAchieveTitle()!=null && requestAchieve.getAchieveTitle().length()>0) {
					searchAchieve.setAchieveTitle(requestAchieve.getAchieveTitle());
				}
				if(requestAchieve.getAchieveValue()!=null) {
					searchAchieve.setAchieveValue(requestAchieve.getAchieveValue());
				}
				eManager.getTransaction().commit();
				logger.info("achievement updated");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchAchieve).toString())
						.header("Content-Type", "application/json").build();
				
			
			}else {
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Achievement Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				eManager.persist(requestAchieve);
				eManager.getTransaction().commit();
				logger.info("achievement created");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestAchieve).toString())
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
	@Path("/achievementdetails")
	public Response getAllAchieve() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();
			//String sql = "SELECT * FROM ACHIEVEMENTS";
			//Query query = eManager.createNativeQuery(sql, Achievement.class);
			//List<Achievement> slu = query.getResultList();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<Achievement> query = builder.createQuery(Achievement.class);
		    Root<Achievement> variableRoot = query.from(Achievement.class);
		    query.select(variableRoot);
		    List<Achievement> slu = eManager.createQuery(query).getResultList();		
			ResponseAchievementList achieveList = new ResponseAchievementList();
			achieveList.setAchieResult(slu);
			logger.info("achievement details");
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), achieveList).toString())
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
	@Path("/deleteachievement")
	public Response deleteAchieve(String request) {
		Achievement achieveUse = new Gson().fromJson(request, Achievement.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  Achievement searchAchieve = eManager.find(Achievement.class, achieveUse.getAchieveId());
		    eManager.remove(searchAchieve);
			eManager.getTransaction().commit();
			logger.info("achievement deleted");
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
	@Path("/findachieve")
	public Response findAchieveById(String request) {
		Achievement achieveUse = new Gson().fromJson(request, Achievement.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from ACHIEVEMENTS where ACHIEVE_ID = :aid";
			//Query query = eManager.createNativeQuery(sql, Achievement.class);
			//query.setParameter("aid", achieveUse.getAchieveId());
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Achievement> criteriaQuery = criteriaBuilder.createQuery(Achievement.class);
			Root<Achievement> achieveRoot = criteriaQuery.from(Achievement.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("achieveId"), achieveUse.getAchieveId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			Achievement achdet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			//Achievement achdet = (Achievement) query.setMaxResults(1).getSingleResult();
			logger.info("achievement found");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), achdet).toString())
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
