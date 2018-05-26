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
import com.ubc.pojo.UsersQuery;

@Path("/userqueries")
public class UserQueryController {

	@POST
	@Path("/createupdateuserquery")
	public Response createupdateUser(String request) {
		UsersQuery requestUser = new Gson().fromJson(request, UsersQuery.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestUser.getQueryid()!=null)
			{
		    eManager.getTransaction().begin();
		    UsersQuery searchUser = eManager.find(UsersQuery.class, requestUser.getQueryid());
			if(searchUser!=null) {
				if(requestUser.getFunction()!=null && requestUser.getFunction().length()>0) {
					searchUser.setFunction(requestUser.getFunction());
				}
				if(requestUser.getPersonName()!=null && requestUser.getPersonName().length()>0) {
					searchUser.setPersonName(requestUser.getPersonName());
				}
				if(requestUser.getEmailId()!=null && requestUser.getEmailId().length()>0) {
					searchUser.setEmailId(requestUser.getEmailId());
				}
				if(requestUser.getMessage()!=null && requestUser.getMessage().length()>0) {
					searchUser.setMessage(requestUser.getMessage());
				}
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchUser).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "User Query Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				eManager.persist(requestUser);
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestUser).toString())
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
	@Path("/usersqueriesdetails")
	public Response getAllUserQuery() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();
			//String sql = "SELECT * FROM PERSONSQUERY";
			//Query query = eManager.createNativeQuery(sql, UsersQuery.class);
			//List<UsersQuery> slu = query.getResultList();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<UsersQuery> query = builder.createQuery(UsersQuery.class);
		    Root<UsersQuery> variableRoot = query.from(UsersQuery.class);
		    query.select(variableRoot);
		    List<UsersQuery> slu = eManager.createQuery(query).getResultList();		
			ResponseUsersQueryList userList = new ResponseUsersQueryList();
			userList.setUserssquery(slu);
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), userList).toString())
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
	@Path("/deleteuserquery")
	public Response deleteUserQuery(String request) {
		UsersQuery requestUser = new Gson().fromJson(request, UsersQuery.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  UsersQuery searchUser = eManager.find(UsersQuery.class, requestUser.getQueryid());
		    eManager.remove(searchUser);
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
	@Path("/finduserquery")
	public Response findQueryById(String request) {
		UsersQuery userDet = new Gson().fromJson(request, UsersQuery.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from PERSONSQUERY where QUERYID = :qid";
			//Query query = eManager.createNativeQuery(sql, UsersQuery.class);
			//query.setParameter("qid", userDet.getQueryid());
			//UsersQuery usedet = (UsersQuery) query.setMaxResults(1).getSingleResult();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<UsersQuery> criteriaQuery = criteriaBuilder.createQuery(UsersQuery.class);
			Root<UsersQuery> achieveRoot = criteriaQuery.from(UsersQuery.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("queryId"), userDet.getQueryid()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			UsersQuery usedet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), usedet).toString())
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
	@Path("/finduserqueryemail")
	public Response findQueryByEmailId(String request) {
		UsersQuery userDet = new Gson().fromJson(request, UsersQuery.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from PERSONSQUERY where EMAIL_ID = :emid";
			//Query query = eManager.createNativeQuery(sql, UsersQuery.class);
			//query.setParameter("emid", userDet.getEmailId());
			//List<UsersQuery> slu = query.getResultList();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<UsersQuery> criteriaQuery = criteriaBuilder.createQuery(UsersQuery.class);
			Root<UsersQuery> achieveRoot = criteriaQuery.from(UsersQuery.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("emailId"), userDet.getEmailId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			List<UsersQuery> slu = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getResultList();
			ResponseUsersQueryList userList = new ResponseUsersQueryList();
			userList.setUserssquery(slu);
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), userList).toString())
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
