package com.inspur.towerwebservice.service;


import com.alibaba.fastjson.JSONObject;
import com.inspur.towerwebservice.model.OrgMsgInfo;
import com.inspur.towerwebservice.model.RequestResultCode;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService//(name = "OgrInfoService",targetNamespace = "http://service.towerwebservice.inspur.com")//  命名空间，写一个有意义的http地址就行.
public interface OrgInfoService {

    @WebMethod(operationName = "OrgInfoService")
    public String OrgInfoService(@WebParam(name="arg0")String arg0);
}
