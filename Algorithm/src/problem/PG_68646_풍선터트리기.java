package problem;

public class PG_68646_풍선터트리기 {
    public int solution(int[] a) {
        int n = a.length;

        if (n == 1) return 1;

        long[] prefixMin = new long[n];
        long[] suffixMin = new long[n];

        prefixMin[0] = Long.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            prefixMin[i] = Math.min(prefixMin[i - 1], a[i - 1]);
        }

        suffixMin[n - 1] = Long.MAX_VALUE;
        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], a[i + 1]);
        }

        int answer = 0;

        for (int i = 0; i < n; i++) {
            // 왼쪽 전체보다 작거나, 오른쪽 전체보다 작으면 마지막까지 살아남을 수 있다
            if (a[i] < prefixMin[i] || a[i] < suffixMin[i]) {
                answer++;
            }
        }

        return answer;
    }
}