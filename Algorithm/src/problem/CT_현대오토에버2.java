package problem;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class CT_현대오토에버2 {
	static int[] parent;
    static int[] rankArr;

    public int[] solution(int n, int[][] networks, int[][] server_pair) {

        Map<Long, int[]> pairCostMap = new HashMap<>();

        for (int[] edge : networks) {
            int a = edge[0];
            int b = edge[1];
            int cost = edge[2];
            long key = (long) a * (n + 1) + b;

            int[] minMax = pairCostMap.get(key);
            if (minMax == null) {
                pairCostMap.put(key, new int[]{cost, cost});
            } else {
                minMax[0] = Math.min(minMax[0], cost);
                minMax[1] = Math.max(minMax[1], cost);
            }
        }

        int min = solve(n, networks, server_pair, pairCostMap, true);
        int max = solve(n, networks, server_pair, pairCostMap, false);

        return new int[]{min, max};
    }

    // isMin이 true면 최소, false면 최대
    static int solve(int n, int[][] networks, int[][] server_pair,
                      Map<Long, int[]> pairCostMap, boolean isMin) {

        init(n);

        long total = 0;

        for (int[] pair : server_pair) {
            int a = pair[0];
            int b = pair[1];
            long key = (long) a * (n + 1) + b;

            int[] minMax = pairCostMap.get(key);
            int cost = isMin ? minMax[0] : minMax[1];

            total += cost;
            union(a, b);
        }

        // 나머지 선로를 정렬 후 크루스칼 진행
        int[][] sorted = networks.clone();
        if (isMin) {
            Arrays.sort(sorted, (x, y) -> x[2] - y[2]);
        } else {
            Arrays.sort(sorted, (x, y) -> y[2] - x[2]);
        }

        for (int[] edge : sorted) {
            int a = edge[0];
            int b = edge[1];
            int cost = edge[2];

            // 서로 다른 컴포넌트일 때만 연결
            if (find(a) != find(b)) {
                union(a, b);
                total += cost;
            }
        }

        return (int) total;
    }

    static void init(int n) {
        parent = new int[n + 1];
        rankArr = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
    }

    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int a, int b) {
        int ra = find(a);
        int rb = find(b);
        if (ra == rb) return;

        // 랭크가 낮은 쪽을 높은 쪽 아래로 붙임
        if (rankArr[ra] < rankArr[rb]) {
            int temp = ra;
            ra = rb;
            rb = temp;
        }
        parent[rb] = ra;
        if (rankArr[ra] == rankArr[rb]) {
            rankArr[ra]++;
        }
    }

    // 테스트
    public static void main(String[] args) {
    	CT_현대오토에버2 sol = new CT_현대오토에버2();

        int[][] networks1 = {{1, 2, 30}, {2, 3, 100}, {2, 3, 2000}, {3, 4, 20}};
        int[][] pairs1 = {{1, 2}, {2, 3}};
        System.out.println(Arrays.toString(sol.solution(4, networks1, pairs1))); // [150, 2050]

        int[][] networks2 = {{1, 2, 16}, {1, 3, 4}, {2, 3, 11}, {2, 3, 8},
                {2, 4, 12}, {3, 5, 7}, {4, 6, 10}, {5, 6, 1}};
        int[][] pairs2 = {{2, 3}, {5, 6}, {3, 5}};
        System.out.println(Arrays.toString(sol.solution(6, networks2, pairs2))); // [30, 47]
    }
}
