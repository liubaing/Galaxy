package com.liubaing.galaxy.service;

import com.liubaing.galaxy.entity.Account;
import com.liubaing.galaxy.persistence.AccountMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author heshuai
 * @version 9/24/14.
 */
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public Account getByEmail(String email) {
        return accountMapper.getAccountByEmail(StringUtils.lowerCase(email));
    }
}