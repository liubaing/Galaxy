package com.ulearning.ulms.appbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulearning.ulms.appbackend.service.CourseManager;

@Controller
@RequestMapping("/course/mycourses")
public class CourseController 
{
	@Autowired
	private CourseManager courseManager;
	
	@RequestMapping(value="/{userID}",method=RequestMethod.GET)
	@ResponseBody
	public String getByUserID(@PathVariable int userID)
	{
		return courseManager.getCourses(userID);
	}
	
	@RequestMapping(value="/{courseID}/{userID}",method=RequestMethod.PUT)
	@ResponseBody
	public void updateCount(@PathVariable int courseID,@PathVariable int userID)
	{
		courseManager.updateCount(courseID, userID); 
	}
	
	@RequestMapping(value="/timestamp/{userID}",method=RequestMethod.GET)
	@ResponseBody
	public String getTimestamp(@PathVariable int userID)
	{
		return courseManager.getTimestamp(userID);
	}
	
	@RequestMapping(value="/reset/{userID}",method=RequestMethod.GET)
	@ResponseBody
	public void reset(@PathVariable int userID)
	{
		courseManager.reset(userID);
	}
	
}
