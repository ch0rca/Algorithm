package problem;

import java.util.Arrays;

public class PG_1832_보행자천국 {

    static final int MOD = 20170805;

    public int solution(int m, int n, int[][] city_map) {

        // fromLeft[i][j]: 왼쪽 칸에서 오른쪽으로 이동해 (i, j)에 도착한 경로 수
        // fromTop[i][j]: 위쪽 칸에서 아래로 이동해 (i, j)에 도착한 경로 수
        long[][] fromLeft = new long[m][n];
        long[][] fromTop = new long[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                long total;
                long leftContribution;
                long topContribution;

                if (i == 0 && j == 0) {
                    // 시작점은 들어오는 방향 제약이 없으므로 양쪽 모두 1로 취급
                    total = 1;
                    leftContribution = 1;
                    topContribution = 1;
                } else {
                    total = (fromLeft[i][j] + fromTop[i][j]) % MOD;
                    leftContribution = fromLeft[i][j];
                    topContribution = fromTop[i][j];
                }

                int value = city_map[i][j];

                // 통행 금지 칸이면 여기서 더 진행할 수 없음
                if (value == 1) {
                    continue;
                }

                long canGoRight;
                long canGoDown;

                if (value == 0) {
                    // 방향 제약 없음: 양쪽 모두 전체 경로 수만큼 진행 가능
                    canGoRight = total;
                    canGoDown = total;
                } else {
                    // value == 2: 직전 이동 방향만 유지 가능 (좌회전, 우회전 금지)
                    canGoRight = leftContribution;
                    canGoDown = topContribution;
                }

                if (j + 1 < n) {
                    fromLeft[i][j + 1] = (fromLeft[i][j + 1] + canGoRight) % MOD;
                }

                if (i + 1 < m) {
                    fromTop[i + 1][j] = (fromTop[i + 1][j] + canGoDown) % MOD;
                }
            }
        }

        return (int) ((fromLeft[m - 1][n - 1] + fromTop[m - 1][n - 1]) % MOD);
    }
}