package com.liubaing.galaxy.entity;

import java.io.Serializable;

import com.liubaing.galaxy.util.core.JSONUtils;

/**
 * 类说明:基类 实现序列化接口
 * @author heshuai
 * @version 2012-10-26
 *
 */
@SuppressWarnings("serial")
public abstract class BaseModel implements Serializable{

	@Override
	public String toString() {
		return JSONUtils.toJson(this, false);
	}

}
