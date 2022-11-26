package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.entity.AdminRole;
import com.meowsauce.entity.AdminRoleMenu;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface AdminRoleMenuService extends IService<AdminRoleMenu> {
    List<AdminRoleMenu> findAllByRids(List<Integer> rids);

    List<AdminRoleMenu> findAllByRid(int rid);

    void updateRoleMenu(int rid, Map<String, List<Integer>> menusIds);

    void addRoleMenuByRid(int id);

    void deleteRoleMenuByRid(int rid);
}
