package script;

import java.util.ArrayList;
import java.util.List;

public class PassWordDict {
    char [] elements = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    String pass = "";

    public static void main(String[] args) {
        List<String> res = new ArrayList<>();
        PassWordDict passWordDict = new PassWordDict();
        passWordDict.add(passWordDict.pass, res);
        for(int i = 0; res.get(res.size()-1).length() < 7; i++){
            passWordDict.add(new StringBuilder(res.get(i)).toString(),res);
        }
    }

    public void add(String pass, List<String> res){
        for(char i : elements){
            StringBuilder builder = new StringBuilder(pass);
            builder.append(i);
            res.add(builder.toString());
            System.out.println(builder.toString());
        }
    }
}
