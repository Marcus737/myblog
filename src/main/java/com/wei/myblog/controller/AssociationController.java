package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.service.AssociationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/myblog/association")
public class AssociationController {

    @Autowired
    AssociationBuilder associationBuilder;

    @PostMapping("/associationUserListWithRole")
    public Result associationUserListWithRole(@RequestParam("userIdList") List<String> userIdList, String roleId){
        for (String userId : userIdList){
            associationBuilder.associateUserWithRole(userId, roleId);
        }
        return Result.succeed();
    }

    @PostMapping("/unAssociationUserListWithRole")
    public Result unAssociationUserListWithRole(@RequestParam("userIdList") List<String> userIdList, String roleId){

        for (String userId : userIdList){
            associationBuilder.unAssociateUserWithRole(userId, roleId);
        }
        return Result.succeed();
    }

    @PostMapping("/associationRoleListWithPermission")
    public Result associationRoleListWithPermission(@RequestParam("roleIdList") List<String> roleIdList, String permissionId){
        for (String roleId : roleIdList){
            associationBuilder.associateRoleWithPermission(roleId, permissionId);
        }
        return Result.succeed();
    }

    @PostMapping("/unAssociationRoleListWithPermission")
    public Result unAssociationRoleListWithPermission(@RequestParam("roleIdList") List<String> roleIdList, String permissionId){
        for (String roleId : roleIdList){
            associationBuilder.unAssociateRoleWithPermission(roleId, permissionId);
        }
        return Result.succeed();
    }

}
