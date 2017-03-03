package com.liubaing.galaxy.worker.processor;

import com.alibaba.fastjson.JSON;
import com.liubaing.galaxy.entity.Account;
import com.liubaing.galaxy.persistence.AccountMapper;
import com.liubaing.galaxy.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataProcessor implements Processor {

    private static final String LOG_FMT = "=====> %d.成功%s共[%d]条数据 <=====";

    @Autowired
    private AccountMapper accountMapper;

    private volatile boolean isRunning = false;

    private int step = 1;

    private void print(String info, int count) {
        log.info(String.format(LOG_FMT, step++, info, count));
    }

    public void process() {
        if (!isRunning) {
            try {
                isRunning = true;
                List<Account> accountList = accountMapper.getAll();
                if (CollectionUtils.isNotEmpty(accountList)) {
                    Map<String, String> accountMap = accountList.stream()
                            .collect(Collectors.toMap(Account::getId, account -> JSON.toJSONString(account)));
                    SessionRepository.loadAllAccount(accountMap);
                    this.print("写入[账户数据]", accountList.size());
                } else {
                    log.warn("无有效账户数据");
                }
            } catch (Exception e) {
                log.error("刷数据异常", e);
            } finally {
                step = 1;
                isRunning = false;
            }
        }
    }
}
