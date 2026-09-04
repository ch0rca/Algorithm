package problem;

import java.util.Arrays;

public class PG_60059_자물쇠와열쇠 {

    public boolean solution(int[][] key, int[][] lock) {

        int n = lock.length;
        int m = key.length;

        // 열쇠가 자물쇠 밖으로 나갈 수 있는 범위까지 포함한 확장 보드 크기
        int newLength = n + 2 * (m - 1);
        int[][] board = new int[newLength][newLength];

        // 확장 보드 정중앙에 원래 자물쇠를 복사
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[m - 1 + i][m - 1 + j] = lock[i][j];
            }
        }

        int[][] currentKey = key;

        // 회전 0도, 90도, 180도, 270도를 모두 시도
        for (int rotation = 0; rotation < 4; rotation++) {
            currentKey = rotate(currentKey);

            // 확장 보드 위의 모든 위치에 열쇠를 옮겨가며 시도
            for (int x = 0; x <= newLength - m; x++) {
                for (int y = 0; y <= newLength - m; y++) {

                    // 열쇠를 board에 더한다
                    addKey(board, currentKey, x, y, 1);

                    // 자물쇠가 열리는지 확인
                    if (isUnlocked(board, m, n)) {
                        return true;
                    }

                    // 다음 시도를 위해 더했던 값을 원상복구
                    addKey(board, currentKey, x, y, -1);
                }
            }
        }

        return false;
    }

    // key 배열을 시계방향으로 90도 회전시킨 새 배열을 반환
    private int[][] rotate(int[][] key) {
        int m = key.length;
        int[][] newKey = new int[m][m];

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < m; c++) {
                newKey[c][m - 1 - r] = key[r][c];
            }
        }

        return newKey;
    }

    // board의 (x, y) 위치에 key를 더하거나(sign=1) 빼는(sign=-1) 함수
    private void addKey(int[][] board, int[][] key, int x, int y, int sign) {
        int m = key.length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                board[x + i][y + j] += key[i][j] * sign;
            }
        }
    }

    // 확장 보드의 중앙 n x n 영역이 모두 1인지 확인
    private boolean isUnlocked(int[][] board, int m, int n) {
        for (int i = m - 1; i < m - 1 + n; i++) {
            for (int j = m - 1; j < m - 1 + n; j++) {
                if (board[i][j] != 1) {
                    return false;
                }
            }
        }
        return true;
    }
}