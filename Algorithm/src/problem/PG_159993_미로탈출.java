package problem;

import java.util.ArrayDeque;
import java.util.Queue;

public class PG_159993_미로탈출 {
	    int n, m;
	    char[][] map;

	    // 상하좌우
	    int[] dx = {-1, 1, 0, 0};
	    int[] dy = {0, 0, -1, 1};

	    static class Node {
	        int x, y, dist;

	        Node(int x, int y, int dist) {
	            this.x = x;
	            this.y = y;
	            this.dist = dist;
	        }
	    }

	    public int solution(String[] maps) {

	        n = maps.length;
	        m = maps[0].length();

	        map = new char[n][m];

	        int sx = 0, sy = 0;
	        int lx = 0, ly = 0;
	        int ex = 0, ey = 0;

	        // 시작점(S), 레버(L), 출구(E) 위치 찾기
	        for (int i = 0; i < n; i++) {
	            map[i] = maps[i].toCharArray();

	            for (int j = 0; j < m; j++) {
	                if (map[i][j] == 'S') {
	                    sx = i;
	                    sy = j;
	                } else if (map[i][j] == 'L') {
	                    lx = i;
	                    ly = j;
	                } else if (map[i][j] == 'E') {
	                    ex = i;
	                    ey = j;
	                }
	            }
	        }

	        // 시작 -> 레버
	        int toLever = bfs(sx, sy, 'L');
	        if (toLever == -1) return -1;

	        // 레버 -> 출구
	        int toExit = bfs(lx, ly, 'E');
	        if (toExit == -1) return -1;

	        return toLever + toExit;
	    }

	    // 목표 문자(target)까지의 최단거리 BFS
	    private int bfs(int sx, int sy, char target) {

	        boolean[][] visited = new boolean[n][m];
	        Queue<Node> q = new ArrayDeque<>();

	        q.offer(new Node(sx, sy, 0));
	        visited[sx][sy] = true;

	        while (!q.isEmpty()) {

	            Node cur = q.poll();

	            // 목표 도착
	            if (map[cur.x][cur.y] == target) {
	                return cur.dist;
	            }

	            for (int d = 0; d < 4; d++) {

	                int nx = cur.x + dx[d];
	                int ny = cur.y + dy[d];

	                // 범위를 벗어나면 제외
	                if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;

	                // 이미 방문했거나 벽이면 제외
	                if (visited[nx][ny] || map[nx][ny] == 'X') continue;

	                visited[nx][ny] = true;
	                q.offer(new Node(nx, ny, cur.dist + 1));
	            }
	        }

	        // 목표까지 갈 수 없음
	        return -1;
	    }
	}