package com.meowsauce.controller;

import com.meowsauce.entity.AdminMenu;
import com.meowsauce.entity.User;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.AdminMenuService;
import com.meowsauce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {
    @Autowired
    private AdminMenuService adminMenuService;
    //查询菜单信息
    @GetMapping("/api/menu")
    public List<AdminMenu> menu(){
        return adminMenuService.getMenusByCurrentUser();
    }
    //根据角色查询菜单信息
    @GetMapping("/api/admin/role/menu")
    public Result listAllMenus(){
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByRoleId(1));
    }
}
