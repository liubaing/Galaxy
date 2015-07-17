package com.liubaing.galaxy.web.controller;

import com.liubaing.galaxy.entity.Account;
import com.liubaing.galaxy.service.AccountService;
import com.liubaing.galaxy.web.vo.ResponseVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("session")
public class SessionController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo signIn(@RequestParam String email, @RequestParam String password) {
        ResponseVo responseVo = ResponseVo.newResponse();
        Account account = accountService.getByEmail(email);
        if (account != null) {
            if (account.state == Account.State.DISABLE.getCode()) {
                responseVo.buildError(ResponseVo.ErrorCode.DISABLE_ACCOUNT);
            } else if (!StringUtils.equals(account.password, DigestUtils.md5Hex(password))) {
                responseVo.buildError(ResponseVo.ErrorCode.PASSWORD_INCORRECT);
            }
        }
        return responseVo;
    }

}
