package problem;

import java.util.Arrays;

public class PG_214289_에어컨 {
    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {

        int n = onboard.length;

        // 탐색이 필요한 실내온도 범위를 실외온도, t1, t2로 한정
        int low = Math.min(temperature, t1);
        int high = Math.max(temperature, t2);
        int size = high - low + 1;

        final int INF = Integer.MAX_VALUE / 2;

        // dp[i][temp - low] : i분 시점에 실내온도가 temp일 때 최소 누적 전력
        int[][] dp = new int[n][size];
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        // 0분의 실내온도는 항상 실외온도와 같음
        dp[0][temperature - low] = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int temp = low; temp <= high; temp++) {
                int cur = dp[i][temp - low];
                if (cur == INF) continue;

                // 1) 에어컨 On, 희망온도 = 현재온도 (온도 유지, 비용 b)
                dp[i + 1][temp - low] = Math.min(dp[i + 1][temp - low], cur + b);

                // 2) 에어컨 On, 1도 상승 (비용 a)
                if (temp + 1 <= high) {
                    dp[i + 1][temp + 1 - low] = Math.min(dp[i + 1][temp + 1 - low], cur + a);
                }

                // 3) 에어컨 On, 1도 하강 (비용 a)
                if (temp - 1 >= low) {
                    dp[i + 1][temp - 1 - low] = Math.min(dp[i + 1][temp - 1 - low], cur + a);
                }

                // 4) 에어컨 Off, 실외온도 방향으로 1도 이동 (비용 0)
                int nextTemp;
                if (temp > temperature) {
                    nextTemp = temp - 1;
                } else if (temp < temperature) {
                    nextTemp = temp + 1;
                } else {
                    nextTemp = temp;
                }
                dp[i + 1][nextTemp - low] = Math.min(dp[i + 1][nextTemp - low], cur);
            }

            // 다음 시각에 승객이 탑승 중이면 t1~t2 범위를 벗어난 상태는 제거
            if (onboard[i + 1] == 1) {
                for (int temp = low; temp <= high; temp++) {
                    if (temp < t1 || temp > t2) {
                        dp[i + 1][temp - low] = INF;
                    }
                }
            }
        }

        // 최솟값 도출
        int answer = INF;
        for (int temp = low; temp <= high; temp++) {
            answer = Math.min(answer, dp[n - 1][temp - low]);
        }

        return answer;
    }
}