package script;

import com.sun.jna.Library;
import com.sun.jna.Native;
import util.JPoint;


public interface JavaJNA extends Library {
    int putAddr(long addr, int value);

    static void main(String[] args) {
        JavaJNA INSTANCE = Native.loadLibrary(System.getProperty("user.dir")+"/pythonproject/pywithc/"+"put.so", JavaJNA.class);
        long addr = JPoint.getJPoint().allocateMemory(8);
        INSTANCE.putAddr(addr,-22);
        int value = JPoint.getJPoint().getInt(addr);
        System.out.println(value);
    }
}
