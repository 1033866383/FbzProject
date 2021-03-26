package script;

import com.pi4j.io.gpio.*;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import util.JPoint;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public interface JavaJNA extends Library {
    int putAddr(long addr, int value);
    long change(int value);
    int putInt(Pointer p, int value);
    void chengarr(int [] arr);

    static void main(String[] args) {

        PointerByReference j = new PointerByReference();
        JavaJNA INSTANCE = Native.loadLibrary(System.getProperty("user.dir")+"/pythonproject/pywithc/"+"put.so", JavaJNA.class);
//        long addr = JPoint.getJPoint().allocateMemory(8);
//        INSTANCE.putAddr(addr,-22);
//        int value = JPoint.getJPoint().getInt(addr);
//        System.out.println(value);
//        int c = 0;
//        long ad = INSTANCE.change(c);
//        System.out.println(ad);
//        INSTANCE.putAddr(ad, 2);
//        int s = JPoint.getJPoint().getInt(c);
//        System.out.println("=="+s);

        Stack st = new Stack();
        Queue q = new LinkedList();
        q.poll();
        int p = 23;
        int size=Native.getNativeSize(int.class);
        Pointer p2 = new Memory(size);
        p2.setFloat(0, p);
        int sdf = INSTANCE.putInt(p2, -12 );
        System.out.println(sdf);
        int [] arr = {1,2};
        INSTANCE.chengarr(arr);
        System.out.println(arr[0]);
    }
}
