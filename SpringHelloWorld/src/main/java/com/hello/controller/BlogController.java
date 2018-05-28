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

import com.google.gson.Gson;
import com.ubc.pojo.BasicResponseObject;
import com.ubc.pojo.Blog;

@Path("/blogs")
public class BlogController {
	@POST
	@Path("/createupdateblog")
	public Response createupdateBlog(String request) {
		Blog requestBlog = new Gson().fromJson(request, Blog.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestBlog.getBlogId()!=null)
			{
		    eManager.getTransaction().begin();
		    Blog searchBlog = eManager.find(Blog.class, requestBlog.getBlogId());
			if(searchBlog!=null) {
				if(requestBlog.getBlogTitle()!=null && requestBlog.getBlogTitle().length()>0) {
					searchBlog.setBlogTitle(requestBlog.getBlogTitle());
				}
				if(requestBlog.getBlogTags()!=null && requestBlog.getBlogTags().length()>0) {
					searchBlog.setBlogTags(requestBlog.getBlogTags());
				}
				if(requestBlog.getBlogImage()!=null && requestBlog.getBlogImage().length()>0) {
					searchBlog.setBlogImage(requestBlog.getBlogImage());
				}
				if(requestBlog.getBlogDesc()!=null && requestBlog.getBlogDesc().length()>0) {
					searchBlog.setBlogDesc(requestBlog.getBlogDesc());
				}
				if(requestBlog.getBlogCategory()!=null && requestBlog.getBlogCategory().length()>0) {
					searchBlog.setBlogCategory(requestBlog.getBlogCategory());
				}
				if(requestBlog.getBlogDate()!=null) {
					searchBlog.setBlogDate(new Date());
				}
					eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchBlog).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Blog Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				requestBlog.setBlogDate(new Date());
				eManager.persist(requestBlog);
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestBlog).toString())
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
	@Path("/blogdetails")
	public Response getAllBlog() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();	
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<Blog> query = builder.createQuery(Blog.class);
		    Root<Blog> variableRoot = query.from(Blog.class);
		    query.select(variableRoot);
		    List<Blog> slu = eManager.createQuery(query).getResultList();		
			ResponseBlogList blogList = new ResponseBlogList();
			blogList.setBlogResult(slu);
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), blogList).toString())
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
	@Path("/deleteblog")
	public Response deleteBlog(String request) {
		Blog blogUse = new Gson().fromJson(request, Blog.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  Blog searchBlog = eManager.find(Blog.class, blogUse.getBlogId());
		    eManager.remove(searchBlog);
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
	@Path("/findblog")
	public Response findBlogById(String request) {
		Blog blogUse = new Gson().fromJson(request, Blog.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from ACHIEVEMENTS where ACHIEVE_ID = :aid";
			//Query query = eManager.createNativeQuery(sql, Achievement.class);
			//query.setParameter("aid", achieveUse.getAchieveId());
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Blog> criteriaQuery = criteriaBuilder.createQuery(Blog.class);
			Root<Blog> achieveRoot = criteriaQuery.from(Blog.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("blogId"), blogUse.getBlogId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			Blog achdet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			//Achievement achdet = (Achievement) query.setMaxResults(1).getSingleResult();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), achdet).toString())
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
