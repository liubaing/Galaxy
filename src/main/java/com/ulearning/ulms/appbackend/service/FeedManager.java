package com.ulearning.ulms.appbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulearning.ulms.appbackend.entity.Feedback;
import com.ulearning.ulms.appbackend.repository.mongodb.FeedRepository;
import com.ulearning.ulms.appbackend.util.core.JSONUtils;

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
