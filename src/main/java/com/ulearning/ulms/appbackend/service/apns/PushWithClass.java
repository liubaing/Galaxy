package com.ulearning.ulms.appbackend.service.apns;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ulearning.ulms.appbackend.entity.Apns;
import com.ulearning.ulms.appbackend.entity.Classes;
import com.ulearning.ulms.appbackend.entity.DeviceToken;
import com.ulearning.ulms.appbackend.entity.User;
import com.ulearning.ulms.appbackend.repository.jpa.ClassesRepository;
import com.ulearning.ulms.appbackend.util.support.ContextAware;

public class PushWithClass extends AbstractPush {

	@Override
	public void pushAndroidNotifications(Apns apns) {
		
		jPushClient.sendNotificationWithTag(apns.getId().getInc(), ASP_KEY + apns.getRecipient() , apns.getMsgContent().getTitle(), apns.getMsgContent().getMessage());
		
	}
	
	@Override
	public void pushAppleNotifications(Apns apns) {
		ClassesRepository classesRepository = WebApplicationContextUtils.getWebApplicationContext(ContextAware.servletContext).getBean(ClassesRepository.class);
		Set<String> tokens = new LinkedHashSet<String>();
		Classes classes = classesRepository.findOne(apns.getRecipient());
		if (classes != null) {
			Set<User> users = classes.getUsers();
			for (User user : users) {
				Set<DeviceToken> deviceTokens = user.getDeviceTokens();
				for (DeviceToken deviceToken : deviceTokens) {
					tokens.add(deviceToken.getDevicetoken());
				}
			}
			super.sendNotifications(apns.getMsgContent().toString(), (String[])tokens.toArray());
		}
	}
}
