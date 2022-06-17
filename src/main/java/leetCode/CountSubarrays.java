package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/6/14 15:01
 */
public class CountSubarrays {

    public static void main(String[] args) {
        CountSubarrays cs = new CountSubarrays();
        long l = cs.countSubarrays(new int[]{1, 1, 1}, 5);
        long l1 = cs.countSubarrays1(new int[]{1, 1, 1}, 5);
        System.out.println(l == l1);
    }

    private long countSubarrays1(int[] nums, int k) {
        int len = nums.length;
        int[] preSum = new int[len+1];
        for(int i = 1; i < preSum.length; i++){
            preSum[i] = preSum[i-1]+nums[i-1];
        }
        long ans = 0;
        for(int i = 1; i < preSum.length; i++){
            int l = 0, r = i - 1;
            while(l <= r){
                int mid = (l + r)>>1;
                long t = (long)((i - mid) * (preSum[i] - preSum[mid]));
                if(t < k){
                    r = mid - 1;
                }else{
                    l = mid + 1;
                }
            }
            ans += (i - r - 1);
        }
        return ans;
    }

    // 前缀和（二分查找加速查找）
    public long countSubarrays(int[] nums, long k) {
        int len = nums.length;
        int[] preSum = new int[len+1];
        for(int i = 1; i < preSum.length; i++){
            preSum[i] = preSum[i-1]+nums[i-1];
        }
        long ans = 0;
        for(int i = 0; i < preSum.length; i++){
            for(int j = i+1; j < preSum.length; j++){
                long t = (long)((j - i) * (preSum[j] - preSum[i]));
                if(t < k)ans++;
            }
        }
        return ans;
    }
}
