package com.netbug_nb.uc.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonAuthenticationHandler implements AuthenticationFailureHandler, AuthenticationSuccessHandler,
		LogoutSuccessHandler, AuthenticationEntryPoint, SessionInformationExpiredStrategy {
	private final static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 登陆失败
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		int status = HttpStatus.UNAUTHORIZED.value();
		String message = exception.getLocalizedMessage();
		if (exception instanceof BadCredentialsException) {
			message = "用户名或密码错误";
		}
		HandlerResponseBody<Authentication> body = new HandlerResponseBody<Authentication>(status, message,
				exception.getClass().getName());
		sendMessage(response, status, body);

	}

	/**
	 * 登陆成功
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		int status = HttpStatus.OK.value();
		HandlerResponseBody<Authentication> body = new HandlerResponseBody<Authentication>(status, authentication);
		sendMessage(response, status, body);

	}

	/**
	 * 登出成功
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		int status = HttpStatus.OK.value();
		HandlerResponseBody<Authentication> body = new HandlerResponseBody<Authentication>(status, authentication);
		sendMessage(response, status, body);

	}

	/**
	 * 无登陆或者无权限
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		int status = HttpStatus.UNAUTHORIZED.value();
		HandlerResponseBody<Authentication> body = new HandlerResponseBody<Authentication>(status,
				exception.getLocalizedMessage(), exception.getClass().getName());
		sendMessage(response, status, body);

	}

	private void sendMessage(HttpServletResponse response, int status, Object body)
			throws IOException, JsonProcessingException {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(status);
		response.getWriter().print(mapper.writeValueAsString(body));
		response.getWriter().flush();
	}

	/**
	 * 登陆数超限
	 */
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException, ServletException {

		int status = HttpStatus.UNAUTHORIZED.value();
		HandlerResponseBody<SessionInformation> body = new HandlerResponseBody<SessionInformation>(status,
				eventØ.getSessionInformation(), "Session Expired");
		sendMessage(eventØ.getResponse(), status, body);

	}

	// @JsonInclude(Include.NON_EMPTY)
	public static class HandlerResponseBody<T> {
		private long timestamp;
		private int status;
		private String message;
		private String exception;
		private T data;

		public HandlerResponseBody() {
		}

		public HandlerResponseBody(int status, T data, String... message) {
			this.status = status;
			if (message.length > 0) {
				this.message = message[0];
			}
			this.data = data;
		}

		public HandlerResponseBody(int status, String message, String exception) {
			this.status = status;
			this.message = message;
			this.exception = exception;
		}

		public long getTimestamp() {
			timestamp = new Date().getTime();
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getException() {
			return exception;
		}

		public void setException(String exception) {
			this.exception = exception;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

	}

}
