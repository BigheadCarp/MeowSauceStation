package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.meowsauce.dto.UserDTO;
import com.meowsauce.entity.AdminRole;
import com.meowsauce.entity.AdminUserRole;
import com.meowsauce.entity.User;
import com.meowsauce.mapper.UserMapper;
import com.meowsauce.result.Result;
import com.meowsauce.service.AdminRoleService;
import com.meowsauce.service.AdminUserRoleService;
import com.meowsauce.service.UserService;
import com.meowsauce.util.IpUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminUserRoleService adminUserRoleService;
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //根据用户名查询用户
    @Override
    public User findUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
    //判断用户是否存在
    @Override
    public boolean isExist(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null){
            return true;
        }
        return false;
    }
    //添加用户
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }
    //登录页注册
    @Override
    public int register(User user, HttpServletRequest request) {
        String userName = user.getUserName();
        String name =  user.getUserNickname();
        String phone = user.getUserTelephoneNumber();
        String password = user.getUserPassword();
        //用户输入的验证码
        String verificationCode = user.getVerificationCode();
        //发送的验证码
        System.out.println("ServiceImpl中的手机号" + phone);
        String toVerificationCode = redisTemplate.opsForValue().get(phone);
        System.out.println("=========================");
        System.out.println("用户输入验证码为" + verificationCode);
        System.out.println("发送的验证码" + toVerificationCode);
        userName = HtmlUtils.htmlEscape(userName);
        user.setUserName(userName);
        name = HtmlUtils.htmlEscape(name);
        user.setUserNickname(name);
        phone = HtmlUtils.htmlEscape(phone);
        user.setUserTelephoneNumber(phone);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        user.setUserRegistrationTime(dateString);
        String ipAddr = IpUtil.getIpAddr(request);
        System.out.println("ip地址为: " + ipAddr);
        user.setUserIp(ipAddr);
        user.setEnabled(true);
        if (userName.equals("") || password.equals("")){
            return 0;
        }
        if (verificationCode.equals("") || !verificationCode.equals(toVerificationCode)){
            return 3;
        }
        boolean exist = isExist(userName);
        if (exist){
            return 2;
        }
        //默认生成16位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5",password,salt,times).toString();
        user.setSalt(salt);
        user.setUserPassword(encodedPassword);
        userMapper.insert(user);
        AdminUserRole userRole = new AdminUserRole();
        userRole.setRid(3);
        userRole.setUid(user.getUserId());
        adminUserRoleService.save(userRole);
        return 1;
    }
    //查询所有用户后添加角色信息并封装
    @Transactional
    @Override
    public List<UserDTO> list() {
        List<User> users = userMapper.selectList(null);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users){
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            userDTOS.add(userDTO);
            System.out.println("真实姓名"+userDTO.getUserNickname());
        }
        userDTOS.forEach(u->{
            List<AdminUserRole> adminUserRoles = adminUserRoleService.listAllByUid(u.getUserId());
            List<AdminRole> roles = adminRoleService.listAllById(adminUserRoles);
            u.setRoles(roles);
        });
        return userDTOS;
    }
    //TODO 可以与editUser整合
    //更新用户状态
    @Override
    public void updateUserStatus(User requestUser) {
        User user = userMapper.selectById(requestUser.getUserId());
        user.setEnabled(user.isEnabled());
        userMapper.updateById(requestUser);
    }
    //重置用户密码
    @Override
    public void resetPassword(User requestUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",requestUser.getUserName());
        User user = userMapper.selectOne(queryWrapper);
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        user.setSalt(salt);
        String encodedPassword = new SimpleHash("md5", "123", salt, times).toString();
        user.setUserPassword(encodedPassword);
        userMapper.updateById(user);
    }
    //更新用户信息
    @Override
    @Transactional
    public void editUser(User requestUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",requestUser.getUserName());
        User user = userMapper.selectOne(queryWrapper);
        user.setUserNickname(requestUser.getUserNickname());
        user.setUserTelephoneNumber(requestUser.getUserTelephoneNumber());
        user.setUserEmail(requestUser.getUserEmail());
        userMapper.updateById(user);
        adminUserRoleService.saveRoleChanges(user.getUserId(),requestUser.getRoles());
    }
    //TODO 与前台注册功能高度冗余
    //后台添加用户
    @Override
    public int registerUser(User requestUser,HttpServletRequest request) {
        String username = requestUser.getUserName();
        String name =  requestUser.getUserNickname();
        String phone = requestUser.getUserTelephoneNumber();
        String password = requestUser.getUserPassword();
        System.out.println("=========================");
        username = HtmlUtils.htmlEscape(username);
        requestUser.setUserName(username);
        name = HtmlUtils.htmlEscape(name);
        requestUser.setUserNickname(name);
        phone = HtmlUtils.htmlEscape(phone);
        requestUser.setUserTelephoneNumber(phone);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        requestUser.setUserRegistrationTime(dateString);
        String ipAddr = IpUtil.getIpAddr(request);
        System.out.println("ip地址为: " + ipAddr);
        requestUser.setUserIp(ipAddr);
        if (username.equals("") || password.equals("")){
            return 0;
        }
        boolean exist = isExist(username);
        System.out.println("查询没出问题");
        if (exist){
            return 2;
        }
        //默认生成16位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5",password,salt,times).toString();
        requestUser.setSalt(salt);
        requestUser.setUserPassword(encodedPassword);
        userMapper.insert(requestUser);
        return 1;
    }
    //根据用户Id删除用户
    @Override
    @Transactional
    public void deleteUserByUserId(int userId) {
        adminUserRoleService.removeByUid(userId);
        userMapper.deleteById(userId);
    }
}
