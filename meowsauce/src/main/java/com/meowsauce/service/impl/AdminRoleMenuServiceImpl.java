package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.AdminRoleMenu;
import com.meowsauce.mapper.AdminRoleMenuMapper;
import com.meowsauce.service.AdminRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminRoleMenuServiceImpl extends ServiceImpl<AdminRoleMenuMapper,AdminRoleMenu> implements AdminRoleMenuService{
    @Autowired
    private AdminRoleMenuMapper adminRoleMenuMapper;
    //根据角色Id集合查询角色与菜单信息
    @Override
    public List<AdminRoleMenu> findAllByRids(List<Integer> rids) {
        QueryWrapper<AdminRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("rid",rids);
        List<AdminRoleMenu> adminRoleMenus = adminRoleMenuMapper.selectList(queryWrapper);
        return adminRoleMenus;
    }
    //根据角色Id查询角色与菜单信息
    @Override
    public List<AdminRoleMenu> findAllByRid(int rid) {
        QueryWrapper<AdminRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid",rid);
        List<AdminRoleMenu> adminRoleMenus = adminRoleMenuMapper.selectList(queryWrapper);
        return adminRoleMenus;
    }
    //根据角色Id,菜单Id,更新角色菜单信息
    @Override
    public void updateRoleMenu(int rid, Map<String, List<Integer>> menusIds) {
        QueryWrapper<AdminRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid",rid);
        adminRoleMenuMapper.delete(queryWrapper);
        List<AdminRoleMenu> rms = new ArrayList<>();
        for (Integer mid : menusIds.get("menusIds")){
            AdminRoleMenu rm = new AdminRoleMenu();
            rm.setMid(mid);
            rm.setRid(rid);
            rms.add(rm);
        }
        saveBatch(rms);
    }
    //给新创建的用户添加两个菜单路径
    @Override
    public void addRoleMenuByRid(int id) {
        AdminRoleMenu a1 = new AdminRoleMenu();
        AdminRoleMenu a2 = new AdminRoleMenu();
        a1.setRid(id);
        a1.setMid(1);
        a2.setRid(id);
        a2.setMid(2);
        adminRoleMenuMapper.insert(a1);
        adminRoleMenuMapper.insert(a2);
    }
    //删除角色与菜单关联信息
    @Override
    public void deleteRoleMenuByRid(int rid) {
        QueryWrapper<AdminRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid",rid);
        adminRoleMenuMapper.delete(queryWrapper);
    }
}
