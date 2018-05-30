package com.hello.controller;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
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
import com.ubc.pojo.BasicResponseObject;
import com.ubc.pojo.EventCategory;

@Path("/eventcategory")
public class EventCategoryController {
	public static Logger logger = LogManager.getLogger(EventCategoryController.class);
    @POST
	@Path("/createupdateventcategory")
    public Response createupdateEventCategory(String request) {
    	EventCategory requestEvent = new Gson().fromJson(request, EventCategory.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestEvent.getEventCategoryId()!=null)
			{
		    eManager.getTransaction().begin();
		    EventCategory searchEvent = eManager.find(EventCategory.class, requestEvent.getEventCategoryId());
			if(searchEvent!=null) {
				if(requestEvent.getEventCategory()!=null && requestEvent.getEventCategory().length()>0) {
					searchEvent.setEventCategory(requestEvent.getEventCategory());
				}
				
				logger.info("event category updated");
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchEvent).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				logger.error("Response sending Failed");
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Banner Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				eManager.persist(requestEvent);
				eManager.getTransaction().commit();
				logger.info("event category created");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestEvent).toString())
						.header("Content-Type", "application/json").build();
			}
		} catch (NamingException ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (PersistenceException ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (Exception ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		}
	}

    
    @GET
	@Path("/eventscategorydetails")
	public Response getAllEventCategory() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<EventCategory> query = builder.createQuery(EventCategory.class);
		    Root<EventCategory> variableRoot = query.from(EventCategory.class);
		    query.select(variableRoot);
		    List<EventCategory> slu = eManager.createQuery(query).getResultList();		
		    ResponseEventCategoryList eventList = new ResponseEventCategoryList();
			eventList.setEventdatas(slu);
			logger.info("events  category details");
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), eventList).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (PersistenceException ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (Exception ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		}
		
	}
    @POST
	@Path("/deleteventcategory")
	public Response deleteEventCategory(String request) {
    	EventCategory eventUse = new Gson().fromJson(request, EventCategory.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  EventCategory searchEvent = eManager.find(EventCategory.class, eventUse.getEventCategoryId());
		    eManager.remove(searchEvent);
			eManager.getTransaction().commit();
			logger.info("event category deleted");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), null).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		logger.error("Response sending Failed");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}
	@POST
	@Path("/findeventcategory")
	public Response findEventByCategoryId(String request) {
		EventCategory eventUse = new Gson().fromJson(request, EventCategory.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from EVENTDETAILS where EVENT_ID = :eid";
			//Query query = eManager.createNativeQuery(sql, Event.class);
			//query.setParameter("eid", eventUse.getEventId());
			//Event evndet = (Event) query.setMaxResults(1).getSingleResult();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<EventCategory> criteriaQuery = criteriaBuilder.createQuery(EventCategory.class);
			Root<EventCategory> achieveRoot = criteriaQuery.from(EventCategory.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("eventCategoryId"), eventUse.getEventCategoryId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			EventCategory evndet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			logger.info(" event category found");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), evndet).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			logger.error("Response sending Failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		logger.error("Response sending Failed");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}

}
