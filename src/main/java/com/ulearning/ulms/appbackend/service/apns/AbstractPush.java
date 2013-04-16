package com.ulearning.ulms.appbackend.service.apns;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;

import org.json.JSONException;
import org.springframework.data.annotation.Transient;
import org.springframework.web.util.WebUtils;

import cn.jpush.api.DeviceEnum;
import cn.jpush.api.JPushClient;

import com.ulearning.ulms.appbackend.entity.Apns;
import com.ulearning.ulms.appbackend.entity.Platform;
import com.ulearning.ulms.appbackend.util.log.LogUtils;
import com.ulearning.ulms.appbackend.util.support.ContextAware;

public abstract class AbstractPush implements IPush{

	//JPUSH
	protected static final String appKey ="ddc477c0e0a61ab9100b2173";
	protected static final String masterSecret = "60dd5ca849ae94f09c07109f";
	
	//Apple Push证书
	protected static File certificate;
	protected static final String password = "Wenhua_2012";
	
	@Transient
	protected JPushClient jPushClient = new JPushClient(masterSecret, appKey, DeviceEnum.Android);
	
	public static String[] tokens;
	
	/**
	 * 
	  * 方法描述：消息推送
	  * @author heshuai
	  * @version 2012-5-9 上午11:52:11
	 */
	public void sendNotifications(String message, String[] tokens)
	{
		try {
			//初始化推送  提示信息alert 小红圈数量badge  铃声default
			PushNotificationPayload payLoad = new PushNotificationPayload(message, 1, "default");
			PushNotificationManager pushManager = new PushNotificationManager();
			
			//TODO 下一步考虑连接池                                                                      
			//true：表示产品推送服务器       false：表示测试的服务器
			certificate = new File(WebUtils.getRealPath(ContextAware.servletContext,"/static/cer/Certificates.p12"));
			pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificate, password,  true));
			List<Device> device = new ArrayList<Device>();
			for (String token : tokens) {
				device.add(new BasicDevice(token));
			}
			pushManager.sendNotifications(payLoad, device);
		} catch (CommunicationException ce) {
			//TODO 直接throw异常，还是只记录
			LogUtils.doLog("试图连接Apple推送通知服务失败",ce);
		} catch (KeystoreException ke) {
			LogUtils.doLog("Apple推送通知证书及密码设置有误",ke);
		} catch (JSONException e) {
			LogUtils.doLog("创建Apple推送通知服务模板类失败，注意推送格式。",message.split(""),e);
		} catch (InvalidDeviceTokenFormatException e) {
			LogUtils.doLog("推送对象的设备令牌格式有误，只允许接受64字节的十六进制形式的Token。",tokens,e);
		} catch (FileNotFoundException e) {
			LogUtils.doLog("证书文件不存在",e);
		}
	}
	
	/**
	 * 
	  * 方法描述：获取失效的deviceTokens
	  * @return String[]
	  * @author heshuai
	  * @version 2012-7-31 下午04:09:53
	 */
	public String[] getInvalidDeviceTokens(){
		try {
			certificate = new File(WebUtils.getRealPath(ContextAware.servletContext,"/static/cer/Certificates.p12"));
			List<Device> inactiveDevices = Push.feedback(certificate, password, true);
			if (inactiveDevices != null) {
				int length = inactiveDevices.size();
				String[] inactiveTokens = new String[(length)];
				for (int i = 0; i < length; i++) {
					inactiveTokens[i] = inactiveDevices.get(i).getToken();
				}
				return inactiveTokens;
			}
		} catch (CommunicationException ce) {
			LogUtils.doLog("试图连接Apple推送通知服务失败",ce);
		} catch (KeystoreException ke) {
			LogUtils.doLog("Apple推送通知证书及密码设置有误",ke);
		}	catch (FileNotFoundException e) {
			LogUtils.doLog("证书文件不存在",e);
		}
		return null;
	}

	@Override
	public void pushNotifications(Apns apns) {
		Set<Platform> platforms = apns.getPlatforms();
		if (platforms.contains(Platform.ANDROID)) {
			this.pushAndroidNotifications(apns);
		}
		if (platforms.contains(Platform.IOS)) {
			this.pushAppleNotifications(apns);
		}
	}
	
	public abstract void pushAndroidNotifications(Apns apns);
	public abstract void pushAppleNotifications(Apns apns);
}
