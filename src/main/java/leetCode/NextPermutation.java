package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/6/17 17:59
 */
public class NextPermutation {

    public static void main(String[] args) {
        byte[] bytes = "this is my first demo".getBytes();
        System.out.println(bytes);
        NextPermutation np = new NextPermutation();
        int[] ints = {3,2,1};
        np.nextPermutation(ints);
        System.out.println(ints);
    }

    public void nextPermutation(int[] nums) {
        int r = nums.length-1, len = nums.length;
        while(r >= 1){
            if(nums[r-1] < nums[r]){
                break;
            }
            r--;
        }
        if(r == 0){
            //逆序0至len-1
            sort1(0,len -1,nums);
            return;
        }
        swap(r,r-1,nums);
        sort1(r,len-1,nums);
    }

    // 逆序排列
    public void sort(int s, int e, int[] nums){
        for(int i = s; i <= e;i++){
            for(int j = i+1; j <= e; j++){
                if(nums[i] < nums[j]){
                    swap(i,j,nums);
                }
            }
        }
    }

    // 升序排列
    public void sort1(int s, int e, int[] nums){
        for(int i = s; i <= e;i++){
            for(int j = i+1; j <= e; j++){
                if(nums[i] > nums[j]){
                    swap(i,j,nums);
                }
            }
        }
    }

    public void swap(int i,int j,int[] nums){
        nums[i] = nums[i] ^ nums[j];
        nums[j] = nums[i] ^ nums[j];
        nums[i] = nums[i] ^ nums[j];
    }
}
