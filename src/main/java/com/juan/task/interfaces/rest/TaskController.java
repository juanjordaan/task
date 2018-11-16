package com.juan.task.interfaces.rest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juan.task.domain.model.Task;
import com.juan.task.infrastructure.persistence.jpa.TaskRepository;

@Controller
@RequestMapping(path="/api/user/{userId}/task")
public class TaskController {
	private Logger LOG = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskAssembler taskAssembler;
	
	@RequestMapping(
			consumes = APPLICATION_JSON_VALUE, 
			produces = APPLICATION_JSON_VALUE, 
			method=POST)
	public ResponseEntity<Task> createTask(@PathVariable(name="userId") Long userId, @RequestBody Task task) {
		LOG.info("create task");
		task = taskRepository.create(taskAssembler.toDto(userId, task));
		
		return new ResponseEntity<Task>(task, OK);
	}
	
	@RequestMapping(path="/{id}", 
	produces = APPLICATION_JSON_VALUE, 
	method=GET)
	public ResponseEntity<Task> getTask(@PathVariable(name="userId") Long userId, @PathVariable(name="id") Long id) {
		LOG.info("get task");
		Task task = taskRepository.findById(id);
		
		return new ResponseEntity<Task>(task, OK);
	}

	@RequestMapping(
			path="/{id}", 
			consumes = APPLICATION_JSON_VALUE, 
			produces = APPLICATION_JSON_VALUE, 
			method=PUT)
	public ResponseEntity<Task> updateTask(@PathVariable(name="userId") Long userId, @Valid @RequestBody Task task) {
		LOG.info("update task");
		task = taskRepository.update(taskAssembler.toDto(userId, task));
		
		return new ResponseEntity<Task>(task, OK);
	}
	
	@RequestMapping(path="/{id}", 
	produces = APPLICATION_JSON_VALUE, 
	method=DELETE)
	public ResponseEntity<Task> deleteTask(@PathVariable(name="userId") Long userId, @PathVariable(name="id") Long id) {
		LOG.info("delete task");
		Task task = taskRepository.deleteById(id);
		
		return new ResponseEntity<Task>(task, OK);
	}
	
	@RequestMapping( 
	produces = APPLICATION_JSON_VALUE, 
	method=GET)
	public ResponseEntity<List<Task>> listUserTasks(@PathVariable(name="userId") Long userId) {
		LOG.info("list task");
		List<Task> tasks = taskRepository.findAllByUserId(userId);
		
		return new ResponseEntity<>(tasks, OK);
	}
}
