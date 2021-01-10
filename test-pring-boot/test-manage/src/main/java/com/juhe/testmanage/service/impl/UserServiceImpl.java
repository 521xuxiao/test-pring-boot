package com.juhe.testmanage.service.impl;

import com.juhe.testmanage.dao.UserDao;
import com.juhe.testmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<Map<String, Object>> queryUser() {
        List<Map<String, Object>> list = userDao.queryUser();
        return list;
    }

    @Override
    @Transactional
    public void addUser(Map<String, Object> params) {
        //        用户名不能重复
        Map<String, String> maps = userSingle(params);
        if(maps != null)
            throw new RuntimeException("用户名已存在");
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        params.put("id", id);
        params.put("salt", salt);
        String password = params.get("password").toString();
        String miPass = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        params.put("password", miPass);
        int num = userDao.addUser(params);
        if(num <= 0)
            throw new RuntimeException("新增失败");
    }

    @Override
    @Transactional
    public void updateUser(Map<String, Object> params) {
        // 查询用户名为admin的id
        Map<String, String> map =  userDao.queryUserId("admin");
        if(params.get("id").toString().equals(map.get("id"))){
            throw new RuntimeException("admin为超管,不允许删除");
        }
//        用户名不能重复
        Map<String, String> maps = userSingle(params);
        if(maps != null)
            throw new RuntimeException("用户名已存在");
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        String miPass = DigestUtils.md5DigestAsHex((params.get("password").toString() + salt).getBytes());
        params.put("salt", salt);
        params.put("password", miPass);
        int num = userDao.updateUser(params);
        if(num <= 0)
            throw new RuntimeException("修改失败");
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
//        先判断要删除的是不是admin超管
        Map<String, String> map = userDao.queryUserId("admin");
        String findId = map.get("id");
        if(findId.equals(id)){
            throw new RuntimeException("admin超管不允许删除");
        }
        int num = userDao.deleteUser(id);
        if(num <= 0)
            throw new RuntimeException("删除失败");
    }

    /**
     * 新增或者修改用户的时候用户名确保唯一性
     */
    public Map<String, String> userSingle(Map<String, Object> params) {
        Map<String, String> map = userDao.userSingle(params);
        return map;
    }
}
