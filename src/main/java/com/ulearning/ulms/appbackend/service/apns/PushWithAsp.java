package com.ulearning.ulms.appbackend.service.apns;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ulearning.ulms.appbackend.entity.Apns;
import com.ulearning.ulms.appbackend.entity.DeviceToken;
import com.ulearning.ulms.appbackend.entity.User;
import com.ulearning.ulms.appbackend.repository.jpa.UserRepository;
import com.ulearning.ulms.appbackend.util.support.ContextAware;

public class PushWithAsp extends AbstractPush {

	
	
	@Override
	public void pushAndroidNotifications(Apns apns) {
		jPushClient.sendNotificationWithTag(apns.getId().getInc(), ASP_KEY + apns.getRecipient(), apns.getMsgContent().getTitle(), apns.getMsgContent().getMessage());
	}

	@Override
	public void pushAppleNotifications(Apns apns) {
		UserRepository userRepository = WebApplicationContextUtils.getWebApplicationContext(ContextAware.servletContext).getBean(UserRepository.class);
		Set<String> tokens = new LinkedHashSet<String>();
		List<User> users = userRepository.findByOrganizationAspID(apns.getRecipient());
		for (User user : users) {
			Set<DeviceToken> deviceTokens = user.getDeviceTokens();
			for (DeviceToken deviceToken : deviceTokens) {
				tokens.add(deviceToken.getDevicetoken());
			}
		}
		super.sendNotifications(apns.getMsgContent().toString(), (String[])tokens.toArray());
	}
}
