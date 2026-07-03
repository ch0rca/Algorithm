package problem;

public class PG_86053_금과은운반 {
    public long solution(int a, int b, int[] g, int[] s, int[] w, int[] t) {

        long left = 0;
        long right = 400000000000000L;
        long answer = right;

        // 시간을 기준으로 이분 탐색
        while (left <= right) {

            long mid = (left + right) / 2;

            long gold = 0;
            long silver = 0;
            long total = 0;

            for (int i = 0; i < g.length; i++) {

                // mid 시간 동안 가능한 운반 횟수
                long trip = mid / (2L * t[i]);

                // 마지막 편도 운반 가능 여부
                if (mid % (2L * t[i]) >= t[i]) {
                    trip++;
                }

                long capacity = trip * w[i];

                // 운반 가능한 금, 은, 전체 광물 계산
                gold += Math.min(capacity, (long) g[i]);
                silver += Math.min(capacity, (long) s[i]);
                total += Math.min(capacity, (long) g[i] + s[i]);
            }

            // 목표량을 모두 운반 가능하면 시간을 줄이기
            if (gold >= a && silver >= b && total >= (long) a + b) {
                answer = mid;
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }

        return answer;
    }
}

