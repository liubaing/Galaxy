package com.liubaing.shiny_liubaing.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liubaing.shiny_liubaing.entity.Catalog;
/**
 * 类说明:课程目录DAO接口
 * @author heshuai
 * @version Feb 24, 2012
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public interface CatalogRepository extends JpaRepository<Catalog, Integer>{
	
	public List<Catalog> findByCourses_AspIDAndCourses_IsCharge(int aspID, int isCharge);
	
}
