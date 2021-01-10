package com.juhe.testmanage.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface PageHelperDao {
    List<Map<String, Object>> page();
}
