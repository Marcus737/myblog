package com.wei.myblog.entity;

import java.io.Serializable;

/**
 * 权限类
 */
public class Permission implements Serializable {
    /**
     * 权限id
     */
    private Integer permissionId;
    /**
     * 权限名称
     */
    private String permissionName;
    /**
     * 权限地址
     */
    private String permissionUrl;

    public Permission() {
    }

    public Permission(Integer permissionId, String permissionName, String permissionUrl) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.permissionUrl = permissionUrl;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                ", permissionUrl='" + permissionUrl + '\'' +
                '}';
    }
}
