package problem;

import java.util.Arrays;

public class PG_64062_징검다리 {

    public int solution(int[] stones, int k) {
        int left = 1;
        int right = Arrays.stream(stones).max().getAsInt();

        while (left <= right) {
            int mid = (left + right) / 2;

            if (canCross(stones, k, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return right;
    }

    private boolean canCross(int[] stones, int k, int people) {
        int zeroCnt = 0;

        for (int stone : stones) {
            if (stone < people) {
                zeroCnt++;
                if (zeroCnt >= k) {
                    return false;
                }
            } else {
                zeroCnt = 0;
            }
        }

        return true;
    }
}