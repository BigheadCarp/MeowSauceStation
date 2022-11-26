package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meowsauce.entity.AdminMenu;
import com.meowsauce.entity.AdminRoleMenu;
import com.meowsauce.entity.AdminUserRole;
import com.meowsauce.entity.User;
import com.meowsauce.mapper.AdminMenuMapper;
import com.meowsauce.mapper.UserMapper;
import com.meowsauce.service.AdminMenuService;
import com.meowsauce.service.AdminRoleMenuService;
import com.meowsauce.service.AdminUserRoleService;
import com.meowsauce.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuServiceImpl implements AdminMenuService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMenuMapper adminMenuMapper;
    @Autowired
    private AdminUserRoleService adminUserRoleService;
    @Autowired
    private AdminRoleMenuService adminRoleMenuService;

    /**
     * 根据当前用户查询出所有菜单项的方法
     * @return
     */
    @Override
    public List<AdminMenu> getMenusByCurrentUser() {
        //从数据库中获取当前用户
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User user = userMapper.selectOne(queryWrapper);
        //获得当前用户对应的所有角色的id列表
        List<Integer> rids = adminUserRoleService.listAllByUid(user.getUserId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        //查询出这些角色对应的所有菜单项
        List<Integer> menuIds = adminRoleMenuService.findAllByRids(rids)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuMapper.selectBatchIds(menuIds).stream().distinct().collect(Collectors.toList());
        //处理菜单项的结构
        handleMenus(menus);
        return menus;
    }

    /**
     * 根据角色查询菜单
     * @param rid
     * @return
     */
    @Override
    public List<AdminMenu> getMenusByRoleId(int rid) {
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rid).stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuMapper.selectBatchIds(menuIds);
        handleMenus(menus);
        return menus;
    }
    //整合查询出的菜单数据
    public void handleMenus(List<AdminMenu> menus){
        for (AdminMenu menu : menus){
            List<AdminMenu> children = getAllByParentId(menu.getId());
            menu.setChildren(children);
        }
        Iterator<AdminMenu> iterator = menus.iterator();
        while (iterator.hasNext()){
            AdminMenu menu = iterator.next();
            if (menu.getParentId() != 0){
                iterator.remove();
            }
        }
    }
    //根据parent_id字段,查询当前菜单的父菜单
    private List<AdminMenu> getAllByParentId(int id) {
        QueryWrapper<AdminMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<AdminMenu> menus = adminMenuMapper.selectList(queryWrapper);
        return menus;
    }
}
