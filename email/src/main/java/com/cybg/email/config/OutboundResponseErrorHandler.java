package com.cybg.email.config;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.cybg.email.model.ApiError;

public class OutboundResponseErrorHandler implements ResponseErrorHandler {

	@Autowired
	MessageSource messageSource;

	@Override
	public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
		HttpStatus status = clientHttpResponse.getStatusCode();
		return status.is4xxClientError() || status.is5xxServerError();
	}

	@Override
	public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
		throw processError(clientHttpResponse);
	}

	@Override
	public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
		throw processError(response);
	}

	private ApiError processError(ClientHttpResponse response) throws IOException {
		// TODO return error
		HttpStatus status = response.getStatusCode();
		String responseAsString = response.getBody().toString();
		// TODO log error
		ApiError apiError = new ApiError(status,"error");
		return apiError;

	}

}
