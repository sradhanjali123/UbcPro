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
//import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.ubc.pojo.BasicResponseObject;
import com.ubc.pojo.SocialLoginUsers;

@Path("/socialusers")
public class UserController {
	// Create a service named: createOrUpdateUser
	
	@POST
	@Path("/createupdateuser")
	public Response createupdateUser(String request) {
		SocialLoginUsers requestUser = new Gson().fromJson(request, SocialLoginUsers.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestUser.getUserid()!=null )
			{
		    eManager.getTransaction().begin();
			SocialLoginUsers searchUser = eManager.find(SocialLoginUsers.class, requestUser.getUserid());
			System.out.print(requestUser.getUserid());
			if(searchUser!=null) {
				if(requestUser.getPhoneno()!=null && requestUser.getPhoneno().length()>0){
					searchUser.setPhoneno(requestUser.getPhoneno());
				}
				else {
					return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "User phone no already  exists").toString())
						.header("Content-Type", "application/json").build();			
				}
				if(requestUser.getUserName()!=null && requestUser.getUserName().length()>0) {
					searchUser.setUserName(requestUser.getUserName());
				}
				else {
					return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "User name already  exists").toString())
						.header("Content-Type", "application/json").build();			
				}
				if(requestUser.getEmailId()!=null && requestUser.getEmailId().length()>0) {
					searchUser.setEmailId(requestUser.getEmailId());
				}
				else {
					return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "User email  already  exists").toString())
						.header("Content-Type", "application/json").build();			
				}
				if(requestUser.getTokenid()!=null && requestUser.getTokenid().length()>0) {
					searchUser.setTokenid(requestUser.getTokenid());
				}
				else {
					return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "User login id already  exists").toString())
						.header("Content-Type", "application/json").build();			
				}
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchUser).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "User Not Found").toString())
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
	@Path("/usersdetails")
	public Response getAllUser() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//long userId = new Random().nextLong();
			// requestUser.setUserId(userId);
			eManager.getTransaction().begin();
			//String sql = "SELECT * FROM SOCIALUSERS";
			//Query query = eManager.createNativeQuery(sql, SocialLoginUsers.class);
			//List<SocialLoginUsers> slu = query.getResultList();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<SocialLoginUsers> query = builder.createQuery(SocialLoginUsers.class);
		    Root<SocialLoginUsers> variableRoot = query.from(SocialLoginUsers.class);
		    query.select(variableRoot);
		    List<SocialLoginUsers> slu = eManager.createQuery(query).getResultList();	
			ResponseUsersList userList = new ResponseUsersList();
			userList.setUsers(slu);
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
	@Path("/deleteuser")
	public Response deleteUser(String request) {
		SocialLoginUsers requestUser = new Gson().fromJson(request, SocialLoginUsers.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		SocialLoginUsers searchUser = eManager.find(SocialLoginUsers.class, requestUser.getUserid());
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
	@Path("/finduser")
	public Response findUserById(String request) {
		SocialLoginUsers userDet = new Gson().fromJson(request, SocialLoginUsers.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from SOCIALUSERS where USER_ID = :uid";
			//Query query = eManager.createNativeQuery(sql, SocialLoginUsers.class);
			//query.setParameter("uid", userDet.getUserid());
			//SocialLoginUsers usedet = (SocialLoginUsers) query.setMaxResults(1).getSingleResult();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<SocialLoginUsers> criteriaQuery = criteriaBuilder.createQuery(SocialLoginUsers.class);
			Root<SocialLoginUsers> achieveRoot = criteriaQuery.from(SocialLoginUsers.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("userId"),   userDet.getUserid()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			SocialLoginUsers usedet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
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


}
