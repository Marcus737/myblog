package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Role;
import com.wei.myblog.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myblog/role")
@RequiresRoles("admin")
@RequiresAuthentication
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 列出拥有此权限的角色
     * @param permissionId
     * @return
     */
    @GetMapping("/listRolesByPermissionId")
    public Result listRolesByPermissionId(@RequestParam("permissionId") String permissionId){
        List<Role> roles = roleService.listRolesByPermissionId(permissionId);
        return Result.succeed(roles);
    }

    /**
     * 列出所有角色
     * @return
     */
    @GetMapping("/listRoles")
    public Result listRoles(){
        return Result.succeed(roleService.listRoles());
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @PostMapping("/saveRole")
    public Result saveRole(Role role){
        roleService.saveRole(role);
        return Result.succeed();
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @PostMapping("/updateRole")
    public Result updateRole(Role role){
        roleService.updateRole(role);
        return Result.succeed();
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @RequestMapping("/removeRole")
    public Result removeRole(@RequestParam(value = "roleId") String roleId){
        roleService.removeRole(roleId);
        return Result.succeed();
    }

    /**
     * 为用户添加角色
     * @param userIdList
     * @param roleId
     * @return
     */
    @PostMapping("/addRoleToUser")
    public Result addRoleToUser(@RequestParam("userIdList") List<String> userIdList
            ,@RequestParam("roleId") String roleId){
        for (String userId: userIdList){
            roleService.addRoleToUser(userId, roleId);
        }
        return Result.succeed();
    }

    /**
     * 删除用户的角色
     * @param userIdList
     * @param roleId
     * @return
     */
    @PostMapping("/removeRoleFromUser")
    public Result removeRoleFromUser(@RequestParam("userIdList") List<String> userIdList
            ,@RequestParam("roleId") String roleId){
        for (String userId: userIdList){
            roleService.removeRoleFromUser(userId, roleId);
        }
        return Result.succeed();
    }
}
