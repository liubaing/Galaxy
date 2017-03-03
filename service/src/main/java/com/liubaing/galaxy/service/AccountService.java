package com.liubaing.galaxy.service;

import com.liubaing.galaxy.entity.Account;
import com.liubaing.galaxy.persistence.AccountMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 账户管理
 *
 * @author heshuai
 * @version 9/24/14.
 */
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public Account getByEmail(final String email) {
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("email", StringUtils.lowerCase(email));
        }};
        return accountMapper.getAccount(param);
    }

    public Account getByAccountId(final String accountId) {
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("accountId", accountId);
        }};
        return accountMapper.getAccount(param);
    }

    public void save(Account account) {
        account.createDate = new Date();
        account.password = DigestUtils.md5Hex(account.password);
        accountMapper.insertAccount(account);
    }
}