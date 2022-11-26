package com.meowsauce.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meowsauce.dto.UserDTO;
import com.meowsauce.entity.User;
import com.meowsauce.result.Result;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService{
    User findUserByName(String username);

    boolean isExist(String userName);

    void add(User user);

    int register(User user, HttpServletRequest request);

    List<UserDTO> list();

    void updateUserStatus(User requestUser);

    void resetPassword(User requestUser);

    void editUser(User requestUser);

    int registerUser(User requestUser,HttpServletRequest request);

    void deleteUserByUserId(int userId);

}
