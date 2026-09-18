package problem;

import java.util.Arrays;

public class PG_12987_숫자게임 {
    public int solution(int[] A, int[] B) {

        // A, B를 오름차순 정렬
        Arrays.sort(A);
        Arrays.sort(B);

        int i = 0;      // 아직 이기지 못한 A 중 가장 작은 값을 가리키는 포인터
        int score = 0;  // B팀이 얻는 승점

        // B를 오름차순으로 하나씩 확인
        for (int j = 0; j < B.length; j++) {
            // 현재 B의 숫자가 남아있는 가장 작은 A를 이길 수 있다면
            if (i < A.length && B[j] > A[i]) {
                score++; // 승점 획득
                i++;     // 이 A는 처리 완료, 다음 A로 이동
            }
            // 이기지 못하면 이 B는 버리고 다음 B로 넘어감
        }

        return score;
    }
}