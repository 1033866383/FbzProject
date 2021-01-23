package util;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class JPoint {

    private Unsafe theUnsafe = null;
    private static JPoint jPoint = null;
    private JPoint(){
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            this.theUnsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Unsafe getJPoint(){
        synchronized(Unsafe.class){
            if(JPoint.jPoint == null){
                JPoint.jPoint = new JPoint();
                return JPoint.jPoint.theUnsafe;
            }
            return JPoint.jPoint.theUnsafe;
        }
    }

}
