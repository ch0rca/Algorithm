package problem;

import java.util.Arrays;

public class PG_62050_지형이동 {

    static int[] parent;

    // 루트 노드를 찾으면서 경로 압축 수행
    static int find(int a) {
        if (parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }

    // 두 집합을 합침, 이미 같은 집합이면 false 반환
    static boolean union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);
        if (aRoot == bRoot) return false;
        parent[bRoot] = aRoot;
        return true;
    }

    public int solution(int[][] land, int height) {
        int N = land.length;
        int nodeCount = N * N;

        // 간선을 저장할 배열: {가중치, 정점1, 정점2}
        // 오른쪽, 아래쪽 방향만 고려하면 중복 없이 모든 간선을 만들 수 있음
        int edgeCapacity = 2 * N * N;
        int[][] edges = new int[edgeCapacity][3];
        int edgeIdx = 0;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int id = r * N + c;

                // 오른쪽 칸과의 간선
                if (c + 1 < N) {
                    int diff = Math.abs(land[r][c] - land[r][c + 1]);
                    int cost = (diff <= height) ? 0 : diff;
                    edges[edgeIdx][0] = cost;
                    edges[edgeIdx][1] = id;
                    edges[edgeIdx][2] = id + 1;
                    edgeIdx++;
                }

                // 아래쪽 칸과의 간선
                if (r + 1 < N) {
                    int diff = Math.abs(land[r][c] - land[r + 1][c]);
                    int cost = (diff <= height) ? 0 : diff;
                    edges[edgeIdx][0] = cost;
                    edges[edgeIdx][1] = id;
                    edges[edgeIdx][2] = id + N;
                    edgeIdx++;
                }
            }
        }

        // 실제로 만들어진 간선 개수만큼만 정렬 대상으로 사용
        int[][] realEdges = Arrays.copyOf(edges, edgeIdx);

        // 가중치 기준 오름차순 정렬
        Arrays.sort(realEdges, (a, b) -> a[0] - b[0]);

        // union-find 초기화
        parent = new int[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            parent[i] = i;
        }

        int totalCost = 0;
        int usedEdgeCount = 0;

        // 크루스칼 알고리즘으로 MST 구성
        for (int[] edge : realEdges) {
            int cost = edge[0];
            int u = edge[1];
            int v = edge[2];

            // 사이클이 생기지 않는 경우에만 간선 선택
            if (union(u, v)) {
                totalCost += cost;
                usedEdgeCount++;

                // 모든 정점이 연결되면 조기 종료
                if (usedEdgeCount == nodeCount - 1) {
                    break;
                }
            }
        }

        return totalCost;
    }
}