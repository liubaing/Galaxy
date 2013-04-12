package com.liubaing.shiny_liubaing.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.liubaing.shiny_liubaing.entity.SDMRelation;

/**
 * 
 * 类说明:学习记录DAO
 * @author heshuai
 * @version 2012-3-12
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */

public interface SCORepository extends CrudRepository<SDMRelation, Integer>{
	
	public List<SDMRelation> findByCourseidAndUserid(int courseID, int userID);
	
}
