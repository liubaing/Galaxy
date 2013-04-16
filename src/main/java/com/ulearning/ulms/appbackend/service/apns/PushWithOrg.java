package com.ulearning.ulms.appbackend.service.apns;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ulearning.ulms.appbackend.entity.Apns;
import com.ulearning.ulms.appbackend.entity.DeviceToken;
import com.ulearning.ulms.appbackend.entity.Organization;
import com.ulearning.ulms.appbackend.entity.User;
import com.ulearning.ulms.appbackend.repository.jpa.OrganizationRepository;
import com.ulearning.ulms.appbackend.util.support.ContextAware;

public class PushWithOrg extends AbstractPush {

	@Override
	public void pushAndroidNotifications(Apns apns) {
		
		jPushClient.sendNotificationWithTag(apns.getId().getInc(), ASP_KEY + apns.getRecipient(), apns.getMsgContent().getTitle(), apns.getMsgContent().getMessage());
		
	}
	
	@Override
	public void pushAppleNotifications(Apns apns) {
		Set<String> tokens = new LinkedHashSet<String>();
		OrganizationRepository organizationRepository =  WebApplicationContextUtils.getWebApplicationContext(ContextAware.servletContext).getBean(OrganizationRepository.class);
		Organization organization = organizationRepository.findOne(apns.getRecipient());
		Set<User> users = organization.getUsers();
		for (User user : users) {
			Set<DeviceToken> deviceTokens = user.getDeviceTokens();
			for (DeviceToken deviceToken : deviceTokens) {
				tokens.add(deviceToken.getDevicetoken());
			}
		}
		super.sendNotifications(apns.getMsgContent().toString(), (String[])tokens.toArray());
	}
}
