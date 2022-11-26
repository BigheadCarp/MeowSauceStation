package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meowsauce.entity.AdminMenu;
import com.meowsauce.entity.AdminPermission;
import com.meowsauce.entity.AdminRole;
import com.meowsauce.entity.AdminUserRole;
import com.meowsauce.mapper.AdminRoleMapper;
import com.meowsauce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminRoleServiceImpl implements AdminRoleService{
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AdminPermissionService adminPermissionService;
    @Autowired
    private AdminRoleMenuService adminRoleMenuService;
    @Autowired
    private AdminMenuService adminMenuService;
    //根据用户名查询所有角色
    @Override
    public List<AdminRole> listRolesByUser(String username) {
        QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",username);
        List<AdminRole> adminRoles = adminRoleMapper.selectList(queryWrapper);
        return adminRoles;
    }
    //查询所有角色
    @Override
    public List<AdminRole> list() {
        List<AdminRole> list = adminRoleMapper.selectList(null);
        return list;
    }
    //根据用户与角色关联信息查询所有角色
    @Override
    public List<AdminRole> listAllById(List<AdminUserRole> adminUserRoles) {
        List<AdminRole> roles = new ArrayList<>();
        adminUserRoles.forEach(e->{
            AdminRole adminRole = adminRoleMapper.selectById(e.getRid());
            roles.add(adminRole);
        });
        return roles;
    }
    //更新角色状态
    @Override
    public AdminRole updateRoleStatus(AdminRole requestRole) {
        AdminRole adminRole = adminRoleMapper.selectById(requestRole.getId());
        adminRole.setEnabled(adminRole.isEnabled());
        return adminRole;
    }
    //修改角色信息
    @Override
    public void editRole(AdminRole requestRole) {
        adminRoleMapper.updateById(requestRole);
    }
    //添加角色并添加基本的菜单路由
    @Override
    @Transactional
    public void addRole(@RequestBody AdminRole requestRole) {
        adminRoleMapper.insert(requestRole);
        adminRoleMenuService.addRoleMenuByRid(requestRole.getId());
    }

    @Override
    public List<AdminRole> listWithPermsAndMenus() {
        List<AdminRole> roles = adminRoleMapper.selectList(null);
        List<AdminPermission> perms;
        List<AdminMenu> menus;
        for (AdminRole role : roles){
            perms = adminPermissionService.listPermsByRoleId(role.getId());
            menus = adminMenuService.getMenusByRoleId(role.getId());
            role.setPerms(perms);
            role.setMenus(menus);
        }
        return roles;
    }

    @Override
    public void deleteRoleById(int rid) {
        adminRoleMapper.deleteById(rid);
    }
}
