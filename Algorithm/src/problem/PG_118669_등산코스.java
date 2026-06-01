package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class PG_118669_등산코스 {

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {

        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] path : paths) {
            int a = path[0], b = path[1], w = path[2];
            graph[a].add(new int[]{b, w});
            graph[b].add(new int[]{a, w});
        }

        // 산봉우리 Set으로 빠른 조회
        Set<Integer> summitSet = new HashSet<>();
        for (int s : summits) {
            summitSet.add(s);
        }

        int[] intensity = new int[n + 1];
        Arrays.fill(intensity, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // 모든 출입구를 출발점으로 동시에 삽입 
        for (int gate : gates) {
            intensity[gate] = 0;
            pq.offer(new int[]{0, gate});
        }

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curIntensity = cur[0];
            int curNode = cur[1];

            // 이미 더 좋은 경로로 처리된 노드는 스킵
            if (curIntensity > intensity[curNode]) continue;

            // 산봉우리에서는 더 이상 탐색을 진행하지 않음
            // (산봉우리에 도착한 순간 경로가 완성됨)
            if (summitSet.contains(curNode)) continue;

            for (int[] next : graph[curNode]) {
                int nextNode = next[0];
                int weight = next[1];

                // 이 경로를 통했을 때 intensity = 현재 최대값과 이 간선 중 더 큰 값
                int newIntensity = Math.max(curIntensity, weight);

                if (newIntensity < intensity[nextNode]) {
                    intensity[nextNode] = newIntensity;
                    pq.offer(new int[]{newIntensity, nextNode});
                }
            }
        }

        // 산봉우리 정렬 후 최소 intensity인 산봉우리 선택
        // 동률이면 번호가 작은 것이 자동 선택되도록 오름차순 정렬
        Arrays.sort(summits);

        int answerSummit = -1;
        int answerIntensity = Integer.MAX_VALUE;

        for (int summit : summits) {
            if (intensity[summit] < answerIntensity) {
                answerIntensity = intensity[summit];
                answerSummit = summit;
            }
        }

        return new int[]{answerSummit, answerIntensity};
    }
}
