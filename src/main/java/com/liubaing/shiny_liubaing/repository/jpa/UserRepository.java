package com.liubaing.shiny_liubaing.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liubaing.shiny_liubaing.entity.User;


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
	
}
