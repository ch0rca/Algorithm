package problem;

import java.util.Arrays;

public class PG_131130_혼자놀기의달인 {
    public int solution(int[] cards) {
        int n = cards.length;
        boolean[] visited = new boolean[n];

        int top1 = 0; // 가장 큰 사이클 크기
        int top2 = 0; // 두 번째로 큰 사이클 크기

        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;

            // i번 상자에서 시작해서 사이클을 순회하며 크기를 센다
            int cycleLength = 0;
            int cur = i;
            while (!visited[cur]) {
                visited[cur] = true;
                cycleLength++;
                cur = cards[cur] - 1; // 카드 번호는 1-indexed이므로 배열 인덱스로 변환
            }

            // 상위 두 사이클 크기 갱신
            if (cycleLength > top1) {
                top2 = top1;
                top1 = cycleLength;
            } else if (cycleLength > top2) {
                top2 = cycleLength;
            }
        }

        // 그룹이 2개 미만이면 top2가 0이라 자동으로 0점이 됨
        return top1 * top2;
    }
}