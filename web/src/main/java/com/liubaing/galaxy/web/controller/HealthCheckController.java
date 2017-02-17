package com.liubaing.galaxy.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liubaing.galaxy.profiler.JvmInfoPicker;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 监控
 * Pinpoint作为APM
 * TODO http://metrics.dropwizard.io/
 *
 * @author heshuai
 * @version 2016/11/25.
 */
@Controller
public class HealthCheckController {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "ping", method = RequestMethod.HEAD)
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "runtime", method = RequestMethod.GET)
    @ResponseBody
    public String runtime() {
        JSONObject jsonObject = new JSONObject();
        JvmInfoPicker picker = JvmInfoPicker.getInstance();
        jsonObject.put("host", picker.getHostName());
        jsonObject.put("pid", picker.getPid());
        jsonObject.put("start", picker.getStartTime());
        jsonObject.put("cpu", picker.getCpu());
        jsonObject.put("thread", picker.getThreadCount());
        jsonObject.put("mem", picker.getMemory());
        return JSON.toJSONString(jsonObject);
    }
}