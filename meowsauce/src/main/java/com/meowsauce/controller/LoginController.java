package com.meowsauce.controller;

import com.meowsauce.entity.User;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.UserService;
import com.meowsauce.util.SMSUtils;
import com.meowsauce.util.ValidateCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 登录功能
     * @param requestUser
     * @return
     */
    @PostMapping(value = "api/login")
    public Result login(@RequestBody User requestUser){
        String username = requestUser.getUserName();
        username = HtmlUtils.htmlEscape(username);

        Subject subject = SecurityUtils.getSubject();
//        subject.getSession().setTimeout(10000);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getUserPassword());
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            User user = userService.findUserByName(username);
            if (!user.isEnabled()) {
                return ResultFactory.buildFailResult("该用户已被禁用");
            }
            return ResultFactory.buildSuccessResult(username);
        } catch (IncorrectCredentialsException e) {
            return ResultFactory.buildFailResult("密码错误");
        } catch (UnknownAccountException e) {
            return ResultFactory.buildFailResult("账号不存在");
        }
    }

    /**
     * 注册功能
     * @param user
     * @return
     */
    @PostMapping("/api/register")
    public Result register(@RequestBody User user, HttpServletRequest request) {
        int status = userService.register(user,request);
        switch (status) {
            case 0:
                return ResultFactory.buildFailResult("用户名和密码不能为空");
            case 1:
                return ResultFactory.buildSuccessResult("注册成功");
            case 2:
                return ResultFactory.buildFailResult("用户已存在");
            case 3:
                return ResultFactory.buildFailResult("验证码错误");
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    //发送验证码
    @PostMapping("/api/sendMsg")
    public Result sendMsg(@RequestBody User user){
        //获取手机号
        String phone = user.getUserTelephoneNumber();
        if (StringUtils.isNotBlank(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            System.out.println("code={" + code +"}");
            //调用腾讯提供的短信服务API完成发送短信
            SMSUtils.sendMessage(phone,code);
//            request.gesession.setAttribute(phone,code);
            //将验证码放入redis
            redisTemplate.opsForValue().set(phone,code);
            return ResultFactory.buildSuccessResult("手机验证码短信发送成功");
        }
        return ResultFactory.buildFailResult("短信发送失败");
    }
    //登出功能
    @GetMapping("api/logout")
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    /**
     * 防止通过伪造参数,访问本需登录才能访问的页面
     * @return
     */
    @GetMapping("api/authentication")
    public String authentication(){
        return "身份认证成功";
    }
}
