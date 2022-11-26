package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
//import java.util.Date;

@Data
@TableName("user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String userIp;
    @NotBlank(message = "用户名不能为空")
    private String userName;

    private String userPassword;
    @Email(message = "请输入正确的邮箱")
    private String userEmail;

    private String userProfilePhoto;

    private String userRegistrationTime;

    private String userBirthday;

    private String userTelephoneNumber;

    private String userNickname;

    private String salt;

    private boolean enabled;

    @TableField(exist = false)
    private String verificationCode;

    @TableField(exist = false)
    private List<AdminRole> roles;
}
