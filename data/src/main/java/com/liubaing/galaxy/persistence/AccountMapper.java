package com.liubaing.galaxy.persistence;

import com.liubaing.galaxy.entity.Account;

import java.util.Map;

/**
 * @author heshuai
 */
public interface AccountMapper {

    Account getAccount(Map<String, Object> param);

    void insertAccount(Account account);

}
