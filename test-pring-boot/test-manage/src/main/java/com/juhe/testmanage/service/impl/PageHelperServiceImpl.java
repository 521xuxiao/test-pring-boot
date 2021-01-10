package com.juhe.testmanage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.juhe.testmanage.dao.PageHelperDao;
import com.juhe.testmanage.service.PageHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PageHelperServiceImpl implements PageHelperService {
    @Autowired
    private PageHelperDao pageHelperDao;
    @Override
    public Map<String, Object> page(int currentPage, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<Map<String, Object>> list = pageHelperDao.page();
        long count = page.getTotal();
        map.put("list", list);
        map.put("count", count);
        return map;
    }
}
