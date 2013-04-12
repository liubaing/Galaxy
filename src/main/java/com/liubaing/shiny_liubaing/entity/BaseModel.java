package com.liubaing.shiny_liubaing.entity;

import java.io.Serializable;

import com.liubaing.shiny_liubaing.util.core.JSONUtils;

/**
 * 类说明:基类 实现序列化接口
 * @author heshuai
 * @version 2012-10-26
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@SuppressWarnings("serial")
public abstract class BaseModel implements Serializable{

	@Override
	public String toString() {
		return JSONUtils.toJson(this, false);
	}

}
