package com.liubaing.galaxy.web.controller;

import com.google.code.kaptcha.Producer;
import com.liubaing.galaxy.entity.Account;
import com.liubaing.galaxy.service.AccountService;
import com.liubaing.galaxy.util.Constants;
import com.liubaing.galaxy.web.vo.ResponseVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("session")
public class SessionController {

    private static final String VERIFY_CODE_KEY = "_vk";

    @Autowired
    private Producer captchaProducer;
    @Autowired
    private AccountService accountService;

    @RequestMapping("login")
    @ResponseBody
    public ResponseVo signIn(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String verifyCode,
                             HttpSession session) {
        ResponseVo<Account> responseVo = ResponseVo.newResponse();
        if (StringUtils.isNotEmpty(verifyCode)
                && StringUtils.equalsIgnoreCase(String.valueOf(session.getAttribute(VERIFY_CODE_KEY)), verifyCode)) {
            session.removeAttribute(VERIFY_CODE_KEY);
            Account account = accountService.getByEmail(email);
            if (account != null) {
                if (StringUtils.equals(account.password, DigestUtils.md5Hex(password))) {
                    session.setAttribute(Constants.ACCOUNT_KEY, account);
                    responseVo.setData(account);
                } else {
                    responseVo.buildError(ResponseVo.ErrorCode.PASSWORD_INCORRECT);
                }
            } else {
                responseVo.buildError(ResponseVo.ErrorCode.INVALID_EMAIL);
            }
        } else {
            responseVo.buildError(ResponseVo.ErrorCode.INCORRECT_VERIFICATION_CODE);
        }
        return responseVo;
    }

    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo checkMaterialName(@RequestParam String email) {
        ResponseVo responseVo = ResponseVo.newResponse();
        if (accountService.getByEmail(email) != null) {
            responseVo.buildError(ResponseVo.ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        return responseVo;
    }

    @RequestMapping("join")
    @ResponseBody
    public ResponseVo signUp(@RequestBody Account account,
                             @RequestParam String verifyCode,
                             HttpSession session) {
        ResponseVo responseVo = ResponseVo.newResponse();
        if (StringUtils.isNotEmpty(verifyCode)
                && StringUtils.equalsIgnoreCase(String.valueOf(session.getAttribute(VERIFY_CODE_KEY)), verifyCode)) {
            session.removeAttribute(VERIFY_CODE_KEY);
            accountService.save(account);
        } else {
            responseVo.buildError(ResponseVo.ErrorCode.INCORRECT_VERIFICATION_CODE);
        }
        return responseVo;
    }

    @RequestMapping("logout")
    @ResponseBody
    public ResponseVo signOut(HttpSession session) {
        session.removeAttribute(Constants.ACCOUNT_KEY);
        return ResponseVo.newResponse();
    }

    /**
     * 生成验证码,摘于官方demo
     */
    @RequestMapping("captchaImage")
    public ModelAndView createCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        request.getSession().setAttribute(VERIFY_CODE_KEY, capText);

        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);

        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

}
