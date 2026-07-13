package problem;

import java.util.*;

public class PG_133500_등대 {

    static List<Integer>[] graph;
    static int[][] dp;
    static boolean[] visited;

    public int solution(int n, int[][] lighthouse) {

        graph = new ArrayList[n + 1];
        dp = new int[n + 1][2];
        visited = new boolean[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : lighthouse) {
            int a = edge[0];
            int b = edge[1];

            graph[a].add(b);
            graph[b].add(a);
        }

        dfs(1);

        return Math.min(dp[1][0], dp[1][1]);
    }

    static void dfs(int now) {

        visited[now] = true;

        // 현재 등대를 켜는 경우
        dp[now][1] = 1;

        for (int next : graph[now]) {

            if (visited[next]) continue;

            dfs(next);

            // 현재 OFF -> 자식은 반드시 ON
            dp[now][0] += dp[next][1];

            // 현재 ON -> 자식은 ON/OFF 중 작은 값 선택
            dp[now][1] += Math.min(dp[next][0], dp[next][1]);
        }
    }
}