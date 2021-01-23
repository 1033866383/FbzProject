package sortAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

//选择排序,选择一个排在一个位置
public class SelectSort {
    public static void main(String[] args) {
        int [] arr = {1,2,4,5,34,2,8,55,444,3,3,3,3333,456,657,678,7989,234,9,0,0,9,876,55,5,23,-234};
        SelectSort selectSort = new SelectSort();
        selectSort.selectSort(arr);
        System.out.println(Arrays.toString(arr));

    }
    public void selectSort(int[] arr){
        //第i个位置的数字，也就是第i小
        for(int i = 0; i < arr.length; i++){
            //获取除了排序好后数字中最小的
            for(int j = i + 1; j < arr.length; j++){
                if(arr[j] < arr[i]){
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }
}
