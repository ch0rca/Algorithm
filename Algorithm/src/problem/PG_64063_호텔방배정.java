package problem;

import java.util.HashMap;
import java.util.Map;

public class PG_64063_호텔방배정 {

    static Map<Long, Long> parent = new HashMap<>();

    public long[] solution(long k, long[] room_number) {

        long[] answer = new long[room_number.length];

        for (int i = 0; i < room_number.length; i++) {
            long want = room_number[i];

            // want번 방부터 시작해서 비어있는 가장 작은 방 번호를 찾는다
            long assigned = find(want);

            answer[i] = assigned;

            // assigned번 방은 이제 배정되었으므로, 다음 탐색은 assigned + 1부터 시작하도록 연결
            parent.put(assigned, assigned + 1);
        }

        return answer;
    }

    // 경로 압축을 적용한 find 함수
    // x가 아직 맵에 없다면 x번 방 자체가 비어있다는 뜻
    static long find(long x) {
        if (!parent.containsKey(x)) {
            return x;
        }

        long root = find(parent.get(x));
        parent.put(x, root); // 경로 압축: 다음 탐색을 위해 바로 최종 결과를 연결해둔다
        return root;
    }
}