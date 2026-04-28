package problem;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
 
public class SWEA_3234_양팔저울 {
 
    static int N;
    static int[] w;
    static boolean[] used;
    static int count;
 
    static void backtrack(int idx, int left, int right) {
        if (idx == N) {
            count++;
            return;
        }
 
        for (int i = 0; i < N; i++) {
            if (used[i]) continue;
 
            used[i] = true;
 
            // 왼쪽에 올리는 경우
            backtrack(idx + 1, left + w[i], right);
 
            // 오른쪽에 올리는 경우
            if (right + w[i] <= left) {
                backtrack(idx + 1, left, right + w[i]);
            }
 
            used[i] = false;
        }
    }
 
    public static void main(String args[]) throws Exception {
    	
    	System.setIn(new FileInputStream("src/problem/test/swea_3234.txt"));
        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();
 
        for (int test_case = 1; test_case <= T; test_case++) {
            N = sc.nextInt();
            w = new int[N];
            used = new boolean[N];
            count = 0;
 
            for (int i = 0; i < N; i++) {
                w[i] = sc.nextInt();
            }
 
            backtrack(0, 0, 0);
 
            System.out.println("#" + test_case + " " + count);
        }
    }
}