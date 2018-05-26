package com.hello.controller;
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
import com.ubc.pojo.Menu;
import io.sentry.*;
@Path("/menu")
public class MenuController {
	@POST
	@Path("/createupdatemenu")
	public Response createupdateMenu(String request) {
		Menu requestMenu = new Gson().fromJson(request, Menu.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
     		if(requestMenu.getMenuId()!=null)
			{
		    eManager.getTransaction().begin();
		    Menu searchMenu = eManager.find(Menu.class, requestMenu.getMenuId());
			if(searchMenu!=null) {
				if(requestMenu.getMenuName()!=null && requestMenu.getMenuName().length()>0) {
					searchMenu.setMenuName(requestMenu.getMenuName());
				}
				if(requestMenu.getMenuOrder()!=null) {
					searchMenu.setMenuOrder(requestMenu.getMenuOrder());
				}
				
				
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), searchMenu).toString())
						.header("Content-Type", "application/json").build();	
			}else {
				return Response.status(Status.NOT_FOUND.getStatusCode())
						.entity(new BasicResponseObject(Status.NOT_FOUND.getStatusCode(), "Banner Not Found").toString())
						.header("Content-Type", "application/json").build();				
			}
			
			}
			else {
				eManager.getTransaction().begin();
				eManager.persist(requestMenu);
				eManager.getTransaction().commit();
				return Response.status(Status.OK.getStatusCode())
						.entity(new BasicResponseObject(Status.OK.getStatusCode(), requestMenu).toString())
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
	@Path("/menudetails")
	public Response getAllMenu() {
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			eManager.getTransaction().begin();
			//String sql = "SELECT * FROM MENUCONTENT";
			//Query query = eManager.createNativeQuery(sql, Menu.class);
			//List<Menu> slu = query.getResultList();
			CriteriaBuilder builder = eManager.getCriteriaBuilder();
		    CriteriaQuery<Menu> query = builder.createQuery(Menu.class);
		    Root<Menu> variableRoot = query.from(Menu.class);
		    query.select(variableRoot);
		    List<Menu> slu = eManager.createQuery(query).getResultList();	
			ResponseMenuList menuList = new ResponseMenuList();
			menuList.setMenuData(slu);
			return Response.status(Status.OK.getStatusCode())
					.entity(new BasicResponseObject(Status.OK.getStatusCode(), menuList).toString())
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
	@Path("/deletemenu")
	public Response deleteMenu(String request) {
		Menu menuUse = new Gson().fromJson(request, Menu.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
		  eManager.getTransaction().begin();
		  Menu searchMenu = eManager.find(Menu.class, menuUse.getMenuId());
		    eManager.remove(searchMenu);
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
	@Path("/findmenu")
	public Response findMenuById(String request) {
		Menu menuUse = new Gson().fromJson(request, Menu.class);
		try {
			EntityManager eManager = ResourceBase.INSTANCE.getEntityManager();
			//String sql = "select * from MENUCONTENT where MENU_ID = :gid";
			//Query query = eManager.createNativeQuery(sql, Menu.class);
			//query.setParameter("gid", menuUse.getMenuId());
			//Menu bandet = (Menu) query.setMaxResults(1).getSingleResult();
			CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
			CriteriaQuery<Menu> criteriaQuery = criteriaBuilder.createQuery(Menu.class);
			Root<Menu> achieveRoot = criteriaQuery.from(Menu.class);
			List<Predicate> conditions = new ArrayList<Predicate>();
			conditions.add(criteriaBuilder.equal(achieveRoot.get("menuId"),  menuUse.getMenuId()));
			criteriaQuery.where(conditions.toArray(new Predicate[] {}));
			Menu bandet = eManager.createQuery(criteriaQuery.select(achieveRoot)).setMaxResults(1)
					.getSingleResult();
			return Response.status(Status.OK.getStatusCode())
				.entity(new BasicResponseObject(Status.OK.getStatusCode(), bandet).toString())
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
