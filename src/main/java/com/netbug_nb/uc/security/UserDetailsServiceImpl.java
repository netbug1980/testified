package com.netbug_nb.uc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.netbug_nb.uc.repository.UserAccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = userAccountRepository.findByUsername(username);
		if (userDetails == null) {
			throw new UsernameNotFoundException("用户名【" + username + "】没有找到");
		}
		// TODO 维护用户的授权属性
		return userDetails;
	}
}
