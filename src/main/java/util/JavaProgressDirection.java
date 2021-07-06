
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaProgressDirection {

    public static void main(String[] args) {
        var javaProgressDirection = new JavaProgressDirection();
        try {
            javaProgressDirection.java7("java7", JavaProgressDirection.class.getResource("JavaProgressDirection.class").getPath());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        javaProgressDirection.java8();
    }

    public void java7(String flag, String filename) throws IOException {
        switch(flag){
            case "java7":
                System.out.println(flag);
                java7("java8", filename);
                break;
            case "java8":
                System.out.println("java8");
                java7("java9", filename);
                break;
            default:
                System.out.println("end");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line=reader.readLine())!=null){
                builder.append(line);
                builder.append(String.format("%n"));
            }
        }
        Map<String, List<String>> anagrams = new HashMap<>();
    }

    public void java8(){
        Thread thread = new Thread(()->{
            System.out.println("lambda java 8");
            Map<List<String>, Object> map = new HashMap<>();

        });
        thread.start();
        //相当于a类运行run，run参数为（a）,输出a，随后再调用a.run(匿名类),参数是匿名类，输出匿名类，此时a的run被重写为输出一个类，最后输出了刚才的匿名类。 lambda 很奇怪
        java8 java8 = (j)->{
            System.out.println(j);
            j.run((j1)->{
                System.out.println(j1);
            });

        };
        System.out.println("java8:"+java8);
        java8 java81 = new java8() {
            @Override
            public void run(JavaProgressDirection.java8 java8) {
                System.out.println(java8);
                //匿名类的run会被创造
                java8.run(new java8() {
                    @Override
                    public void run(JavaProgressDirection.java8 java8) {
                        System.out.println(this);
                        System.out.println(java8);
                    }
                });
            }
        };

        java8.run(java8);
        System.out.println("____________________");
        java81.run(java81);

    }
    interface java8{
        void run(java8 java8);
        default void run(){
            System.out.println("run default function");
        }
        static void runStatic(){
            System.out.println("run static function");
        }
    }

    public void java10(){
        var java10 = "java10";
    }

    public void java11(){
        " ".isBlank();
    }
//    public void java12(){
//        String in = "";
//        int res = switch (in){
//            case "1" -> 2;
//            case "2" -> 3;
//            default -> throw new IllegalStateException("Unexpected value: " + in);
//        };
//    }

//    public void java13(){
//        String html = """ html;
//                        body;
//                         run;
//                        """;
//        System.out.println();
//    }
}
