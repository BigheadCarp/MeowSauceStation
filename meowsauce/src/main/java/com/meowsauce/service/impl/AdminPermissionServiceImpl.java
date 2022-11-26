package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meowsauce.entity.*;
import com.meowsauce.mapper.AdminPermissionMapper;
import com.meowsauce.mapper.UserMapper;
import com.meowsauce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminPermissionServiceImpl implements AdminPermissionService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminPermissionMapper adminPermissionMapper;
    @Autowired
    private AdminUserRoleService adminUserRoleService;
    @Autowired
    private AdminRolePermissionService adminRolePermissionService;
    //根据用户名查询权限信息
    @Override
    public Set<String> listPermissionURLsByUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User user = userMapper.selectOne(queryWrapper);
        List<Integer> rids = adminUserRoleService.listAllByUid(user.getUserId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        List<Integer> pids = adminRolePermissionService.findAllByRid(rids)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
        List<AdminPermission> perms = adminPermissionMapper.selectBatchIds(pids);
        Set<String> URLs = perms.stream().map(AdminPermission::getUrl).collect(Collectors.toSet());
        return URLs;
    }
    //判断用户是否具有该路径的权限
    public boolean needFilter(String requestAPI){
        List<AdminPermission> ps = adminPermissionMapper.selectList(null);
        for (AdminPermission p : ps){
            if (p.getUrl().equals(requestAPI)){
                return true;
            }
        }
        return false;
    }
    //查询权限信息
    @Override
    public List<AdminPermission> list() {
        return adminPermissionMapper.selectList(null);
    }
    //根据角色Id查询权限
    @Override
    public List<AdminPermission> listPermsByRoleId(int rid) {
        List<Integer> rps = adminRolePermissionService.listRolePermissionByRoleId(rid).stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
        if (rps.size() > 0){
            List<AdminPermission> adminPermissions = adminPermissionMapper.selectBatchIds(rps);
            return adminPermissions;
        }
        return new ArrayList<AdminPermission>();
    }
}
