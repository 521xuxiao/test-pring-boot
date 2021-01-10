package com.juhe.testmanage.service;

import java.util.List;
import java.util.Map;

public interface ToyService {
    void addToy(Map<String, Object> params);

    void addToys(Map<String, Object> params);

    Map<String, Object> queryToy(int currentPage, int pageSize);

    void updateToy(Map<String, Object> params);

    void deleteToy(String id);

    List<Map<String, Object>> downloadWasherChargrBusiness();

    void uploadExcel(List<Map<String, Object>> list);

    List<Map<String, Object>> socketList(String name, int age);

    void addData();
}
