package testframework;

import annotion.FT;
import com.alibaba.fastjson.JSONObject;
import util.JPoint;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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

    private List<String> getClassFile(){
        var classFiles = new ArrayList<String>();
        var file = new File(System.getProperty("user.dir"));
        getFile(file, classFiles);
        return classFiles;
    }

    private void getFile(File file, List<String> classFiles){
      if(file.isFile() && file.getName().endsWith("class")){
          classFiles.add(file.getAbsolutePath());
      }else if(file.isDirectory()){
          var files = file.listFiles();
          for(var subfile : files){
            getFile(subfile, classFiles);
          }
      }
    }

    private JSONObject readJsonFile(String fileName){
        if (fileName == null || "".equals(fileName)){
            return null;
        }
        try {
            var in = new BufferedReader(new BufferedReader(new FileReader(fileName)));
            var stringBuilder = new StringBuilder();
            String tmp = null;
            while ((tmp = in.readLine()) != null){
                stringBuilder.append(tmp);
            }
            var res = JSONObject.parseObject(stringBuilder.toString());
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public void runAll(){
        var classpath = this.getClass().getResource("/").getPath();
        getTestMethod(classpath);
    }

    public void getTestMethod(String rootDir){
        var classFiles = getClassFile();
        classFiles.stream().forEach(x -> {
              if(x != null){
                  try {
                      var classUrl =  x.substring(x.indexOf(rootDir) + rootDir.length() + 1, x.length() - 6).replace("\\", ".").replace("/", ".");
                      var clazz = this.getClass().getClassLoader().loadClass(classUrl);
                      var fields = clazz.getFields();
                      for (var i : fields) i.setAccessible(true);
                      var methods = clazz.getMethods();
                      for(Method method : methods){
                          if(method.getAnnotation(FT.class) != null){
                              System.out.println(">>> 开始自动化测试 >>>");
                              var unsafe = JPoint.getJPoint();
                              var object =  unsafe.allocateInstance(clazz);
                              var types =  method.getParameterTypes();
                              var an = method.getAnnotation(FT.class);
                              var jsonName = an.value();
                              JSONObject params = readJsonFile(jsonName);
                              System.out.println(Arrays.toString(types));
                              if (params == null){

                              }
                              method.invoke(object, new Object []{0,0,0,0,0,0,0,0,"1"});
                              object = null;
                              System.out.println(">>> 自动化测试结束 >>>");
                          }
                      }
                  } catch (ClassNotFoundException e) {
                      e.printStackTrace();
                  } catch (InvocationTargetException e) {
                      e.printStackTrace();
                  } catch (IllegalAccessException e) {
                      e.printStackTrace();
                  } catch (InstantiationException e) {
                      e.printStackTrace();
                  }
              }
          });
    }

    public static void main(String[] args) throws IOException {
        var mainRun = new MainRun();
        mainRun.runAll();
    }
}
