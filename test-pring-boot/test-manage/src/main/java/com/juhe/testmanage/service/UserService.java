package com.juhe.testmanage.service;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<Map<String, Object>> queryUser();

    void addUser(Map<String, Object> params);

    void updateUser(Map<String, Object> params);

    void deleteUser(String id);
}
