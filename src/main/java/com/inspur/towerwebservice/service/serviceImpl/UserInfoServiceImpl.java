package com.inspur.towerwebservice.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.inspur.towerwebservice.model.OrgInfo;
import com.inspur.towerwebservice.model.RequestResultCode;
import com.inspur.towerwebservice.model.UserInfo;
import com.inspur.towerwebservice.model.UserResultCode;
import com.inspur.towerwebservice.service.UserInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;


@WebService(name="UserInfoService",
        serviceName = "UserInfoService",
        targetNamespace = "http://service.towerwebservice.inspur.com/",
        endpointInterface = "com.inspur.towerwebservice.service.UserInfoService")
@Component
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @WebMethod(operationName = "UserInfoService")
    @Override
    public String UserInfoService(String info) {
        JSONObject jsonObject = new JSONObject();
        System.out.print("\n" + "接收到的人员信息字符串为:" + info);

        try {
            net.sf.json.JSONObject jsonStr = net.sf.json.JSONObject.fromObject(info);
            String param = jsonStr.get("param") + "";
            net.sf.json.JSONObject listJson = net.sf.json.JSONObject.fromObject(param);
            String listJsonStr = listJson.get("listJson").toString();
            JSONArray array = JSONArray.fromObject(listJsonStr);
            System.out.println(array);
            // JSON数组转换成存储对象的集合
            //参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
            List<UserInfo> listUserInfo = JSONArray.toList(array, new UserInfo(), new JsonConfig());

            if (listUserInfo.size() == 0) {
                jsonObject.put("serviceId", "CHNTEMS");
                jsonObject.put("rsp", "1");
                jsonObject.put("errDesc", "推送人员数据不可为空");
            } else {
                for (UserInfo userInfo : listUserInfo) {
                    System.out.println("\n" + "****************************开始循环打印UserInfo信息*******************************");
                    Class cls = userInfo.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        Field f = fields[i];
                        f.setAccessible(true);
                        System.out.println("属性名:" + f.getName() + "  属性值:" + f.get(userInfo));
                    }
                    System.out.println("\n" + "****************************循环打印UserInfo信息结束*******************************");
                    //以下为入库操作
                    List<OrgInfo>  list = org(userInfo.getOrgCode());
                    String sfName = "";//省份名称
                    String sfCode = "";//省份编码
                    String cityName = "";//地市名称
                    String cityCode = "";//地市编码
                    String orgName= "";//部门名称
                    if(list!=null&&list.size()>0){
                        cityName = list.get(0).getSyDsName();
                        cityCode = list.get(0).getSyDs();
                        sfName = list.get(0).getSySf();
                        sfCode = list.get(0).getSySfCode();
                        orgName = list.get(0).getOrg_name();
                    }

                    String countSql = "select count(1) from SYS_USERS_V3 where LOGINNAME = ?";
                    Integer count = this.jdbcTemplate.queryForObject(countSql,new Object[]{userInfo.getUserCode()},Integer.class);
                    if(count>0){
                        String updateSql = "update SYS_USERS_V3 t set " +
                                "       t.showname = ?,\n" +
                                "       t.username = ?,\n" +
                                "       t.loginname = ?,\n" +
                                "       t.tel = ?,\n" +
                                "       t.email = ?,\n" +
                                "       t.status = ?,\n" +
                                "       t.provincecode = ?,\n" +
                                "       t.citycode = ?,\n" +
                                "       t.des = ?,\n" +
                                "       t.department = ?,\n" +
                                "       t.dept_code = ?,\n" +
                                "       t.user_code = ?,\n" +
                                "       t.city = ?,\n" +
                                "       t.province = ? where t.loginname=?";
                        int num = this.jdbcTemplate.update(updateSql,
                                userInfo.getUserName(),
                                userInfo.getUserName(),
                                userInfo.getUserCode(),
                                userInfo.getMobile(),
                                userInfo.getEmail(),
                                "1",
                                sfCode,
                                cityCode,
                                userInfo.getSortNo(),
                                orgName,
                                userInfo.getOrgCode(),
                                userInfo.getUserCode(),
                                cityName,
                                sfName,
                                userInfo.getUserCode()
                        );
                        System.out.print("\n"+"成功更新"+num+"条人员信息！");
                    }else {
                        String intIdSql = "select max(t.userid) from SYS_USERS_V3 t";
                        Integer intId = this.jdbcTemplate.queryForObject(intIdSql,Integer.class);
                        intId=intId+1;
                        String insertSql = "insert into SYS_USERS_V3 t (t.userid,t.showname,t.username,t.loginname,t.pwd,t.tel,t.email,t.status,t.provincecode,t.citycode,t.des,t.department,t.dept_code,t.user_code,t.city,t.province) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        int num = this.jdbcTemplate.update(insertSql,
                                intId,
                                userInfo.getUserName(),
                                userInfo.getUserName(),
                                userInfo.getUserCode(),
                                "e10adc3949ba59abbe56e057f20f883e",
                                userInfo.getMobile(),
                                userInfo.getEmail(),
                                "1",
                                sfCode,
                                cityCode,
                                userInfo.getSortNo(),
                                orgName,
                                userInfo.getOrgCode(),
                                userInfo.getUserCode(),
                                cityName,
                                sfName
                        );
                        System.out.print("\n"+"成功插入"+num+"条人员信息！");
                    }
                }
                jsonObject.put("serviceId", "CHNTEMS");
                jsonObject.put("rsp", "0");
                jsonObject.put("errDesc", "成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("serviceId", "CHNTEMS");
            jsonObject.put("rsp", "1");
            jsonObject.put("errDesc", "未知错误，请联系管理员");
        }
        return jsonObject.toString();
    }
    //获取组织
    public List<OrgInfo> org (String orgCode){
        String sql ="select t.dept_name as org_name,t.sy_ds as syDsName,t.sy_ds_code as syDs,t.sy_sf as sySf,t.sy_sf_code as sySfCode from SYS_DEPT_V3 t where t.dept_code = ?";
        List<OrgInfo>  list=this.jdbcTemplate.query(sql,new Object[]{orgCode}, new BeanPropertyRowMapper<OrgInfo>(OrgInfo.class));
        return list;
    }
}
