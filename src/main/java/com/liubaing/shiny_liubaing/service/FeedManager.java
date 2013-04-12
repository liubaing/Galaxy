package com.liubaing.shiny_liubaing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liubaing.shiny_liubaing.entity.Feedback;
import com.liubaing.shiny_liubaing.repository.mongodb.FeedRepository;
import com.liubaing.shiny_liubaing.util.core.JSONUtils;

/**
 * 
 * @author heshuai
 * @version 2012-10-26
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Service
public class FeedManager {

	@Autowired
	private FeedRepository feedRepository;
	
	public void saveFeedback(String feedbackStr)
	{
		Feedback feedback = JSONUtils.fromJson(feedbackStr, Feedback.class);
		if (feedback != null ) {
			feedRepository.save(feedback);
		}
	}
}
