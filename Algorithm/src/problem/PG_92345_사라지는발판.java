package problem;

public class PG_92345_사라지는발판 {

    // 상하좌우 이동
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    int row, col;

    public int solution(int[][] board, int[] aloc, int[] bloc) {

        row = board.length;
        col = board[0].length;

        // 현재 플레이어(A)부터 시작
        return dfs(aloc[0], aloc[1], bloc[0], bloc[1], board);
    }

    // 현재 플레이어 : (r1,c1)
    // 상대 플레이어 : (r2,c2)
    private int dfs(int r1, int c1, int r2, int c2, int[][] board) {

        // 현재 위치의 발판이 이미 사라졌다면 패배
        if (board[r1][c1] == 0)
            return 0;

        int result = 0;

        // 4방향 탐색
        for (int d = 0; d < 4; d++) {

            int nr = r1 + dr[d];
            int nc = c1 + dc[d];

            // 범위를 벗어나면 제외
            if (nr < 0 || nr >= row || nc < 0 || nc >= col)
                continue;

            // 발판이 없으면 이동 불가
            if (board[nr][nc] == 0)
                continue;

            // 현재 발판 제거
            board[r1][c1] = 0;

            // 상대 차례 진행
            int move = dfs(r2, c2, nr, nc, board) + 1;

            // 원상 복구 (백트래킹)
            board[r1][c1] = 1;

            // 현재까지 진 경우이고 이번에 이길 수 있다면 갱신
            if (result % 2 == 0 && move % 2 == 1) {
                result = move;
            }
            // 계속 지는 경우라면 최대한 오래 버틴다.
            else if (result % 2 == 0 && move % 2 == 0) {
                result = Math.max(result, move);
            }
            // 이미 이길 수 있다면 최대한 빨리 이긴다.
            else if (result % 2 == 1 && move % 2 == 1) {
                result = Math.min(result, move);
            }
        }

        return result;
    }
}