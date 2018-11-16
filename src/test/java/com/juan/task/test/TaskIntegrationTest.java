package com.juan.task.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.juan.task.domain.model.Task;
import com.juan.task.domain.model.User;

@RunWith(SpringRunner.class)
public class TaskIntegrationTest extends AbstractIntegrationTest<Task> {

	@Test
	public void createTaskTest_Passes() {
		Task task = post(1L, new Task("My task", "Description of task", new Date(), "pending"));
		assertThat(task).isNotNull();
		
		delete(1L, task.getId());
	}
	
	@Test
	public void getTaskTest_Passes() {
		Task task = post(1L, new Task("My task", "Description of task", new Date(), "pending"));
		task = get(1L, task.getId());
		assertThat(task).isNotNull();
		
		delete(1L, task.getId());
	}
	
	@Test
	public void updateTaskTest_Passes() {
		Task task = post(1L, new Task("My task", "Description of task", new Date(), "pending"));
		task = put(1L, new Task("My task", "Description of task", new Date(), "finished"));
		
		assertThat(task.getStatus()).isEqualTo("finished");
		
		delete(1L, task.getId());
	}
	
	@Test
	public void deleteTaskTest_Passes() {
		Task task = post(1L, new Task("My task", "Description of task", new Date(), "pending"));
		assertThat(task).isNotNull();
		
		delete(1L, task.getId());
	}
	
	@Test
	public void listTaskTest_Passes() {
		Task task1 = post(1L, new Task("My task", "Description of task", new Date(), "pending"));
		Task task2 = post(1L, new Task("My other task", "Description of task", new Date(), "pending"));
		
		delete(1L, task1.getId());
		delete(1L, task2.getId());
	}
	
	@Override
	protected String getRestPath() {
		return "/api/user/{userId}/task";
	}
	
	protected Task post(Long userId, Task dto) {
		ResponseEntity<Task> responseEntity = this.restTemplate.exchange(getRestPath().replace("{userId}", ""+userId), HttpMethod.POST, new HttpEntity<>(dto, defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		Task response = responseEntity.getBody();
		
		return response;
	}
	
	protected Task get(Long userId, Long id) {
		ResponseEntity<Task> responseEntity = this.restTemplate.exchange(getRestPath().replace("{userId}", ""+userId) + "/" + id, HttpMethod.GET, new HttpEntity<>(defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		Task response = responseEntity.getBody();
		
		return response;
	}
	
	protected Task put(Long userId, Task dto) {
		ResponseEntity<Task> responseEntity = this.restTemplate.exchange(getRestPath().replace("{userId}", ""+userId) + "/" + dto.getId(), HttpMethod.PUT, new HttpEntity<>(dto, defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		Task response = responseEntity.getBody();
		
		return response;
	}
	
	protected void delete(Long userId, Long id) {
		ResponseEntity<Task> responseEntity = this.restTemplate.exchange(getRestPath().replace("{userId}", ""+userId) + "/" + id, HttpMethod.DELETE, new HttpEntity<>(defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
	}
	
	protected List<Task> list(Long userId) {
		ResponseEntity<List<Task>> responseEntity = this.restTemplate.exchange(getRestPath().replace("{userId}", ""+userId), HttpMethod.GET, new HttpEntity<>(defaultHeaders), new ParameterizedTypeReference<List<Task>>() {});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		List<Task> response = responseEntity.getBody();
		
		return response;
	}
}
