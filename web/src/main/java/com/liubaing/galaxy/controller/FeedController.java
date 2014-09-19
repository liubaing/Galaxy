package com.liubaing.galaxy.controller;

import com.liubaing.galaxy.service.FeedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for feed requests
 *
 * @author heshuai
 * @version 2012-10-26
 */
@Controller
@RequestMapping("feeds")
public class FeedController {

    @Autowired
    private FeedManager feedManager;

    @RequestMapping(value = "feedback", method = RequestMethod.POST)
    @ResponseBody
    public void feedback(@RequestBody String feedbackStr) {
        feedManager.saveFeedback(feedbackStr);
    }
}
