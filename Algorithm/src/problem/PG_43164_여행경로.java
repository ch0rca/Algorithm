package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PG_43164_여행경로 {

    private String[][] tickets;
    private boolean[] visited;
    private List<String> route;
    private boolean found;

    public String[] solution(String[][] tickets) {
        this.tickets = tickets;
        int n = tickets.length;
        visited = new boolean[n];
        route = new ArrayList<>();
        found = false;

        Arrays.sort(tickets, (a, b) -> a[1].compareTo(b[1]));

        route.add("ICN");
        dfs("ICN", 1, n);

        return route.toArray(new String[0]);
    }

    // current: 현재 위치한 공항
    // depth: 지금까지 방문한 공항의 수 (ICN 포함)
    // total: 전체 티켓의 수
    private void dfs(String current, int depth, int total) {
        // 티켓을 모두 소진한 경우 정답 경로 확정
        if (depth == total + 1) {
            found = true;
            return;
        }

        for (int i = 0; i < total; i++) {
            // 출발지가 현재 공항이고 아직 사용하지 않은 티켓 선택
            if (tickets[i][0].equals(current) && !visited[i]) {
                visited[i] = true;
                route.add(tickets[i][1]);

                dfs(tickets[i][1], depth + 1, total);

                // 정답 경로를 이미 찾았다면 즉시 종료
                if (found) return;

                // 백트래킹: 현재 선택을 취소하고 이전 상태로 복원
                visited[i] = false;
                route.remove(route.size() - 1);
            }
        }
    }
}