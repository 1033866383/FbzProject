package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public strictfp class ByteChange {
    private byte [] bytes = null;
    private  int index = 0;
    public ByteChange(){
        this.bytes = new byte[10];
        this.index = 0;
    }
    public byte[] getByte(){
        int end = this.bytes.length-1;
        while(true){
            if(this.bytes[end] == 0){
                end--;
            }else{
                break;
            }
        }
        byte[] res = new byte[end+1];
        for(int i = 0; i < res.length; i++){
            res[i] = this.bytes[i];
        }
        return res;
    }
    public void put(int tmp){
        if(index == bytes.length-1){
            byte [] bytes = new byte[this.bytes.length*2];
            for(int i = 0; i < this.bytes.length; i++){
                bytes[i] = this.bytes[i];
            }
            this.bytes = bytes;
            this.bytes[++index] = (byte)tmp;
        }else{
            this.bytes[++index] = (byte)tmp;
        }
    }
    public static void main(String [] args) throws IOException {
        File f = new File("C:\\Users\\Administrator\\Desktop\\PythonStudy.py");
        InputStream in = new FileInputStream(f);
        int tmp = 0;
        ByteChange bt = new ByteChange();
        while((tmp = in.read()) != -1){
            bt.put(tmp);
        }
        System.out.println(new String(bt.getByte()));
    }
}
