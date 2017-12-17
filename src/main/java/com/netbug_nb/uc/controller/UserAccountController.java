package com.netbug_nb.uc.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netbug_nb.uc.domain.UserAccount;
import com.netbug_nb.uc.repository.UserAccountRepository;
import com.netbug_nb.uc.service.UserAccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户账户")
@RestController
@RequestMapping("/api/uc/useraccount")
public class UserAccountController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserAccountRepository userAccountRepository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserAccount findUserAccount(@PathVariable String id) {
		return userAccountRepository.findOne(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteUserAccount(@PathVariable String id) {
		userAccountRepository.delete(id);
	}

	@ApiOperation(value = "获取账户信息", notes = "根据账户ID获取账户信息")
	@RequestMapping(value = "/cache/{id}", method = RequestMethod.GET)
	public UserAccount findUserAccountFromCache(@PathVariable String id) {
		return userAccountService.findUserAccountFromCache(id);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public UserAccount register(@RequestBody UserAccount userAccount) {
		String id = UUID.randomUUID().toString();
		userAccount.setId(id);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
		return userAccountRepository.save(userAccount);
	}

	@RequestMapping(value = "/find/all", method = RequestMethod.GET)
	public List<UserAccount> findAll() throws JsonProcessingException {
		return userAccountRepository.findAll();
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public void error() {
		Assert.isTrue(false, "Error");
	}

}
