package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.entity.AdminPermission;
import com.meowsauce.entity.AdminRolePermission;

import java.util.List;

public interface AdminRolePermissionService extends IService<AdminRolePermission> {
    List<AdminRolePermission> findAllByRid(List<Integer> Rids);

    void savePermChanges(int id, List<AdminPermission> perms);

    List<AdminRolePermission> listRolePermissionByRoleId(int rid);

    void deleteRolePermissionByRid(int rid);
}
