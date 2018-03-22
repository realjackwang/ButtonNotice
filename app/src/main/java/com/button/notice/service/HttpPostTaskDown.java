package com.button.notice.service;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.button.notice.util.StringUtil;
import com.loopj.android.http.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.util.EntityUtils;
import droidninja.filepicker.utils.FileUtils;

import static com.button.notice.service.Constant.URL_Download;
import static java.net.SocketOptions.SO_TIMEOUT;


/**
 * Created by Jack on 2017/12/7.
 */

public class HttpPostTaskDown extends AsyncTask<String, String, String> {

    /**  传递URL，达到指定的功能，查询，添加等**/
    private String path;
    private String file;

    /** 返回信息处理回调接口 */
    private ResponseHandler rHandler;

    /** 请求类对象 */
    private CommonRequest request;

    public HttpPostTaskDown(String path,String file,ResponseHandler rHandler){
        this.path = path;
        this.file = file;
        this.rHandler = rHandler;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlStr = "http://191737tw35.51mypc.cn/ServletTest/DownloadServlet";
        if(isCancelled())
            return null;

        StringBuilder resultBuf = new StringBuilder();

            BasicHttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 5*1000);
            HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
            HttpClient client = new DefaultHttpClient(httpParams);


            HttpPost request = new HttpPost(urlStr);

            try {

                List<NameValuePair> param = new ArrayList<NameValuePair>();
                param.add(new BasicNameValuePair("url",path));
                request.setEntity(new UrlEncodedFormEntity(param, "UTF-8"));




                // 执行请求返回相应
                HttpResponse response = client.execute(request);
                // 判断是否请求成功
                if (response.getStatusLine().getStatusCode() == 200) {
                    // 获得响应信息
                    String responseMessage = EntityUtils.toString(response
                            .getEntity());
                    Log.i("responseMessage", responseMessage + ",长度："
                            + responseMessage.length());
                    String[] responseMes = responseMessage.split("@<><2><>@");

                    for (int i = 0; i < responseMes.length; i++) {
                        String[] file = responseMes[i].split("@<><1><>@");
                        GenerateImage(file);
                    }
                    Log.i("生成成功", "生成成功");


                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBuf.toString();
    }


    public boolean GenerateImage(String[] imgStr)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null){ //图像数据为空
            return false;}
        try
        {
            //Base64解码
            byte[] b = Base64.decode(imgStr[1].getBytes(), Base64.DEFAULT);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }

            File files =new File(Environment.getExternalStorageDirectory()+"/Button");
            if(!files.exists()){
                files.mkdir();
            }
//            Environment.getExternalStorageDirectory()+"/Button/"
            String imgFilePath =file+imgStr[0];
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }




    @Override
    protected void onPostExecute(String result) {

        if (rHandler != null) {
            if (!"".equals(result)) {
				/* 交易成功时需要在处理返回结果时手动关闭Loading对话框，可以灵活处理连续请求多个接口时Loading框不断弹出、关闭的情况 */

                CommonResponse response = new CommonResponse(result);
                // 这里response.getResCode()为多少表示业务完成也是和服务器约定好的
                if ("0".equals(response.getResCode())) { // 正确
                    rHandler.success(response);
                } else {
                    rHandler.fail(response.getResCode(), response.getResMsg());
                }
            }
        }
    }

}
