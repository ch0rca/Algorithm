package problem;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class PG_250134_수레움직이기 {

    static int n, m;
    static boolean[] wall;
    static int redDest, blueDest;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public int solution(int[][] maze) {
        n = maze.length;
        m = maze[0].length;

        int cellCount = n * m;
        wall = new boolean[cellCount];

        int redStart = -1, blueStart = -1;
        redDest = -1;
        blueDest = -1;

        // 격자를 순회하며 시작 칸, 도착 칸, 벽 위치를 파싱
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int idx = i * m + j;
                switch (maze[i][j]) {
                    case 1: redStart = idx; break;
                    case 2: blueStart = idx; break;
                    case 3: redDest = idx; break;
                    case 4: blueDest = idx; break;
                    case 5: wall[idx] = true; break;
                    default: break;
                }
            }
        }

        int redStartMask = 1 << redStart;
        int blueStartMask = 1 << blueStart;

        Queue<long[]> queue = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();

        queue.offer(new long[]{redStart, blueStart, redStartMask, blueStartMask, 0});
        visited.add(encode(redStart, blueStart, redStartMask, blueStartMask));

        while (!queue.isEmpty()) {
            long[] cur = queue.poll();
            int rPos = (int) cur[0];
            int bPos = (int) cur[1];
            int rMask = (int) cur[2];
            int bMask = (int) cur[3];
            int turn = (int) cur[4];

            // 두 수레 모두 자신의 도착 칸에 도달한 상태면 정답
            if (rPos == redDest && bPos == blueDest) {
                return turn;
            }

            int[] redNexts = nextPositions(rPos, redDest, rMask);
            int[] blueNexts = nextPositions(bPos, blueDest, bMask);

            for (int nr : redNexts) {
                for (int nb : blueNexts) {
                    // 두 수레가 같은 칸으로 이동하는 경우 금지
                    if (nr == nb) continue;
                    // 두 수레가 서로 자리를 맞바꾸는 이동 금지
                    if (nr == bPos && nb == rPos) continue;

                    int newRMask = rMask | (1 << nr);
                    int newBMask = bMask | (1 << nb);

                    long key = encode(nr, nb, newRMask, newBMask);

                    // 처음 방문하는 상태만 큐에 추가
                    if (visited.add(key)) {
                        queue.offer(new long[]{nr, nb, newRMask, newBMask, turn + 1});
                    }
                }
            }
        }

        // 큐가 모두 소진될 때까지 목표 상태를 찾지 못하면 풀 수 없는 퍼즐
        return 0;
    }

    // 현재 위치, 자신의 도착 칸, 방문 마스크를 받아 다음 턴에 이동 가능한 칸 목록을 반환
    static int[] nextPositions(int pos, int dest, int mask) {
        // 이미 자신의 도착 칸에 있으면 움직이지 않고 그대로 고정
        if (pos == dest) {
            return new int[]{pos};
        }

        int row = pos / m;
        int col = pos % m;

        int[] buffer = new int[4];
        int count = 0;

        for (int d = 0; d < 4; d++) {
            int nr = row + dx[d];
            int nc = col + dy[d];

            // 격자 범위를 벗어나면 스킵
            if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;

            int nIdx = nr * m + nc;

            // 벽이거나 이미 방문했던 칸이면 스킵
            if (wall[nIdx]) continue;
            if ((mask & (1 << nIdx)) != 0) continue;

            buffer[count++] = nIdx;
        }

        int[] result = new int[count];
        System.arraycopy(buffer, 0, result, 0, count);
        return result;
    }

    // (빨간 위치, 파란 위치, 빨간 방문마스크, 파란 방문마스크)를 하나의 long 값으로 압축
    static long encode(int rPos, int bPos, int rMask, int bMask) {
        return ((long) rPos << 36) | ((long) bPos << 32) | ((long) rMask << 16) | bMask;
    }
}