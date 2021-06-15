package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FindAllFilePath {

    public static List<String> findFileAbstractURl(){
        List<String> packageList = new ArrayList<>();
        File file = new File(System.getProperty("user.dir") + "/target");
        getFile(file, packageList);
        return packageList;
    }

    private static void getFile(File file, List<String> packageList){
         if(file.isFile()){
            String res = getPackageWithFileName(file);
            if(res != null)
                packageList.add(res);
         }else if(file.isDirectory()){
             File[] files = file.listFiles();
             for(File subfile : files){
                getFile(subfile, packageList);
             }
        }
    }

    private static String getPackageWithFileName(File file){
        if( !file.getName().endsWith("class") && !file.getName().endsWith("java")){
            return null;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String head = null;
            while ((head = bufferedReader.readLine()) != null){
                if(head.contains("package ")){
                    String res = head.replace("package", "").replace(" ", "").replace(";", "") + "." +file.getName().substring(0, file.getName().length() - 6);
                    return res;
                }else{
                   return file.getName().substring(0, file.getName().length() - 5);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>();
        List<String> ls =  FindAllFilePath.findFileAbstractURl();
        ls.stream().forEach(x ->{
            if(x != null) {
                try {
                    Class<?> aClass = Class.forName(x);
                    if(aClass.getAnnotation(annotion.FRpc.class) != null){
                        classes.add(aClass);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(classes);
    }
}
