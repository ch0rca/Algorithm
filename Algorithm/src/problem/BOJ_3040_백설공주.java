package problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//https://www.acmicpc.net/problem/3040
public class BOJ_3040_백설공주 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 아홉 난쟁이의 모자 숫자를 배열에 저장
        int[] hats = new int[9];
        int total = 0;

        for (int i = 0; i < 9; i++) {
            hats[i] = Integer.parseInt(br.readLine().trim());
            total += hats[i]; // 9개의 합을 미리 구해둠
        }

        // 제외할 두 수의 합 = 전체 합 - 100
        int target = total - 100;

        // 9C2 = 36가지 조합으로 제외할 두 수를 탐색
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (hats[i] + hats[j] == target) {
                    // 제외할 두 인덱스를 건너뛰고 나머지 7개 출력
                    for (int k = 0; k < 9; k++) {
                        if (k != i && k != j) {
                            System.out.println(hats[k]);
                        }
                    }
                    return;
                }
            }
        }
    }
}