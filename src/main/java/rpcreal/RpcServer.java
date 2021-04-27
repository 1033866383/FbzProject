package rpcreal;

import annotion.FRpc;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@FRpc
public class RpcServer {

    public void start(int port, String clazz){
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public Map<String, Object> getService(String clazz){
//        Map<String, Object> services = new HashMap<>();
//        String [] clazzes = clazz.split(",");
//        List<Class<?>> classes = new ArrayList<>();
//        for(String cl : clazzes){
//            List<Class<?>> classList = getClasses(cl);
//        }
//    }

    public static void main(String[] args) throws ClassNotFoundException {
        List ls = getClasses("rpcreal");
        System.out.println(ls);
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
            String [] files = directory.list();
            File[] fileList =  directory.listFiles();
            for(int i = 0; fileList != null && i < fileList.length; i++){
                File file = fileList[i];
                if(file.isFile() && file.getName().endsWith(".class")){
                    Class<?> clazz = Class.forName(pckgname + '.' + files[i].substring(0, files[i].length() - 6));
                    if(clazz.getAnnotation(annotion.FRpc.class) != null){
                        classes.add(clazz);
                    }
                }else if(file.isDirectory()){
                    List<Class<?>> result = getClasses(pckgname + '.' + file.getName());
                    if(result != null && result.size() != 0){
                        classes.addAll(result);
                    }
                }
            }
        }else{
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package b");
        }
        return classes;
    }
}
