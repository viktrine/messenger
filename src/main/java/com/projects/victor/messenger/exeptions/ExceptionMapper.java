package com.projects.victor.messenger.exeptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.projects.victor.messenger.model.ErrorMessage;

public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<DatanotFoundException>{
	@Override
	public Response toResponse(DatanotFoundException exception) {
		// TODO Auto-generated method stub
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),404, "localhost:8080/messenger/webapi/messages/111");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}
}
