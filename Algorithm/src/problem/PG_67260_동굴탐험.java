package problem;

import java.util.*;

public class PG_67260_동굴탐험 {

    List<Integer>[] graph;

    public boolean solution(int n, int[][] path, int[][] order) {

        // 인접 리스트 생성
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 양방향 그래프 생성
        for (int[] p : path) {
            graph[p[0]].add(p[1]);
            graph[p[1]].add(p[0]);
        }

        // before[x] : x를 방문하기 전에 먼저 방문해야 하는 방
        int[] before = new int[n];
        Arrays.fill(before, -1);

        // after[x] : x를 방문하면 다음에 방문 가능한 방
        int[] after = new int[n];
        Arrays.fill(after, -1);

        for (int[] o : order) {
            before[o[1]] = o[0];
            after[o[0]] = o[1];
        }

        // 시작 방(0번)에 선행 조건이 있으면 불가능
        if (before[0] != -1) {
            return false;
        }

        boolean[] visited = new boolean[n];

        // 아직 방문하지 못해 대기 중인 방
        int[] wait = new int[n];
        Arrays.fill(wait, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;

        int count = 1;

        while (!queue.isEmpty()) {

            int now = queue.poll();

            // 현재 방을 방문하면서 잠금이 해제되는 방이 있는 경우
            if (after[now] != -1 && wait[after[now]] != -1) {

                int next = after[now];

                visited[next] = true;
                queue.offer(next);
                count++;
            }

            // 연결된 방 탐색
            for (int next : graph[now]) {

                if (visited[next]) continue;

                // 선행 방을 아직 방문하지 않았다면 대기
                if (before[next] != -1 && !visited[before[next]]) {
                    wait[next] = next;
                    continue;
                }

                visited[next] = true;
                queue.offer(next);
                count++;
            }
        }

        return count == n;
    }
}