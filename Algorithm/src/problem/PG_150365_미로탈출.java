package problem;

import java.util.Arrays;

public class PG_150365_미로탈출 {

    public String solution(int n, int m, int x, int y, int r, int c, int k) {

        // 출발지에서 목표까지 맨해튼 거리
        int dist = Math.abs(x - r) + Math.abs(y - c);

        // k가 dist보다 작거나, 남은 이동이 홀짝 불일치이면 불가능
        if (dist > k || (k - dist) % 2 != 0) {
            return "impossible";
        }

        // 사전순: d < l < r < u
        int[] dr = {1, 0, 0, -1};
        int[] dc = {0, -1, 1, 0};
        char[] dirName = {'d', 'l', 'r', 'u'};

        StringBuilder sb = new StringBuilder();
        int curX = x, curY = y;

        for (int step = 0; step < k; step++) {
            // 이번 이동 후 남은 이동 횟수
            int remaining = k - step - 1;

            for (int d = 0; d < 4; d++) {
                int nx = curX + dr[d];
                int ny = curY + dc[d];

                // 격자 범위 체크
                if (nx < 1 || nx > n || ny < 1 || ny > m) continue;

                // 이동 후 목표까지 남은 거리
                int nextDist = Math.abs(nx - r) + Math.abs(ny - c);

                // 남은 이동 횟수로 목표에 정확히 도달 가능한지 확인
                if (nextDist <= remaining && (remaining - nextDist) % 2 == 0) {
                    sb.append(dirName[d]);
                    curX = nx;
                    curY = ny;
                    break; 
                }
            }
        }

        return sb.toString();
    }
}
