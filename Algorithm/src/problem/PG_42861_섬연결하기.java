package problem;

import java.util.Arrays;

public class PG_42861_섬연결하기 {

    static int[] parent;

    public int solution(int n, int[][] costs) {

        // 비용(세 번째 값) 기준으로 오름차순 정렬
        Arrays.sort(costs, (a, b) -> a[2] - b[2]);

        // 유니온 파인드 초기화: 각 섬은 처음엔 자기 자신이 부모
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int totalCost = 0;
        int edgeCount = 0;

        // 비용이 작은 다리부터 하나씩 확인
        for (int[] edge : costs) {
            int a = edge[0];
            int b = edge[1];
            int cost = edge[2];

            // 사이클이 생기지 않는 경우에만 연결 (union이 true를 반환)
            if (union(a, b)) {
                totalCost += cost;
                edgeCount++;

                // 섬이 n개면 n-1개의 간선만 있으면 모두 연결됨
                if (edgeCount == n - 1) {
                    break;
                }
            }
        }

        return totalCost;
    }

    // x가 속한 집합의 루트를 찾는 함수
    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // a와 b를 같은 집합으로 합치는 함수
    static boolean union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        // 이미 같은 그룹이면 사이클이 생기므로 합치지 않음
        if (aRoot == bRoot) return false;

        parent[bRoot] = aRoot;
        return true;
    }
}