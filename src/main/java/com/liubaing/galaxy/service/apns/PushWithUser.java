package com.liubaing.galaxy.service.apns;

import cn.jpush.api.JPushClient;

import com.ulearning.ulms.appbackend.entity.Apns;
import com.ulearning.ulms.appbackend.util.core.Const;

public class PushWithUser extends AbstractPush {

	@Override
	public void pushNotifications(Apns apns) {
		jPushClient = new JPushClient(masterSecret, appKey);
		jPushClient.sendNotificationWithAlias(apns.getId(), USER_KEY + apns.getRecipient() , apns.getMsgTitle(), apns.getMsgContent());
		super.sendNotifications(apns.getMsgContent(), );
	}
}
