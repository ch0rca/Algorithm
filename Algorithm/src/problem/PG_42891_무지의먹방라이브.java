package problem;

import java.util.Arrays;

public class PG_42891_무지의먹방라이브 {

    // k의 파라미터 타입은 long! (최대 2 x 10^13이라 int 범위 초과)
    public int solution(int[] food_times, long k) {

        int n = food_times.length;

        // [시간, 원래 번호] 쌍으로 배열 생성
        int[][] foods = new int[n][2];
        for (int i = 0; i < n; i++) {
            foods[i][0] = food_times[i];
            foods[i][1] = i + 1;
        }

        // 먹는 시간 기준 오름차순 정렬
        Arrays.sort(foods, (a, b) -> a[0] - b[0]);

        long remainK = k;
        int remainCount = n;
        int prevTime = 0;

        int i = 0;
        for (; i < n; i++) {
            int curTime = foods[i][0];
            long diff = (long) (curTime - prevTime) * remainCount;

            // 이번 구간을 다 돌 수 없다면 여기서 멈춘다
            if (diff > remainK) {
                break;
            }

            remainK -= diff;
            remainCount--;
            prevTime = curTime;
        }

        // 모든 음식을 다 먹은 경우
        if (remainCount == 0) {
            return -1;
        }

        // 남은 음식들만 잘라서 원래 번호 기준으로 재정렬
        int[][] remainFoods = Arrays.copyOfRange(foods, i, n);
        Arrays.sort(remainFoods, (a, b) -> a[1] - b[1]);

        // k를 remainCount로 나눈 나머지가 다음에 먹어야 할 음식의 인덱스
        int answerIndex = (int) (remainK % remainCount);

        return remainFoods[answerIndex][1];
    }
}