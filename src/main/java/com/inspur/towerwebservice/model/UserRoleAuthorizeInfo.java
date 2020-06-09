package com.inspur.towerwebservice.model;

import java.util.Arrays;

/**
 * @author Li-Xiaoxu
 * @version 1.0
 * @date 2020/6/4 15:36
 */
public class UserRoleAuthorizeInfo {
    private String flag;
    private String serviceId;
    private String userCode;
    private String orgCode;
    private String permissionKey;
    private String[] roleCodes;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String[] getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(String[] roleCodes) {
        this.roleCodes = roleCodes;
    }

    @Override
    public String toString() {
        return "UserRoleAuthorizeInfo{" +
                "flag='" + flag + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", userCode='" + userCode + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", permissionKey='" + permissionKey + '\'' +
                ", roleCodes=" + Arrays.toString(roleCodes) +
                '}';
    }
}
