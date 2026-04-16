package problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Stack;
import java.io.FileInputStream;


//https://www.acmicpc.net/problem/2493
public class BOJ_2493_탑 {
 
	 public static void main(String[] args) throws IOException {
		 
	    	System.setIn(new FileInputStream("src/problem/test/boj_2493.txt"));
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	        int N = Integer.parseInt(br.readLine());
	        int[] heights = new int[N];

	        StringTokenizer st = new StringTokenizer(br.readLine());
	        for (int i = 0; i < N; i++) {
	            heights[i] = Integer.parseInt(st.nextToken());
	        }

	        // (index, height) 저장
	        Stack<int[]> stack = new Stack<>();

	        StringBuilder sb = new StringBuilder();

	        for (int i = 0; i < N; i++) {

	            int currentHeight = heights[i];

	            // 현재보다 작은 탑 제거
	            while (!stack.isEmpty() && stack.peek()[1] < currentHeight) {
	                stack.pop(); // 현재 탑보다 작은 탑은 앞으로 어떤 탑의 신호도 못 받음 → 쓸모 없으므로 제거
	            }

	            if (stack.isEmpty()) {
	                sb.append(0).append(" ");
	                // 왼쪽에 나보다 큰 탑이 없음
	            } else {
	                sb.append(stack.peek()[0]).append(" ");
	            }

	            // 현재 탑 추가
	            stack.push(new int[]{i + 1, currentHeight}); // i+1: 탑 번호 (문제는 1-index), currentHeight: 높이
	        }

	        System.out.println(sb.toString());
	    }
 
}