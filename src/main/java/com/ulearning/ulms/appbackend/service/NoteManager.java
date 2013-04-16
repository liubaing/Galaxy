package com.ulearning.ulms.appbackend.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.ulearning.ulms.appbackend.entity.Note;
import com.ulearning.ulms.appbackend.repository.jpa.NoteRepository;
import com.ulearning.ulms.appbackend.util.core.JSONUtils;
import com.ulearning.ulms.appbackend.util.core.ObjectUtils;

/**
 * 
 * 类说明:笔记业务
 * @author heshuai
 * @version 2012-3-12
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Service
public class NoteManager {

	@Autowired
	private NoteRepository noteRepository;

	/**
	 * 
	  * 方法描述：返回当前课程中该用户的笔记，格式为json
	  * @param: 
	  * @return: 
	  * @author: heshuai
	  * @version: 2012-3-31 下午02:19:19
	 */
	public String get(int courseID,int userID)
	{
		List<Note> noteList = noteRepository.findByCourseidAndUserid(courseID, userID);
		if (!ObjectUtils.isEmpty(noteList)) {
			return JSONUtils.toJson(noteList);
		}
		return null;
	}
	
	/**
	 * 
	  * 方法描述：解析json数组为note对象集合，并持久化
	  * @param: [{"0" : [{"text":"Test7","id":{"page":3},"length":"0","start":"0"}]}]
	  * @return: 
	  * @author: heshuai
	  * @version: 2012-3-13 下午07:02:50
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public void add(String note, int courseID, int userID)
	{
		//首先执行删除该用户该课程的note
		noteRepository.deleteByCourseidAndUserid(courseID, userID);
		//构造反射类型
		TypeToken<List<Map<Integer,List<Note>>>> type = new TypeToken<List<Map<Integer,List<Note>>>>(){};
		List<Map<Integer,List<Note>>> list = JSONUtils.fromJson(note, type);
		List<Note> notes = new LinkedList<Note>();
		Date date = new Date();
		for(Map<Integer,List<Note>> map : list)
		{
			for(Map.Entry entry : map.entrySet())
			{
				int lessonID = (Integer)entry.getKey();
				List<Note> tempList = (List<Note>)entry.getValue();
				for (Note tempNote : tempList) {
					tempNote.setCourseid(courseID);
					tempNote.setCreateDate(date);
					tempNote.setLessonid(lessonID);
					tempNote.setUserid(userID);
					notes.add(tempNote);
				}
			}
		}
		if (!ObjectUtils.isEmpty(notes)) {
			noteRepository.save(notes);
		}
	}
}
