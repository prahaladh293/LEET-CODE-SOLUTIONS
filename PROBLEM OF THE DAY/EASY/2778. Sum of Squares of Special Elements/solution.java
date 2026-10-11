
class Solution {
    public int sumOfSquares(int[] nums) {
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            if (isdivide(i + 1, nums)) {
                int product = nums[i] * nums[i];
                sum = sum + product;
            }
        }

        return sum;
    }

    public boolean isdivide(int k, int[] nums) {
        return nums.length % k == 0;
    }
}
