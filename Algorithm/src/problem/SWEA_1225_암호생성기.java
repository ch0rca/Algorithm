package problem;

import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.Deque;
import java.io.FileInputStream;

//https://swexpertacademy.com/main/solvingProblem/solvingProblem.do
public class SWEA_1225_암호생성기 {
	
    public static void main(String args[]) throws Exception {
 
    	// System.setIn(new FileInputStream("src/problem/test/swea_1225.txt"));
        Scanner sc = new Scanner(System.in);


        for(int test_case = 1; test_case <= 10; test_case++)
        {

            // 8개의 숫자를 저장할 덱 선언
            // 앞에서 꺼내고(pollFirst) 뒤로 넣는(offerLast) 큐 구조로 사용
        	int tcNum = sc.nextInt();

            Deque<Integer> deque = new ArrayDeque<>();

            // 8개 숫자 입력
            for(int i = 0; i < 8; i++){
                deque.offerLast(sc.nextInt());
            }

            int step = 0;

            while(true){
                int decreaseAmount = (step % 5) + 1;

                int value = deque.pollFirst();
                value -= decreaseAmount;

                if(value <= 0){
                    value = 0;
                    deque.offerLast(value);
                    break;
                }

                deque.offerLast(value);
                step++;
            }

         // 결과 출력
            StringBuilder sb = new StringBuilder();
            sb.append("#").append(test_case).append(" ");

            int idx = 0;
            for(int num : deque){
                sb.append(num);
                if(idx < 7) sb.append(" "); 
                idx++;
            }

            System.out.println(sb.toString());
        }
    }
}

