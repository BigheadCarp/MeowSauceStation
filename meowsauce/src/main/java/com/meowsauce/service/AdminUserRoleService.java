package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.AdminRole;
import com.meowsauce.entity.AdminUserRole;
import com.meowsauce.mapper.AdminUserRoleMapper;

import java.util.Arrays;
import java.util.List;

public interface AdminUserRoleService extends IService<AdminUserRole>{
    List<AdminUserRole> listAllByUid(int uId);

    void saveRoleChanges(int userId, List<AdminRole> roles);


    void removeByUid(int userId);
}
