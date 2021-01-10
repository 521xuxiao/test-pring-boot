package com.juhe.testmanage.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ToyDao {
    int addToy(Map<String, Object> params);

    int addToys(Map<String, Object> params);

    List<Map<String, Object>> queryToy();

    int params(Map<String, Object> params);

    int deleteToy(String id);

    List<Map<String, Object>> downloadWasherChargrBusiness();

    int uploadExcel(List<Map<String, Object>> list);

    List<Map<String, Object>> socketList(String name, int age);

    void addData(Map<String, String> map);
}
