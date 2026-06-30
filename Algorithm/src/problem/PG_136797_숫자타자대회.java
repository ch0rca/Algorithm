package problem;

import java.util.HashMap;
import java.util.Map;

public class PG_136797_숫자타자대회 {

    static int[] rowOf = new int[10];
    static int[] colOf = new int[10];

    static {
        rowOf[1] = 0; colOf[1] = 0;
        rowOf[2] = 0; colOf[2] = 1;
        rowOf[3] = 0; colOf[3] = 2;
        rowOf[4] = 1; colOf[4] = 0;
        rowOf[5] = 1; colOf[5] = 1;
        rowOf[6] = 1; colOf[6] = 2;
        rowOf[7] = 2; colOf[7] = 0;
        rowOf[8] = 2; colOf[8] = 1;
        rowOf[9] = 2; colOf[9] = 2;
        rowOf[0] = 3; colOf[0] = 1;
    }

    public int solution(String numbers) {

        // 상태 키: 왼손위치 * 10 + 오른손위치, 값: 그 상태까지의 최소 누적 가중치
        Map<Integer, Integer> states = new HashMap<>();
        states.put(encode(4, 6), 0);

        for (int i = 0; i < numbers.length(); i++) {
            int digit = numbers.charAt(i) - '0';
            Map<Integer, Integer> nextStates = new HashMap<>();

            for (Map.Entry<Integer, Integer> entry : states.entrySet()) {
                int key = entry.getKey();
                int cost = entry.getValue();
                int left = key / 10;
                int right = key % 10;

                if (left == digit) {
                    update(nextStates, encode(digit, right), cost + 1);

                } else if (right == digit) {
                    update(nextStates, encode(left, digit), cost + 1);

                } else {

                    int leftMoveCost = cost + distance(left, digit);
                    update(nextStates, encode(digit, right), leftMoveCost);

                    int rightMoveCost = cost + distance(right, digit);
                    update(nextStates, encode(left, digit), rightMoveCost);
                }
            }

            states = nextStates;
        }

        // 모든 글자를 처리한 후 남은 상태들 중 최소 가중치가 정답
        int answer = Integer.MAX_VALUE;
        for (int cost : states.values()) {
            answer = Math.min(answer, cost);
        }

        return answer;
    }

    // 두 위치를 하나의 정수 키로 합치는 함수
    private int encode(int left, int right) {
        return left * 10 + right;
    }

    // 같은 상태가 여러 경로로 도달했을 때 더 작은 비용만 남기는 함수
    private void update(Map<Integer, Integer> map, int key, int cost) {
        Integer prev = map.get(key);
        if (prev == null || cost < prev) {
            map.put(key, cost);
        }
    }

    // 두 숫자 키 사이의 최소 이동 가중치 계산
    private int distance(int a, int b) {
        int dr = Math.abs(rowOf[a] - rowOf[b]);
        int dc = Math.abs(colOf[a] - colOf[b]);
        int diagonalSteps = Math.min(dr, dc);
        int straightSteps = Math.abs(dr - dc);
        return diagonalSteps * 3 + straightSteps * 2;
    }
}