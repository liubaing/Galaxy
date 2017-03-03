package cn.nhorizon.ads.server.service;

import com.liubaing.galaxy.entity.Account;
import com.liubaing.galaxy.persistence.AccountMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccountServiceTest extends BaseTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public Account testGetByEmail() {
        String email = "";
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("email", StringUtils.lowerCase(email));
        }};
        return accountMapper.getAccount(param);
    }

    @Test
    public Account testGetByAccountId() {
        String accountId = "";
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("accountId", accountId);
        }};
        return accountMapper.getAccount(param);
    }


    public void testSave() {
        Account account = new Account();
        account.createDate = new Date();
        account.password = DigestUtils.md5Hex(account.password);
        accountMapper.insertAccount(account);
    }
}