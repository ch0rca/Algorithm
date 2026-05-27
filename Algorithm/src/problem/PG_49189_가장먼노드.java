package problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PG_49189_가장먼노드 {
    public int solution(int n, int[][] edge) {
        // 노드 번호가 1부터 시작하므로 n+1 크기
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edge) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int[] dist = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dist[i] = -1;
        }
        dist[1] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : graph[cur]) {
                if (dist[next] == -1) {
                    dist[next] = dist[cur] + 1;
                    queue.add(next);
                }
            }
        }

        // 최대 거리 계산
        int maxDist = 0;
        for (int i = 1; i <= n; i++) {
            maxDist = Math.max(maxDist, dist[i]);
        }

        // 최대 거리와 같은 노드 개수 카운트
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == maxDist) {
                count++;
            }
        }

        return count;
    }
}