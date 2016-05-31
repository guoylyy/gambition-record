package com.gambition.recorder;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class UrlConnectionUtil {
    private static final String TYPE_POST = "POST";

    public static JSONObject doPost(String apiLink, Map<String, Object> params) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(apiLink);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(TYPE_POST);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            DataOutputStream oStream = new DataOutputStream(connection.getOutputStream());
            JSONObject inputObject = new JSONObject();
            for (String key : params.keySet()) {
                inputObject.put(key, params.get(key));
            }
            oStream.write(inputObject.toJSONString().getBytes("utf-8"));
            Log.i("requestUrl", apiLink);
            Log.i("UrlConnectionUtil", inputObject.toJSONString());
            oStream.flush();
            oStream.close();
            String result = IOUtility.inputStream2String(connection.getInputStream());
            Log.i("result is: ", result);
            JSONObject resultObject = JSON.parseObject(result);
            return resultObject;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
