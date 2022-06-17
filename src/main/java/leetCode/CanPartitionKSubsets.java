package leetCode;

import java.util.Arrays;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/6/14 10:50
 */
public class CanPartitionKSubsets {


    public static void main(String[] args) {
        CanPartitionKSubsets cpk = new CanPartitionKSubsets();
        System.out.println(cpk.canPartitionKSubsets(new int[]{10,1,10,9,6,1,9,5,9,10,7,8,5,2,10,8}, 11));
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int a : nums){
            sum+= a;
        }
        if(sum % k != 0){
            return false;
        }
        Arrays.sort(nums);
        return process(nums.length - 1, nums, new int[k], sum / k);
    }

    public boolean process(int index, int[] nums, int[] partions, int max){
        if(index < 0)return true;
        for(int i = 0; i < partions.length; i++){
            partions[i] += nums[index];
            if(partions[i] <= max && process(index - 1, nums, partions, max)){
                return true;
            }
            partions[i] -= nums[index];
        }
        return false;
    }
}
