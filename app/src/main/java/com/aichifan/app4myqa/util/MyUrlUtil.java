package com.aichifan.app4myqa.util;

import android.text.TextUtils;
import android.util.Log;

import com.aichifan.app4myqa.MainActivity;
import com.aichifan.app4myqa.vo.LoginAccount;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by yoda on 16/8/29.
 */
public class MyUrlUtil {

    static final String COOKIES_HEADER = "Set-Cookie";
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    /**
     * 访问url获取数据流
     * @param urlStr 链接
     * @param method 方法
     * @param params 参数
     * @return 数据流
     */
    public static InputStream requestByUrl(String urlStr, String method, String params) {
        try {
            if(TextUtils.isEmpty(method)) {
                method = "GET";
            }
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            if(msCookieManager.getCookieStore().getCookies().size() > 0)
            {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                conn.setRequestProperty("Cookie",
                        TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));
            }
            //如果有参数，默认是json格式数据提交
            if(!TextUtils.isEmpty(params)) {
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                OutputStreamWriter wr = new OutputStreamWriter(os);
                wr.write(params);
                wr.flush();
                wr.close();
                os.close();
            }
            if(conn.getResponseCode() == 200) {
                return conn.getInputStream();
            }else {
                return conn.getInputStream();
            }
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static InputStream moniLogin(String loginID, String password) {
        LoginAccount account = new LoginAccount(loginID, password);
        try {
            URL url = new URL(MainActivity.HOST + "/main/login");
            Gson gson = new Gson();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter wr = new OutputStreamWriter(os);
            wr.write(gson.toJson(account));
            wr.flush();
            wr.close();
            os.close();
            if(conn.getResponseCode() == 200) {
                Map<String, List<String>> headerFields = conn.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
                if(cookiesHeader != null)
                {
                    for (String cookie : cookiesHeader)
                    {
                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    }
                }
                return conn.getInputStream();
            }
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
