package problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PG_84021_퍼즐조각채우기 {

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public int solution(int[][] game_board, int[][] table) {
        int n = game_board.length;
        boolean[][] visitedBoard = new boolean[n][n];
        boolean[][] visitedTable = new boolean[n][n];

        List<List<int[]>> blanks = new ArrayList<>(); // game_board의 빈 공간 덩어리들
        List<List<int[]>> pieces = new ArrayList<>();  // table의 조각 덩어리들

        // game_board에서 0으로 연결된 빈 공간 찾기
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (game_board[i][j] == 0 && !visitedBoard[i][j]) {
                    blanks.add(normalize(getShape(game_board, visitedBoard, i, j, 0, n)));
                }
            }
        }

        // table에서 1로 연결된 조각 찾기
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (table[i][j] == 1 && !visitedTable[i][j]) {
                    pieces.add(normalize(getShape(table, visitedTable, i, j, 1, n)));
                }
            }
        }

        boolean[] used = new boolean[pieces.size()];
        int answer = 0;

        // 각 빈 공간에 맞는 조각을 찾아서 채우기
        for (List<int[]> blank : blanks) {
            for (int p = 0; p < pieces.size(); p++) {
                if (used[p]) continue;

                List<int[]> piece = pieces.get(p);
                if (piece.size() != blank.size()) continue;

                List<int[]> rotated = piece;
                boolean matched = false;

                // 4방향 회전을 모두 시도
                for (int rot = 0; rot < 4; rot++) {
                    if (rot > 0) rotated = rotate(rotated);
                    if (sameShape(rotated, blank)) {
                        matched = true;
                        break;
                    }
                }

                if (matched) {
                    used[p] = true;
                    answer += blank.size();
                    break;
                }
            }
        }

        return answer;
    }

    // BFS로 target 값(0 또는 1)이 연결된 좌표들을 모두 수집
    private List<int[]> getShape(int[][] grid, boolean[][] visited, int sr, int sc, int target, int n) {
        List<int[]> shape = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            shape.add(cur);

            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && !visited[nr][nc] && grid[nr][nc] == target) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }
        }

        return shape;
    }

    // 좌표들의 최소 행/열을 0으로 맞추고 정렬하여 비교 가능한 형태로 정규화
    private List<int[]> normalize(List<int[]> shape) {
        int minR = Integer.MAX_VALUE;
        int minC = Integer.MAX_VALUE;

        for (int[] p : shape) {
            minR = Math.min(minR, p[0]);
            minC = Math.min(minC, p[1]);
        }

        List<int[]> result = new ArrayList<>();
        for (int[] p : shape) {
            result.add(new int[]{p[0] - minR, p[1] - minC});
        }

        
        result.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        return result;
    }

    // 시계 방향 90도 회전 후 다시 정규화
    private List<int[]> rotate(List<int[]> shape) {
        List<int[]> rotated = new ArrayList<>();
        for (int[] p : shape) {
            rotated.add(new int[]{p[1], -p[0]});
        }
        return normalize(rotated);
    }

    // 정규화된 두 모양이 좌표별로 완전히 같은지 확인
    private boolean sameShape(List<int[]> a, List<int[]> b) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i)[0] != b.get(i)[0] || a.get(i)[1] != b.get(i)[1]) {
                return false;
            }
        }
        return true;
    }
}