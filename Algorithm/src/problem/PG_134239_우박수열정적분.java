package problem;

import java.util.ArrayList;
import java.util.List;

public class PG_134239_우박수열정적분 {
    public double[] solution(int k, int[][] ranges) {

        //  k에서 시작해서 1이 될 때까지 반복
        List<Long> seqList = new ArrayList<>();
        long cur = k;
        seqList.add(cur);
        while (cur != 1) {
            if (cur % 2 == 0) {
                cur = cur / 2;
            } else {
                cur = cur * 3 + 1;
            }
            seqList.add(cur);
        }

        int n = seqList.size() - 1; // 수열이 1이 될 때까지 걸린 횟수

        long[] seq = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            seq[i] = seqList.get(i);
        }

        double[] answer = new double[ranges.length];

        for (int idx = 0; idx < ranges.length; idx++) {
            int a = ranges[idx][0];
            int c = ranges[idx][1]; // 0 이하의 값

            // 실제 끝 x좌표는 n + c 로 계산됨
            int end = n + c;

            if (a > end) {
                // 시작점이 끝점보다 크면 유효하지 않은 구간
                answer[idx] = -1.0;
                continue;
            }

            // 부동소수점 오차를 줄이기
            long doubledSum = 0;
            for (int i = a; i < end; i++) {
                doubledSum += seq[i] + seq[i + 1];
            }

            answer[idx] = doubledSum / 2.0;
        }

        return answer;
    }
}