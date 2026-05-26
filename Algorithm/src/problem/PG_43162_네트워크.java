package problem;

public class PG_43162_네트워크 {

    private boolean[] visited;

    public int solution(int n, int[][] computers) {
        visited = new boolean[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                dfs(i, n, computers);
            }
        }

        return count;
    }

    // node와 연결된 모든 컴퓨터를 방문 처리
    private void dfs(int node, int n, int[][] computers) {
        visited[node] = true;

        for (int j = 0; j < n; j++) {
            // node와 연결되어 있고 아직 방문하지 않은 경우
            if (computers[node][j] == 1 && !visited[j]) {
                dfs(j, n, computers);
            }
        }
    }
}