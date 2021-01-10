package com.juhe.testmanage.controller;

import com.juhe.testcommon.pojo.ReturnData;
import com.juhe.testmanage.service.PageHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/pageHelper")
public class PageHelperController {
    @Autowired
    private PageHelperService pageHelperService;

    @GetMapping("/page")
    public ReturnData page(int currentPage, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map = pageHelperService.page(currentPage, pageSize);
        return new ReturnData(map);
    }
}
