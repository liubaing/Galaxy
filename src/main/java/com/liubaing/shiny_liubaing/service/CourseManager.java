package com.liubaing.shiny_liubaing.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liubaing.shiny_liubaing.entity.Course;
import com.liubaing.shiny_liubaing.entity.UserCourse;
import com.liubaing.shiny_liubaing.repository.jpa.CourseRepository;
import com.liubaing.shiny_liubaing.repository.jpa.UserCourseRepository;
import com.liubaing.shiny_liubaing.service.apns.PushWithUser;
import com.liubaing.shiny_liubaing.util.core.Const;
import com.liubaing.shiny_liubaing.util.core.DateTimeUtils;
import com.liubaing.shiny_liubaing.util.core.JSONUtils;
import com.liubaing.shiny_liubaing.util.core.ObjectUtils;
import com.liubaing.shiny_liubaing.util.support.CacheManager;


/**
 * 类说明:我的课程
 * @author heshuai
 * @version Feb 24, 2012
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Service 
public class CourseManager
{
	@Autowired 
	private CacheManager cacheManager;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserCourseRepository userCourseRepository;
	
	@Autowired
	private ApnsManager apnsManager;
	
	@Autowired
	private UserManager userManager;
	
	private static final String DATETOSTR_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 
	  * 方法描述：该用户的课程timestamp
	  * @param: 
	  * @return: 格式范例<code>{"timestamp":137010245846}</code>
	  * @author: heshuai
	  * @version: 2012-5-15 下午01:25:06
	 */
	public String getTimestamp(int userID){
		String timestamp = (String)cacheManager.get(userID+Const.LMS_COURSES_TIMESTAMP);
		if (ObjectUtils.isBlank(timestamp)) {
			timestamp = this.resetTimestamp(userID);
		}
		return timestamp;
	}
	
	/**
	 * 
	  * 方法描述：返回该用户的JSON格式课程列表
	  * @param userID 用户ID
	  * @return 格式范例<code>[{"id":2488,"title":"巴塞尔协议","code":"cba201027","price":50,"description":"第三版"}]</code>
	  * @author heshuai
	  * @version 2012-3-31 下午03:54:06
	 */
	public String getCourses(int userID)
	{
		//得到该用户的课程json格式
		String mycourses = (String)cacheManager.get(userID+Const.LMS_COURSES_JSON);
		if (ObjectUtils.isBlank(mycourses)) {
			mycourses = this.resetCourses(userID);
		}
		return mycourses;
	}

	
	
	/**
	 * 
	 * 方法描述：更新课程下载次数
	 * 			每人每课下载次数不超过5次
	 * @param 用户ID，课程ID
	 * @author heshuai
	 * @version 2012-3-23 上午11:36:26
	 */
	public void updateCount(int courseID, int userID)
	{
		UserCourse userCourse = userCourseRepository.findById_CourseIDAndId_UserID(courseID, userID);
		int count = userCourse.getDownloadCount();
		userCourse.setDownloadCount(++count);
		//当下载次数大于等于5时，更新memcache信息
		if (count >= Const.DOWNLOAD_MAX) {
			this.resetTimestamp(userID);
			this.resetCourses(userID);
		}
	}
	
	/**
	 * 
	  * 方法描述：重置memcache中该用户的课程列表及时间戳
	  * 			并推送消息
	  * @author: heshuai
	  * @version: 2012-5-17 下午03:05:28
	 */
	public void reset(int userID){
		this.resetTimestamp(userID);
		this.resetCourses(userID);
		//推送新课程消息
		apnsManager.pushNotifications(new PushWithUser(), userID);
	}
	
	/**
	 * 
	  * 方法描述：重置memcache中该用户的课程列表的时间戳
	  * @author: heshuai
	  * @version: 2012-5-17 下午03:05:28
	 */
	private String resetTimestamp(int userID)
	{
		String timestamp = ObjectUtils.generateTimestamp();
		//更新时间戳
		cacheManager.set(userID+Const.LMS_COURSES_TIMESTAMP, timestamp);
		return timestamp;
	}
	/**
	 * 
	  * 方法描述：重置memcache中该用户的课程列表
	  * @author: heshuai
	  * @version: 2012-5-17 下午03:05:28
	 */
	private String resetCourses(int userID){
		List<Course> temp = new LinkedList<Course>();
		if (!userManager.isStu(userID)) {
			int aspID = userManager.getAspIDByUserID(userID);
			List<Course> courses = courseRepository.findByAspIDAndIsCharge(aspID, Course.CHARGE_STATUS_ALLOW);
			for (Course course : courses) {
				temp.add(course.handle4Seri());
			}
		} else {
			List<UserCourse> userCourses = userCourseRepository.findById_UserIDOrderByApplyTimeDesc(userID);
			Map<Integer,Course> mapAll = this.getAllCourse();
			Date date = new Date();
			for(UserCourse userCourse : userCourses)
			{
				int courseID = userCourse.getId().getCourseID();
				Course course = mapAll.get(courseID);
				if (course != null) {
					//如果下载次数大于等于5次或者超过学习期限，取消序列化下载链接字段
					Date limit = userCourse.getExpireTime();
					if (!(limit != null && date.before(limit) && userCourse.getDownloadCount() < Const.DOWNLOAD_MAX)) {
						course.setLink(null);
					}
					course.setLimit(DateTimeUtils.DateToStr(limit, DATETOSTR_FORMAT));
					temp.add(course.handle4Seri());
				}
			}
		}
		String mycourses = JSONUtils.toJson(temp);
		cacheManager.set(userID+Const.LMS_COURSES_JSON, mycourses);
		return mycourses;
	}
	
	/**
	 * 
	  * 方法描述：得到所有课程 Map
	  * @author: heshuai
	  * @version: 2012-3-31 下午03:52:31
	 */
	private Map<Integer, Course> getAllCourse()
	{
		Map<Integer, Course> map = new HashMap<Integer, Course>();
		List<Course> courses = courseRepository.findAll();
		for(Course course : courses)
		{
			map.put(course.getId(), course);
		}
		return map;
	}
}
