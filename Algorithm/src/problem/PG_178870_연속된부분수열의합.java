package problem;

import java.util.Arrays;

public class PG_178870_연속된부분수열의합 {

    public int[] solution(int[] sequence, int k) {

        int n = sequence.length;

        int start = 0;
        long sum = 0;

        // 지금까지 찾은 최소 길이와 그때의 시작, 끝 인덱스
        int bestLen = Integer.MAX_VALUE;
        int bestStart = -1;
        int bestEnd = -1;

        for (int end = 0; end < n; end++) {

            sum += sequence[end];

            // 합이 k 이상이면 왼쪽 포인터를 당기면서 줄여본다
            while (sum >= k && start <= end) {

                if (sum == k) {
                    int len = end - start + 1;
                    // 더 짧은 구간을 찾은 경우에만 갱신
                    if (len < bestLen) {
                        bestLen = len;
                        bestStart = start;
                        bestEnd = end;
                    }
                }

                sum -= sequence[start];
                start++;
            }
        }

        return new int[]{bestStart, bestEnd};
    }
}