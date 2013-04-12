package com.liubaing.shiny_liubaing.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.liubaing.shiny_liubaing.entity.DeviceToken;

/**
 * 类说明:设备令牌持久化
 * @author heshuai
 * @version 2012-5-7
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Integer>{

	public DeviceToken findByUseridAndDevicetoken(int userID, String deviceToken);
	
	public List<DeviceToken> findByStatus(int status);
	
	public List<DeviceToken> findByUseridAndStatus(int userID, int status);
	
	@Modifying
	@Query("delete from DeviceToken where devicetoken in ?1 ")
	public void deleteByDevicetokenIn(String[] tokens);
	
}
