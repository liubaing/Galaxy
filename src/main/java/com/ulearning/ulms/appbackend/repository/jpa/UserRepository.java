package com.ulearning.ulms.appbackend.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ulearning.ulms.appbackend.entity.User;


/**
 * 类说明:用户DAO接口
 * @author heshuai
 * @version Feb 24, 2012
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsernameAndPassword(String username, String password);
	
	public User findByUsername(String username);
	
	public User findByToken(String token);
	
	public List<User> findByOrganizationAspID(int aspID);
	
}
