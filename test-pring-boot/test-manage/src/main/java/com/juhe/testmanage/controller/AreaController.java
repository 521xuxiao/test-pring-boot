package com.juhe.testmanage.controller;

import com.juhe.testcommon.pojo.ReturnData;
import com.juhe.testmanage.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/juhe/area")
@ResponseBody
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * 新增地方表数据
     * @param params   {"parentId":'', "name":''}
     * @return
     */
    @PostMapping("/addArea")
    public ReturnData addArea(@RequestBody Map<String, Object> params) {
        areaService.addArea(params);
        return new ReturnData("新增成功");
    }

    /**
     * 修改地区表
     * @param params  {"name": '', id: ''}
     * @return
     */
    @PostMapping("/updateArea")
    public ReturnData updateArea(@RequestBody Map<String, Object> params) {
        areaService.updateArea(params);
        return new ReturnData("修改成功");
    }

    @GetMapping("/deleteArea")
    public ReturnData deleteArea(String id) {
        areaService.deleteArea(id);
        return new ReturnData("删除成功");
    }

    /**
     * 递归查询area表格里面的数据
     */
    @GetMapping("queryAreatList")
    public ReturnData queryAreatList() {
        List<Map<String, Object>> list = areaService.queryAreatList();
        return new ReturnData(list);
    }
}

