package com.meowsauce.controller;

import com.meowsauce.dto.UserDTO;
import com.meowsauce.entity.AdminRole;
import com.meowsauce.entity.User;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.AdminRoleService;
import com.meowsauce.service.AdminUserRoleService;
import com.meowsauce.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    //查询所有用户
    @GetMapping("/api/admin/user")
    public Result listUsers(){
        List<UserDTO> list = userService.list();
        return ResultFactory.buildSuccessResult(list);
    }
    //更新用户状态
    @PutMapping("/api/admin/user/status")
    public Result updateUserStatus(@RequestBody @Valid User requestUser) {
        userService.updateUserStatus(requestUser);
        return ResultFactory.buildSuccessResult("用户状态更新成功");
    }
    //重置用户密码
    @PutMapping("/api/admin/user/password")
    public Result resetPassword(@RequestBody @Valid User requestUser){
        userService.resetPassword(requestUser);
        return ResultFactory.buildSuccessResult("重置密码成功");
    }
    //修改用户信息
    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody @Valid User requestUser){
        userService.editUser(requestUser);
        return ResultFactory.buildSuccessResult("修改用户信息成功");
    }
    //后台用户注册功能 不需要注册
    @PostMapping("/api/admin/register")
    public Result registerUser(@RequestBody @Valid User requestUser, HttpServletRequest request){
        int status = userService.registerUser(requestUser,request);
        switch (status) {
            case 0:
                return ResultFactory.buildFailResult("用户名和密码不能为空");
            case 1:
                return ResultFactory.buildSuccessResult("注册成功");
            case 2:
                return ResultFactory.buildFailResult("用户已存在");
        }
        return ResultFactory.buildFailResult("未知错误");
    }
    //删除用户
    @PostMapping("/api/admin/delete")
    public Result deleteUser(@RequestBody @Valid User user){
        userService.deleteUserByUserId(user.getUserId());
        return ResultFactory.buildSuccessResult("用户信息删除成功");
    }
}
