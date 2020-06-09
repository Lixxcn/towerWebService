package com.inspur.towerwebservice.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OrgInfo {

    private String mdm_org_code;
    private String org_code;
    private String org_name;
    private String org_level;
    private String org_parent_code;
    private String enterp_type;
    private String org_status;
    private String org_change;
    private String org_relation;
    private String org_remarks;
    private String org_path;
    private String created_by;
    private String creation_date;
    private String last_updated_by;
    private String last_update_date;
    private String org_reserved_text_1;
    private String sySf;
    private String sySfCode;
    private String syDs;
    private String syDsName;

    public String getMdm_org_code() {
        return mdm_org_code;
    }

    public void setMdm_org_code(String mdm_org_code) {
        this.mdm_org_code = mdm_org_code;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_level() {
        return org_level;
    }

    public void setOrg_level(String org_level) {
        this.org_level = org_level;
    }

    public String getOrg_parent_code() {
        return org_parent_code;
    }

    public void setOrg_parent_code(String org_parent_code) {
        this.org_parent_code = org_parent_code;
    }

    public String getEnterp_type() {
        return enterp_type;
    }

    public void setEnterp_type(String enterp_type) {
        this.enterp_type = enterp_type;
    }

    public String getOrg_status() {
        return org_status;
    }

    public void setOrg_status(String org_status) {
        this.org_status = org_status;
    }

    public String getOrg_change() {
        return org_change;
    }

    public void setOrg_change(String org_change) {
        this.org_change = org_change;
    }

    public String getOrg_relation() {
        return org_relation;
    }

    public void setOrg_relation(String org_relation) {
        this.org_relation = org_relation;
    }

    public String getOrg_remarks() {
        return org_remarks;
    }

    public void setOrg_remarks(String org_remarks) {
        this.org_remarks = org_remarks;
    }

    public String getOrg_path() {
        return org_path;
    }

    public void setOrg_path(String org_path) {
        this.org_path = org_path;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    public String getOrg_reserved_text_1() {
        return org_reserved_text_1;
    }

    public void setOrg_reserved_text_1(String org_reserved_text_1) {
        this.org_reserved_text_1 = org_reserved_text_1;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getSySf() {
        return sySf;
    }

    public void setSySf(String sySf) {
        this.sySf = sySf;
    }

    public String getSySfCode() {
        return sySfCode;
    }

    public void setSySfCode(String sySfCode) {
        this.sySfCode = sySfCode;
    }

    public String getSyDs() {
        return syDs;
    }

    public void setSyDs(String syDs) {
        this.syDs = syDs;
    }

    public String getSyDsName() {
        return syDsName;
    }

    public void setSyDsName(String syDsName) {
        this.syDsName = syDsName;
    }
}
