package com.meowsauce.service;

import com.meowsauce.entity.AdminPermission;

import java.util.List;
import java.util.Set;

public interface AdminPermissionService {
    Set<String> listPermissionURLsByUser(String username);

    boolean needFilter(String requestAPI);

    List<AdminPermission> list();

    List<AdminPermission> listPermsByRoleId(int rid);
}
