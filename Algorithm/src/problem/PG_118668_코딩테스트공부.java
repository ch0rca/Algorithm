package problem;

import java.util.PriorityQueue;
import java.util.Arrays;

public class PG_118668_코딩테스트공부 {

    public int solution(int alp, int cop, int[][] problems) {

        int maxAlp = 0, maxCop = 0;
        for (int[] p : problems) {
            maxAlp = Math.max(maxAlp, p[0]);
            maxCop = Math.max(maxCop, p[1]);
        }

        // 이미 목표 이상이면 클램핑
        alp = Math.min(alp, maxAlp);
        cop = Math.min(cop, maxCop);

        int[][] dist = new int[maxAlp + 1][maxCop + 1];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[alp][cop] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, alp, cop});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0], a = cur[1], c = cur[2];

            // 이미 더 짧은 경로로 방문된 상태면 스킵
            if (cost > dist[a][c]) continue;

            // 목표 상태 도달 시 즉시 반환
            if (a == maxAlp && c == maxCop) return cost;

            // 알고력 1 직접 증가
            if (a < maxAlp) {
                int newCost = dist[a][c] + 1;
                if (newCost < dist[a + 1][c]) {
                    dist[a + 1][c] = newCost;
                    pq.offer(new int[]{newCost, a + 1, c});
                }
            }

            // 코딩력 1 직접 증가
            if (c < maxCop) {
                int newCost = dist[a][c] + 1;
                if (newCost < dist[a][c + 1]) {
                    dist[a][c + 1] = newCost;
                    pq.offer(new int[]{newCost, a, c + 1});
                }
            }

            // 현재 상태에서 풀 수 있는 문제 풀기
            for (int[] p : problems) {
                int alpReq = p[0], copReq = p[1];
                int alpRwd = p[2], copRwd = p[3], pcost = p[4];

                // 현재 알고력/코딩력이 요구 조건을 충족하는 경우만
                if (a >= alpReq && c >= copReq) {
                    // 목표 범위를 초과하지 않도록 클램핑
                    int na = Math.min(a + alpRwd, maxAlp);
                    int nc = Math.min(c + copRwd, maxCop);

                    int newCost = dist[a][c] + pcost;
                    if (newCost < dist[na][nc]) {
                        dist[na][nc] = newCost;
                        pq.offer(new int[]{newCost, na, nc});
                    }
                }
            }
        }

        return dist[maxAlp][maxCop];
    }
}