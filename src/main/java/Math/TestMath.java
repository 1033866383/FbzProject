package Math;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract interface TestMath {
    static BigInteger bigInteger = new BigInteger("1");
    public abstract void test(TestMath test);

    public static void main(String[] args) {
        for(int i = 1; i <= 200000000000000000000000000000000000000000000000000000000000000000000000000000D; i++){
//            bigInteger = bigInteger.multiply(new BigInteger(""+i));
//            System.out.println(bigInteger.toString());
        }
//        System.out.println(bigInteger.toString());
    }
}
