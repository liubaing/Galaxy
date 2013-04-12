package com.liubaing.shiny_liubaing.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.liubaing.shiny_liubaing.entity.BookMark;

/**
 * 
 * 类说明:书签DAO接口
 * @author heshuai
 * @version 2012-3-12
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public interface BookMarkRepository extends CrudRepository<BookMark, Integer> {

	
	public List<BookMark> findByCourseidAndUserid(int courseID, int userID);

	@Modifying
	@Query("delete BookMark where courseid = ? and userid = ? ")
	public void deleteByCourseidAndUserid(int courseID, int userID);
	
}
