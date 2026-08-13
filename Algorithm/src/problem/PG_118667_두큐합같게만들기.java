package problem;

public class PG_118667_두큐합같게만들기 {
    public int solution(int[] queue1, int[] queue2) {
        int n = queue1.length;

        // 두 큐를 하나의 배열로 이어붙임 (순서 유지)
        int[] arr = new int[2 * n];
        long sum1 = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = queue1[i];
            sum1 += queue1[i];
        }

        long sum2 = 0;
        for (int i = 0; i < n; i++) {
            arr[n + i] = queue2[i];
            sum2 += queue2[i];
        }

        long total = sum1 + sum2;

        // 합이 홀수면 절반으로 나눌 수 없음
        if (total % 2 != 0) {
            return -1;
        }
        long target = total / 2;

        // lo, hi 구간이 현재 queue1이 가진 원소들의 범위를 의미
        int lo = 0;
        int hi = n - 1;
        long curSum = sum1;
        int count = 0;

        while (true) {
            if (curSum == target) {
                return count;
            }

            if (curSum > target) {
                // 합이 크면 왼쪽 원소를 다른 큐로 넘긴 것으로 간주하고 구간을 줄임
                if (lo > hi) {
                    return -1;
                }
                curSum -= arr[lo];
                lo++;
            } else {
                // 합이 작으면 오른쪽 원소를 받아온 것으로 간주하고 구간을 늘림
                hi++;
                if (hi >= arr.length) {
                    return -1;
                }
                curSum += arr[hi];
            }

            count++;
        }
    }
}