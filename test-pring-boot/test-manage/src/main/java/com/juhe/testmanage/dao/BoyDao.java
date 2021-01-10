package com.juhe.testmanage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface BoyDao {
    int addBoy(Map<String, Object> params);

    List<Map<String, Object>> queryBoy(@Param("CurrentPage")Integer CurrentPage, @Param("pageSize")Integer pageSize);

    int updateBoy(Map<String, Object> params);

    int deleteBoy(@Param("id") String id);
}
