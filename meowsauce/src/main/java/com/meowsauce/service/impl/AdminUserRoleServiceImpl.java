package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.AdminRole;
import com.meowsauce.entity.AdminUserRole;
import com.meowsauce.entity.User;
import com.meowsauce.mapper.AdminUserRoleMapper;
import com.meowsauce.service.AdminUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class AdminUserRoleServiceImpl extends ServiceImpl<AdminUserRoleMapper,AdminUserRole> implements AdminUserRoleService{
    @Autowired
    private AdminUserRoleMapper adminUserRoleMapper;
    //根据用户Id查询所有用户角色关联信息
    @Override
    public List<AdminUserRole> listAllByUid(int uId) {
        QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uId);
        List<AdminUserRole> adminUserRoles = adminUserRoleMapper.selectList(queryWrapper);
        return adminUserRoles;
    }
    //根据用户Id修改用户角色关联信息
    @Override
    @Transactional
    public void saveRoleChanges(int userId, List<AdminRole> roles) {
        QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userId);
        remove(queryWrapper);
        List<AdminUserRole> userRoles = new ArrayList<>();
        roles.forEach(r -> {
            AdminUserRole ur = new AdminUserRole();
            ur.setUid(userId);
            ur.setRid(r.getId());
            userRoles.add(ur);
        });
        saveBatch(userRoles);
    }
    //根据用户Id删除用户
    @Override
    public void removeByUid(int userId) {
        QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userId);
        remove(queryWrapper);
    }
}
