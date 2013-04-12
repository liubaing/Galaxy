package com.liubaing.shiny_liubaing.service;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.liubaing.shiny_liubaing.entity.BookMark;
import com.liubaing.shiny_liubaing.repository.jpa.BookMarkRepository;
import com.liubaing.shiny_liubaing.util.core.JSONUtils;
import com.liubaing.shiny_liubaing.util.core.ObjectUtils;

/**
 * 类说明:书签业务
 * @author heshuai
 * @version 2012-3-12
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Service
public class BookMarkManager {

	@Autowired
	private BookMarkRepository bookMarkRepository;
	
	/**
	 * 
	  * 方法描述：返回当前课程中该用户的书签列表，格式为json
	  * @author: heshuai
	  * @version: 2012-3-31 下午01:23:56
	 */
	public String get(int courseID,int userID)
	{
		List<BookMark> bookMarks = bookMarkRepository.findByCourseidAndUserid(courseID, userID);
		if (!ObjectUtils.isEmpty(bookMarks)) {
			Type type = new TypeToken<List<BookMark>>(){}.getType();
			return JSONUtils.toJson(bookMarks, type);
		}
		return null;
	}
	/**
	 * 
	  * 方法描述：解析json数组为书签对象集合，并持久化
	  * @author: heshuai
	  * @version: 2012-3-13 下午07:19:53
	 */
	public void add(String bookmark, int courseID, int userID)
	{
		bookMarkRepository.deleteByCourseidAndUserid(courseID, userID);
		TypeToken<List<BookMark>> type = new TypeToken<List<BookMark>>(){};
		List<BookMark> bookMarks = JSONUtils.fromJson(bookmark, type);
		if (!ObjectUtils.isEmpty(bookMarks)) {
			for (BookMark _bookMark : bookMarks) {
				_bookMark.setCourseid(courseID);
				_bookMark.setUserid(userID);
			}
			bookMarkRepository.save(bookMarks);
		}
	}
}
