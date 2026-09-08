package problem;

import java.util.ArrayList;
import java.util.List;

public class PG_42894_블록게임 {

    static final int EMPTY = 0;
    static final int BLACK = -1;

    public int solution(int[][] board) {
        int N = board.length;
        int removedCount = 0;

        // 색깔별로 지금까지 스캔한 좌표 목록 (색깔 번호는 1~200 범위)
        List<int[]>[] coordsByBlock = new ArrayList[201];
        for (int i = 0; i <= 200; i++) {
            coordsByBlock[i] = new ArrayList<>();
        }

        // 보드를 위에서 아래로, 왼쪽에서 오른쪽으로 딱 한 번만 스캔
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int block = board[r][c];

                if (block == EMPTY) {
                    // 빈 칸이 실제로 검은 블록으로 채워질 수 있는지 확인
                    fillBlack(board, r, c);
                } else {
                    List<int[]> coords = coordsByBlock[block];
                    coords.add(new int[]{r, c});

                    // 좌표가 4개 모였고 꽉 찬 직사각형 조건을 만족하면 제거
                    if (canRemove(block, coords, board)) {
                        removedCount++;

                        // 제거된 칸들을 검은 블록으로 치환해서
                        // 그 위에 놓인 다른 블록도 연쇄적으로 제거될 수 있게 처리
                        for (int[] cell : coords) {
                            fillBlack(board, cell[0], cell[1]);
                        }
                    }
                }
            }
        }

        return removedCount;
    }

    // (r, c) 칸에 검은 블록이 실제로 도달해서 채워질 수 있는지 판단
    // 맨 윗줄이거나, 바로 위 칸이 이미 검은 블록이면 중력에 의해 채워질 수 있음
    static void fillBlack(int[][] board, int r, int c) {
        if (r == 0 || board[r - 1][c] == BLACK) {
            board[r][c] = BLACK;
        } else {
            board[r][c] = EMPTY;
        }
    }

    // 지금까지 모인 블록 좌표만으로 꽉 찬 직사각형을 만들 수 있는지 확인
    static boolean canRemove(int block, List<int[]> coords, int[][] board) {
        // 블록은 항상 4칸으로 이루어지므로 아직 다 모이지 않았으면 판단 보류
        if (coords.size() != 4) return false;

        int minR = Integer.MAX_VALUE, maxR = Integer.MIN_VALUE;
        int minC = Integer.MAX_VALUE, maxC = Integer.MIN_VALUE;

        for (int[] cell : coords) {
            minR = Math.min(minR, cell[0]);
            maxR = Math.max(maxR, cell[0]);
            minC = Math.min(minC, cell[1]);
            maxC = Math.max(maxC, cell[1]);
        }

        // 바운딩 박스 안의 모든 칸이 검은 블록이거나 자기 자신의 색인지 확인
        for (int r = minR; r <= maxR; r++) {
            for (int c = minC; c <= maxC; c++) {
                int value = board[r][c];
                if (value != BLACK && value != block) {
                    return false;
                }
            }
        }

        return true;
    }
}