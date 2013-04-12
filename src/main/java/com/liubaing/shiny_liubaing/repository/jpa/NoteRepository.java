package com.liubaing.shiny_liubaing.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.liubaing.shiny_liubaing.entity.Note;

/**
 * 
 * 类说明:笔记DAO
 * @author heshuai
 * @version 2012-3-12
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public interface NoteRepository extends CrudRepository<Note, Integer>{

	public List<Note> findByCourseidAndUserid(int courseID, int userID);
	
	@Modifying
	@Query("delete Note where courseid = ?1 and userid = ?2 ")
	public void deleteByCourseidAndUserid(int courseID, int userID);
	
}
