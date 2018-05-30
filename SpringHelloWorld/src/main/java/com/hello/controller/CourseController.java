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
import com.ubc.pojo.Course;


@Path("/course")
public class CourseController {
	public static Logger logger = LogManager.getLogger(CourseController.class);
	@POST
	@Path("/createupdatecourse")
	public Response createupdateCourse(String request) {
		Course requestCourse = new Gson().fromJson(request, Course.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestCourse.getCourseId()!=null)
			{
		    eManager.getTransaction().begin();
		    Course searchCourse = eManager.find(Course.class, requestCourse.getCourseId());
			if(searchCourse!=null) {
				if(requestCourse.getCourseDate()!=null && requestCourse.getCourseDate().length()>0) {
					requestCourse.setCourseDate(searchCourse.getCourseDate());
				}
				if(requestCourse.getCourseDesc()!=null && requestCourse.getCourseDesc().length()>0) {
					requestCourse.setCourseDesc(searchCourse.getCourseDesc());
				}
				if(requestCourse.getCourseImage()!=null && requestCourse.getCourseImage().length()>0) {
					requestCourse.setCourseImage(searchCourse.getCourseImage());
				}
				if(requestCourse.getCourseName()!=null && requestCourse.getCourseName().length()>0) {
					requestCourse.setCourseName(searchCourse.getCourseName());
				}
				if(requestCourse.getCoursePrice()!=null && requestCourse.getCoursePrice().length()>0) {
					requestCourse.setCoursePrice(searchCourse.getCoursePrice());
				}	
				eManager.getTransaction().commit();
				logger.info("course updated");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchCourse).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				logger.info("Response failed");
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Banner Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				eManager.persist(requestCourse);
				eManager.getTransaction().commit();
				logger.info("course created");
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestCourse).toString())
						.header("Content-Type", "application/json").build();
			}
		} catch (NamingException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (PersistenceException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (Exception ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		}
	}

	@GET
	@Path("/coursedetails")
	public Response getAllCourse() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();
			//String sql = "SELECT * FROM COURSEPOINT";
			//Query query = eManager.createNativeQuery(sql, Course.class);
			//List<Course> slu = query.getResultList();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<Course> query = builder.createQuery(Course.class);
		    Root<Course> variableRoot = query.from(Course.class);
		    query.select(variableRoot);
		    List<Course> slu = eManager.createQuery(query).getResultList();	
			ResponseCourseList courseList = new ResponseCourseList();
			courseList.setCourseData(slu);
			logger.info(" course details");
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), courseList).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (PersistenceException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		} catch (Exception ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
		}
		
	}


	@POST
	@Path("/deletecourse")
	public Response deleteCourse(String request) {
		Course courseUse = new Gson().fromJson(request, Course.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  Course searchCourse = eManager.find(Course.class, courseUse.getCourseId());
		    eManager.remove(searchCourse);
			eManager.getTransaction().commit();
			logger.info("course deleted");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), null).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		logger.error("Response failed");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}
	@POST
	@Path("/findcoursebyid")
	public Response findCourseById(String request) {
		Course courseUse = new Gson().fromJson(request, Course.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from COURSEPOINT where COURSE_ID = :cid";
			//Query query = eManager.createNativeQuery(sql, Course.class);
			//query.setParameter("cid", courseUse.getCourseId());
			//Course bandet = (Course) query.setMaxResults(1).getSingleResult();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
			Root<Course> achieveRoot = criteriaQuery.from(Course.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("courseId"), courseUse.getCourseId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			Course bandet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			logger.info(" course found ");
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), bandet).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		logger.error("Response failed");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}
	@POST
	@Path("/findcoursebyname")
	public Response findCourseByName(String request) {
		Course courseUse = new Gson().fromJson(request, Course.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from COURSEPOINT where COURSE_NAME = :cid";
			//Query query = eManager.createNativeQuery(sql, Course.class);
			//query.setParameter("cid", courseUse.getCourseName());
			//List<Course> slu = query.getResultList();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
			Root<Course> achieveRoot = criteriaQuery.from(Course.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("courseName"), courseUse.getCourseName()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			List<Course> slu = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getResultList();
			ResponseCourseList courseList = new ResponseCourseList();
			courseList.setCourseData(slu);
			logger.info(" course found ");
			//Course bandet = (Course) query.setMaxResults(1).getSingleResult();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), courseList).toString())
					.header("Content-Type", "application/json").build();
		} catch (NamingException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();	} 
		catch (PersistenceException ex) {
			logger.error("Response failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
					.header("Content-Type", "application/json").build();
	} catch (Exception ex) {
		logger.error("Response failed");
		return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(new BasicResponseObject(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage()).toString())
				.header("Content-Type", "application/json").build();
	}
	
	}


}
