package com.liubaing.galaxy.persistence;

import com.liubaing.galaxy.entity.Account;

import java.util.List;
import java.util.Map;

/**
 * @author heshuai
 */
public interface AccountMapper {

    List<Account> getAll();

    Account getAccount(Map<String, Object> param);

    void insertAccount(Account account);

}
