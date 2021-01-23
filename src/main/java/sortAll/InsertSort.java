package sortAll;

import java.util.Arrays;

//插入排序
public class InsertSort {
    public static void main(String[] args) {
        int [] arr = {1,2,4,5,34,2,8,55,444,3,3,3,3333,456,657,678,7989,234,9,0,0,9,876,55,5,23,-234};
        InsertSort insertSort = new InsertSort();
        insertSort.insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    //从第二个开始往前插
    public void insertSort(int [] arr){
            if(arr == null || arr.length < 2){
                return;
            }
            for (int i = 1; i < arr.length; i++) {
                int temp = arr[i];
                int k = i - 1;
                while(k >= 0 && arr[k] > temp)
                    k--;
                //腾出位置插进去,要插的位置是 k + 1;
                for(int j = i ; j > k + 1; j--)
                    arr[j] = arr[j-1];
                //插进去
                arr[k+1] = temp;
            }
    }

}
