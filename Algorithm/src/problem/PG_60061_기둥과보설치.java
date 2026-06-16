package problem;

import java.util.ArrayList;
import java.util.List;

public class PG_60061_기둥과보설치 {

    static boolean[][][] board;
    static int N;

    public int[][] solution(int n, int[][] build_frame) {
        N = n;
        board = new boolean[n + 1][n + 1][2]; // [x][y][0=기둥, 1=보]

        for (int[] frame : build_frame) {
            int x = frame[0];
            int y = frame[1];
            int type = frame[2]; 
            int action = frame[3];

            if (action == 1) {
                // 설치 후 유효하지 않으면 원복
                board[x][y][type] = true;
                if (!isValid()) {
                    board[x][y][type] = false;
                }
            } else {
                // 삭제 후 유효하지 않으면 원복
                board[x][y][type] = false;
                if (!isValid()) {
                    board[x][y][type] = true;
                }
            }
        }

        // x -> y -> type 순으로 순회하면 자동 정렬
        List<int[]> result = new ArrayList<>();
        for (int x = 0; x <= N; x++) {
            for (int y = 0; y <= N; y++) {
                if (board[x][y][0]) result.add(new int[]{x, y, 0}); // 기둥
                if (board[x][y][1]) result.add(new int[]{x, y, 1}); // 보
            }
        }

        return result.toArray(new int[0][]);
    }

    // 현재 board의 모든 구조물이 유효한지 검사
    static boolean isValid() {
        for (int x = 0; x <= N; x++) {
            for (int y = 0; y <= N; y++) {
                if (board[x][y][0] && !canPlaceColumn(x, y)) return false;
                if (board[x][y][1] && !canPlaceBeam(x, y)) return false;
            }
        }
        return true;
    }

    // 기둥 설치 가능 여부 검사
    static boolean canPlaceColumn(int x, int y) {
        // 바닥 위에 설치
        if (y == 0) return true;
        // 아래에 기둥이 있는 경우
        if (board[x][y - 1][0]) return true;
        // 왼쪽 보의 오른쪽 끝 위에 있는 경우
        if (x > 0 && board[x - 1][y][1]) return true;
        // 오른쪽 보의 왼쪽 끝 위에 있는 경우
        if (board[x][y][1]) return true;

        return false;
    }

    // 보 설치 가능 여부 검사
    static boolean canPlaceBeam(int x, int y) {
        // 왼쪽 끝 아래에 기둥이 있는 경우
        if (y > 0 && board[x][y - 1][0]) return true;
        // 오른쪽 끝 아래에 기둥이 있는 경우
        if (x + 1 <= N && y > 0 && board[x + 1][y - 1][0]) return true;
        // 양쪽 모두 보와 연결된 경우
        if (x > 0 && x + 1 <= N && board[x - 1][y][1] && board[x + 1][y][1]) return true;

        return false;
    }
}