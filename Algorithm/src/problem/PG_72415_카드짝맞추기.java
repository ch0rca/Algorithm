package problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PG_72415_카드짝맞추기 {

    static int[][] board;
    static int minOperation;

    public int solution(int[][] board, int r, int c) {
        this.board = board;
        this.minOperation = Integer.MAX_VALUE;

        // 남아있는 카드들의 좌표를 모두 수집
        List<int[]> cards = new ArrayList<>(); 
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0) {
                    cards.add(new int[]{board[i][j], i, j});
                }
            }
        }

        // 같은 값끼리 두 개씩 묶어서 쌍 리스트 생성
        boolean[] grouped = new boolean[cards.size()];
        List<int[][]> pairs = new ArrayList<>(); 
        for (int i = 0; i < cards.size(); i++) {
            if (grouped[i]) continue;
            for (int j = i + 1; j < cards.size(); j++) {
                if (!grouped[j] && cards.get(i)[0] == cards.get(j)[0]) {
                    pairs.add(new int[][]{
                            {cards.get(i)[1], cards.get(i)[2]},
                            {cards.get(j)[1], cards.get(j)[2]}
                    });
                    grouped[i] = true;
                    grouped[j] = true;
                    break;
                }
            }
        }

        boolean[] visited = new boolean[pairs.size()];
        backtrack(pairs, visited, r, c, 0);

        return minOperation;
    }

    // 남은 쌍들을 모든 순서로 시도하며 최소 비용
    static void backtrack(List<int[][]> pairs, boolean[] visited, int curR, int curC, int acc) {

        // 이미 찾은 최소값보다 크거나 같으면 더 진행할 필요 없음
        if (acc >= minOperation) return;

        boolean allDone = true;
        for (boolean v : visited) {
            if (!v) {
                allDone = false;
                break;
            }
        }
        if (allDone) {
            minOperation = Math.min(minOperation, acc);
            return;
        }

        for (int i = 0; i < pairs.size(); i++) {
            if (visited[i]) continue;

            int[] posA = pairs.get(i)[0];
            int[] posB = pairs.get(i)[1];
            int value = board[posA[0]][posA[1]]; // 복원용으로 값 저장

            visited[i] = true;

            // A, B가 아직 보드에 카드로 남아있는 상태에서 이동 비용 계산
            int costAB = bfs(curR, curC, posA[0], posA[1]) + 1
                       + bfs(posA[0], posA[1], posB[0], posB[1]) + 1;

            int costBA = bfs(curR, curC, posB[0], posB[1]) + 1
                       + bfs(posB[0], posB[1], posA[0], posA[1]) + 1;

            // 비용 계산이 끝난 후, 이번 쌍을 실제로 보드에서 제거
            board[posA[0]][posA[1]] = 0;
            board[posB[0]][posB[1]] = 0;

            // A를 먼저 뒤집은 경우
            backtrack(pairs, visited, posB[0], posB[1], acc + costAB);
            // B를 먼저 뒤집은 경우
            backtrack(pairs, visited, posA[0], posA[1], acc + costBA);

            board[posA[0]][posA[1]] = value;
            board[posB[0]][posB[1]] = value;
            visited[i] = false;
        }
    }

    // 현재 보드 상태를 기준으로 (sr,sc) -> (er,ec) 최소 키 입력 횟수를 구하는 BFS
    static int bfs(int sr, int sc, int er, int ec) {
        if (sr == er && sc == ec) return 0;

        boolean[][] visited = new boolean[4][4];
        int[][] dist = new int[4][4];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];

            if (r == er && c == ec) {
                return dist[r][c];
            }

            // 방향키 이동
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr < 0 || nr >= 4 || nc < 0 || nc >= 4) continue;

                if (!visited[nr][nc]) {
                    visited[nr][nc] = true;
                    dist[nr][nc] = dist[r][c] + 1;
                    queue.add(new int[]{nr, nc});
                }
            }

            // Ctrl + 방향키 이동
            for (int d = 0; d < 4; d++) {
                int jumpR = r, jumpC = c; // 카드가 없을 경우 점프할 마지막 칸
                int nr = r, nc = c;

                while (true) {
                    int tr = nr + dr[d];
                    int tc = nc + dc[d];

                    if (tr < 0 || tr >= 4 || tc < 0 || tc >= 4) break;

                    nr = tr;
                    nc = tc;
                    jumpR = nr;
                    jumpC = nc;

                    // 카드를 만나면 더 진행하지 않고 멈춤
                    if (board[nr][nc] != 0) break;
                }

                if (!visited[jumpR][jumpC]) {
                    visited[jumpR][jumpC] = true;
                    dist[jumpR][jumpC] = dist[r][c] + 1;
                    queue.add(new int[]{jumpR, jumpC});
                }
            }
        }

        return -1;
    }
}