package com.juan.task.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.juan.task.domain.model.User;

@RunWith(SpringRunner.class)
public class UserIntegrationTest extends AbstractIntegrationTest<User> {

	@Test
	public void createUserTest_Passes() {
		User user = post(new User("jjordaan", "juan", "jordaan"));
		delete(user.getId());
	}
	
	@Test
	public void getUserTest_Passes() {
		User user = post(new User("jjordaan", "juan", "jordaan"));
		user = get(user.getId());
		
		assertThat(user).isNotNull();
		
		delete(user.getId());
	}
	
	@Test
	public void updateUserTest_Passes() {
		User user = post(new User("jjordaan", "juan", "jordaan"));
		user.setLastName(user.getLastName()+"2");
		user = put(user);
		
		assertThat(user.getLastName()).endsWith("2");
		
		delete(user.getId());
	}
	
	@Test
	public void deleteUserTest_Passes() {
		User user = post(new User("jjordaan", "juan", "jordaan"));
		delete(user.getId());
	}
	
	@Test
	public void listUserTest_Passes() {
		User user = post(new User("jjordaan", "juan", "jordaan"));
		List<User> users = list();
		
		assertThat(users.size()).isEqualTo(2);
		
		delete(user.getId());
	}
	
	@Override
	protected String getRestPath() {
		return "/user";
	}
	
	protected User post(User dto) {
		ResponseEntity<User> responseEntity = this.restTemplate.exchange(getRestPath(), HttpMethod.POST, new HttpEntity<>(dto, defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		User response = responseEntity.getBody();
		
		return response;
	}
	
	protected User get(Long id) {
		ResponseEntity<User> responseEntity = this.restTemplate.exchange(getRestPath() + "/" + id, HttpMethod.GET, new HttpEntity<>(defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		User response = responseEntity.getBody();
		
		return response;
	}
	
	protected User put(User dto) {
		ResponseEntity<User> responseEntity = this.restTemplate.exchange(getRestPath() + "/" + dto.getId(), HttpMethod.PUT, new HttpEntity<>(dto, defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		User response = responseEntity.getBody();
		
		return response;
	}
	
	protected void delete(Long id) {
		ResponseEntity<User> responseEntity = this.restTemplate.exchange(getRestPath() + "/" + id, HttpMethod.DELETE, new HttpEntity<>(defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
	}
	
	protected List<User> list() {
		ResponseEntity<List<User>> responseEntity = this.restTemplate.exchange(getRestPath(), HttpMethod.GET, new HttpEntity<>(defaultHeaders), new ParameterizedTypeReference<List<User>>() {});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		List<User> response = responseEntity.getBody();
		
		return response;
	}
}
