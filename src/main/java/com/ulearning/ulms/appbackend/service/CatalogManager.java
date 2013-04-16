package com.ulearning.ulms.appbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ulearning.ulms.appbackend.entity.Apns;
import com.ulearning.ulms.appbackend.entity.Catalog;
import com.ulearning.ulms.appbackend.entity.Course;
import com.ulearning.ulms.appbackend.entity.Platform;
import com.ulearning.ulms.appbackend.entity.User;
import com.ulearning.ulms.appbackend.repository.jpa.CatalogRepository;
import com.ulearning.ulms.appbackend.repository.jpa.UserRepository;
import com.ulearning.ulms.appbackend.service.apns.PushWithAsp;
import com.ulearning.ulms.appbackend.util.core.Const;
import com.ulearning.ulms.appbackend.util.core.JSONUtils;
import com.ulearning.ulms.appbackend.util.core.ObjectUtils;
import com.ulearning.ulms.appbackend.util.support.CacheManager;


/**
 * 类说明:课程目录
 * @author heshuai
 * @version Feb 24, 2012
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Service 
public class CatalogManager
{
	
	@Autowired 
	private CacheManager cacheManager;
	
	@Autowired
	private CatalogRepository catalogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApnsManager apnsManager;

	/**
	 * 
	  * 方法描述：返回JSON格式的课程目录
	  * @author heshuai
	  * @version 2012-3-29 下午08:43:26
	 */
	public String generateJSON(String token){
		User user = userRepository.findByToken(token);
		int aspID = user.getOrganization().getAspID();
		String catalogStr = (String)cacheManager.get(aspID+Const.LMS_CATALOG_JSON);
		if (ObjectUtils.isEmpty(catalogStr)) {
			catalogStr = this.resetCatalog(aspID);
		}
		return catalogStr;
	}
	/**
	 * 
	  * 方法描述：返回课程目录时间戳
	  * @return 当前时间毫秒数
	  * @author heshuai
	  * @version 2012-4-13 上午10:53:48
	 */
	public String getTimestamp(String token){
		User user = userRepository.findByToken(token);
		int aspID = user.getOrganization().getAspID();
		String timestamp = (String)cacheManager.get(aspID+Const.LMS_CATALOG_TIMESTAMP);
		if (ObjectUtils.isEmpty(timestamp)) {
			timestamp = ObjectUtils.generateTimestamp();
			cacheManager.set(aspID+Const.LMS_CATALOG_TIMESTAMP, timestamp);
		}
		return timestamp;
	}
	/**
	 * 
	  * 方法描述：重置memcache中课程目录及时间戳
	  * @author heshuai
	  * @version 2012-5-17 下午03:05:28
	 */
	@Transactional(readOnly = true)
	public void reset(int aspID){
		//更新时间戳
		String timestamp = ObjectUtils.generateTimestamp();
		cacheManager.set(aspID+Const.LMS_CATALOG_TIMESTAMP, timestamp);
		//更新课程目录
		this.resetCatalog(aspID);
		//推送新课程消息
		Apns apns = new Apns(aspID, new PushWithAsp());
		apns.getMsgContent().setTitle("新课程");
		apns.getMsgContent().setMessage("您有了一门新的课程，立即查看");
		apns.addPlatform(Platform.ANDROID);
		apnsManager.pushNotifications(apns);
	}
	
	private String resetCatalog(int aspID){
		//获取目前有效课程目录
		List<Catalog> catalogs = catalogRepository.findByCourses_AspIDAndCourses_IsCharge(aspID, Course.CHARGE_STATUS_ALLOW);
		String catalogStr;
		if(catalogs != null){
			for (int i = 0, catalogSize = catalogs.size(); i < catalogSize; i++) {
				List<Course> courses = catalogs.get(i).getCourses();
				if(courses != null){
					for (int j = 0, courseSize = courses.size(); j < courseSize; j++) {
						courses.get(j).handle4Seri();
					}
				}
			}
		}
		catalogStr = JSONUtils.toJson(catalogs);  
		cacheManager.set(aspID+Const.LMS_CATALOG_JSON, catalogStr);
		return catalogStr;
	}
}
