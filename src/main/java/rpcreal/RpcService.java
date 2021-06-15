package rpcreal;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class RpcService implements Runnable{

    private Socket client;
    private Map<String, Object> services;

    public RpcService(Socket client, Map<String, Object> services) {
        super();
        this.client = client;
        this.services = services;
    }

    @Override
    public void run() {
        InputStream in = null;
        ObjectInputStream oin = null;
        OutputStream out = null;
        ObjectOutputStream oout = null;
        RpcResponse response = new RpcResponse();
        try {
            in = client.getInputStream();
            oin = new ObjectInputStream(in);
            out = client.getOutputStream();
            oout = new ObjectOutputStream(out);

            Object param = oin.readObject();
            RpcRequest request = null;
            if (!(param instanceof RpcRequest)){
                response.setError(new Exception("参数错误"));
                oout.writeObject(response);
                oout.flush();
            }else{
                request = (RpcRequest) param;
            }

            Object service = services.get(request.getClassName());
            Class<?> clazz = service.getClass();
            Method method = clazz.getMethod(request.getMethodName(), request.getParamTypes());
            Object result = method.invoke(service, request.getParams());
            response.setResult(result);
            oout.writeObject(response);
            oout.flush();
            return;
        }catch(IOException | ClassNotFoundException | NoSuchMethodException e){
            try {	//异常处理
                if(oout != null){
                    response.setError(e);
                    oout.writeObject(response);
                    oout.flush();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally{
            try {	// 关闭流
                if(in != null) in.close();
                if(oin != null) oin.close();
                if(out != null) out.close();
                if(oout != null) oout.close();
                if(client != null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
