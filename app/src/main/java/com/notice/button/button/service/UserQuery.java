package com.notice.button.button.service;

/**
 * Created by Jack on 2017/12/22.
 */

public class UserQuery extends CommonRequest {

    /** 查找对应的表**/
    private String Table;
    /** 查找对应的行 **/
    private String Id;
    /** 查找对应的列 **/
    private String List;

    public void Query(ResponseHandler rHandler){

        final CommonRequest request =new CommonRequest();
        request.addRequestParam("Table",this.getTable());
        request.addRequestParam("Id",this.getId());
        request.addRequestParam("List",this.getList());
        new HttpPostTask(Constant.URL_Query,request,rHandler).execute();

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
