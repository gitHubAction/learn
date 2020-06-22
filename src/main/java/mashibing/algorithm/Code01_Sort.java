package mashibing.algorithm;

/**
 * ClassName:    Sort_01
 * Package:    mashibing.algorithm
 * Datetime:    2020/6/22   9:52
 * Author:   zsh
 * Description:
 */
public class Code01_Sort {


    public static void main(String[] args) {

    }


    public static void insertionSort(int[] arr){
        if(arr.length < 2)return;
        //0-0位置上有序
        //0-1位置上有序
        //0-2位置上有序
        //0-3位置上有序
        //。。。
        //0-i位置上有序
        for (int i = 1; i < arr.length; i++) {
            for (int j = i-1; j > 0 && arr[j] > arr[j+1] ; j--) {
                swap(arr,j,j+1);
            }
        }
    }
    
    public static void selectionSort(int[] arr){
        if(arr.length < 2)return;
        //从0~N-1
        //从1~N-1
        //从2~N-1
        //从3~N-1
        for (int i = 0; i < arr.length-1; i++) {
            int minIndex = i;
            //
            for (int j = i+1; j < arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            swap(arr,i,minIndex);
        }
    }

    public static void bubbleSort(int[] arr){
        if(arr.length < 2)return;

        //从0~N
        //从0~N-1
        //从0~N-2
        //从0~N-3
        for (int e = arr.length-1; e >0 ; e--) {

            for (int i = 0; i < e; i++) {
                if(arr[i] > arr[i+1]){
                    swap(arr,i,i+1);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int i1) {
        int temp = arr[i];
        arr[i] = arr[i1];
        arr[i1] = temp;
    }

    private static void swap1(int[] arr, int i, int i1) {
        arr[i] = arr[i] ^ arr[i1];
        arr[i1] = arr[i] ^ arr[i1];
        arr[i] = arr[i] ^ arr[i1];
    }
}
