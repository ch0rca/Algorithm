package problem;

import java.util.Arrays;

public class PG_49191_순위 {
    public int solution(int n, int[][] results) {
        // win[i][j] = true이면 i번 선수가 j번 선수를 이길 수 있음
        boolean[][] win = new boolean[n + 1][n + 1];

        for (int[] result : results) {
            int a = result[0];
            int b = result[1];
            win[a][b] = true; 
        }

        // k를 중간 경유 선수로 삼아 간접 승리 관계 전파
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    // i가 k를 이기고, k가 j를 이기면 -> i는 j도 이김
                    if (win[i][k] && win[k][j]) {
                        win[i][j] = true;
                    }
                }
            }
        }

        int answer = 0;

        for (int i = 1; i <= n; i++) {
            int known = 0; // i 선수와 승패 관계가 파악된 선수 수
            for (int j = 1; j <= n; j++) {
                if (i == j) continue;
                // i가 j를 이기거나, j가 i를 이기면 관계 파악 가능
                if (win[i][j] || win[j][i]) {
                    known++;
                }
            }
            if (known == n - 1) {
                answer++;
            }
        }

        return answer;
    }
}