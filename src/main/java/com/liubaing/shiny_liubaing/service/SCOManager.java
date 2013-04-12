package com.liubaing.shiny_liubaing.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.liubaing.shiny_liubaing.entity.SDMRelation;
import com.liubaing.shiny_liubaing.repository.jpa.SCORepository;
import com.liubaing.shiny_liubaing.util.core.JSONUtils;
import com.liubaing.shiny_liubaing.util.core.ObjectUtils;

/**
 * 类说明:学习记录
 * User: heshuai
 * Date: 2012-3-23
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Service
public class SCOManager {
	
	@Autowired
	private SCORepository scoRepository;
	
	/**
	 * 
	 * 方法描述：通过课程ID，用户ID得到学习记录
	 * @param: 
	 * @return: 
	 * @author: heshuai
	 * @version: 2012-3-26 上午11:52:54
	 */
	public String get(int courseID,int userID)
	{
		List<SDMRelation> list = scoRepository.findByCourseidAndUserid(courseID, userID);
		if (!ObjectUtils.isEmpty(list)) {
			return JSONUtils.toJson(list,JSONUtils.SINCE_VERSION_11);
		}
		return null;
	}
	
	/**
	 * 
	  * 方法描述：持久化学习记录
	  * @param: 
	  * @return: 
	  * @author: heshuai
	  * @version: 2012-4-11 下午03:58:58
	 */
	@SuppressWarnings("rawtypes")
	public void add(String record,int courseID,int userID)
	{
		//删除学习记录
		scoRepository.delete(scoRepository.findByCourseidAndUserid(courseID, userID));
		//构造反射类型
		TypeToken<List<Map<Integer,SDMRelation>>> type = new TypeToken<List<Map<Integer,SDMRelation>>>(){};
		List<Map<Integer,SDMRelation>> list = JSONUtils.fromJson(record, type);
		List<SDMRelation> scos = new LinkedList<SDMRelation>();
		for(Map<Integer,SDMRelation> map : list)
		{
			for(Map.Entry entry : map.entrySet())
			{
				int lessonid = (Integer)entry.getKey();
				SDMRelation temp = (SDMRelation)entry.getValue();
				temp.setCourseid(courseID);
				temp.setUserid(userID);
				temp.setLessonid(lessonid);
				scos.add(temp);
			}
		}
		scoRepository.save(scos);
	}
}
