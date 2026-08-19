package problem;

public class PG_42897_도둑질 {
    public int solution(int[] money) {

        int n = money.length;

        // 경우 1: 첫 번째 집 포함, 마지막 집 제외 (0번 ~ n-2번)
        int[] case1 = new int[n - 1];
        System.arraycopy(money, 0, case1, 0, n - 1);

        // 경우 2: 첫 번째 집 제외, 마지막 집 포함 가능 (1번 ~ n-1번)
        int[] case2 = new int[n - 1];
        System.arraycopy(money, 1, case2, 0, n - 1);

        // 두 경우 중 더 큰 값이 정답
        return Math.max(robLinear(case1), robLinear(case2));
    }

    // 일렬로 배치된 집들에서 인접하지 않게 훔칠 수 있는 최대 금액을 구하는 함수
    private int robLinear(int[] arr) {
        int m = arr.length;

        if (m == 0) return 0;
        if (m == 1) return arr[0];

        int[] dp = new int[m];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);

        for (int i = 2; i < m; i++) {
            // 현재 집을 안 터는 경우 vs 현재 집을 터는 경우(전전 집까지의 최댓값 + 현재 집)
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + arr[i]);
        }

        return dp[m - 1];
    }
}