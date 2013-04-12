package com.liubaing.shiny_liubaing.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liubaing.shiny_liubaing.entity.Order;


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
