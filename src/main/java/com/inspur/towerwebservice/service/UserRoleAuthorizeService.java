package com.inspur.towerwebservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Li-Xiaoxu
 * @version 1.0
 * @date 2020/6/4 11:52
 */
@WebService
public interface UserRoleAuthorizeService {

    @WebMethod(operationName = "UserRoleAuthorizeService")
    public String RoleInfoService(@WebParam(name = "info", targetNamespace = "http://service.towerwebservice.inspur.com/") String info);
}
