package com.cybg.email.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@JsonIgnoreProperties({ "stackTrace", "localizedMessage", "suppressed", "status" })
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ApiError extends RuntimeException {
	@NonNull
	private HttpStatus status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:Ss")
	private LocalDateTime timestamp;

	@NonNull
	@Setter
	private String code;
	@NonNull
	@Setter
	private String message;

	@Setter
	private String errorCode;
	@Setter
	private String errorMessage;

	@Setter
	private String correlationId;

	@NonNull
	@Setter
	private String category;

	@Setter
	@JsonIgnore
	private Object[] args;

	@Setter
	private List<ApiSubError> subErrors;

	public ApiError(HttpStatus status, String code) {
		this.status = status;
		this.code = code;
		this.timestamp = LocalDateTime.now();
	}

	public ApiError(@NonNull String code, @NonNull String message, @NonNull String category,
			@NonNull HttpStatus status) {
		this.code = code;
		this.message = message;
		this.category = category;
		this.status = status;
	}
}
