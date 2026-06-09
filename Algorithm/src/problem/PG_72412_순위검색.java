package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PG_72412_순위검색 {

    public int[] solution(String[] info, String[] query) {

        Map<String, List<Integer>> map = new HashMap<>();

        for (String s : info) {
            String[] tokens = s.split(" ");
            // tokens[0]=언어, [1]=직군, [2]=경력, [3]=음식, [4]=score
            String[] conditions = { tokens[0], tokens[1], tokens[2], tokens[3] };
            int score = Integer.parseInt(tokens[4]);

            // 4가지 조건의 모든 부분집합(2^4=16)에 대해 Map에 score 삽입
            for (int mask = 0; mask < 16; mask++) {
                StringBuilder key = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    // 해당 비트가 1이면 와일드카드("-"), 0이면 실제 조건값
                    if ((mask >> i & 1) == 1) {
                        key.append("-");
                    } else {
                        key.append(conditions[i]);
                    }
                }
                map.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(score);
            }
        }

        for (List<Integer> list : map.values()) {
            Collections.sort(list);
        }

        int[] answer = new int[query.length];

        for (int q = 0; q < query.length; q++) {
            String[] tokens = query[q].split(" and | ");
            String key = tokens[0] + tokens[1] + tokens[2] + tokens[3];
            int minScore = Integer.parseInt(tokens[4]);

            List<Integer> list = map.get(key);

            if (list == null) {
                answer[q] = 0;
                continue;
            }

            answer[q] = list.size() - lowerBound(list, minScore);
        }

        return answer;
    }

    private int lowerBound(List<Integer> list, int target) {
        int lo = 0;
        int hi = list.size();

        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (list.get(mid) < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        return lo;
    }
}