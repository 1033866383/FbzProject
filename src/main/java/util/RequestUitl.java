package util;

import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class RequestUitl {
    private final static String GET = "GET";
    private final static String POST = "POST";

    public static String get(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, GET);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, POST);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //get只接受url和cookie
    private static String sendData(String url, Map<String, String> headers, Map<String, Object> params, String type) throws IOException {
        var httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod(type);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = null;
        InputStream inputStream = null;

        if(headers != null){
            for(Map.Entry<String, String> entry : headers.entrySet()){
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        if(params != null){
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(new JSONObject(params).toString().getBytes());
            outputStream.close();
        }
        int tmp = 0;
        inputStream = httpURLConnection.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(out);
        while ((tmp = inputStream.read()) != -1){
            dout.write(tmp);
        }
        byte[] storingData = out.toByteArray();
        dout.close();
        out.close();
        inputStream.close();
        return new String(storingData);
    }



    public static void main(String[] args) {

    }
}
