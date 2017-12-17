package com.netbug_nb.uc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

import com.netbug_nb.uc.domain.UserAccount;
import com.netbug_nb.uc.service.UserAccountService;

@Component
public class SessionEventApplicationListener {
	final static Logger logger = LoggerFactory.getLogger(SessionEventApplicationListener.class);

	@Autowired
	private UserAccountService userAccountService;

	@Component
	public class SessionCreatedEventListener implements ApplicationListener<SessionCreatedEvent> {

		@Override
		public void onApplicationEvent(SessionCreatedEvent event) {
			logger.debug("SessionCreatedEvent:{}", event.getSession().getId());
		}

	}

	@Component
	public class SessionDeletedEventListener implements ApplicationListener<SessionDeletedEvent> {

		@Override
		public void onApplicationEvent(SessionDeletedEvent event) {
			logger.debug("SessionDeletedEvent:{}-{}", event.getSession().getId(),
					userAccountService.findActiveSessionCount(event));

		}
	}

	@Component
	public class SessionExpiredEventListener implements ApplicationListener<SessionExpiredEvent> {

		@Override
		public void onApplicationEvent(SessionExpiredEvent event) {
			logger.debug("SessionExpiredEvent:{}-{}", event.getSession().getId(),
					userAccountService.findActiveSessionCount(event));

		}
	}

	@Component
	public class AuthenticationSuccessEventListener
			implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

		@Override
		public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
			UserAccount userAccount = SecurityUtil.getCurrentUserAccount();
			logger.debug("AuthenticationSuccessEvent:{}", userAccount.getUsername());
			userAccountService.onOffLine(userAccount.getId(), true);
		}

	}
}
