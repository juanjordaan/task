package com.juan.task.interfaces.rest;

import org.springframework.stereotype.Component;

import com.juan.task.domain.model.Task;
import com.juan.task.domain.model.User;

@Component
public class TaskAssembler {

	public Task toDto(Long userId, Task task) {
		task.setUser(new User());
		task.getUser().setId(userId);
		
		return task;
	}
}
