package com.inspur.towerwebservice.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.inspur.towerwebservice.model.MsgInfo;
import com.inspur.towerwebservice.model.OrgInfo;
import com.inspur.towerwebservice.model.OrgMsgInfo;
import com.inspur.towerwebservice.model.RequestResultCode;
import com.inspur.towerwebservice.service.OrgInfoService;
import javafx.scene.DepthTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebService(name="OrgInfoService",
        serviceName = "OrgInfoService",
        targetNamespace = "http://service.towerwebservice.inspur.com/",
        endpointInterface = "com.inspur.towerwebservice.service.OrgInfoService")
@Component
public class OrgInfoServoceImpl implements OrgInfoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @WebMethod(operationName = "OrgInfoService")
    @Override
    public String OrgInfoService(String arg0) {
        JSONObject jsonObject = new JSONObject();
        try{
            //将String转换为实体类
            OrgMsgInfo orgMsgInfo=com.alibaba.fastjson.JSONObject.parseObject(arg0,OrgMsgInfo.class);

            System.out.print("\n");
            System.out.print("\n"+"接受到的组织字符串信息为:"+arg0);
            if(orgMsgInfo==null){
                jsonObject.put("flag","999");
                jsonObject.put("error_code","failed");
                jsonObject.put("desc","推送组织数据不可为空");
            }else{
                List<OrgInfo> orgInfoList=orgMsgInfo.getListJson();
                System.out.print("\n"+"+获取的组织信息数组长度为："+orgInfoList.size());
                for (OrgInfo orgInfo : orgInfoList) {
                    System.out.println("\n"+"****************************开始循环打印OrgMsgInfo信息*******************************");
                    Class cls = orgInfo.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    for(int i=0; i<fields.length; i++){
                        Field f = fields[i];
                        f.setAccessible(true);
                        System.out.println("属性名:" + f.getName() + "  属性值:" + f.get(orgInfo));
                    }
                    System.out.println("\n"+"****************************循环打印OrgMsgInfo信息结束*******************************");
                    //以下为入库操作

                    //部门类型
                    String deptType = "";
                    String deptLevel = "";
                    if("00".equals(orgInfo.getOrg_level())){
                        deptType="铁塔集团";
                        deptLevel="0";
                    }else if("01".equals(orgInfo.getOrg_level())){
                        deptType="总部";
                        deptLevel="1";
                    }else if("02".equals(orgInfo.getOrg_level())){
                        deptType="省分";
                        deptLevel="2";
                    }else if("03".equals(orgInfo.getOrg_level())){
                        deptType="地市";
                        deptLevel="3";
                    }else if("04".equals(orgInfo.getOrg_level())){
                        deptType="部门";
                        deptLevel="4";
                    }
                    //使用状态
                    String status = "";
                    if("00".equals(orgInfo.getOrg_status())){
                        status="1";
                    }else{
                        status="0";
                    }
                    //组织地址查询
                    String pathName = orgPath(orgInfo.getOrg_path(),orgInfo.getOrg_name());
                    //省份地市查询
                    Map<String,String> sfSd = city(orgInfo.getOrg_code());

                    String countSql = "select count(1) from SYS_DEPT_V3 where dept_code = ?";
                    Integer count = this.jdbcTemplate.queryForObject(countSql,new Object[]{orgInfo.getOrg_code()},Integer.class);
                    if(count>0){
                        String updateSql = "update SYS_DEPT_V3 t set " +
                                "t.int_id = ?,\n" +
                                "t.dept_code = ?,\n" +
                                "t.dept_name = ?,\n" +
                                "t.full_name = ?,\n" +
                                "t.sy_ds = ?,\n" +
                                "t.parent_dept_code = ?,\n" +
                                "t.dept_type = ?,\n" +
                                "t.dept_level = ?,\n" +
                                "t.dept_desc = ?,\n" +
                                "t.status = ?,\n" +
                                "t.create_time = ?,\n" +
                                "t.parent_id = ?,\n" +
                                "t.sy_ds_code = ?,\n" +
                                "t.sy_sf = ?,\n" +
                                "t.sy_sf_code = ?,\n" +
                                "t.mdm_area_code = ?,\n" +
                                "t.FULL_CODE = ? where t.dept_code = ?";
                        int num = this.jdbcTemplate.update(updateSql,
                                orgInfo.getOrg_code(),
                                orgInfo.getOrg_code(),
                                orgInfo.getOrg_name(),
                                pathName,
                                sfSd.get("SYDS"),
                                orgInfo.getOrg_parent_code(),
                                deptType,
                                deptLevel,
                                "",
                                status,
                                new Date(),
                                orgInfo.getOrg_parent_code(),
                                sfSd.get("SYDSCODE"),
                                sfSd.get("SYSF"),
                                sfSd.get("SYSFCODE"),
                                orgInfo.getMdm_org_code(),
                                orgInfo.getOrg_path(),
                                orgInfo.getOrg_code()
                        );
                        System.out.print("\n"+"成功更新"+num+"条组织信息！");
                        //更新用户信息
                        updateUser(sfSd.get("SYDS"),orgInfo.getOrg_name(),sfSd.get("SYSF"),orgInfo.getOrg_code());
                    }else{
                        String insertSql = "insert into SYS_DEPT_V3 t (t.int_id,t.dept_code,t.dept_name,t.full_name,t.sy_ds,t.parent_dept_code,t.dept_type,t.dept_level,t.dept_desc,t.status,t.create_time,t.parent_id,t.sy_ds_code,t.sy_sf,t.sy_sf_code,t.mdm_area_code,t.FULL_CODE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        int num = this.jdbcTemplate.update(insertSql,
                                orgInfo.getOrg_code(),
                                orgInfo.getOrg_code(),
                                orgInfo.getOrg_name(),
                                pathName,
                                sfSd.get("SYDS"),
                                orgInfo.getOrg_parent_code(),
                                deptType,
                                deptLevel,
                                "",
                                status,
                                new Date(),
                                orgInfo.getOrg_parent_code(),
                                sfSd.get("SYDSCODE"),
                                sfSd.get("SYSF"),
                                sfSd.get("SYSFCODE"),
                                orgInfo.getMdm_org_code(),
                                orgInfo.getOrg_path()
                        );
                        System.out.print("\n"+"成功插入"+num+"条组织信息！");
                    }
                }
                jsonObject.put("flag","000");
                jsonObject.put("error_code","success");
                jsonObject.put("desc","成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("flag","999");
            jsonObject.put("error_code","failed");
            jsonObject.put("desc","未知错误，请联系管理员");
        }
        return jsonObject.toJSONString();
    }

    /**
     * 地址查询
     * @param path
     * @param deptName
     * @return
     */
    public String orgPath(String path,String deptName){
        String pathName=null;
        int value = path.indexOf(',');//是否包含','
        if(value != -1){//包含
           String[] paths =  path.split(",");
           for (int i=0;i<paths.length-1;i++){
               String sql = "select dept_name from SYS_DEPT_V3 where dept_code = ?";
               List<String> dept = this.jdbcTemplate.queryForList(sql,String.class,paths[i]);
               if(dept!=null && dept.size()>0){
                   pathName = dept.get(0)+","+deptName+",";
               }

           }
        }
        return pathName;
    }

    public Map<String,String> city(String code){
        Map<String,String> map = new HashMap<String, String>();
        String sql = "select \n" +
                "case\n" +
                "   when code1 is not null then code1\n" +
                "  when code2 is not null then code2\n" +
                "end sySfCode,\n" +
                "case\n" +
                "   when code3 is not null then code3\n" +
                "  when code4 is not null then code4\n" +
                "end syDsCode,\n" +
                "case\n" +
                "   when name1 is not null then name1\n" +
                "  when name2 is not null then name2\n" +
                "end sySf,\n" +
                "case\n" +
                "   when name3 is not null then name3\n" +
                "  when name4 is not null then name4\n" +
                "end syDs,\n" +
                "code,\n" +
                "name\n" +
                " from(\n" +
                "select \n" +
                "\n" +
                "  (select p1.code from T_AREA p1 where p1.code= jj1  and p1.type=1) as code1,\n" +
                "  (select p1.code from T_AREA p1 where p1.code= jj2  and p1.type=1) as code2,\n" +
                "  (select p1.code from T_AREA p1 where p1.code= jj3  and p1.type=2) as code3,\n" +
                "  (select p1.code from T_AREA p1 where p1.code= jj2  and p1.type=2) as code4,\n" +
                "\n" +
                "\n" +
                "  (select p1.name from T_AREA p1 where p1.code= jj1  and p1.type=1) as name1,\n" +
                "  (select p1.name from T_AREA p1 where p1.code= jj2  and p1.type=1) as name2,\n" +
                "  (select p1.name from T_AREA p1 where p1.code= jj3  and p1.type=2) as name3,\n" +
                "  (select p1.name from T_AREA p1 where p1.code= jj2  and p1.type=2) as name4,\n" +
                "  code,\n" +
                "  name\n" +
                " from (\n" +
                "select substr(t1.org_path,0,instr(t1.org_path,',',1,1)-1) first,\n" +
                "       substr(t1.org_path,instr(t1.org_path,',',1,1)+1,instr(t1.org_path,',',1,2)-instr(t1.org_path,',',1,1)-1) jj1,\n" +
                "       substr(t1.org_path,instr(t1.org_path,',',1,2)+1,instr(t1.org_path,',',1,3)-instr(t1.org_path,',',1,2)-1) jj2,\n" +
                "       substr(t1.org_path,instr(t1.org_path,',',1,3)+1,instr(t1.org_path,',',1,4)-instr(t1.org_path,',',1,3)-1) jj3,\n" +
                "       substr(t1.org_path,instr(t1.org_path,',',1,4)+1,instr(t1.org_path,',',1,5)-instr(t1.org_path,',',1,4)-1) jj4,\n" +
                "       substr(t1.org_path,instr(t1.org_path,',',1,5)+1,instr(t1.org_path,',',1,6)-instr(t1.org_path,',',1,5)-1) jj5\n" +
                "       ,t1.org_code as code,\n" +
                "       t1.org_name as name\n" +
                "  from ORG_BASE t1)) where code = ?";
        List<OrgInfo>  list=this.jdbcTemplate.query(sql,new Object[]{code}, new BeanPropertyRowMapper<OrgInfo>(OrgInfo.class));
        if(list!=null && list.size()>0){
            map.put("syDs",list.get(0).getSyDs());
            map.put("syDsName",list.get(0).getSyDsName());
            map.put("sySf",list.get(0).getSySf());
            map.put("sySfCode",list.get(0).getSySfCode());
        }
        return map;
    }

    /**
     * 更新人员信息
     */
    public void updateUser(String city,String department,String province,String code){
        String sql = "update SYS_USERS_V3 t set t.city=?,t.department=?,t.province=? where t.dept_code=?";
        this.jdbcTemplate.update(sql,city,department,province,code);
    }
}
