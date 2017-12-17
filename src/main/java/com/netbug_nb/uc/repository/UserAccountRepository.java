package com.netbug_nb.uc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.netbug_nb.uc.domain.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
	public UserAccount findByUsername(String username);
}
