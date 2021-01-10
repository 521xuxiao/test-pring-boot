package com.juhe.testmanage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface AreaDao {
    int addArea(Map<String, Object> params);

    int updateArea(Map<String, Object> params);

    int deleteArea(@Param("id") String id);

    List<Map<String, Object>> queryArea(@Param("id") String id);

    List<Map<String, Object>> queryAreatList();
}
