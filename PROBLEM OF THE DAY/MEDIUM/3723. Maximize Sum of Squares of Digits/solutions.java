
class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > num * 9) return "";

        StringBuilder res = new StringBuilder();

        while (num-- > 0) {
            if (sum >= 9) {
                res.append('9');
                sum -= 9;
            } else if (sum == 0) {
                res.append('0');
            } else {
                res.append(sum);
                sum = 0;
            }
        }

        return res.toString();
    }
}