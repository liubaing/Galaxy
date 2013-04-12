package com.liubaing.shiny_liubaing.service.apns;

import cn.jpush.api.JPushClient;

import com.ulearning.ulms.appbackend.entity.Apns;
import com.ulearning.ulms.appbackend.util.core.Const;

public class PushWithAsp extends AbstractPush {

	@Override
	public void pushNotifications(Apns apns) {
		jPushClient = new JPushClient(masterSecret, appKey);
		jPushClient.sendNotificationWithTag(apns.getId(), ASP_KEY + apns.getRecipient() , apns.getMsgTitle(), apns.getMsgContent());
		super.sendNotifications(apns.getMsgContent(), );
	}
}
