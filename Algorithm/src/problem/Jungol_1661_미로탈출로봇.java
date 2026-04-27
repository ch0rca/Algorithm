package problem;

import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

//https://jungol.co.kr/problem/1661
public class Jungol_1661_미로탈출로봇 {
    
    
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
    
    public static void main(String[] args) throws IOException {

    	System.setIn(new FileInputStream("src/problem/test/jungol_1661.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        int X = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());
        
        
        st = new StringTokenizer(br.readLine());
        int sx = Integer.parseInt(st.nextToken()) - 1; 
        int sy = Integer.parseInt(st.nextToken()) - 1;  
        int ex = Integer.parseInt(st.nextToken()) - 1;  
        int ey = Integer.parseInt(st.nextToken()) - 1;  
        

        int[][] maze = new int[Y][X];
        for (int i = 0; i < Y; i++) {
            String line = br.readLine();
            for (int j = 0; j < X; j++) {
                maze[i][j] = line.charAt(j) - '0';
            }
        }
        

        boolean[][] visited = new boolean[Y][X];
        
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{sx, sy, 0});
        visited[sy][sx] = true;
        
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cx = cur[0];
            int cy = cur[1];
            int dist = cur[2];
            
            // 도착점에 도달하면 거리 출력 후 종료
            if (cx == ex && cy == ey) {
                System.out.println(dist);
                return;
            }
            
            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];
                
                // 미로 범위를 벗어나면 스킵
                if (nx < 0 || nx >= X || ny < 0 || ny >= Y) continue;
                
                // 이미 방문했거나 벽이면 스킵
                if (visited[ny][nx] || maze[ny][nx] == 1) continue;
                
                // 방문 처리 후 큐에 추가
                visited[ny][nx] = true;
                queue.add(new int[]{nx, ny, dist + 1});
            }
        }
    }
}