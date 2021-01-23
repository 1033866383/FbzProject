package datastruct;

import sun.misc.Unsafe;
import util.JPoint;


/*
* 实现了一个java数组的数据结构
* */

public class FIntArray{
    private final int intsize = 4;
    public int len = 0;
    private final Unsafe theunsafe = JPoint.getJPoint();
    private long headaddress = 0L;
    public FIntArray(int len){
        this.headaddress = theunsafe.allocateMemory(intsize * len);
        for(int i = 0; i < len; i++){
            theunsafe.putInt(headaddress + i * intsize, 0);
        }
        this.len = len;
    }

    public int getV(int index){
        if(index >= len){
            throw new ArrayIndexOutOfBoundsException();
        }
        return theunsafe.getInt(headaddress + intsize * index);
    }

    public void setV(int index, int value){
        if(index >= len){
            throw new ArrayIndexOutOfBoundsException();
        }
        theunsafe.putInt(headaddress + intsize * index, value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for(int i = 0; i < len; i++){
            stringBuilder.append(theunsafe.getInt(headaddress + i * intsize));
            if(i != len -1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        theunsafe.freeMemory(headaddress);
    }

    public static void main(String[] args) {
        FIntArray fIntArray = new FIntArray(10);
        System.out.println(fIntArray);
        for(int i = 0; i < 10; i++ ){
            fIntArray.setV(i,1 << i);
        }
        System.out.println(fIntArray);
        fIntArray.theunsafe.freeMemory(fIntArray.headaddress);
    }
}
