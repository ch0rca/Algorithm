package problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Stack;

// https://jungol.co.kr/problem/1141
public class Jungol_1141_불쾌한날 {

    public static void main(String[] args) throws IOException {

    	// System.setIn(new FileInputStream("src/problem/test/jungol_1141.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 소의 수

        Stack<Integer> stack = new Stack<>();
        long answer = 0; // 

        for (int i = 0; i < N; i++) {
            int height = Integer.parseInt(br.readLine());

            // 현재보다 작거나 같은 값 제거
            while (!stack.isEmpty() && stack.peek() <= height) {
                stack.pop();
            }

            answer += stack.size(); // 남아있는 값 개수 더하기
            stack.push(height); // 현재 값 추가
        }

        System.out.println(answer);
    }
}