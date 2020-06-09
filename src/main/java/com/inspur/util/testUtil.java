package com.inspur.util;

import com.inspur.towerwebservice.model.AreaInfo;
import com.inspur.towerwebservice.model.MsgInfo;
import com.inspur.towerwebservice.model.UserInfo;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import java.lang.reflect.Field;

import java.util.List;

public class testUtil {

    public static void main(String[] args) throws IllegalAccessException {
        /*String arg0 = "{\"flag\":\"2\",\"listJson\":[{\"area_base_id\":1462,\"area_code\":\"130000\",\"area_level\":\"2\",\"area_name\":\"河北分公司\",\"area_parent_code\":\"100000\",\"area_path\":\"100000,130000,\",\"area_status\":\"1\",\"created_by\":\"mdmAdmin\",\"creation_date\":\"2019-04-10 10:30:52\",\"mdm_area_code\":\"AREA200001462\"}]}";
        System.out.print("\n"+"字符串为:" + arg0);
        MsgInfo msgInfo = com.alibaba.fastjson.JSONObject.parseObject(arg0, MsgInfo.class);
        System.out.print("\n"+"数据标识flag为:" + msgInfo.getFlag());
        List<AreaInfo> areaInfoList = msgInfo.getListJson();
        for (AreaInfo areaInfo : areaInfoList) {
            System.out.println("\n"+"****************************开始循环打印AreaInfo信息*******************************");
            Class cls = areaInfo.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);
                System.out.println("属性名:" + f.getName() + "  属性值:" + f.get(areaInfo));
            }
            System.out.println("\n"+"****************************循环打印AreaInfo信息结束*******************************");
         */


        String info = "{\"param\":{\"flag\":\"add\",\"listJson\":[{\"createDate\":\"2020-01-14\",\"flag\":\"1\",\"orgCode\":\"021000010300\",\"sex\":\"女\",\"userCode\":\"chenst5\",\"userName\":\"陈孙媞\"}]},\"url\":\"http://123.126.34.171:7009/services/UserInfoService?wsdl\"}";
        System.out.print("\n" + "字符串为:" + info);
        //转json
        net.sf.json.JSONObject jsonStr = net.sf.json.JSONObject.fromObject(info);
        String param = jsonStr.get("param") + "";
        net.sf.json.JSONObject listJson = net.sf.json.JSONObject.fromObject(param);
        String listJsonStr = listJson.get("listJson").toString();
        JSONArray array = JSONArray.fromObject(listJsonStr);
        // JSON数组转换成存储对象的集合
        //参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
        List<UserInfo> listUserInfo = JSONArray.toList(array, new UserInfo(), new JsonConfig());
    }
}
