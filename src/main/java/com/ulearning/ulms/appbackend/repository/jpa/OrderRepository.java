package com.ulearning.ulms.appbackend.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ulearning.ulms.appbackend.entity.Order;


/**
 * 订单DAO
 * @author heshuai
 * @version 2012-10-9
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	public Order findByOrderNo(String orderNo);
	
}
