package sortAll;

public class QuitSort {
    public void quitSort(int [] arr, int left, int right){
        if(left >= right){
            return;
        }
        int l = left , r = right, tmp = arr[left];
        while(l < r){
            while(l < r && tmp < arr[r]){
                r--;
            }
            arr[l] = arr[r];
            while(l < r && tmp >= arr[l]){
                l++;
            }
            arr[r] = arr[l];
            arr[l] = tmp;
        }
        quitSort(arr, left, l - 1);
        quitSort(arr, r+1, right);
    }
}
