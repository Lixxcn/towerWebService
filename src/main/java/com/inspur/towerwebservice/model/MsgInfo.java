package com.inspur.towerwebservice.model;

import java.util.List;

public class MsgInfo {
    String flag;
    List<AreaInfo> listJson;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<AreaInfo> getListJson() {
        return listJson;
    }

    public void setListJson(List<AreaInfo> listJson) {
        this.listJson = listJson;
    }
}
