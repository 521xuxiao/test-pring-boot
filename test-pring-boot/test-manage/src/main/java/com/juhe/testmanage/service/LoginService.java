package com.juhe.testmanage.service;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface LoginService {
    void loginIn(Map<String, Object> parms, HttpSession session);
}
