package problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
import java.io.FileInputStream;

// https://www.acmicpc.net/problem/1158
public class BOJ_1158_요세푸스 {

    public static void main(String[] args) throws IOException {
    	
    	//System.setIn(new FileInputStream("src/problem/test/boj_1158.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            queue.offer(i);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<");

        // 사람이 모두 제거될 때까지 반복
        while (!queue.isEmpty()) {

            // K-1번 회전 (앞 → 뒤)
            for (int i = 0; i < K - 1; i++) {
                queue.offer(queue.poll()); // 맨 앞 사람을 뒤로 보내는 과정 → 원형에서 한 칸 이동한 것과 같음
            }

            // K번째 사람 제거
            sb.append(queue.poll());

            // 마지막 요소가 아니라면 ", " 추가
            if (!queue.isEmpty()) {
                sb.append(", ");
            }
        }

        sb.append(">");
        System.out.println(sb.toString());
    }
}