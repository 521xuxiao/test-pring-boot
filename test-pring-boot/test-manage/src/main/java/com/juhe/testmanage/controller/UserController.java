package com.juhe.testmanage.controller;

import com.juhe.testcommon.pojo.ReturnData;
import com.juhe.testmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("juhe/user")
@ResponseBody
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/queryUser")
    public ReturnData queryUser() {
        List<Map<String, Object>> list = userService.queryUser();
        return new ReturnData(list);
    }

    /**
     * 新增用户列表
     * @params {"username":"", "password":""}
     */
    @PostMapping("/addUser")
    public ReturnData addUser(@RequestBody Map<String, Object> params) {
        userService.addUser(params);
        return new ReturnData("新增成功");
    }

    /**
     * 修改用户列表
     * @params {"id":"", "username":"", "password":""}
     */
    @PostMapping("/updateUser")
    public ReturnData updateUser(@RequestBody Map<String, Object> params) {
        userService.updateUser(params);
        return new ReturnData("修改成功");
    }

    /**
     * 删除用户列表
     * @Params {"id":""}
     */
    @GetMapping("deleteUser")
    public ReturnData deleteUser(String id) {
        userService.deleteUser(id);
        return new ReturnData("删除成功");
    }
}
