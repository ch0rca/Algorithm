package problem;

import java.util.Arrays;

public class PG_43236_징검다리 {
    public int solution(int distance, int[] rocks, int n) {

        // 이분탐색 전에 반드시 오름차순 정렬
        Arrays.sort(rocks);

        int left = 0;
        int right = distance;
        int answer = 0;

        while (left <= right) {
            int mid = (left + right) / 2;

            // 최소 간격을 mid로 만들 때 제거해야 하는 바위 수 계산
            int removeCount = countRemoval(rocks, distance, mid);

            if (removeCount > n) {
                // 제거해야 할 바위가 너무 많음 -> 기준 간격을 줄여야 함
                right = mid - 1;
            } else {
                // n개 이하로 제거 가능 -> 더 큰 간격도 가능한지 확인
                answer = mid;
                left = mid + 1;
            }
        }

        return answer;
    }

    // 최소 간격이 minGap이 되도록 하기 위해 제거해야 하는 바위의 개수를 센다
    private int countRemoval(int[] rocks, int distance, int minGap) {
        int removeCount = 0;
        int lastPosition = 0;

        for (int rock : rocks) {
            if (rock - lastPosition < minGap) {
                // 직전 지점과의 거리가 기준보다 작으면 이 바위를 제거한 것으로 처리
                removeCount++;
            } else {
                // 기준을 만족하면 이 바위를 남기고 기준점을 갱신
                lastPosition = rock;
            }
        }

        // 마지막으로 남긴 바위와 도착점 사이의 구간도 검사
        // 도착점은 제거할 수 없으므로 이 구간이 기준보다 좁으면 mid는 달성 불가능하다는 신호로 처리
        if (distance - lastPosition < minGap) {
            removeCount++;
        }

        return removeCount;
    }
}