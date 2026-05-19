package problem;

import java.util.TreeMap;

public class PG_42628_이중우선순위큐 {
    public int[] solution(String[] operations) {

        TreeMap<Integer, Integer> map = new TreeMap<>();

        for (String op : operations) {
            String[] parts = op.split(" ");
            String command = parts[0];
            int value = Integer.parseInt(parts[1]);

            if (command.equals("I")) {
                // 삽입: 이미 있는 값이면 count +1, 없으면 1로 초기화
                map.put(value, map.getOrDefault(value, 0) + 1);

            } else {
                // 빈 큐에 삭제 명령이 오면 무시
                if (map.isEmpty()) continue;

                int target;
                if (value == 1) {
                    target = map.lastKey();
                } else {
                    target = map.firstKey();
                }

                int count = map.get(target);
                if (count == 1) {
                    // 해당 값의 개수가 1개면 key 자체를 제거
                    map.remove(target);
                } else {
                    // 개수가 2개 이상이면 count만 1 감소
                    map.put(target, count - 1);
                }
            }
        }

        if (map.isEmpty()) {
            return new int[]{0, 0};
        }

        // TreeMap은 항상 정렬 상태를 유지하므로 바로 최솟값/최댓값 참조 가능
        return new int[]{map.lastKey(), map.firstKey()};
    }
}