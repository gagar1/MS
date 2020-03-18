package com.cybg.email.rest;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BaseRestTemplateClient implements RestTemplateClient {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public <T> ResponseEntity<T> getPostResponse(String url, String path, Object payload, HttpHeaders headers,
			Class<T> responseType) {
		URI uri = getURI(url, path);
		return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(payload, headers), responseType);
	}

	private URI getURI(String url, String path) {
		URI uri = null;
		try {
			uri = new URI("");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;
	}

}
