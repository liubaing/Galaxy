package com.liubaing.galaxy.service.apns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

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
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

import com.ulearning.ulms.appbackend.util.log.LogUtils;

import cn.jpush.api.JPushClient;


public abstract class AbstractPush implements IPush ,ServletContextAware{

	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
	
	//JPUSH
	protected static final String appKey ="";
	protected static final String masterSecret = "";
	
	//Apple Push证书
	protected static File certificate ;
	protected static final String password = "";
	
	protected JPushClient jPushClient;
	
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
			
			WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(arg0)
			//TODO 下一步考虑连接池                                                                      
			//true：表示产品推送服务器       false：表示测试的服务器
			certificate = new File(WebUtils.getTempDir(servletContext),"/common/cer/Certificates.p12");
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
			certificate = new File(WebUtils.getTempDir(servletContext),"/common/cer/Certificates.p12");
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
		}
		return null;
	}
	
	
}
