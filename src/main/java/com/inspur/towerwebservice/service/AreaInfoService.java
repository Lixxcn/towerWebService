package com.inspur.towerwebservice.service;

import com.alibaba.fastjson.JSONObject;
import com.inspur.towerwebservice.model.AreaInfo;
import com.inspur.towerwebservice.model.MsgInfo;
import com.inspur.towerwebservice.model.RequestResultCode;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService //(name = "AreaInfoService",serviceName = "AreaInfoService",targetNamespace = "http://service.towerwebservice.inspur.com")//  命名空间，写一个有意义的http地址就行.
public interface AreaInfoService {

    @WebMethod(operationName = "AreaInfoService")
    public String AreaInfoService(@WebParam(name = "arg0") String arg0);

}


