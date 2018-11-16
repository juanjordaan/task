package com.juan.task.test;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.juan.task.TaskApplication;
import com.juan.task.domain.model.User;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes=TaskApplication.class)
public abstract class AbstractIntegrationTest <Dto extends Object>{
	@Autowired
	protected TestRestTemplate restTemplate;
	
	protected HttpHeaders defaultHeaders = new HttpHeaders();
	
	protected final Class<Dto> dtoClass;
	
	@SuppressWarnings("unchecked")
	public AbstractIntegrationTest() {
		this.dtoClass = (Class<Dto>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Before
	public void setup() {
		defaultHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
		defaultHeaders.add(ACCEPT, APPLICATION_JSON_VALUE);
	}
	
	protected abstract String getRestPath();
	
	/*protected Dto get(Object... urlVariables) {
		ResponseEntity<Dto> responseEntity = this.restTemplate.exchange(getRestPath(), HttpMethod.GET, new HttpEntity<>(defaultHeaders), dtoClass, urlVariables);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		Dto response = responseEntity.getBody();
		
		return response;
	}*/
	
	/*protected List<Dto> list() {
		ResponseEntity<Dto> responseEntity = this.restTemplate.exchange(getRestPath(), HttpMethod.GET, new HttpEntity<>(defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		List<Dto> response = responseEntity.getBody();
		
		return response;
	}*/
	
	/*protected Dto post(Dto dto) {
		ResponseEntity<Dto> responseEntity = this.restTemplate.exchange(getRestPath(), HttpMethod.POST, new HttpEntity<>(dto, defaultHeaders), dtoClass);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		
		Dto response = responseEntity.getBody();
		
		return response;
	}*/
}
