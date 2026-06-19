package problem;

import java.util.*;

public class PG_92344_파괴되지않은건물 {
    public int solution(int[][] board, int[][] skill) {
        int N = board.length;
        int M = board[0].length;

        int[][] diff = new int[N + 1][M + 1];

        for (int[] s : skill) {
            int type = s[0];
            int r1   = s[1];
            int c1   = s[2];
            int r2   = s[3];
            int c2   = s[4];
            int degree = s[5];

            // 공격이면 내구도 감소, 회복이면 내구도 증가
            int v = (type == 1) ? -degree : degree;

            diff[r1][c1]         += v;
            diff[r1][c2 + 1]     -= v;
            diff[r2 + 1][c1]     -= v;
            diff[r2 + 1][c2 + 1] += v;
        }

        for (int r = 0; r < N; r++) {
            for (int c = 1; c < M; c++) {
                diff[r][c] += diff[r][c - 1];
            }
        }

        for (int c = 0; c < M; c++) {
            for (int r = 1; r < N; r++) {
                diff[r][c] += diff[r - 1][c];
            }
        }

        int answer = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (board[r][c] + diff[r][c] >= 1) {
                    answer++;
                }
            }
        }

        return answer;
    }
}