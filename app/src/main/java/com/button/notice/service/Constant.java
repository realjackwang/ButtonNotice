package com.button.notice.service;

/**
 * Created by Jack on 2017/12/7.
 */

public class Constant {
    public static String URL = "http://button.iok.la:37757/ServletTest/"; // IP地址请改为你自己的IP
    public static String URL_Register = URL + "RegisterServlet" ;
    public static String URL_Login = URL + "LoginServlet" ;
    public static String URL_Create = URL + "CreateServlet" ;
    public static String URL_Delete = URL + "DeleteServlet" ;
    public static String URL_Updata = URL + "UpdataServlet";
    public static String URL_Query = URL + "QueryServlet";
    public static String URL_Connect = URL + "ConnectServlet";
    public static String URL_Upload =URL +"UploadServlet";
    public static String URL_Excel =URL +"ExcelServlet";
    public static String URL_Download =URL +"DownloadServlet";
    public static String URL_Files ="http://button.iok.la:37757/upload/";

    public static String FILE_FILES =" G:\\upload\\activitys\\1\\files\\";

    public static int HANDLER_HTTP_RECEIVE_FAIL =0;
    public static int HANDLER_HTTP_SEND_FAIL =0;

}
