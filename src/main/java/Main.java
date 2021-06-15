import sortAll.QuitSort;

import java.util.*;

public class Main {
    Main c = new Main();
    public static void main(String[] args) {
        var c = new Main();
        System.out.println(System.getenv());
        int [] arr = new int[]{3,2,1,3,4,6,8,-1,-23};
        Main main = new Main();
        new QuitSort().quitSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
        Set<HashMap> res = new HashSet<>();
        HashMap hq = new HashMap();
        hq.put("1",1);
        HashMap h2 = new HashMap();
        h2.put("2",2);
        System.out.println(hq.hashCode());
        System.out.println(h2.hashCode());
        res.add(h2);
        res.add(hq);
        System.out.println(res);
    }
    public int adjacentElementsProductGTY(int[] input_one) {
        int res = Integer.MIN_VALUE;
        for(int i = 0,j =1 ; j < input_one.length; ){
            res =  Math.max(input_one[i++] * input_one[j++], res);
        }
        return res;
    }
}

// token d780fafb92b0c993cc5a25d298ecc5e97df8c6ad