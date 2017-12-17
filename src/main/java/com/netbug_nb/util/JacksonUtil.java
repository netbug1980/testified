package com.netbug_nb.util;

import java.io.IOException;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	private static final ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

	private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public static <T> T readValue(String content, Class<?> collectionClass, Class<?>... elementClasses)
			throws JsonParseException, JsonMappingException, IOException {
		JavaType javaType = getCollectionType(collectionClass, elementClasses);
		return mapper.readValue(content, javaType);
	}

	public static <T> T readValue(String content, Class<T> valueType)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(content, valueType);
	}

	public static String writeValueAsString(Object value)
			throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.writeValueAsString(value);
	}

}
