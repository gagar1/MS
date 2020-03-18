package com.cybg.email.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface RestTemplateClient {

	public <T> ResponseEntity<T> getPostResponse(String url, String path, Object payload, HttpHeaders headers, Class<T> responseType);
}
