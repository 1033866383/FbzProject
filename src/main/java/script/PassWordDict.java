package script;

import java.util.ArrayList;
import java.util.List;

public class PassWordDict {
    char [] elements = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    String pass = "";
    List<String> res = new ArrayList<>();

    public static void main(String[] args) {
        PassWordDict passWordDict = new PassWordDict();
        passWordDict.add(passWordDict.pass);
        for(int i = 0; i < passWordDict.res.size(); i++){
            passWordDict.add(new StringBuilder(passWordDict.res.get(i)).toString());
        }
    }

    public void add(String pass){
        for(char i : elements){
            StringBuilder builder = new StringBuilder(pass);
            builder.append(i);
            res.add(builder.toString());
            System.out.println(builder.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
