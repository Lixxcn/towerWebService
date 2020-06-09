package com.inspur.towerwebservice.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.inspur.towerwebservice.model.UserRoleAuthorizeInfo;
import com.inspur.towerwebservice.service.UserRoleAuthorizeService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author Li-Xiaoxu
 * @version 1.0
 * @date 2020/6/4 11:59
 */
@WebService(name="UserRoleAuthorizeService",
        serviceName = "UserRoleAuthorizeService",
        targetNamespace = "http://service.towerwebservice.inspur.com/",
        endpointInterface = "com.inspur.towerwebservice.service.UserRoleAuthorizeService")
@Component
public class UserRoleAuthorizeServiceImpl implements UserRoleAuthorizeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @WebMethod(operationName = "UserRoleAuthorizeService")
    @Override
    public String RoleInfoService(String info) {
        JSONObject jsonObject = new JSONObject();
        System.out.print("\n" + "接收到的人员信息字符串为:" + info);
        try{
            net.sf.json.JSONObject jsonStr = net.sf.json.JSONObject.fromObject(info);
            String param = jsonStr.get("param") + "";
            net.sf.json.JSONObject listJson = net.sf.json.JSONObject.fromObject(param);
            String listJsonStr = listJson.get("listJson").toString();
            JSONArray array = JSONArray.fromObject(listJsonStr);
            System.out.println(array);
            // JSON数组转换成存储对象的集合
            //参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
            List<UserRoleAuthorizeInfo> userRoleAuthorizeInfos = JSONArray.toList(array,new UserRoleAuthorizeInfo(),new JsonConfig());

            if (userRoleAuthorizeInfos.size() == 0) {
                jsonObject.put("serviceId", "CHNTCRM");
                jsonObject.put("rsp", "1");
                jsonObject.put("errDesc", "推送人员数据不可为空");
            } else {
                for (UserRoleAuthorizeInfo userRoleAuthorizeInfo : userRoleAuthorizeInfos) {
                    System.out.println("\n" + "****************************开始循环打印userRoleAuthorizeInfo信息*******************************");
                    Class cls = userRoleAuthorizeInfo.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        Field f = fields[i];
                        f.setAccessible(true);
                        if(f.get(userRoleAuthorizeInfo) instanceof String[]){//如果直接输出String[]输出的为地址值
                            System.out.println("属性名:" + f.getName() + "  属性值:" + Arrays.toString((String[]) f.get(userRoleAuthorizeInfo)));
                        }else{
                            System.out.println("属性名:" + f.getName() + "  属性值:" + f.get(userRoleAuthorizeInfo));
                        }
                    }
                    System.out.println("\n" + "****************************循环打印userRoleAuthorizeInfo信息结束*******************************");
                    //以下为入库操作
                    //1:add,2: change,3: delete
                    if("1".equals(userRoleAuthorizeInfo.getFlag())){
                        //授权进行新增
                    }else if("3".equals(userRoleAuthorizeInfo.getFlag())){
                        //授权进行删除
                    }else if("4".equals(userRoleAuthorizeInfo.getFlag())){
                        //授权进行check
                    }else {
                        System.out.println("该请求" + userRoleAuthorizeInfo +"的flag字段不符合要求，为：" + userRoleAuthorizeInfo.getFlag());
                    }
                }
                jsonObject.put("serviceId", "CHNTCRM");
                jsonObject.put("rsp", "0");
                jsonObject.put("errDesc", "成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("serviceId", "CHNTCRM");
            jsonObject.put("rsp", "1");
            jsonObject.put("errDesc", "未知错误，请联系管理员");
        }
        return jsonObject.toString();
    }
}
