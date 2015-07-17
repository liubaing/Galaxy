package com.liubaing.galaxy.persistence;


import com.liubaing.galaxy.entity.Account;

/**
 * @author heshuai
 */
public interface AccountMapper {

    Account getAccountByEmail(String email);

    void insertAccount(Account account);

    void updateAccount(Account account);

}
