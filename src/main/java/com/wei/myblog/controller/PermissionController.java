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

    @GetMapping("/listPermissions")
    public Result listPermissions(){
        List<Permission> permissions = permissionService.listPermissions();
        return Result.succeed(permissions);
    }

    @PostMapping("/savePermission")
    public Result savePermission(Permission permission){
        permissionService.savePermission(permission);
        return Result.succeed();
    }

    @PostMapping("/updatePermission")
    public Result updatePermission(Permission permission){
        permissionService.updatePermission(permission);
        return Result.succeed();
    }

    @GetMapping("/removePermission")
    public Result removePermission(@RequestParam(value = "permissionName")String permissionName){
        permissionService.removePermission(permissionName);
        return Result.succeed();
    }
}
