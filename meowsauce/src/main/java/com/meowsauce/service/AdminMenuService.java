package com.meowsauce.service;

import com.meowsauce.entity.AdminMenu;

import java.util.List;

public interface AdminMenuService {
    public List<AdminMenu> getMenusByCurrentUser();

    List<AdminMenu> getMenusByRoleId(int i);

}
