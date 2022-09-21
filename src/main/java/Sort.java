import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Arrays;

/**
 * @Author: zhangsh
 * @Date: 2019/9/18 16:25
 * @Version 1.0
 * Description
 */
public class Sort {


    public static void main(String[] args) {
        JSONObject json1 = JSONUtil.createObj(JSONConfig.create().setOrder(true))
                .set("aKey", "value1")
                .set("bJob", "value2")
                .set("cGood", "value3")
                .set("d", true);

// {"a_key":"value1","b_job":"value2","c_good":"value3","d":true}
        final String s = json1.toJSONString(0, (pair) -> {
            pair.setKey(StrUtil.toUnderlineCase(pair.getKey()));
            return true;
        });
        System.out.println(s);
        /*int[] ints1 = {3,6,7};
        int[] ints2 = {1,2,5};
        int[] ints = merge(ints1,ints2);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }*/
//        System.out.println(null == Integer.valueOf(1) );
        System.out.println("   32   ".trim());
        int[] ints2 = {3,5,6,1,2,4};
        for(int i = 0; i <ints2.length; i++){
            int cur = ints2[i], preIndex = i-1;
            while(preIndex >= 0 && cur > ints2[preIndex]){
                ints2[preIndex+1] = ints2[preIndex];
                preIndex--;
            }
            ints2[preIndex+1] = cur;
        }
        int[] res = insertSort(ints2);
    }

    public static int[] bubleSort(int[] arr){
        if(arr == null || arr.length < 2)return arr;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if(arr[i] > arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }


    public static int[] insertSort(int[] arr){
        if(arr == null || arr.length < 2)return arr;
        for (int i = 0; i < arr.length; i++) {
            int cur = arr[i];
            int preIndex = i-1;

            while (preIndex >= 0 && cur < arr[preIndex]){
                arr[preIndex+1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex+1] = cur;
        }

        return arr;
    }


    public static int[] selectionSort(int[] arr){
        if(arr == null || arr.length < 2)return arr;
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i+1; j < arr.length; j++) {
                if(arr[j] < arr[minIndex]){
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }


    public static int[] mergeSort(int[] arr){
        if(arr == null || arr.length < 2)return arr;
        int mid = arr.length/2;
        int[] leftArr = Arrays.copyOfRange(arr,0,mid);
        int[] rightArr = Arrays.copyOfRange(arr,mid,arr.length);
        return merge(mergeSort(leftArr),mergeSort(rightArr));
    }

    private static int[] merge(int[] leftArr, int[] rightArr) {
        int[] result = new int[leftArr.length+rightArr.length];

        for (int index = 0,left = 0,right = 0; index < result.length; index++) {
            if(left >= leftArr.length){
                result[index] = rightArr[right++];
            }else if(right >= rightArr.length){
                result[index] = leftArr[left++];
            }else if(rightArr[right] > leftArr[left]){
                result[index] = rightArr[right++];
            }else {
                result[index] = leftArr[left++];
            }
        }
        return result;

    }


    public static int[] quickSort(int[] arr,int left,int right){
        if(arr == null || arr.length < 2)return arr;
        int paviot;
        if (right > left){
            paviot = partition(arr,left,right);
            quickSort(arr,left,paviot-1);
            quickSort(arr,paviot+1,right);
        }
        return arr;
    }

    private static int partition(int[] arr, int left, int right) {
        int key = arr[left];
        while (left < right){
            while (left < right && arr[right] >= key){
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= key ){
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = key;
        return left;
    }

}
