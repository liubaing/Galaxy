package com.liubaing.shiny_liubaing.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liubaing.shiny_liubaing.entity.User;
import com.liubaing.shiny_liubaing.entity.Version;
import com.liubaing.shiny_liubaing.repository.jpa.UserRepository;
import com.liubaing.shiny_liubaing.service.CatalogManager;
import com.liubaing.shiny_liubaing.service.CourseManager;
import com.liubaing.shiny_liubaing.service.VersionManager;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	private static final String VIEW = "admin";
	
	private static final int DEFAULT_APSID = 1;

	@Autowired
	private VersionManager versionManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseManager courseManager;
	
	@Autowired
	private CatalogManager catalogManager;
	
	@RequestMapping(value="/tools", method = RequestMethod.GET)
	public String adminTools()
	{
		return VIEW;
	}
	
	@RequestMapping(value="/version", method = RequestMethod.GET)
	@ResponseBody
	public String getVerison(@RequestParam(required = false) String callback)
	{
		if (StringUtils.isBlank(callback)) {
			return versionManager.checkUpdate();
		}else{
			return callback + "("+versionManager.checkUpdate()+")";
		}
	}
	
	@RequestMapping(value="/version/update", method = RequestMethod.POST)
	public String updateVerison(Version version)
	{
		versionManager.saveUpdateRecord(version);
		return "redirect:tools";
	}
	
	@RequestMapping(value = "/resetCache", method = RequestMethod.POST)
	public String resetCache(@RequestParam(required = false) String loginName){
		if (StringUtils.isBlank(loginName)) {
			catalogManager.reset(DEFAULT_APSID);
		}else{
			User user = userRepository.findByUsername(loginName);
			if (user != null) {
				courseManager.reset(user.getId());
			}
		}
		return "redirect:tools";
	}
}
