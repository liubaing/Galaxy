package com.ulearning.ulms.appbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulearning.ulms.appbackend.service.BookMarkManager;

@Controller
@RequestMapping("/course/bookmarks")
public class BookMarkController {
	
	@Autowired
	private BookMarkManager bookMarkManager;
	
	@RequestMapping(value="/{courseID}/{userID}",method=RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable int courseID,@PathVariable int userID)
	{
		return bookMarkManager.get(courseID, userID);
	}
	
	@RequestMapping (value="/{courseID}/{userID}",method=RequestMethod.POST)
	@ResponseBody
	public void add(@PathVariable int courseID, @PathVariable int userID, 
			@RequestBody String bookmark)
	{
		bookMarkManager.add(bookmark,courseID,userID);
	}
	
}
