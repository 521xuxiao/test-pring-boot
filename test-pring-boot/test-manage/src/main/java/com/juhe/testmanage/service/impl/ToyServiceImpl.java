package com.juhe.testmanage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.juhe.testmanage.dao.ToyDao;
import com.juhe.testmanage.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ToyServiceImpl implements ToyService {
    @Autowired
    private ToyDao toyDao;

    @Override
    public void addToy(Map<String, Object> params) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        params.put("id", id);
        int num = toyDao.addToy(params);
        if(num <= 0) {
            throw new RuntimeException("新增失败");
        }
    }

    @Override
    @Transactional
    public void addToys(Map<String, Object> params) {
        String id = UUID.randomUUID().toString().replaceAll("-","");
        params.put("id", id);
        if(params.get("boyId") == null || "".equals(params.get("boyId").toString())){
            throw new RuntimeException("传入的boyId不能为空");
        }
        if(params.get("areaId") == null || "".equals(params.get("areaId").toString())){
            throw new RuntimeException("请选择地区");
        }
        int num = toyDao.addToys(params);
        if(num <= 0){
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public Map<String, Object> queryToy(int currentPage, int pageSize) {
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<Map<String, Object>> list = toyDao.queryToy();
        Long count = page.getTotal();
        Map<String, Object> map= new HashMap<>();
        map.put("count", count);
        map.put("toyList", list);
        return map;
    }

    @Override
    @Transactional
    public void updateToy(Map<String, Object> params) {
        int num = toyDao.params(params);
        if(num <= 0)
            throw new RuntimeException("修改失败");
    }

    @Override
    @Transactional
    public void deleteToy(String id) {
        int num = toyDao.deleteToy(id);
        if(num <= 0)
            throw new RuntimeException("删除失败");
    }

    @Override
    public List<Map<String, Object>> downloadWasherChargrBusiness() {
        List<Map<String, Object>> list = toyDao.downloadWasherChargrBusiness();
        return list;
    }

    @Override
    public void uploadExcel(List<Map<String, Object>> list) {
        int num = toyDao.uploadExcel(list);
        if(num <= 0){
            throw new RuntimeException("批量插入成功");
        }
    }

    @Override
    public List<Map<String, Object>> socketList(String name, int age) {
        List<Map<String, Object>> list = toyDao.socketList(name, age);
        System.err.println(name+":"+age);
        return list;
    }

    @Override
    public void addData() {
        Map<String, String> map = new HashMap<>();
        map.put("num", "8");
        map.put("phone", "13361040132");
        toyDao.addData(map);
    }
}
