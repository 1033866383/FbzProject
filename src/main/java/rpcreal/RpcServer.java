package rpcreal;

import annotion.FRpc;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@FRpc
public class RpcServer {

    public void start(int port, String clazz){
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            Map<String, Object> services = getService(clazz);
            Executor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
            while (true){
                Socket client = server.accept();
                RpcService service = new RpcService(client, services);
                executor.execute(service);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getService(String clazz){
        try {
            Map<String, Object> services = new HashMap<>();
            String [] clazzes = clazz.split(",");
            List<Class<?>> classes = new ArrayList<>();
            for(String cl : clazzes){
                List<Class<?>> classList = getClasses(cl);
                classes.addAll(classList);
            }
            for(Class<?> cla : classes){
                Object obj = cla.getConstructor().newInstance();
                services.put(cla.getAnnotation(FRpc.class).value().getName(), obj);
            }
            return services;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    //https://blog.csdn.net/beyond_qjm/article/details/79083126

    public static void main(String[] args) throws ClassNotFoundException {
        List ls = getClasses("rpcreal");
        List lss = getClasses("lambdatest");
        System.out.println(ls);
        System.out.println(lss);
    }


    //扫描注解
    public static List<Class<?>> getClasses(String pckgname) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File directory = null;
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if(cld == null)
                throw new ClassNotFoundException("get classload failure");
            String path = pckgname.replace(".", "/");
            URL resource = cld.getResource(path);
            if(resource == null)
                throw new ClassNotFoundException("no resource for" + path);
            directory = new File(resource.getFile());
        } catch (ClassNotFoundException e) {
                throw new ClassNotFoundException(pckgname + " (" + directory + ") does not appear to be a valid package a");
        }
        if(directory.exists()){
            File[] fileList =  directory.listFiles();
            for(File file : fileList){
                if(file.isFile() && file.getName().endsWith(".class")){
                    Class<?> clazz = Class.forName(pckgname + '.' + file.getName().substring(0, file.getName().length() - 6));
                    if(clazz.getAnnotation(annotion.FRpc.class) != null){
                        classes.add(clazz);
                    }
                }else if(file.isDirectory()){
                    List<Class<?>> result = getClasses(pckgname + '.' + file.getName());
                    if(result != null && result.size() != 0)
                        classes.addAll(result);
                }
            }
        }else{
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package b");
        }
        return classes;
    }
}
