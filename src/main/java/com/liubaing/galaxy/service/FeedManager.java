package com.liubaing.galaxy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liubaing.galaxy.entity.Feedback;
import com.liubaing.galaxy.repository.mongodb.FeedRepository;
import com.liubaing.galaxy.util.core.JSONUtils;

/**
 * 
 * @author heshuai
 * @version 2012-10-26
 *
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
