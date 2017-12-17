package com.netbug_nb.uc.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.netbug_nb.uc.domain.UserAccount;

/**
 * 用户工具类
 */
public class SecurityUtil {
	/*
	 * 获取当前用户
	 * 
	 * @return
	 */
	public static UserAccount getCurrentUserAccount() {
		UserAccount user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = null;
		if (authentication != null) {
			principal = authentication.getPrincipal();
		}
		if (principal != null && principal instanceof UserAccount) {
			user = (UserAccount) principal;
		}
		return user;
	}
}
