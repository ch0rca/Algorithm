package problem;

import java.util.ArrayDeque;
import java.util.Queue;

public class PG_87694_아이템줍기 {

	    static final int SIZE = 102;
	    static int[][] map = new int[SIZE][SIZE];
	    static boolean[][] visited = new boolean[SIZE][SIZE];

	    // 상하좌우 이동
	    static int[] dx = {1, -1, 0, 0};
	    static int[] dy = {0, 0, 1, -1};

	    static class Node {
	        int x, y, dist;

	        Node(int x, int y, int dist) {
	            this.x = x;
	            this.y = y;
	            this.dist = dist;
	        }
	    }

	    public int solution(int[][] rectangle, int characterX, int characterY,
	                        int itemX, int itemY) {

	        // 1. 모든 사각형을 채운다.
	        for (int[] r : rectangle) {
	            int x1 = r[0] * 2;
	            int y1 = r[1] * 2;
	            int x2 = r[2] * 2;
	            int y2 = r[3] * 2;

	            for (int x = x1; x <= x2; x++) {
	                for (int y = y1; y <= y2; y++) {
	                    if (map[x][y] != 2) {
	                        map[x][y] = 1;
	                    }
	                }
	            }
	        }

	        // 2. 사각형 내부를 제거하여 테두리만 남긴다.
	        for (int[] r : rectangle) {
	            int x1 = r[0] * 2;
	            int y1 = r[1] * 2;
	            int x2 = r[2] * 2;
	            int y2 = r[3] * 2;

	            for (int x = x1 + 1; x < x2; x++) {
	                for (int y = y1 + 1; y < y2; y++) {
	                    map[x][y] = 2;
	                }
	            }
	        }

	        // 3. 테두리만 따라 BFS 수행
	        return bfs(characterX * 2, characterY * 2, itemX * 2, itemY * 2);
	    }

	    private int bfs(int sx, int sy, int ex, int ey) {

	        Queue<Node> q = new ArrayDeque<>();
	        q.offer(new Node(sx, sy, 0));
	        visited[sx][sy] = true;

	        while (!q.isEmpty()) {
	            Node cur = q.poll();

	            // 아이템에 도착
	            if (cur.x == ex && cur.y == ey) {
	                return cur.dist / 2; // 좌표를 2배 늘렸으므로 거리도 절반
	            }

	            // 4방향 탐색
	            for (int d = 0; d < 4; d++) {
	                int nx = cur.x + dx[d];
	                int ny = cur.y + dy[d];

	                // 범위를 벗어나면 제외
	                if (nx < 0 || ny < 0 || nx >= SIZE || ny >= SIZE) continue;

	                // 이미 방문했거나 테두리가 아니면 제외
	                if (visited[nx][ny] || map[nx][ny] != 1) continue;

	                visited[nx][ny] = true;
	                q.offer(new Node(nx, ny, cur.dist + 1));
	            }
	        }

	        return -1;
	    }
	}
