package com.cybg.email.logging;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class CustomLogFormat extends LayoutBase<ILoggingEvent> {

	private static Logger logger = LoggerFactory.getLogger(CustomLogFormat.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String doLayout(ILoggingEvent iLoggingEvent) {
		return getLogFormat(iLoggingEvent);
	}

	private String getLogFormat(ILoggingEvent iLoggingEvent) {

		Map<String, Object> map = new LinkedHashMap<>();
		map.put("TIMESTAMP", String.valueOf(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp())));
		map.put("CORRELATION_ID", iLoggingEvent.getMDCPropertyMap().get("CORRELATION_ID"));
		return objectMapper.toString();
	}

}
