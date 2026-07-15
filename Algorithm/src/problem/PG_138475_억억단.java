package problem;

import java.util.*;

public class PG_138475_억억단 {

    public int[] solution(int e, int[] starts) {

        // 각 숫자의 약수 개수
        int[] cnt = new int[e + 1];

        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                cnt[j]++;
            }
        }

        // best[i] : i~e 구간의 정답
        int[] best = new int[e + 1];
        best[e] = e;

        for (int i = e - 1; i >= 1; i--) {

            if (cnt[i] >= cnt[best[i + 1]]) {
                best[i] = i;
            } else {
                best[i] = best[i + 1];
            }
        }

        int[] answer = new int[starts.length];

        for (int i = 0; i < starts.length; i++) {
            answer[i] = best[starts[i]];
        }

        return answer;
    }
}