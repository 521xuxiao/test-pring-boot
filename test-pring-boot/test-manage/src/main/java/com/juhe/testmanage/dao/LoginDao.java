package com.juhe.testmanage.dao;

import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpSession;
import java.util.Map;
@Mapper
public interface LoginDao {

    Map<String, String> queryUserId(Map<String, Object> parms);
}
