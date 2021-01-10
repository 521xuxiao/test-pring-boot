package com.juhe.testmanage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserDao {
    List<Map<String, Object>> queryUser();

    int addUser(Map<String, Object> params);

    Map<String, String> queryUserId(@Param("admin") String admin);

    int updateUser(Map<String, Object> params);

    int deleteUser(String id);

    Map<String, String> userSingle(Map<String, Object> params);
}
