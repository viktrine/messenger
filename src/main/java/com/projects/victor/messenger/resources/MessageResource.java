package com.projects.victor.messenger.resources;
	
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.projects.victor.messenger.model.Message;
import com.projects.victor.messenger.services.MessageService;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	private MessageService messageService = new MessageService();
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getAlljsonMessage(@QueryParam("year") int year,
			@QueryParam("start") int start, 
			@QueryParam("size") int size) {
		System.out.println("xml");
		if(year > 0)
			return messageService.getAllMessagesforYear(year);
		if(start > 0 && size > 0)
			return messageService.getAllMessagesPaginate(start, size);
		return messageService.getAllMessages();
	}	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> getAllxmlMessage(@QueryParam("year") int year,
			@QueryParam("start") int start, 
			@QueryParam("size") int size) {
		System.out.println("json");
		if(year > 0)
			return messageService.getAllMessagesforYear(year);
		if(start > 0 && size > 0)
			return messageService.getAllMessagesPaginate(start, size);
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId")Long id, @Context UriInfo info) {
		Message message = messageService.getMessage(id);
		message.addLink(getUrlInfo(info, message), "rel");
		message.addLink(getProfileInfo(info, message), "profile");
		message.addLink(getcommentsInfo(info, message), "comments");
		return message;
	}

	private String getcommentsInfo(UriInfo info, Message message) {
		String uri = info.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build().toString();
				return uri;
	}

	private String getProfileInfo(UriInfo info, Message message) {
		String uri = info.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build().toString();
				return uri;
	}

	private String getUrlInfo(UriInfo info, Message message) {
		String uri = info.getBaseUriBuilder()
		.path(MessageResource.class)
		.path(Long.toString(message.getId()))
		.build().toString();
		return uri;
	}
	
	/*
	@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}
	
	*/
	@POST
	public Response addMessage(Message message, @Context UriInfo info) throws URISyntaxException {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = info.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				.entity(newMessage)
				.build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
