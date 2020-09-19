package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Permission;
import com.wei.myblog.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myblog/permission")
@RequiresRoles("admin")
@RequiresAuthentication
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    /**
     * 列出所有权限
     * @return
     */
    @GetMapping("/listPermissions")
    public Result listPermissions(){
        List<Permission> permissions = permissionService.listPermissions();
        return Result.succeed(permissions);
    }

    /**
     * 保存权限
     * @param permission
     * @return
     */
    @PostMapping("/savePermission")
    public Result savePermission(Permission permission){
        permissionService.savePermission(permission);
        return Result.succeed();
    }

    /**
     * 更新权限
     * @param permission
     * @return
     */
    @PostMapping("/updatePermission")
    public Result updatePermission(Permission permission){
        permissionService.updatePermission(permission);
        return Result.succeed();
    }

    /**
     * 删除权限
     * @param permissionId
     * @return
     */
    @GetMapping("/removePermission")
    public Result removePermission(@RequestParam(value = "permissionId")String permissionId){
        permissionService.removePermission(permissionId);
        return Result.succeed();
    }

    /**
     * 为角色添加权限
     * @param permissionId
     * @param roleIdList
     * @return
     */
    @PostMapping("/addPermissionToRole")
    public Result addPermissionToRole(@RequestParam(value = "permissionId") String permissionId
            , @RequestParam(value = "roleIdList") List<String> roleIdList){
        permissionService.addPermissionToRole(permissionId, roleIdList);
        return Result.succeed();
    }

    /**
     * 删除角色的权限
     * @param permissionId
     * @param roleIdList
     * @return
     */
    @PostMapping("/removePermissionFromRole")
    public Result removePermissionFromRole(@RequestParam(value = "permissionId") String permissionId
            , @RequestParam(value = "roleIdList") List<String> roleIdList){
        permissionService.removePermissionFromRole(permissionId, roleIdList);
        return Result.succeed();
    }
}
