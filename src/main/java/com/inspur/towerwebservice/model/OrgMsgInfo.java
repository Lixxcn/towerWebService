package com.inspur.towerwebservice.model;

import java.util.List;

public class OrgMsgInfo {

    private String flag;
    private List<OrgInfo> listJson;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<OrgInfo> getListJson() {
        return listJson;
    }

    public void setListJson(List<OrgInfo> listJson) {
        this.listJson = listJson;
    }
}
