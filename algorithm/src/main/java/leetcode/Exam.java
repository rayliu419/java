package leetcode;


public class Exam {

    int mySqrt(int x) {
        if (x < 2) return x;
        int low = 1;
        int high = 46340;
        int ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid == x / mid) {
                return mid;
            } else if (mid > x / mid ) {
                high = mid - 1;
            } else {
                // 可能也是一个解
                ans = mid;
                low = mid + 1;
            }
        }
        return ans;
    }
}
