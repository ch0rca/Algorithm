package problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.io.FileInputStream;

public class BOJ_2252_줄세우기 {

    public static void main(String[] args) throws Exception {
    	
    	System.setIn(new FileInputStream("src/problem/test/boj_2252.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 학생 수
        int M = Integer.parseInt(st.nextToken()); // 비교 횟수

        // 인접 리스트: graph[a]에 b가 있으면 a는 b보다 앞에 서야 함
        ArrayList<Integer>[] graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        // 진입 차수 배열: inDegree[i] = i 앞에 반드시 서야 하는 학생 수
        int[] inDegree = new int[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a -> b 방향 간선: a가 b보다 앞
            graph[a].add(b);
            inDegree[b]++;
        }

        // 위상정렬 BFS 시작: 진입 차수가 0인 학생부터 큐에 삽입
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!queue.isEmpty()) {
            int node = queue.poll(); // 현재 줄의 맨 앞에 세울 학생
            sb.append(node).append(" ");

            // 현재 학생 다음에 서야 하는 학생들의 진입 차수 감소
            for (int next : graph[node]) {
                inDegree[next]--;

                // 진입 차수가 0이 되면 이제 줄을 세울 수 있음
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        System.out.println(sb.toString().trim());
    }
}