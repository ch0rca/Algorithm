package problem;

import java.util.ArrayList;
import java.util.List;

public class PG_1843_사칙연산 {
    public int solution(String[] arr) {

        // 숫자와 연산자를 분리해서 저장
        List<Integer> numList = new ArrayList<>();
        List<Character> opList = new ArrayList<>();

        for (String token : arr) {
            if (token.equals("+") || token.equals("-")) {
                opList.add(token.charAt(0));
            } else {
                numList.add(Integer.parseInt(token));
            }
        }

        int n = numList.size();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = numList.get(i);
        }

        // i~j 구간에서 만들 수 있는 최댓값/최솟값
        long[][] dpMax = new long[n][n];
        long[][] dpMin = new long[n][n];

        // 구간 길이가 0인 경우 초기화
        for (int i = 0; i < n; i++) {
            dpMax[i][i] = nums[i];
            dpMin[i][i] = nums[i];
        }

        // 구간 길이를 1씩 늘려가며 채움
        for (int len = 1; len < n; len++) {
            for (int i = 0; i + len < n; i++) {
                int j = i + len;

                long maxVal = Long.MIN_VALUE;
                long minVal = Long.MAX_VALUE;

                // i~j 구간을 k, k+1 지점에서 나눠서 결합
                for (int k = i; k < j; k++) {
                    char op = opList.get(k); // i~k 다음에 오는 연산자

                    long cand1, cand2;

                    if (op == '+') {
                        // 덧셈은 최댓값끼리, 최솟값끼리 더하면 각각 최댓값/최솟값이 됨
                        cand1 = dpMax[i][k] + dpMax[k + 1][j];
                        cand2 = dpMin[i][k] + dpMin[k + 1][j];
                    } else {
                        // 뺄셈은 오른쪽을 최소화해야 전체가 최대가 되고, 반대로 하면 최소가 됨
                        cand1 = dpMax[i][k] - dpMin[k + 1][j];
                        cand2 = dpMin[i][k] - dpMax[k + 1][j];
                    }

                    maxVal = Math.max(maxVal, cand1);
                    minVal = Math.min(minVal, cand2);
                }

                dpMax[i][j] = maxVal;
                dpMin[i][j] = minVal;
            }
        }

        return (int) dpMax[0][n - 1];
    }
}