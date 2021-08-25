package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.dao.RoleDao;
import com.wei.myblog.entity.Role;
import com.wei.myblog.service.AssociationBuilder;
import com.wei.myblog.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myblog/role")
@RequiresRoles("admin")
@RequiresAuthentication
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    AssociationBuilder associationBuilder;

    @GetMapping("/listRolesByPermissionId")
    public Result listRolesByPermissionId(String permissionId){
        List<Role> roles = roleService.listRolesByPermissionId(permissionId);
        return Result.succeed(roles);
    }

    @GetMapping("/listRoles")
    public Result listRoles(){
        return Result.succeed(roleService.listRoles());
    }

    @PostMapping("/saveRole")
    public Result saveRole(Role role){
        roleService.saveRole(role);
        return Result.succeed();
    }

    @PostMapping("/updateRole")
    public Result updateRole(Role role){
        roleService.updateRole(role);
        return Result.succeed();
    }

    @RequestMapping("/removeRole")
    public Result removeRole(@RequestParam(value = "roleName") String roleName){
        roleService.removeRole(roleName);
        return Result.succeed();
    }
}
