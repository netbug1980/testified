package com.netbug_nb.uc.service;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.events.AbstractSessionEvent;
import org.springframework.stereotype.Service;

import com.netbug_nb.uc.domain.UserAccount;
import com.netbug_nb.uc.repository.UserAccountRepository;

@Service
public class UserAccountService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private RedisOperationsSessionRepository redisOperationsSessionRepository;

	/**
	 * 查询某账户当前活动的所有sessions
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<ExpiringSession> findActiveSessions(String username) {
		Map<String, ExpiringSession> m = ((Map) redisOperationsSessionRepository
				.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username));
		return m.values();
	}

	/**
	 * 动态维护人员的在线状态
	 * 
	 * @param event
	 * @return
	 */
	public int findActiveSessionCount(AbstractSessionEvent event) {
		SecurityContextImpl securityContext = (SecurityContextImpl) event.getSession()
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		if (securityContext != null) {
			UserAccount userAccount = (UserAccount) securityContext.getAuthentication().getPrincipal();
			String username = userAccount.getUsername();
			String userAccountId = userAccount.getId();
			int count = this.findActiveSessions(username).size();
			this.onOffLine(userAccountId, count > 0);
			return count;
		} else {
			return -1;
		}
	}

	/**
	 * 动态维护账户的在线状态
	 * 
	 * @param event
	 * @return
	 */
	public int findActiveSessionCount(String username) {
		return this.findActiveSessions(username).size();
	}

	/**
	 * 在线或者下线
	 * 
	 * @param online
	 */
	public void onOffLine(String userAccountId, boolean online) {
		CriteriaDefinition criteriaDefinition = Criteria.where("id").is(userAccountId);
		Query query = Query.query(criteriaDefinition);
		Update update = Update.update("online", online);
		mongoOperations.updateFirst(query, update, UserAccount.class);
	}

	@Cacheable(cacheNames = "UserAccountCache", key = "#id")
	public UserAccount findUserAccountFromCache(String id) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

		}
		return userAccountRepository.findOne(id);
	}
}
