package com.cybg.email.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cybg.email.model.ApiError;
import com.cybg.email.model.ApiSubError;
import com.cybg.email.model.ApiValidationError;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("");
		List<ApiSubError> errors = new ArrayList();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(new ApiValidationError(error.getObjectName(), error.getField(), error.getRejectedValue(),
					error.getDefaultMessage()));
		}
		ApiError apiError = new ApiError(BAD_REQUEST, ex.getBindingResult().getObjectName() + "." + "INVALID");
		apiError.setMessage(
				messageSource.getMessage(apiError.getCode(), ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.getDefault()));
		apiError.setSubErrors(errors);
		apiError.setCorrelationId(MDC.get("CORRELATION_ID"));
		apiError.setCategory("BAD_REQUEST");
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// logger.error(LogsEncoder.encode(ex.getMessage()), ex);
		String code = ((MismatchedInputException) ex.getCause()).getPath().get(0).getFieldName() + "." + "INVALID";
		ApiError apiError = new ApiError(BAD_REQUEST, code);
		apiError.setMessage(
				messageSource.getMessage(apiError.getCode(), ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.getDefault()));
		apiError.setCategory("BAD_REQUEST");
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		ResponseEntity<Object> errorResponseEntity = new ResponseEntity<>(apiError, apiError.getStatus());
		// logger.info("errorResponseEntity -> {}", errorResponseEntity);
		return errorResponseEntity;
	}

	@ExceptionHandler(value = { ApiError.class })
	public ResponseEntity<Object> handleAPIError(ApiError ex, Locale locale) {
		Object[] args = null != ex.getArgs() ? ex.getArgs() : ArrayUtils.EMPTY_OBJECT_ARRAY;
		ex.setMessage(messageSource.getMessage(ex.getCode(), args, locale));
		// logger.error(LogsEncoder.encode(ex.getMessage()), ex);
		if (StringUtils.isNotEmpty(ex.getErrorCode())) {
			String errorMessage = messageSource.getMessage(ex.getErrorCode(), args, locale);
			ex.setErrorMessage(errorMessage);
		}
		return buildResponseEntity(ex);
	}

}
