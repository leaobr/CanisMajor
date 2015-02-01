package net.leaobr.e4.rcp.cm.model;

import java.util.List;

import org.eclipse.e4.core.services.events.IEventBroker;

public interface ITodoService {
	
	List<Todo> getTodos();

	boolean saveTodo(Todo todo);

	Todo getTodo(long id);

	boolean deleteTodo(long id);

}
