package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 1089. 复写零
 * 给你一个长度固定的整数数组 arr，请你将该数组中出现的每个零都复写一遍，并将其余的元素向右平移。
 *
 * 注意：请不要在超过该数组长度的位置写入元素。
 *
 * 要求：请对输入的数组 就地 进行上述修改，不要从函数返回任何东西。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：[1,0,2,3,0,4,5,0]
 * 输出：null
 * 解释：调用函数后，输入的数组将被修改为：[1,0,0,2,3,0,0,4]
 * @date 2022/6/17 9:59
 */
public class DuplicateZeros {

    /**
     * 直接补0，从右往左遍历，避免移动覆盖未遍历到的元素
     * @param arr
     */
    public void duplicateZeros(int[] arr){
        int cnt = 0, len = arr.length;
        for(int i : arr){
            if(i == 0)cnt++;
        }
        for(int i = len - 1; i >= 0; i--){
            if(arr[i] == 0)cnt--;
            if(i + cnt < len){
                //后移
                arr[i+cnt] = arr[i];
                //补0
                if(arr[i] == 0 && i+cnt+1 < len){
                    arr[i+cnt+1] = 0;
                }
            }
        }
    }
}
