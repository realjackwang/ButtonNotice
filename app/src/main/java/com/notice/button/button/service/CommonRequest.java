package com.notice.button.button.service;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Jack on 2017/12/7.
 */

public class CommonRequest {

    private String userName;
    private String passWord;


    /** 查找对应的表**/
    private String Table;
    /** 查找对应的行 **/
    private String Id;
    /** 查找对应的列 **/
    private String List;


    /**
     * 请求码，类似于接口号（在本文中用Servlet做服务器时暂时用不到）
     */
    private String requestCode;
    /**
     * 请求参数
     * （说明：这里只用一个简单map类封装请求参数，对于请求报文需要上送一个数组的复杂情况需要自己再加一个ArrayList类型的成员变量来实现）
     */
    private HashMap<String, String> requestParam;

    public CommonRequest() {
        requestCode = "";
        requestParam = new HashMap<>();
    }

    /**
     * 设置请求代码，即接口号，在本例中暂时未用到
     */
    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    /**
     * 为请求报文设置参数
     * @param paramKey 参数名
     * @param paramValue 参数值
     */
    public void addRequestParam(String paramKey, String paramValue) {
        requestParam.put(paramKey, paramValue);
    }

    /**
     * 将请求报文体组装成json形式的字符串，以便进行网络发送
     * @return 请求报文的json字符串
     */
    public String getJsonStr() {
        // 由于Android源码自带的JSon功能不够强大（没有直接从Bean转到JSonObject的API），为了不引入第三方资源这里我们只能手动拼装一下啦
        JSONObject object = new JSONObject();
        JSONObject param = new JSONObject(requestParam);
        try {
            // 下边2个"requestCod'e"、"requestParam"是和服务器约定好的请求体字段名称，在本文接下来的服务端代码会说到
//            object.put("","{");
            object.put("requestCode", requestCode);
            object.put("requestParam", param);
        } catch (JSONException e) {
        //    LogUtil.logErr("请求报文组装异常：" + e.getMessage());
        }
        // 打印原始请求报文
     //   LogUtil.logRequest(object.toString());
        return object.toString();
    }

    /**
     * 用于登录
     * 用setUserName和setPassWord设置账户密码
     * @param rHandler 返回成功或失败的值
     */
    public void Login(ResponseHandler rHandler){       //登录

        final CommonRequest request =new CommonRequest();
            request.addRequestParam("userAccount",this.getUserName());
            request.addRequestParam("userPassword",this.getPassWord());

        new HttpPostTask(Constant.URL_Login,request,rHandler).execute();

    }

    /**
     * 用于注册
     * 用setUserName和setPassWord设置账户密码
     * @param rHandler 返回成功或失败的值
     */
    public void Signup(ResponseHandler rHandler){   //注册

        final CommonRequest request =new CommonRequest();
        request.addRequestParam("userAccount",this.getUserName());
        request.addRequestParam("userPassword",this.getPassWord());
        new HttpPostTask(Constant.URL_Register,request,rHandler).execute();

    }

    /**
     * 用于上传信息（用户，通知，等等）
     * 上传前首先用setTable方法设置要上传到那个表，然后用addRequestParam添加要上传的列的名字和列的值
     * @param request 需要上传的信息
     * @param rHandler 返回成功或失败的值
     */
    public void Updata(CommonRequest request,ResponseHandler rHandler){   //更新用户信息
        request.addRequestParam("Table",this.getTable());
        new HttpPostTask(Constant.URL_Updata,request,rHandler).execute();
    }

    /**
     * 用于创建表（发通知，发公告）
     * 创建前首先用setTable方法设置要上传到那个表，然后用addRequestParam添加要上传的列的名字和列的值
     * @param rHandler 返回成功或失败的值
     */
    public void Create( CommonRequest request,ResponseHandler rHandler){  //创建

       request.addRequestParam("Table",this.getTable());
        new HttpPostTask(Constant.URL_Create,request,rHandler).execute();

    }

    /**
     * 用于删除表（删除公告，撤回）
     * @param rHandler 返回成功或失败的值
     */
    public void Delete(ResponseHandler rHandler){  //删除

        final CommonRequest request =new CommonRequest();
//        request.addRequestParam("userAccount",this.getUserName());
//        request.addRequestParam("userPassword",this.getPassWord());
        new HttpPostTask(Constant.URL_Register,request,rHandler).execute();

    }


    /**
     * 用于查询
     * 查询前首先用setTable方法设置查询哪个表，然后用setId方法设置要查询哪一行
     * @param rHandler 返回成功或失败的值
     */
    public void Query(ResponseHandler rHandler){

        final CommonRequest request =new CommonRequest();
        request.addRequestParam("Table",this.getTable());
        request.addRequestParam("Id",this.getId());
        request.addRequestParam("List",this.getList());
        new HttpPostTask(Constant.URL_Query,request,rHandler).execute();

    }




/**
 * 用于获取当前用户的Id
 * @param c 当前的Activity名称
 * @return 当前用户的Id
 **/

    public String getCurrentId(Context c) {
        final SharedPreferences sp = c.getSharedPreferences("DODODO", Context.MODE_PRIVATE);
        String Id=sp.getString("Id","");
        return Id;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getTable() {
        return Table;
    }

    public void setTable(String table) {
        Table = table;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getList() {
        return List;
    }

    public void setList(String list) {
        List = list;
    }


}
