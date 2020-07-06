package mashibing.algorithm.class4;

/**
 * ClassName:    Code02_heapSort
 * Package:    mashibing.algorithm.class4
 * Datetime:    2020/7/6   15:51
 * Author:   zsh
 * Description: 堆排序(先构造堆结构，然后依次将最后的元素与堆顶元素互换，再构造堆)
 */
public class Code02_heapSort {

    public static void heapSort(int[] arr){
        //构造堆
        /*for (int i = 0; i < arr.length; i++) {
            heapinsert(arr,i);
        }*/
        for (int i = arr.length -1 ; i > 0; i--) {
            heapify(arr,0,i);
        }
        int heapSize = arr.length;
        swap(arr,0,--heapSize);
        while (heapSize > 0){
            heapify(arr,0,heapSize);
            swap(arr,0,--heapSize);
        }
    }

    private static void heapify(int[] arr, int i, int heapSize) {
        int left = (2 * i) + 1;
        while (left < heapSize){
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[i] > arr[largest] ? i : largest;
            if(i == largest){
                break;
            }
            //父子交换
            swap(arr,i,largest);
            //索引下沉
            i = largest;
            //左孩子下沉
            left = (2 * i) + 1;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
