package problem;

import java.util.ArrayList;
import java.util.List;

public class PG_92343_양과늑대 {

    static int[] info;
    static List<Integer>[] graph;
    static int answer = 0;

    public int solution(int[] info, int[][] edges) {
        this.info = info;
        int n = info.length;

        // 인접 리스트 구성
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
        }

        // 루트(0번)를 방문한 상태로 시작, 후보 목록은 루트의 자식들
        List<Integer> candidates = new ArrayList<>(graph[0]);
        // 루트는 양(0)이므로 sheep=1, wolf=0
        dfs(candidates, 1, 0);

        return answer;
    }

    static void dfs(List<Integer> candidates, int sheep, int wolf) {
        answer = Math.max(answer, sheep);

        for (int next : candidates) {
            int nextSheep = sheep + (info[next] == 0 ? 1 : 0);
            int nextWolf  = wolf  + (info[next] == 1 ? 1 : 0);

            // 늑대 수가 양 수 이상이 되면 이 선택은 불가
            if (nextWolf >= nextSheep) continue;

            // 다음 후보 목록 구성: 현재 next를 제거하고 next의 자식들을 추가
            List<Integer> nextCandidates = new ArrayList<>(candidates);
            nextCandidates.remove(Integer.valueOf(next));
            nextCandidates.addAll(graph[next]);

            dfs(nextCandidates, nextSheep, nextWolf);
        }
    }
}