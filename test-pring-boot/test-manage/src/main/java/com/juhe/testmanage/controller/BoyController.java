package com.juhe.testmanage.controller;

import com.juhe.testcommon.pojo.ReturnData;
import com.juhe.testmanage.service.BoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/juhe/boy")
public class BoyController {
    @Autowired
    private BoyService boyService;

    /**
     * 新增boy基础表
     * @param params  {"name": ""}
     * @return
     */
    @PostMapping("/addBoy")
    public ReturnData addBoy(@RequestBody Map<String, Object> params) {
        boyService.addBoy(params);
        return new ReturnData("成功");
    }

    /**
     * 查询boy
     * @params {"currentPage": "", pageSize: ""}
     */
    @GetMapping("/queryBoy")
    public ReturnData queryBoy(Integer CurrentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map = boyService.queryBoy(CurrentPage, pageSize);
        return new ReturnData(map);
    }

    /**
     * 修改boy数据
     * @Param {"name": "", "id":""}
     */
    @PostMapping("updateBoy")
    public ReturnData updateBoy(@RequestBody Map<String, Object> params) {
        boyService.updateBoy(params);
        return new ReturnData("成功");
    }

    /**
     * 删除boy表
     * @params {"id": ''}
     */
    @GetMapping("/deleteBoy")
    public ReturnData deleteBoy(String id) {
        boyService.deleteBoy(id);
        return new ReturnData("删除成功");
    }
}
