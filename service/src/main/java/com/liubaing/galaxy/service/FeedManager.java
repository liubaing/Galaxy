package com.liubaing.galaxy.service;

import com.alibaba.fastjson.JSON;
import com.liubaing.galaxy.entity.Feedback;
import com.liubaing.galaxy.repository.mongodb.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author heshuai
 * @version 2012-10-26
 */
@Service
public class FeedManager {

    @Autowired
    private FeedRepository feedRepository;

    public void saveFeedback(String feedbackStr) {
        Feedback feedback = JSON.parseObject(feedbackStr, Feedback.class);
        if (feedback != null) {
            feedRepository.save(feedback);
        }
    }
}
