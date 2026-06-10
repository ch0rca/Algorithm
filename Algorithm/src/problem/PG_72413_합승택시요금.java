package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class PG_72413_합승택시요금 {

    static final int INF = Integer.MAX_VALUE;

    public int solution(int n, int s, int a, int b, int[][] fares) {

        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] fare : fares) {
            int c = fare[0], d = fare[1], f = fare[2];
            graph[c].add(new int[]{d, f});
            graph[d].add(new int[]{c, f});
        }

        // s, a, b 각각을 출발점으로 다익스트라 3번 수행
        int[] distS = dijkstra(s, n, graph);
        int[] distA = dijkstra(a, n, graph);
        int[] distB = dijkstra(b, n, graph);

        // 모든 노드를 합승 종료 지점 K로 가정하고 최솟값 탐색
        int answer = INF;
        for (int k = 1; k <= n; k++) {
            if (distS[k] == INF || distA[k] == INF || distB[k] == INF) continue;
            // S에서 K까지 합승 + K에서 A까지 각자 + K에서 B까지 각자
            int cost = distS[k] + distA[k] + distB[k];
            answer = Math.min(answer, cost);
        }

        return answer;
    }

    // start 노드에서 모든 노드까지의 최단거리를 다익스트라로 계산
    private int[] dijkstra(int start, int n, List<int[]>[] graph) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> x[0] - y[0]);
        pq.offer(new int[]{0, start});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curCost = cur[0];
            int curNode = cur[1];

            // 이미 더 짧은 경로로 확정된 노드는 스킵
            if (curCost > dist[curNode]) continue;

            for (int[] next : graph[curNode]) {
                int nextNode = next[0];
                int weight = next[1];
                int newCost = dist[curNode] + weight;

                // 더 짧은 경로 발견 시 갱신
                if (newCost < dist[nextNode]) {
                    dist[nextNode] = newCost;
                    pq.offer(new int[]{newCost, nextNode});
                }
            }
        }

        return dist;
    }
}
