package com.liubaing.galaxy.service;

import com.liubaing.galaxy.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 类说明:用户业务
 *
 * @author heshuai
 * @version Feb 24, 2012
 */
@Service
public class UserManager {
    @Autowired
    private UserRepository userRepository;

}
