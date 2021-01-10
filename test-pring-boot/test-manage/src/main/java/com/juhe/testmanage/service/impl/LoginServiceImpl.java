package com.juhe.testmanage.service.impl;

import com.juhe.testmanage.dao.LoginDao;
import com.juhe.testmanage.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;

    @Transactional
    @Override
    public void loginIn(Map<String, Object> parms, HttpSession session) {
        //带着username和password查询userId存到session里面
        Map<String, String> map = loginDao.queryUserId(parms);
        if(map == null)
            throw new RuntimeException("没有此用户");
        String id = map.get("id");
        String salt = map.get("salt");
        String password = map.get("password");
        session.setAttribute("user_key", id);
        String miPassword = DigestUtils.md5DigestAsHex((parms.get("password").toString() + salt).getBytes());
        if(!miPassword.equals(password))
            throw new RuntimeException("密码不正确");
    }
}
