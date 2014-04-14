package com.liubaing.galaxy.controller;

import com.liubaing.galaxy.task.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class UserController
{
	@Autowired
	private UserManager userManager;
	
}
