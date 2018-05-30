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
import com.ubc.pojo.BasicResponseObject;
import com.ubc.pojo.UserEvent;

@Path("/userevents")
public class UserEventController {
	public static Logger logger = LogManager.getLogger(UserEventController.class);
	
	@POST
	@Path("/createupdatuserevent")
	public Response createupdateUserEvent(String request) {
		UserEvent requestuserEvent = new Gson().fromJson(request, UserEvent.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestuserEvent.getUserEventId()!=null)
			{
		    eManager.getTransaction().begin();
		    UserEvent searchuserEvent = eManager.find(UserEvent.class, requestuserEvent.getUserEventId());
			if(searchuserEvent!=null) {
//				if(requestuserEvent.getEventName()!=null && requestuserEvent.getEventName().length()>0) {
//					searchuserEvent.setEventName(requestuserEvent.getEventName());
//				}
//				if(requestuserEvent.getLike()!=null && requestuserEvent.getLike().length()>0) {
//					searchuserEvent.setLike(requestuserEvent.getLike());
//				}
//				if(requestuserEvent.getEventName()!=null && requestuserEvent.getEventName().length()>0) {
//					searchuserEvent.setEventName(requestuserEvent.getEventName());
//				}
				if(requestuserEvent.getUserid()!=null) {
					searchuserEvent.setUserid(requestuserEvent.getUserid());
				}
				if(requestuserEvent.getLikeStatus()!=null && requestuserEvent.getLikeStatus().length()>0) {
					searchuserEvent.setLikeStatus(requestuserEvent.getLikeStatus());
				}
				if(requestuserEvent.getEventId()!=null) {
					searchuserEvent.setEventId(requestuserEvent.getEventId());
				}
				
				eManager.getTransaction().commit();
				logger.info("User event updated");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchuserEvent).toString())
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
				eManager.persist(requestuserEvent);
				eManager.getTransaction().commit();
				logger.info("User event created");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestuserEvent).toString())
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
	@Path("/usereventsdetails")
	public Response getAllUserEvent() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();
			//String sql = "SELECT * FROM USEREVENTDETAILS";
			//Query query = eManager.createNativeQuery(sql, UserEvent.class);
			//List<UserEvent> slu = query.getResultList();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<UserEvent> query = builder.createQuery(UserEvent.class);
		    Root<UserEvent> variableRoot = query.from(UserEvent.class);
		    query.select(variableRoot);
		    List<UserEvent> slu = eManager.createQuery(query).getResultList();	
			ResponseUserEventList eventList = new ResponseUserEventList();
			eventList.setUsereventdatas(slu);
			logger.info("User event details");
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
	@Path("/deleteuserevent")
	public Response deleteUserEvent(String request) {
		UserEvent eventUse = new Gson().fromJson(request, UserEvent.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  UserEvent searchEvent = eManager.find(UserEvent.class, eventUse.getUserEventId());
		    eManager.remove(searchEvent);
			eManager.getTransaction().commit();
			logger.info("User event deleted");
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
	@Path("/findusereventbyusereventid")
	public Response findUserEventByUserEventId(String request) {
		UserEvent eventUse = new Gson().fromJson(request, UserEvent.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from USEREVENTDETAILS where USEREVENT_ID = :eid";
			//Query query = eManager.createNativeQuery(sql, UserEvent.class);
			//query.setParameter("eid", eventUse.getUserEventId());
			//UserEvent evndet = (UserEvent) query.setMaxResults(1).getSingleResult();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<UserEvent> criteriaQuery = criteriaBuilder.createQuery(UserEvent.class);
			Root<UserEvent> achieveRoot = criteriaQuery.from(UserEvent.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("userEventId"),   eventUse.getUserEventId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			UserEvent usedet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			logger.info("User event found");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), usedet).toString())
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
	@Path("/finduserdetaileventsbyuserid")
	public Response findUserEventByUserId(String request) {
		UserEvent eventUse = new Gson().fromJson(request, UserEvent.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from USEREVENTDETAILS where USER_ID = :ueid";
			//Query query = eManager.createNativeQuery(sql, UserEvent.class);
			//query.setParameter("ueid", eventUse.getUserid());
			//List<UserEvent> slu = query.getResultList();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<UserEvent> criteriaQuery = criteriaBuilder.createQuery(UserEvent.class);
			Root<UserEvent> achieveRoot = criteriaQuery.from(UserEvent.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("userId"),   eventUse.getUserid()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			List<UserEvent> slu= eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getResultList();
			ResponseSingleUserEventList usereventList = new ResponseSingleUserEventList();
			usereventList.setSingleuserEventDatas(slu);
			logger.info("User event found");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), usereventList).toString())
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
	@Path("/userlikestatus")
	public Response verifyLikeStatus(String request) throws NamingException {
		UserEvent requestUser = new Gson().fromJson(request, UserEvent.class);
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
	 		if(requestUser.getUserid()!=null)
			{
		    eManager.getTransaction().begin();
		   // String sql = "insert into USEREVENTDETAILS values(?,?,?)";
			//Query query = eManager.createNativeQuery(sql, UserEvent.class);
			//query.setParameter("ulid", requestUser.getUserid());
			//query.setParameter("eid", requestUser.getEventId());
			//UserEvent evndet = (UserEvent) query.setMaxResults(1);
//			if(evndet!=null) {
//				if(requestUser.getUserid()!=null && requestUser.getEventId()!=null) {
//					evndet.setLikeStatus("1");		
//				} 
//				else { evndet.setLikeStatus("0");}
				eManager.persist(requestUser);
				eManager.getTransaction().commit();
				logger.info("User event like");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestUser).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				logger.error("Response sending Failed");
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "event ids Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
	}
			
			
			
		//else {
//				return Response.status(Status.OK.getStatusCode())
//						.entity(new BasicResponseObject(Status.OK.getStatusCode(), "user Not Generated").toString())
//						.header("Content-Type", "application/json").build();
//			}
//		} catch (NamingException ex) {
//			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
//					.header("Content-Type", "application/json").build();
//		} catch (PersistenceException ex) {
//			ex.printStackTrace();
//			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
//					.header("Content-Type", "application/json").build();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
//					.header("Content-Type", "application/json").build();
//		}
	
//	@POST
//	@Path("/findusereventbyname")
//	public Response findUserEventByName(String request) {
//		UserEvent eventUse = new Gson().fromJson(request, UserEvent.class);
//		try {
//			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
//			//String sql = "select * from USEREVENTDETAILS where EVENT_NAME = :unid";
//			//Query query = eManager.createNativeQuery(sql, UserEvent.class);
//			//query.setParameter("unid", eventUse.getEventName());
//			//List<UserEvent> slu = query.getResultList();
//			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
//			CriteriaQuery<UserEvent> criteriaQuery = criteriaBuilder.createQuery(UserEvent.class);
//			Root<UserEvent> achieveRoot = criteriaQuery.from(UserEvent.class);
//			List<Predicate> conditions = new ArrayList<Predicate>();
//			conditions.add(criteriaBuilder.equal(achieveRoot.get("eventName"),   eventUse.getEventName()));
//			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
//			List<UserEvent> slu= eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
//					.getResultList();
//			ResponseSingleUserEventList usereventList = new ResponseSingleUserEventList();
//			usereventList.setSingleuserEventDatas(slu);
//			return Response.status(Status.OK.getStatusCode())
//				.entity(new BasicResponseObject(Status.OK.getStatusCode(), usereventList).toString())
//					.header("Content-Type", "application/json").build();
//		} catch (NamingException ex) {
//			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
//					.header("Content-Type", "application/json").build();	} 
//		catch (PersistenceException ex) {
//			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
//					.header("Content-Type", "application/json").build();
//	} catch (Exception ex) {
//		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
//				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
//				.header("Content-Type", "application/json").build();
//	}
//	
//	}
	@POST
	@Path("/finduserdetaileventsbyeventid")
	public Response findUserEventByEventId(String request) {
		UserEvent eventUse = new Gson().fromJson(request, UserEvent.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from USEREVENTDETAILS where EVENT_ID = :ueid";
			//Query query = eManager.createNativeQuery(sql, UserEvent.class);
			//query.setParameter("ueid", eventUse.getEventId());
			//List<UserEvent> slu = query.getResultList();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<UserEvent> criteriaQuery = criteriaBuilder.createQuery(UserEvent.class);
			Root<UserEvent> achieveRoot = criteriaQuery.from(UserEvent.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("eventId"),   eventUse.getEventId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			List<UserEvent> slu= eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getResultList();
			ResponseSingleUserEventList usereventList = new ResponseSingleUserEventList();
			usereventList.setSingleuserEventDatas(slu);
			logger.info("User event details found");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), usereventList).toString())
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
