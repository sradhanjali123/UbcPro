package com.hello.controller;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/Hi")
public class HelloController {
	@GET
	@Path("/sayHi/{name}")
	public Response sayHi(@PathParam("name") String name) {
		String message = "Hello! " + name;
		return Response.status(Status.OK.getStatusCode()).entity(message).build();
	}
}
