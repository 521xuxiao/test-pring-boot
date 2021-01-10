package com.juhe.testmanage.service;

import java.util.Map;

public interface BoyService {
    void addBoy(Map<String, Object> params);

    Map<String, Object> queryBoy(Integer CurrentPage, Integer pageSize);

    void updateBoy(Map<String, Object> params);

    void deleteBoy(String id);
}
