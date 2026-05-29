package problem;

import java.util.Arrays;

public class PG_43238_입국심사 {
    public long solution(int n, int[] times) {
        // 최솟값 1, 최댓값은 가장 느린 심사관이 혼자 n명 처리하는 시간
        long lo = 1;
        long hi = (long) Arrays.stream(times).max().getAsInt() * n;
        long answer = hi;

        while (lo <= hi) {
            long mid = (lo + hi) / 2;

            // mid분 동안 처리 가능한 총 인원 계산
            long total = 0;
            for (int t : times) {
                total += mid / t;
                // n 이상이 되는 순간 스탑
                if (total >= n) break;
            }

            if (total >= n) {
                // mid분으로 n명 처리 가능 -> 더 짧은 시간도 가능한지 탐색
                answer = mid;
                hi = mid - 1;
            } else {
                // mid분으로 부족 -> 시간을 늘림
                lo = mid + 1;
            }
        }

        return answer;
    }
}