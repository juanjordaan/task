package com.juan.task.interfaces.rest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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

import com.juan.task.domain.model.User;
import com.juan.task.infrastructure.persistence.jpa.UserRepository;

@Controller
@RequestMapping(path="/user")
public class UserController extends AbstractController {
	private Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(
			consumes = APPLICATION_JSON_VALUE, 
			produces = APPLICATION_JSON_VALUE, 
			method=POST)
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		LOG.info("create user");
		user = userRepository.create(user);
		
		return new ResponseEntity<User>(user, OK);
	}
	
	@RequestMapping(path="/{id}", 
	produces = APPLICATION_JSON_VALUE, 
	method=GET)
	public ResponseEntity<User> getUser(@PathVariable(name="id") Long id) {
		LOG.info("get user");
		User user = userRepository.findById(id);
		
		return new ResponseEntity<User>(user, OK);
	}

	@RequestMapping(
			path="/{id}", 
			consumes = APPLICATION_JSON_VALUE, 
			produces = APPLICATION_JSON_VALUE, 
			method=PUT)
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
		LOG.info("update user");
		user = userRepository.update(user);
		
		return new ResponseEntity<User>(user, OK);
	}
	
	@RequestMapping(path="/{id}", 
	produces = APPLICATION_JSON_VALUE, 
	method=DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable(name="id") Long id) {
		LOG.info("delete user");
		User user = userRepository.deleteById(id);
		
		return new ResponseEntity<User>(user, OK);
	}
	
	@RequestMapping( 
	produces = APPLICATION_JSON_VALUE, 
	method=GET)
	public ResponseEntity<List<User>> listUser() {
		LOG.info("list user");
		List<User> users = userRepository.findAll();
		
		return new ResponseEntity<>(users, OK);
	}
}
