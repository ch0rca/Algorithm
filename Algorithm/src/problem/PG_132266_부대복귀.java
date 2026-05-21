package problem;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PG_132266_부대복귀 {
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] road : roads) {
            int a = road[0];
            int b = road[1];
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        // 거리 배열
        int[] dist = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dist[i] = -1;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(destination);
        dist[destination] = 0;

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (int next : graph.get(cur)) {
                // 아직 방문하지 않은 노드만 처리
                if (dist[next] == -1) {
                    dist[next] = dist[cur] + 1;
                    queue.add(next);
                }
            }
        }

        // sources 순서대로 최단거리 추출
        int[] answer = new int[sources.length];
        for (int i = 0; i < sources.length; i++) {
            answer[i] = dist[sources[i]];
        }

        return answer;
    }
}