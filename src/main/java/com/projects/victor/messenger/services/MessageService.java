package com.projects.victor.messenger.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.projects.victor.messenger.database.DatabaseClass;
import com.projects.victor.messenger.exeptions.DatanotFoundException;
import com.projects.victor.messenger.model.Comment;
import com.projects.victor.messenger.model.ErrorMessage;
import com.projects.victor.messenger.model.Message;

public class MessageService {
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	public MessageService() {
		messages.put(1L, new Message(1,"Hello World","Victor"));
		messages.put(2L, new Message(2,"Hello Jersey","Linner"));
		messages.put(111L, new Message(111,"The accessed message not found","Localhost"));
	}
 	public java.util.List<Message> getAllMessages() {
		return new ArrayList<>(messages.values());
	}
 	public List<Message> getAllMessagesforYear(int year){
 		List<Message> messageOfYear = new ArrayList<>();
 		Calendar calendar = Calendar.getInstance();
 		for(Message message : messages.values()) {
 			calendar.setTime(message.getCreated());
 			if(calendar.get(Calendar.YEAR) == year) {
 				messageOfYear.add(message);
 			}
 		}
 		return messageOfYear;
 	}
 	
 	public List<Message> getAllMessagesPaginate(int start, int size){
 		ArrayList<Message> list = new ArrayList<>(messages.values());
 		if(start + size > list.size())
 			return new ArrayList<Message>();
 		return list.subList(start, start + size);
 	}
 	public Message getMessage(Long id) {
 		Message message =  messages.get(id);
 		ErrorMessage errorMessage = new ErrorMessage("Not Found",404, "localhost:8080/messenger/webapi/messages/111");
		Response response = Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
 		if(message == null)
 			throw new WebApplicationException(response);
 		return message;
 	}
 	public Message addMessage(Message message) {
 		message.setId(messages.size() + 1);
 		messages.put(message.getId(), message);
 		return message;
 	}
 	public Message updateMessage(Message message) {
 		if(message.getId() <= 0) {
 			return null;
 		}
 		messages.put(message.getId(), message);
 		return message;
 	}
 	public Message removeMessage(Long id) {
 		return messages.remove(id);
 	}
}
