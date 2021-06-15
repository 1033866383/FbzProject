package util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ScanAnnotion {
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
