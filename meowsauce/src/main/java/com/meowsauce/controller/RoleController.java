package com.meowsauce.controller;

import com.meowsauce.entity.AdminPermission;
import com.meowsauce.entity.AdminRole;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.AdminPermissionService;
import com.meowsauce.service.AdminRoleMenuService;
import com.meowsauce.service.AdminRolePermissionService;
import com.meowsauce.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoleController {
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private AdminRoleMenuService adminRoleMenuService;
    @Autowired
    private AdminRolePermissionService adminRolePermissionService;
    @Autowired
    private AdminPermissionService adminPermissionService;
    //查询所有角色
    @GetMapping("/api/admin/role")
    public Result listRoles(){
        List<AdminRole> list = adminRoleService.listWithPermsAndMenus();
        return ResultFactory.buildSuccessResult(list);
    }
    //更新角色状态
    @PutMapping("/api/admin/role/status")
    public Result updateRoleStatus(@RequestBody AdminRole requestRole){
        AdminRole adminRole = adminRoleService.updateRoleStatus(requestRole);
        String message = "用户" + adminRole.getNameZh() + "状态更新成功";
        return ResultFactory.buildSuccessResult(message);
    }
    //修改角色信息
    @PutMapping("/api/admin/role")
    public Result editRole(@RequestBody AdminRole requestRole){
        adminRoleService.editRole(requestRole);
        adminRolePermissionService.savePermChanges(requestRole.getId(),requestRole.getPerms());
        String message = "修改角色信息成功";
        return ResultFactory.buildSuccessResult(message);
    }
    //添加角色
    @PostMapping("/api/admin/role")
    public Result addRole(@RequestBody AdminRole requestRole){
        adminRoleService.addRole(requestRole);
        return ResultFactory.buildSuccessResult("添加角色成功");
    }
    //查询权限信息
    @GetMapping("/api/admin/role/perm")
    public Result listPerms(){
        List<AdminPermission> list = adminPermissionService.list();
        return ResultFactory.buildSuccessResult(list);
    }
    //更新角色菜单
    @PutMapping("/api/admin/role/menu")
    public Result updateRoleMenu(@RequestParam int rid,@RequestBody Map<String,List<Integer>> menusIds){
        adminRoleMenuService.updateRoleMenu(rid,menusIds);
        return ResultFactory.buildSuccessResult("更新成功");
    }
    //删除角色,以及角色关联的信息
    @Transactional
    @PostMapping("/api/admin/role/delete")
    public Result deleteRole(@RequestBody AdminRole role){
        adminRoleService.deleteRoleById(role.getId());
        adminRoleMenuService.deleteRoleMenuByRid(role.getId());
        adminRolePermissionService.deleteRolePermissionByRid(role.getId());
        return ResultFactory.buildSuccessResult("删除成功");
    }
}
