package problem;

import java.util.PriorityQueue;

public class PG_12984_지형편집 {

    public long solution(int[][] land, int P, int Q) {

        int n = land.length;

        // 땅의 최솟값과 최댓값을 구해서 탐색 범위를 정함
        int lo = Integer.MAX_VALUE;
        int hi = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                lo = Math.min(lo, land[i][j]);
                hi = Math.max(hi, land[i][j]);
            }
        }

        // 비용 함수가 볼록 함수이므로 삼분 탐색으로 최적의 목표 높이를 찾음
        while (hi - lo > 2) {
            int mid1 = lo + (hi - lo) / 3;
            int mid2 = hi - (hi - lo) / 3;

            long cost1 = calcCost(land, mid1, P, Q);
            long cost2 = calcCost(land, mid2, P, Q);

            if (cost1 < cost2) {
                hi = mid2 - 1;
            } else {
                lo = mid1 + 1;
            }
        }

        // 좁아진 구간 안에서 하나씩 확인하여 최솟값 확정
        long answer = Long.MAX_VALUE;
        for (int h = lo; h <= hi; h++) {
            answer = Math.min(answer, calcCost(land, h, P, Q));
        }

        return answer;
    }

    // 목표 높이 h로 모든 칸을 맞추는 데 드는 총 비용 계산
    private long calcCost(int[][] land, int h, int P, int Q) {
        long total = 0;

        for (int[] row : land) {
            for (int v : row) {
                if (v > h) {
                    // 목표보다 높으면 블록을 제거해야 함
                    total += (long) (v - h) * Q;
                } else {
                    // 목표보다 낮으면 블록을 추가해야 함
                    total += (long) (h - v) * P;
                }
            }
        }

        return total;
    }
}