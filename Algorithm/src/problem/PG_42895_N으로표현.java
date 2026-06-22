package problem;

import java.util.HashSet;
import java.util.Set;

public class PG_42895_N으로표현 {
    public int solution(int N, int number) {

        // dp[i] : N을 정확히 i번 사용해서 만들 수 있는 모든 수의 집합
        Set<Long>[] dp = new HashSet[9];
        for (int i = 1; i <= 8; i++) {
            dp[i] = new HashSet<>();
        }

        // N을 1번 사용했을 때 만들 수 있는 수는 N 자기 자신뿐
        dp[1].add((long) N);
        if (dp[1].contains((long) number)) {
            return 1;
        }

        for (int i = 2; i <= 8; i++) {

            // N을 i번 이어붙인 수 (예: i=3이면 NNN)
            long concat = 0;
            for (int k = 0; k < i; k++) {
                concat = concat * 10 + N;
            }
            dp[i].add(concat);

            // N을 j번 사용한 결과와 (i-j)번 사용한 결과를 사칙연산으로 조합
            for (int j = 1; j < i; j++) {
                for (long a : dp[j]) {
                    for (long b : dp[i - j]) {
                        dp[i].add(a + b);
                        dp[i].add(a - b);
                        dp[i].add(a * b);

                        // 0으로 나누는 경우는 제외
                        if (b != 0) {
                            dp[i].add(a / b);
                        }
                    }
                }
            }

            // 목표 숫자를 찾으면 현재 사용 횟수가 최솟값이므로 즉시 반환
            if (dp[i].contains((long) number)) {
                return i;
            }
        }

        // 8번 이내로 만들 수 없으면 -1 반환
        return -1;
    }
}