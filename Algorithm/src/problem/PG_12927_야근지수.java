package problem;

import java.util.PriorityQueue;
import java.util.Collections;

public class PG_12927_야근지수 {
    public long solution(int n, int[] works) {

        // 최대 힙: 가장 작업량이 많은 일이 항상 먼저 나오도록 역순 비교자 사용
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        long totalSum = 0;
        for (int w : works) {
            maxHeap.add(w);
            totalSum += w;
        }

        // n시간 안에 모든 일을 끝낼 수 있으면 야근 지수는 0
        if (totalSum <= n) {
            return 0;
        }

        // n시간 동안 매번 가장 큰 작업량을 1씩 줄인다
        for (int i = 0; i < n; i++) {
            int biggest = maxHeap.poll();
            biggest -= 1;
            maxHeap.add(biggest);
        }

        // 남은 작업량들의 제곱을 모두 더해서 야근 지수 계산
        long answer = 0;
        for (int value : maxHeap) {
            answer += (long) value * value;
        }

        return answer;
    }
}
