package com.juhe.testmanage.service.impl;

import com.juhe.testmanage.dao.AreaDao;
import com.juhe.testmanage.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Transactional
    @Override
    public void addArea(Map<String, Object> params) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        params.put("id", id);
        int num = areaDao.addArea(params);
        if(num <= 0) {
            throw new RuntimeException("新增失败");
        }
    }

    @Transactional
    @Override
    public void updateArea(Map<String, Object> params) {
        int num = areaDao.updateArea(params);
        if(num <= 0) {
            throw new RuntimeException("修改失败");
        }
    }

    @Transactional
    @Override
    public void deleteArea(String id) {
        List<Map<String, Object>> list = areaDao.queryArea(id);
        if(list.size()>0) {
            throw new RuntimeException("要删除的数据有子, 请先把子删除再来删此数据");
        }
        int num = areaDao.deleteArea(id);
        if(num <= 0) {
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public List<Map<String, Object>> queryAreatList() {
        List<Map<String, Object>> list = areaDao.queryAreatList();
        List<Map<String, Object>> newList = new ArrayList<>();
        for (Map<String, Object> map1: list) {
            if("#".equals(map1.get("parent_id"))) {
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("parent_id", map1.get("parent_id"));
                newMap.put("id", map1.get("id"));
                newMap.put("name", map1.get("name"));
                newList.add(newMap);
            }
        }
        List<Map<String, Object>> changeList =  circy(newList, list);
        return changeList;
    }
    /**
     * 递归处理数据
     */
    public List<Map<String, Object>> circy(List<Map<String, Object>> newList, List<Map<String, Object>> oldList) {
        for (Map<String, Object> outMap: newList) {
            List<Map<String, Object>> middleList = new ArrayList<>();
            for (Map<String, Object> oldMap:  oldList) {
                if(outMap.get("id").toString().equals( oldMap.get("parent_id").toString() )) {
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("id", oldMap.get("id").toString());
                    map1.put("name", oldMap.get("name").toString());
                    map1.put("parent_id", oldMap.get("parent_id").toString());
                    middleList.add(map1);
                }
            }
            if(middleList.size() != 0) {
                circy(middleList, oldList);
                outMap.put("children", middleList);
            }
        }
        return newList;
    }
}
