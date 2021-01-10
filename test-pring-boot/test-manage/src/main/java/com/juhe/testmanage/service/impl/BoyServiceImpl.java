package com.juhe.testmanage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.juhe.testmanage.dao.BoyDao;
import com.juhe.testmanage.service.BoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BoyServiceImpl implements BoyService {
    @Autowired
    private BoyDao boyDao;

    @Override
    @Transactional
    public void addBoy(Map<String, Object> params) {
        if(params.get("name") == null || "".equals(params.get("name").toString())) {
            throw new RuntimeException("传入的参数不能为空");
        }
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        params.put("id", id);
        int num = boyDao.addBoy(params);
        if(num <= 0)
            throw new RuntimeException("新增失败");
    }

    @Override
    public Map<String, Object> queryBoy(Integer CurrentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Page<Object> page = PageHelper.startPage(CurrentPage, pageSize);
        List<Map<String, Object>> list = boyDao.queryBoy(CurrentPage, pageSize);
        Long count = page.getTotal();
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    @Transactional
    @Override
    public void updateBoy(Map<String, Object> params) {
        int num = boyDao.updateBoy(params);
        if(num<=0) {
            throw new RuntimeException("更新失败");
        }
    }

    @Transactional
    @Override
    public void deleteBoy(String id) {
        int num = boyDao.deleteBoy(id);
        if(num<=0) {
            throw new RuntimeException("删除失败!!");
        }
    }
}
