package com.inspur.towerwebservice.config;


import com.inspur.towerwebservice.model.AreaInfo;
import com.inspur.towerwebservice.model.UserRoleAuthorizeInfo;
import com.inspur.towerwebservice.service.*;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {
    @Autowired
    private Bus bus;
    @Autowired
    private AreaInfoService areaInfoService;
    @Autowired
    private OrgInfoService orgInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    private UserRoleAuthorizeService userRoleAuthorizeService;

    @Bean
    public Endpoint endpointAreaService() {
        EndpointImpl endpoint = new EndpointImpl(bus, areaInfoService);
        endpoint.publish("/AreaInfoService");//接口发布在 /AreaInfoService 目录下
        return endpoint;
    }

    @Bean
    public Endpoint endpointOrgService() {
        EndpointImpl endpoint = new EndpointImpl(bus, orgInfoService);
        endpoint.publish("/OrgInfoService");//接口发布在 /OrgInfoService 目录下
        return endpoint;
    }

    @Bean
    public Endpoint endpointUserService() {
        EndpointImpl endpoint = new EndpointImpl(bus, userInfoService);
        endpoint.publish("/UserInfoService");//接口发布在 /UserInfoService 目录下
        return endpoint;
    }

    @Bean
    public Endpoint endpointRoleService() {
        EndpointImpl endpoint = new EndpointImpl(bus, roleInfoService);
        endpoint.publish("/RoleInfoService");//接口发布在 /RoleInfoService 目录下
        return endpoint;
    }

    @Bean
    public Endpoint endpointUserRoleAuthorizeService() {
        EndpointImpl endpoint = new EndpointImpl(bus, userRoleAuthorizeService);
        endpoint.publish("/UserRoleAuthorizeService");//接口发布在 /UserRoleAuthorizeService 目录下
        return endpoint;
    }
}
