package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RequestUitl {
    enum TYPE{
        GET, POST, PUT, DELETE
    }
    public static class Response{
        Map<String, List<String>> resHeaders;
        String res;
        int code;
        Response(int code, String res, Map<String, List<String>> resHeaders){
            this.code = code;
            this.res = res;
            this.resHeaders = resHeaders;
        }

        @Override
        public String toString() {
            return this.res;
        }
    }

    /**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     *
     * @param actionUrl 访问的服务器URL
     * @param params 普通参数
     * @param files 文件参数
     * @return
     * @throws IOException
     */
    public static void postFile(String actionUrl, Map<String, Object> params, Map<String, File> files) throws IOException
    {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet())
        {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }

        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        InputStream in = null;
        // 发送文件数据
        if (files != null)
        {
            for (Map.Entry<String, File> file : files.entrySet())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                // name是post中传参的键 filename是文件的名称
                sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getKey() + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());

                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1)
                {
                    outStream.write(buffer, 0, len);
                }

                is.close();
                outStream.write(LINEND.getBytes());
            }

            // 请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();
            // 得到响应码
            int res = conn.getResponseCode();
            if (res == 200)
            {
                in = conn.getInputStream();
                int ch;
                StringBuilder sb2 = new StringBuilder();
                while ((ch = in.read()) != -1)
                {
                    sb2.append((char) ch);
                }
            }
            outStream.close();
            conn.disconnect();
        }
    }

    public static Response deleteForm(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, TYPE.DELETE.name(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response putForm(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, TYPE.PUT.name(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response getForm(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, TYPE.GET.name(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response postForm(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, TYPE.POST.name(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response deleteJson(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, TYPE.DELETE.name(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response putJson(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, TYPE.PUT.name(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response getJson(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, TYPE.GET.name(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response postJson(String url, Map<String, String> headers, Map<String, Object> params){
        try {
            return sendData(url, headers, params, TYPE.POST.name(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //get只接受url和cookie
    private static Response sendData(String url, Map<String, String> headers, Map<String, Object> params, String type, boolean isJson) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod(type);
        if (isJson == true) {
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
        } else {
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        }
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
            if(isJson)
                outputStream.write(new JSONObject(params).toString().getBytes());
            else{
                StringBuilder stringBuilder = new StringBuilder();
                for(Map.Entry<String, Object> e : params.entrySet()){
                    stringBuilder.append(e.getKey()+"="+e.getValue()).append("&");
                }
                String param = stringBuilder.substring(0, stringBuilder.length() - 1);
                System.out.println("参数：" + param);
                outputStream.write(param.getBytes());
            }
            outputStream.close();
        }
        int tmp = 0;
        inputStream = httpURLConnection.getInputStream();
        ByteChange byteChange = new ByteChange();
        while ((tmp = inputStream.read()) != -1){
            byteChange.put(tmp);
        }
        Map<String, List<String>> setHeaders = httpURLConnection.getHeaderFields();
        inputStream.close();
        return new Response(httpURLConnection.getResponseCode(), new String(byteChange.getByte(), "GBK"), setHeaders);
    }



    public static void main(String[] args) {
    }
}
