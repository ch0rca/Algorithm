package problem;

import java.util.ArrayDeque;
import java.util.Queue;

public class PG_60063_블록이동하기 {

    public int solution(int[][] board) {
        int N = board.length;

        // visited[r1][c1][r2][c2] 방식은 100^4로 불가
        // 상태: (r1, c1, r2, c2) 에서 r1<=r2, c1<=c2 정규화 보장
        // 가로: r1==r2, c2==c1+1 -> c1 기준 (r1, c1, 0)
        // 세로: c1==c2, r2==r1+1 -> r1, c1 기준 (r1, c1, 1)
        // visited[r1][c1][dir] 은 같은 (r1,c1)에 가로/세로가 다를 수 있어 충분
        // 하지만 세로일 때 r2=r1+1, 가로일 때 c2=c1+1이 완전히 결정됨
        // 따라서 visited[r1][c1][dir]로 충분히 구분 가능
        boolean[][][] visited = new boolean[N][N][2];


        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, 0, 1, 0});
        visited[0][0][0] = true; 

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r1 = cur[0], c1 = cur[1];
            int r2 = cur[2], c2 = cur[3];
            int time = cur[4];

            // 두 칸 중 하나라도 목적지면 종료
            if ((r1 == N - 1 && c1 == N - 1) || (r2 == N - 1 && c2 == N - 1)) {
                return time;
            }

            // 현재 방향 판별
            boolean horizontal = (r1 == r2);

            // 다음 상태 후보를 모아서 한 번에 처리
            int[][] candidates = new int[8][4];
            int cnt = 0;

            // 직선 이동: 상하좌우 4방향
            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};

            for (int d = 0; d < 4; d++) {
                int nr1 = r1 + dr[d], nc1 = c1 + dc[d];
                int nr2 = r2 + dr[d], nc2 = c2 + dc[d];

                // 두 칸 모두 범위 안이고 벽이 아닌 경우만 이동 가능
                if (inRange(nr1, nc1, N) && inRange(nr2, nc2, N)
                        && board[nr1][nc1] == 0 && board[nr2][nc2] == 0) {
                    candidates[cnt++] = new int[]{nr1, nc1, nr2, nc2};
                }
            }

            if (horizontal) {
                // 가로 방향: (r1, c1) ~ (r1, c2), c2 = c1 + 1

                // 위쪽 회전: 대각선 (r1-1, c1), (r1-1, c2) 둘 다 비어있어야 함
                if (r1 - 1 >= 0
                        && board[r1 - 1][c1] == 0
                        && board[r1 - 1][c2] == 0) {
                    candidates[cnt++] = new int[]{r1 - 1, c1, r1, c1};
                    candidates[cnt++] = new int[]{r1 - 1, c2, r1, c2};
                }

                // 아래쪽 회전: 대각선 (r1+1, c1), (r1+1, c2) 둘 다 비어있어야 함
                if (r1 + 1 < N
                        && board[r1 + 1][c1] == 0
                        && board[r1 + 1][c2] == 0) {
                    candidates[cnt++] = new int[]{r1, c1, r1 + 1, c1};
                    candidates[cnt++] = new int[]{r1, c2, r1 + 1, c2};
                }
                
            } else {

                if (c1 - 1 >= 0
                        && board[r1][c1 - 1] == 0
                        && board[r2][c1 - 1] == 0) {
                    candidates[cnt++] = new int[]{r1, c1 - 1, r1, c1};
                    candidates[cnt++] = new int[]{r2, c1 - 1, r2, c1};
                }

                if (c1 + 1 < N
                        && board[r1][c1 + 1] == 0
                        && board[r2][c1 + 1] == 0) {
                    candidates[cnt++] = new int[]{r1, c1, r1, c1 + 1};
                    candidates[cnt++] = new int[]{r2, c1, r2, c1 + 1};
                }
            }

            // 후보 상태들을 정규화 후 visited 체크하여 큐에 삽입
            for (int i = 0; i < cnt; i++) {
                int nr1 = candidates[i][0], nc1 = candidates[i][1];
                int nr2 = candidates[i][2], nc2 = candidates[i][3];

                // 정규화: 항상 (nr1, nc1) <= (nr2, nc2)
                if (nr1 > nr2 || (nr1 == nr2 && nc1 > nc2)) {
                    int tmp;
                    tmp = nr1; nr1 = nr2; nr2 = tmp;
                    tmp = nc1; nc1 = nc2; nc2 = tmp;
                }

                // 방향 판별: 같은 행이면 가로(0), 다른 행이면 세로(1)
                int dir = (nr1 == nr2) ? 0 : 1;

                if (!visited[nr1][nc1][dir]) {
                    visited[nr1][nc1][dir] = true;
                    queue.offer(new int[]{nr1, nc1, nr2, nc2, time + 1});
                }
            }
        }
        return -1;
    }

    // 좌표 (r, c)가 N x N 범위 안에 있는지 확인
    private boolean inRange(int r, int c, int N) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}