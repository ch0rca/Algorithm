package problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.FileInputStream;

//https://www.acmicpc.net/problem/7576
public class BOJ_7576_토마토 {

    static class Tomato {
        int x;   
        int y;   
        int date; 

        Tomato(int x, int y, int date) {
            this.x = x;
            this.y = y;
            this.date = date;
        }
    }

    public static void main(String[] args) throws Exception {
    	
    	System.setIn(new FileInputStream("src/problem/test/boj_7576.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken()); 

        int[][] grid = new int[N][M];

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        Queue<Tomato> queue = new ArrayDeque<>();

        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < M; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());

                // 익은 토마토만 날짜 0으로 큐에 삽입
                if (grid[y][x] == 1) {
                    queue.add(new Tomato(x, y, 0));
                }
            }
        }

        int lastDate = 0;
        while (!queue.isEmpty()) {
            Tomato cur = queue.poll();

            for (int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];

                // 범위 내이고 아직 익지 않은 토마토인 경우
                if (nx >= 0 && nx < M && ny >= 0 && ny < N && grid[ny][nx] == 0) {
                    grid[ny][nx] = 1; 
                    queue.add(new Tomato(nx, ny, cur.date + 1));
                    lastDate = Math.max(lastDate, cur.date + 1);
                }
            }
        }

        // 익지 못한 토마토(0) 가 남아있는지 확인
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                if (grid[y][x] == 0) {
                    System.out.println(-1);
                    return;
                }
            }
        }

        System.out.println(lastDate);
    }
}