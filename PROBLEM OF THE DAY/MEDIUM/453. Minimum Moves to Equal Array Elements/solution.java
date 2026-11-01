class Solution {
    public int minMoves(int[] nums) {
      int min=Integer.MAX_VALUE;
      for(int n:nums) {
        min=Math.min(min,n);
      }  
      int count=0;
      for(int n:nums) {
        count+=n-min;
      }
      return count;
    }
}