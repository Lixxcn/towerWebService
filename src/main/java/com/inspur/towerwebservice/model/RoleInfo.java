package com.inspur.towerwebservice.model;

/**
 * @author Li-Xiaoxu
 * @version 1.0
 * @date 2020/6/4 14:28
 */
public class RoleInfo {
    private String flag;
    private String serviceId;
    private String roleId;
    private String roleName;
    private String roleLevel;
    private String roleCode;
    private String roleState;
    private String createDate;
    private String statusCd;
    private String remark;
    private String belongCode;
    private String belongName;

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(String roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleState() {
        return roleState;
    }

    public void setRoleState(String roleState) {
        this.roleState = roleState;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBelongCode() {
        return belongCode;
    }

    public void setBelongCode(String belongCode) {
        this.belongCode = belongCode;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }

    @Override
    public String toString() {
        return "RoleInfo{" +
                "flag='" + flag + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleLevel='" + roleLevel + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", roleState='" + roleState + '\'' +
                ", createDate='" + createDate + '\'' +
                ", statusCd='" + statusCd + '\'' +
                ", remark='" + remark + '\'' +
                ", belongCode='" + belongCode + '\'' +
                ", belongName='" + belongName + '\'' +
                '}';
    }
}
