package com.juhe.testmanage.controller;

import com.juhe.testcommon.pojo.ReturnData;
import com.juhe.testmanage.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/juhe/login")
public class LoginController {
    @Autowired(required = false)
    private LoginService loginService;

    /**
     * 测试登录接口
     * @param {"username": "", "password": ""}
     * @return
     */
    @PostMapping("/loginIn")
    public ReturnData loginIn(@RequestBody Map<String, Object> parms, HttpSession session) {
        loginService.loginIn(parms, session);
        return new ReturnData("登录成功");
    }
}
