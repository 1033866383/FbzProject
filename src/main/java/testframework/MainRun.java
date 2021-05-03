package testframework;

import annotion.FT;
import util.FindAllFilePath;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MainRun {

    @FT
    public void run(){
        System.out.println("ft");
    }

    @FT
    public void test(){
        System.out.println("ssd");
    }

    public void getTestMethod(){
        List<String> filePackage =  FindAllFilePath.findFileAbstractURl();
        filePackage.stream().forEach(x ->{
            if(x != null){
                try {
                    Class<?> clazz = Class.forName(x);
                    Method[] methods = clazz.getMethods();
                    for(Method method : methods){
                        if(method.getAnnotation(FT.class) != null){
                            System.out.println(">>> 开始自动化测试 >>>");
                            Object object = clazz.getConstructor(null).newInstance();
                            method.invoke(object, null);
                            System.out.println(">>> 自动化测试结束 >>>");
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("load failure" + x);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args) throws IOException {
        MainRun mainRun = new MainRun();
        mainRun.getTestMethod();

    }
}
