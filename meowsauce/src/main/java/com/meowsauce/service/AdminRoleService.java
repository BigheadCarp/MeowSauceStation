package com.meowsauce.service;

import com.meowsauce.entity.AdminRole;
import com.meowsauce.entity.AdminUserRole;

import java.util.Arrays;
import java.util.List;

public interface AdminRoleService {
    List<AdminRole> listRolesByUser(String username);

    List<AdminRole> list();

    List<AdminRole> listAllById(List<AdminUserRole> adminUserRoles);

    AdminRole updateRoleStatus(AdminRole requestRole);

    void editRole(AdminRole requestRole);

    void addRole(AdminRole requestRole);

    List<AdminRole> listWithPermsAndMenus();

    void deleteRoleById(int rid);
}
