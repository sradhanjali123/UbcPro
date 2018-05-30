package com.hello.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.ubc.pojo.Comment;


@Path("/comments")
public class CommentController {
	public static Logger logger = LogManager.getLogger(CommentController.class);
	@POST
	@Path("/createupdatecomment")
	public Response createupdateComment(String request) {
		Comment requestComment = new Gson().fromJson(request, Comment.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestComment.getCommentId()!=null)
			{
		    eManager.getTransaction().begin();
		    Comment searchComment = eManager.find(Comment.class, requestComment.getCommentId());
			if(searchComment!=null) {
				if(requestComment.getBlogComment()!=null && requestComment.getBlogComment().length()>0) {
					searchComment.setBlogComment(requestComment.getBlogComment());
				}
				if(requestComment.getBlogId()!=null ) {
					searchComment.setBlogId(requestComment.getBlogId());
				}
				if(requestComment.getUserId()!=null ) {
					searchComment.setUserId(requestComment.getUserId());
				}
				
				
				if(requestComment.getCommentDate()!=null) {
					searchComment.setCommentDate(new Date());
				}
				logger.info("comment updated");
					eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchComment).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				logger.error("Response failed");
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Comment Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				requestComment.setCommentDate(new Date());
				eManager.persist(requestComment);
				eManager.getTransaction().commit();
				logger.info("comment created");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestComment).toString())
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
	@Path("/commentdetails")
	public Response getAllComment() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();	
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
		    Root<Comment> variableRoot = query.from(Comment.class);
		    query.select(variableRoot);
		    List<Comment> slu = eManager.createQuery(query).getResultList();		
			ResponseCommentList commentList = new ResponseCommentList();
			commentList.setCommentResult(slu);
			logger.info("comment created details");
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), commentList).toString())
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
	@Path("/deletecomment")
	public Response deleteComment(String request) {
		Comment commentUse = new Gson().fromJson(request, Comment.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  Comment searchComment = eManager.find(Comment.class, commentUse.getCommentId());
		    eManager.remove(searchComment);
			eManager.getTransaction().commit();
			logger.info("comment deleted");
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
	@Path("/findcomment")
	public Response findCommentById(String request) {
		Comment commentUse = new Gson().fromJson(request, Comment.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from ACHIEVEMENTS where ACHIEVE_ID = :aid";
			//Query query = eManager.createNativeQuery(sql, Achievement.class);
			//query.setParameter("aid", achieveUse.getAchieveId());
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
			Root<Comment> achieveRoot = criteriaQuery.from(Comment.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("commentId"), commentUse.getCommentId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			Comment achdet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			logger.info("comment found");
			//Achievement achdet = (Achievement) query.setMaxResults(1).getSingleResult();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), achdet).toString())
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
	@Path("/findcommentbyblogid")
	public Response findCommentByBlogId(String request) {
		Comment commentUse = new Gson().fromJson(request, Comment.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from EVENTDETAILS where EVENT_NAME = :edid";
			//Query query = eManager.createNativeQuery(sql, Event.class);
			//query.setParameter("edid", eventUse.getEventName());
			//List<Event> slu = query.getResultList();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
			Root<Comment> achieveRoot = criteriaQuery.from(Comment.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("blogId"), commentUse.getBlogId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			List<Comment> slu  = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getResultList();
			ResponseCommentList blogList = new ResponseCommentList();
			blogList.setCommentResult(slu);
			logger.info("found blog comment");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), blogList).toString())
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
	@Path("/findcommentbyuserid")
	public Response findCommentByUserId(String request) {
		Comment commentUse = new Gson().fromJson(request, Comment.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from EVENTDETAILS where EVENT_NAME = :edid";
			//Query query = eManager.createNativeQuery(sql, Event.class);
			//query.setParameter("edid", eventUse.getEventName());
			//List<Event> slu = query.getResultList();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
			Root<Comment> achieveRoot = criteriaQuery.from(Comment.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("userId"), commentUse.getUserId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			List<Comment> slu  = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getResultList();
			ResponseCommentList blogList = new ResponseCommentList();
			blogList.setCommentResult(slu);
			logger.info("found user comments");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), blogList).toString())
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
