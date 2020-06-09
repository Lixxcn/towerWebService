package com.inspur.towerwebservice.service.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.inspur.towerwebservice.model.AreaInfo;
import com.inspur.towerwebservice.model.MsgInfo;
import com.inspur.towerwebservice.model.RequestResultCode;
import com.inspur.towerwebservice.service.AreaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.lang.reflect.Field;
import java.util.List;

@WebService(name = "AreaInfoService",
        serviceName = "AreaInfoService",
        targetNamespace = "http://service.towerwebservice.inspur.com/",
        endpointInterface = "com.inspur.towerwebservice.service.AreaInfoService")
@Component
public class AreaInfoServiceImpl implements AreaInfoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @WebMethod(operationName = "AreaInfoService")
    @Override
    public String AreaInfoService(String arg0){
        JSONObject jsonObject = new JSONObject();
        try {
            //将String转换为实体类
            MsgInfo msgInfo=com.alibaba.fastjson.JSONObject.parseObject(arg0,MsgInfo.class);
            System.out.print("\n"+"接受到的区域字符串信息为:"+arg0);
            if (msgInfo==null){
                jsonObject.put("flag","999");
                jsonObject.put("error_code","failed");
                jsonObject.put("desc","推送区域信息不可为空");
            }else {
                List<AreaInfo> areaInfoList=msgInfo.getListJson();
                System.out.print("\n"+"推送的区域信息数组长度为："+areaInfoList.size());

                for (AreaInfo areaInfo: areaInfoList) {
                    System.out.println("\n"+"****************************开始循环打印AreaInfo信息*******************************");
                    Class cls = areaInfo.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    for(int i=0; i<fields.length; i++){
                        Field f = fields[i];
                        f.setAccessible(true);
                        System.out.println("属性名:" + f.getName() + "  属性值:" + f.get(areaInfo));
                    }
                    System.out.println("\n"+"****************************循环打印AreaInfo信息结束*******************************");
                    //以下为入库操作
                    int type=Integer.parseInt(areaInfo.getArea_level());
                    //管理区域登记转化
                    if (type == 1){
                        type = 0;
                    }else if (type == 2){
                        type = 1;
                    }else if (type == 3){
                        type = 2;
                    }else if (type == 5){
                        type = 3;
                    }
                    //管理区域状态转化
                    Integer stateflag=Integer.parseInt(areaInfo.getArea_status())==0?1:0;
                    StringBuffer selectByKey=new StringBuffer();
                    selectByKey.append("select count(*) from t_area where code = ?");
                    //判断地区表中是否已经存在
                    int codeNum=this.jdbcTemplate.queryForObject(selectByKey.toString(),new Object[]{areaInfo.getArea_code()},Integer.class);
                    if (codeNum>0){
                        StringBuffer updateByKey = new StringBuffer();
                        updateByKey.append("update t_area set id=?,name=?,parent_id=?,type=?,sort=?,stateflag=? where code=?");
                        //已经存在则更新
                        int updateNum=this.jdbcTemplate.update(updateByKey.toString(),areaInfo.getArea_code(),areaInfo.getArea_name(),
                                areaInfo.getArea_parent_code(),type,Integer.parseInt(areaInfo.getArea_code()),stateflag,areaInfo.getArea_code());
                        System.out.print("\n"+"成功更新"+updateNum+"条区域信息！");
                        //保存字典信息
                        saveDictInfo(areaInfo, type);
                    }else{
                        StringBuffer insertAreaInfo=new StringBuffer();
                        insertAreaInfo.append("insert into t_area (id,code,name,parent_id,type,sort,stateflag) values(?,?,?,?,?,?,?)");
                        //没有则进行插入
                        int insertNum=this.jdbcTemplate.update(insertAreaInfo.toString(),areaInfo.getArea_code(),areaInfo.getArea_code(),areaInfo.getArea_name(),
                                 areaInfo.getArea_parent_code(),type,Integer.parseInt(areaInfo.getArea_code()),stateflag);
                        System.out.print("\n"+"成功插入"+insertNum+"条区域信息！");
                        //保存字典信息
                        saveDictInfo(areaInfo, type);
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
        return jsonObject.toString();
    }



    private void saveDictInfo(AreaInfo areaInfo, int type) {
        //判断字典表中是否已经存在
        StringBuffer selectDictByKey=new StringBuffer();
        selectDictByKey.append("select count(*) from sys_dict_config where dict_id=?");
        int dictNum=this.jdbcTemplate.queryForObject(selectDictByKey.toString(),new Object[]{areaInfo.getArea_code()},Integer.class);
        String dictType="";
        String dictParentValue="";
        String dictMoudle="";
        int dictId=Integer.parseInt(areaInfo.getArea_code());
        if(type==0) {
            dictType="tower";
            dictMoudle="总部";
        }else{
            StringBuffer selectParntName = new StringBuffer();
            selectParntName.append("select name from t_area where code = '");
            selectParntName.append(areaInfo.getArea_parent_code());
            selectParntName.append("'");

            if(type==1){
                dictType="province";
                dictParentValue="铁塔集团";
                dictMoudle="省份";
            }else if(type==2){
                dictType="city";
                dictMoudle="地市";
                dictParentValue=this.jdbcTemplate.queryForObject(selectParntName.toString(),String.class);
            }else if(type==3){
                dictType="county";
                dictMoudle="区县";
                dictParentValue=this.jdbcTemplate.queryForObject(selectParntName.toString(),String.class);
            }
        }
        if (dictNum>0){
            //字典表已经存在，更新
            StringBuffer updateDictByKey = new StringBuffer();
            updateDictByKey.append("update sys_dict_config set dict_type=?,dict_text=?,dict_value=?,dict_parent_value=?,remark=?,dict_moudle=? where dict_id=?");
            int updateDictNum = this.jdbcTemplate.update(updateDictByKey.toString(), dictType, areaInfo.getArea_name(), areaInfo.getArea_name(), dictParentValue, areaInfo.getArea_code(), dictMoudle, dictId);
            System.out.print("\n"+"成功更新" + updateDictNum + "条字典数据！");

        }else{
            //字典表中不存在，插入
            StringBuffer insertDict = new StringBuffer();
            insertDict.append("insert into  sys_dict_config (dict_id,dict_type,dict_text,dict_value,dict_order,dict_parent_value,remark,dict_moudle) values (?,?,?,?,?,?,?,?)");
            int insertDictNum = this.jdbcTemplate.update(insertDict.toString(), dictId,dictType, areaInfo.getArea_name(), areaInfo.getArea_name(), dictId , dictParentValue, areaInfo.getArea_code(), dictMoudle);
            System.out.print("\n"+"成功更新" + insertDictNum + "条字典数据！");
        }
    }
}
