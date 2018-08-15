package com.projects.victor.messenger.resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.projects.victor.messenger.beans.MessageFilterBean;

@Path("injectdemo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InjectDemoResource {
	@GET
	@Path("annotation")
	public String getParamUsingAnnotations(@BeanParam MessageFilterBean messageFilterBean) {
		return "Matrix Para  " + messageFilterBean.getMatrixParam() + " HeaderParam " + messageFilterBean.getHeader() + " CookieValue: " + messageFilterBean.getCookieValue();
	}
	
	@GET
	@Path("context")
	public String getParamContext(@Context UriInfo info, @Context HttpHeaders headers) {
		String path = info.getPathParameters().toString();
		String cookies = headers.getCookies().toString();
		return "Path : " + path + " Cookies: "+ cookies;
	}
}
