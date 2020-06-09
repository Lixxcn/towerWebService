package com.inspur.towerwebservice.service;


import com.inspur.towerwebservice.model.OrgMsgInfo;
import com.inspur.towerwebservice.model.RequestResultCode;
import com.inspur.towerwebservice.model.UserInfo;
import com.inspur.towerwebservice.model.UserResultCode;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService//(name = "UserInfoService",targetNamespace = "http://service.towerwebservice.inspur.com/")//  命名空间，写一个有意义的http地址就行.
public interface UserInfoService {

    @WebMethod(operationName = "UserInfoService")
    public String UserInfoService(@WebParam(name="info",targetNamespace = "http://service.towerwebservice.inspur.com/") String info);
}
