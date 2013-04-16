package com.ulearning.ulms.appbackend.repository.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ulearning.ulms.appbackend.entity.UserCourse;
/**
 * 
 * 类说明:用户课程DAO
 * @author heshuai
 * @version 2012-2-24
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public interface UserCourseRepository extends JpaRepository<UserCourse, Serializable>{
	
	public List<UserCourse> findById_CourseID(int courseID);
	
	public List<UserCourse> findById_UserIDOrderByApplyTimeDesc(int userID);
	
	public UserCourse findById_CourseIDAndId_UserID(int courseID, int userID);
	
}
