package problem;

import java.util.Arrays;

public class PG_258705_산모양타일링 {

    public int solution(int n, int[] tops) {

        final int MOD = 10007;

        // dp1[i]: i번째 위치까지 채웠을 때, 노란 마름모로 끝난 경우의 수
        // dp2[i]: i번째 위치까지 채웠을 때, 노란 마름모가 아닌 모양으로 끝난 경우의 수
        long[] dp1 = new long[n];
        long[] dp2 = new long[n];

        dp1[0] = 1;
        dp2[0] = (2 + tops[0]) % MOD;

        for (int i = 1; i < n; i++) {
            // 마름모로 끝나는 경우는 tops 값과 무관하게 이전 상태 각각에서 1가지씩
            dp1[i] = (dp1[i - 1] + dp2[i - 1]) % MOD;

            // 마름모가 아닌 모양으로 끝나는 경우는 tops[i] 값에 따라 선택지가 늘어남
            dp2[i] = (dp1[i - 1] * (1 + tops[i]) + dp2[i - 1] * (2 + tops[i])) % MOD;
        }

        return (int) ((dp1[n - 1] + dp2[n - 1]) % MOD);
    }
}