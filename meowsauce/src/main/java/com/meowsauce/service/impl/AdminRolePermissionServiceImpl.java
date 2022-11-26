package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.AdminPermission;
import com.meowsauce.entity.AdminRole;
import com.meowsauce.entity.AdminRolePermission;
import com.meowsauce.mapper.AdminRolePermissionMapper;
import com.meowsauce.service.AdminRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminRolePermissionServiceImpl extends ServiceImpl<AdminRolePermissionMapper, AdminRolePermission> implements AdminRolePermissionService {
    @Autowired
    private AdminRolePermissionMapper adminRolePermissionMapper;
    //根据Rid查询所有权限
    @Override
    public List<AdminRolePermission> findAllByRid(List<Integer> Rids) {
        QueryWrapper<AdminRolePermission> queryWrapper = new QueryWrapper<>();
        if (Rids != null) {
            queryWrapper.in("rid", Rids);
            List<AdminRolePermission> adminRolePermissions = adminRolePermissionMapper.selectList(queryWrapper);
            return adminRolePermissions;
        }
        return new ArrayList<AdminRolePermission>();
    }
    //更改角色权限
    @Transactional
    @Override
    public void savePermChanges(int rid, List<AdminPermission> perms) {
        QueryWrapper<AdminRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", rid);
        adminRolePermissionMapper.delete(queryWrapper);
        List<AdminRolePermission> rps = new ArrayList<>();
        perms.forEach(p -> {
            AdminRolePermission rp = new AdminRolePermission();
            rp.setRid(rid);
            rp.setPid(p.getId());
            rps.add(rp);
        });
        saveBatch(rps);
    }
    //根据角色Id更改角色权限信息
    @Override
    public List<AdminRolePermission> listRolePermissionByRoleId(int rid) {
        QueryWrapper<AdminRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", rid);
        List<AdminRolePermission> adminRolePermissions = adminRolePermissionMapper.selectList(queryWrapper);
        return adminRolePermissions;
    }
    //根据角色Id删除角色权限信息
    @Override
    public void deleteRolePermissionByRid(int rid) {
        QueryWrapper<AdminRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", rid);
        adminRolePermissionMapper.delete(queryWrapper);
    }
}
