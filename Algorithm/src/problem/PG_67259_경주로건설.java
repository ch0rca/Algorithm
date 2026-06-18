package problem;

import java.util.Arrays;
import java.util.PriorityQueue;

public class PG_67259_경주로건설 {

    static int[] dr = {-1, 1, 0, 0}; 
    static int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board) {
        int N = board.length;

        // (r,c)에 dir 방향으로 도착했을 때의 최소 비용
        int[][][] dist = new int[N][N][4];
        for (int[][] d2 : dist)
            for (int[] d1 : d2)
                Arrays.fill(d1, Integer.MAX_VALUE);

        // {cost, r, c, dir} 순서, 비용 오름차순 정렬
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        pq.offer(new int[]{0, 0, 0, -1});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0];
            int r    = cur[1];
            int c    = cur[2];
            int dir  = cur[3];

            // 방향이 확정된 경우, 이미 더 낮은 비용으로 방문했으면 스킵
            if (dir != -1 && cost > dist[r][c][dir]) continue;

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if (board[nr][nc] == 1) continue; // 벽

                // 기본 직선 비용
                int nextCost = cost + 100;

                // 이전 방향이 있고 다른 방향으로 틀었다면 코너 비용 추가
                if (dir != -1 && dir != d) {
                    nextCost += 500;
                }

                // 더 저렴한 경로가 발견되면 갱신
                if (nextCost < dist[nr][nc][d]) {
                    dist[nr][nc][d] = nextCost;
                    pq.offer(new int[]{nextCost, nr, nc, d});
                }
            }
        }

        // 도착점에 어느 방향으로 왔든 최솟값
        int answer = Integer.MAX_VALUE;
        for (int d = 0; d < 4; d++) {
            answer = Math.min(answer, dist[N - 1][N - 1][d]);
        }

        return answer;
    }
}