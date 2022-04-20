package mashibing.algorithm.class1;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2021/11/2 15:58
 */
public class InsertionSort {

    /**
     * 插入排序
     * 0~1上有序
     * 0~2上有序
     * 0~3上有序
     * 0~4上有序
     * ....
     * 0~N上有序
     * @param args
     * @return void
     * @author zhangshihao01
     * @date 2021/11/2 16:01
     */
    public static void insertionSort(int[] args) {
        if(args.length == 0 || args.length  == 1){
            return;
        }
        for (int i = 1; i < args.length; i++) {
            for (int j = i - 1; j >= 0 && args[j] > args[j + 1]; j--) {
                System.out.println(j);
                swap(args,j,j+1);
            }
            System.out.println("----");
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
//        int[] ints = generateRandomArray(5, 10);
//        for (int i = 0; i < ints.length; i++) {
//            System.out.print(ints[i]+" ");
//        }
        int[] ints = {2,0,-4,4,4};
        System.out.println();
        insertionSort(ints);
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i]+" ");
        }
    }
}
