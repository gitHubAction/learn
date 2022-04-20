package mashibing.algorithm.class1;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2021/11/2 15:42
 */
public class SelectionSort {

    /**
     * 选择排序，
     * 0~n-1最小的数放到对应位置上
     * 1~n-1最小的数放到对应位置上
     * 2~n-1最小的数放到对应位置上
     * ....
     * @param arr
     * @return void
     * @author zhangshihao01
     * @date 2021/11/2 15:47
     */
    public static void selectionSort(int[] arr) {
        if(arr.length == 0 || arr.length == 1){
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex  = i;
            for (int j = i+1; j < arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            swap(arr, i, minIndex);
        }
    }

    private static void swap(int[] arr, int i, int minIndex) {
        int temp = arr[minIndex];
        arr[minIndex] = arr[i];
        arr[i] = temp;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        // Math.random()   [0,1)
        // Math.random() * N  [0,N)
        // (int)(Math.random() * N)  [0, N-1]
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] ints = generateRandomArray(5, 10);
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i]+" ");
        }
        System.out.println();
        selectionSort(ints);
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i]+" ");
        }
    }
}
