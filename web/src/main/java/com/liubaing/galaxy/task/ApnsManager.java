package com.liubaing.galaxy.task;

import com.liubaing.galaxy.repository.jpa.DeviceTokenRepository;
import com.liubaing.galaxy.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类说明:消息推送
 * @author heshuai
 * @version 2012-5-7
 *
 */
@Service
public class ApnsManager {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DeviceTokenRepository deviceTokenRepository;
	
}
