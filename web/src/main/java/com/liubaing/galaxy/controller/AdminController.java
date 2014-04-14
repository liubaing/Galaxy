package com.liubaing.galaxy.controller;

import com.alibaba.fastjson.JSON;
import com.liubaing.galaxy.task.VersionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	

	@Autowired
	private VersionManager versionManager;

	@RequestMapping(value="/version", method = RequestMethod.GET)
	@ResponseBody
	public String getVerison(@RequestParam(required = false) String callback)
	{
		if (StringUtils.isBlank(callback)) {
			return JSON.toJSONString(versionManager.checkUpdate());
		}else{
			return callback + "("+versionManager.checkUpdate()+")";
		}
	}
}
