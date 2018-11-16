package com.juan.task.infrastructure.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SpringRestExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(SpringRestExceptionHandler.class);

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	protected ResponseEntity<ApiErrorResponse> handleThrowable(Throwable e) {
		LOG.error("Throwable caught in Rest handler", e);

		return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
