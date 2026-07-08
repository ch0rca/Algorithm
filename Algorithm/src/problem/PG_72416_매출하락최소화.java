package problem;

import java.util.*;

public class PG_72416_매출하락최소화 {

    List<Integer>[] tree;
    int[][] dp;
    int[] sales;

    public static void main(String[] args) {
        PG_72416_매출하락최소화 sol = new PG_72416_매출하락최소화();

        int[] sales = {14, 17, 15, 18, 19, 14, 16, 18};
        int[][] links = {
                {1, 2}, {1, 3}, {2, 4}, {2, 5},
                {3, 6}, {3, 7}, {4, 8}
        };

        System.out.println(sol.solution(sales, links));
    }

    public int solution(int[] sales, int[][] links) {

        int n = sales.length;
        this.sales = sales;

        // 트리 생성
        tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }

        // 부모 -> 자식 연결
        for (int[] link : links) {
            tree[link[0]].add(link[1]);
        }

        // dp[i][0] : i가 불참할 때 최소 매출 감소
        // dp[i][1] : i가 참석할 때 최소 매출 감소
        dp = new int[n + 1][2];

        // 루트부터 DFS
        dfs(1);

        // 루트가 참석/불참 중 더 작은 값 반환
        return Math.min(dp[1][0], dp[1][1]);
    }

    private void dfs(int now) {

        // 현재 직원이 참석하는 경우 자신의 매출 감소 포함
        dp[now][1] = sales[now - 1];

        // 리프 노드는 팀원이 없으므로 불참 비용은 0
        if (tree[now].isEmpty()) {
            dp[now][0] = 0;
            return;
        }

        int sum = 0;

        // 자식 중 이미 참석하는 경우가 있는지 확인
        boolean attendedChild = false;

        // 모든 자식이 불참이라면
        // 가장 적은 비용으로 한 명을 참석시키기 위한 추가 비용
        int extra = Integer.MAX_VALUE;

        for (int next : tree[now]) {

            dfs(next);

            // 자식이 참석하는 것이 더 유리한 경우
            if (dp[next][1] < dp[next][0]) {
                attendedChild = true;
            }

            // 각 자식의 최소 비용 선택
            sum += Math.min(dp[next][0], dp[next][1]);

            // 자식을 강제로 참석시킬 때 필요한 최소 추가 비용
            extra = Math.min(extra, dp[next][1] - dp[next][0]);
        }

        // 현재 직원이 참석하는 경우
        dp[now][1] += sum;

        // 현재 직원이 불참하는 경우
        if (attendedChild) {
            // 이미 참석한 자식이 있으므로 그대로 사용
            dp[now][0] = sum;
        } else {
            // 모든 자식이 불참이면 한 명을 반드시 참석시켜야 함
            dp[now][0] = sum + extra;
        }
    }
}