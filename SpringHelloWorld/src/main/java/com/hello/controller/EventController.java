package com.hello.controller;
import io.sentry.*;
import java.util.ArrayList;

import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
import com.ubc.pojo.Event;

@Path("/events")
public class EventController {
	    @POST
		@Path("/createupdatevent")
		public Response createupdateEvent(String request) {
			Event requestEvent = new Gson().fromJson(request, Event.class);
			try {
				EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
	     		if(requestEvent.getEventId()!=null)
				{
			    eManager.getTransaction().begin();
			    Event searchEvent = eManager.find(Event.class, requestEvent.getEventId());
				if(searchEvent!=null) {
					if(requestEvent.getEventDesc()!=null && requestEvent.getEventDesc().length()>0) {
						searchEvent.setEventDesc(requestEvent.getEventDesc());
					}
					if(requestEvent.getEventImage()!=null && requestEvent.getEventImage().length()>0) {
						searchEvent.setEventImage(requestEvent.getEventImage());
					}
					if(requestEvent.getEventName()!=null && requestEvent.getEventName().length()>0) {
						searchEvent.setEventName(requestEvent.getEventName());
					}
					if(requestEvent.getEventVenue()!=null && requestEvent.getEventVenue().length()>0) {
						searchEvent.setEventVenue(requestEvent.getEventVenue());
					}
					if(requestEvent.getLikeCount()!=null) {
						searchEvent.setLikeCount(requestEvent.getLikeCount());
					}
					if(requestEvent.getEventPriority()!=null) {
						searchEvent.setEventPriority(requestEvent.getEventPriority());
					}
					if(requestEvent.getEventDate()!=null) {
						searchEvent.setEventDate(requestEvent.getEventDate());
					}
					
					eManager.getTransaction().commit();
					return Response.status(Status.OK.getStatusCode())
							.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchEvent).toString())
							.header("Content-Type", "application/json").build();	
				}else {
					return Response.status(Status.NOT_FOUND.getStatusCode())
							.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Banner Not Found").toString())
							.header("Content-Type", "application/json").build();				
				}
				
				}
				else {
					eManager.getTransaction().begin();
					eManager.persist(requestEvent);
					eManager.getTransaction().commit();
					return Response.status(Status.OK.getStatusCode())
							.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestEvent).toString())
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
		@Path("/eventsdetails")
		public Response getAllEvent() {
			try {
				EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
				eManager.getTransaction().begin();
				//String sql = "SELECT * FROM EVENTDETAILS";
				//Query query = eManager.createNativeQuery(sql, Event.class);
				//List<Event> slu = query.getResultList();
				CriteriaBuilder builder = eManager.getCriteriaBuilder();
			    CriteriaQuery<Event> query = builder.createQuery(Event.class);
			    Root<Event> variableRoot = query.from(Event.class);
			    query.select(variableRoot);
			    List<Event> slu = eManager.createQuery(query).getResultList();		
				ResponseEventList eventList = new ResponseEventList();
				eventList.setEventdatas(slu);
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), eventList).toString())
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
		@Path("/deletevent")
		public Response deleteEvent(String request) {
			Event eventUse = new Gson().fromJson(request, Event.class);
			try {
				EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			  eManager.getTransaction().begin();
			Event searchEvent = eManager.find(Event.class, eventUse.getEventId());
			    eManager.remove(searchEvent);
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
		@Path("/findevent")
		public Response findEventById(String request) {
			Event eventUse = new Gson().fromJson(request, Event.class);
			try {
				EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
				//String sql = "select * from EVENTDETAILS where EVENT_ID = :eid";
				//Query query = eManager.createNativeQuery(sql, Event.class);
				//query.setParameter("eid", eventUse.getEventId());
				//Event evndet = (Event) query.setMaxResults(1).getSingleResult();
				CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
				CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
				Root<Event> achieveRoot = criteriaQuery.from(Event.class);
				List<Predicate> conditions = new ArrayList<Predicate>();
				conditions.add(criteriaBuilder.equal(achieveRoot.get("eventId"), eventUse.getEventId()));
				criteriaQuery.where(conditions.toArray(new Predicate[] {}));
				Event evndet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
						.getSingleResult();
				return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), evndet).toString())
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
		@Path("/findeventdate")
		public Response findEventByDate(String request) {
			Event eventUse = new Gson().fromJson(request, Event.class);
			try {
				EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
				//String sql = "select * from EVENTDETAILS where EVENT_DATE = :edid";
				//Query query = eManager.createNativeQuery(sql, Event.class);
				//query.setParameter("edid", eventUse.getEventDate());
				//List<Event> slu = query.getResultList();
				CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
				CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
				Root<Event> achieveRoot = criteriaQuery.from(Event.class);
				List<Predicate> conditions = new ArrayList<Predicate>();
				conditions.add(criteriaBuilder.equal(achieveRoot.get("eventDate"), eventUse.getEventDate()));
				criteriaQuery.where(conditions.toArray(new Predicate[] {}));
				List<Event> slu  = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
						.getResultList();
				ResponseEventDateList eventList = new ResponseEventDateList();
				eventList.setEventdatedatas(slu);
				return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), eventList).toString())
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
		@Path("/findeventpriority")
		public Response findEventByPriority(String request) {
			Event eventUse = new Gson().fromJson(request, Event.class);
			try {
				EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
				//String sql = "select * from EVENTDETAILS where EVENT_PRIORITY = :epid";
				//Query query = eManager.createNativeQuery(sql, Event.class);
				//query.setParameter("epid", eventUse.getEventPriority());
				//Event evndet = (Event) query.setMaxResults(1).getSingleResult();
				CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
				CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
				Root<Event> achieveRoot = criteriaQuery.from(Event.class);
				List<Predicate> conditions = new ArrayList<Predicate>();
				conditions.add(criteriaBuilder.equal(achieveRoot.get("eventPriority"), eventUse.getEventPriority()));
				criteriaQuery.where(conditions.toArray(new Predicate[] {}));
				Event evndet  = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
						.getSingleResult();
				return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), evndet).toString())
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
		@GET
		@Path("/findupcomingevent")
		public Response findUpcomingEventByDate(String request) {
			//Event eventUse = new Gson().fromJson(request, Event.class);
			try {
				EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
				//String sql = "select * from EVENTDETAILS ORDER BY EVENT_DATE DESC";
				//Query query = eManager.createNativeQuery(sql, Event.class);
				//query.setParameter("edid", eventUse.getEventDate());
				CriteriaBuilder cb = eManager.getCriteriaBuilder();
				CriteriaQuery<Event> q = cb.createQuery(Event.class);
				Root<Event> b = q.from(Event.class);
				q.select(b).orderBy(cb.desc(b.get("eventDate")));
				List<Event> slu = eManager.createQuery(q).getResultList();
				ResponseEventDateList eventList = new ResponseEventDateList();
				eventList.setEventdatedatas(slu);
				return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), eventList).toString())
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
		@Path("/findeventbyname")
		public Response findEventByName(String request) {
			Event eventUse = new Gson().fromJson(request, Event.class);
			try {
				EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
				//String sql = "select * from EVENTDETAILS where EVENT_NAME = :edid";
				//Query query = eManager.createNativeQuery(sql, Event.class);
				//query.setParameter("edid", eventUse.getEventName());
				//List<Event> slu = query.getResultList();
				CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
				CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
				Root<Event> achieveRoot = criteriaQuery.from(Event.class);
				List<Predicate> conditions = new ArrayList<Predicate>();
				conditions.add(criteriaBuilder.equal(achieveRoot.get("eventName"), eventUse.getEventName()));
				criteriaQuery.where(conditions.toArray(new Predicate[] {}));
				List<Event> slu  = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
						.getResultList();
				ResponseEventDateList eventList = new ResponseEventDateList();
				eventList.setEventdatedatas(slu);
				return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), eventList).toString())
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
		@Path("/findeventlikescount")
		public Response findEventCount(String request) {
			Event eventUse = new Gson().fromJson(request, Event.class);
			try {
				EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
				//String sql = "select EVENT_ID from USEREVENTDETAILS where EVENT_ID = :edid";
				//Query query = eManager.createNativeQuery(sql);
				//query.setParameter("edid", eventUse.getEventId());
				CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
				CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
				Root<Event> achieveRoot = criteriaQuery.from(Event.class);
				List<Predicate> conditions = new ArrayList<Predicate>();
				conditions.add(criteriaBuilder.equal(achieveRoot.get("eventId"), eventUse.getEventId()));
				criteriaQuery.where(conditions.toArray(new Predicate[] {}));
				Long slu  = (long) eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
						.getResultList().size();
				//Long slu = (long) query.getResultList().size();
				eventUse.setLikeCount(slu);
				return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), eventUse).toString())
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
