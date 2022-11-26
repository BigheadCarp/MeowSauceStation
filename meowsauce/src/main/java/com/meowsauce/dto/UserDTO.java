package com.meowsauce.dto;

import com.meowsauce.entity.AdminRole;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 后台用户管理用户信息的展示
 */
@Data
@ToString
public class UserDTO {
    private int userId;

    private String userName;

    private String userTelephoneNumber;

    private String userEmail;

    private String userNickname;

    private boolean enabled;

    private List<AdminRole> roles;
}
