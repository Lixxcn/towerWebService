package com.inspur.towerwebservice.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.inspur.towerwebservice.model.RoleInfo;
import com.inspur.towerwebservice.service.RoleInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Li-Xiaoxu
 * @version 1.0
 * @date 2020/6/4 11:59
 */
@WebService(name="RoleInfoService",
        serviceName = "RoleInfoService",
        targetNamespace = "http://service.towerwebservice.inspur.com/",
        endpointInterface = "com.inspur.towerwebservice.service.RoleInfoService")
@Component
public class RoleInfoServiceImpl implements RoleInfoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @WebMethod(operationName = "RoleInfoService")
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
            List<RoleInfo> listRoleInfo = JSONArray.toList(array,new RoleInfo(),new JsonConfig());

            if (listRoleInfo.size() == 0) {
                jsonObject.put("serviceId", "CHNTCRM");
                jsonObject.put("rsp", "1");
                jsonObject.put("errDesc", "推送人员数据不可为空");
            } else {
                for (RoleInfo roleInfo : listRoleInfo) {
                    System.out.println("\n" + "****************************开始循环打印roleInfo信息*******************************");
                    Class cls = roleInfo.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        Field f = fields[i];
                        f.setAccessible(true);
                        System.out.println("属性名:" + f.getName() + "  属性值:" + f.get(roleInfo));
                    }
                    System.out.println("\n" + "****************************循环打印roleInfo信息结束*******************************");
                    //以下为入库操作
                    //1:add,2: change,3: delete
                    if("1".equals(roleInfo.getFlag())){
                        //添加角色
                    }else if("2".equals(roleInfo.getFlag())){
                        //修改角色
                    }else if("3".equals(roleInfo.getFlag())){
                        //删除角色
                    }else {
                        System.out.println("该请求" + roleInfo +"的flag字段不符合要求，为：" + roleInfo.getFlag());
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
