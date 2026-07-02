package problem;

public class PG_70130_스타수열 {
    public int solution(int[] a) {

        int n = a.length;
        int[] count = new int[n];

        // 각 숫자의 등장 횟수 저장
        for (int num : a) {
            count[num]++;
        }

        int answer = 0;

        // 공통 원소가 될 숫자를 하나씩 선택
        for (int num = 0; num < n; num++) {

            // 현재 최댓값보다 많이 만들 수 없는 경우 생략
            if (count[num] <= answer) {
                continue;
            }

            int pair = 0;

            // 인접한 두 원소를 이용해 최대한 많은 쌍 생성
            for (int i = 0; i < n - 1; i++) {

                // 공통 원소를 포함하고 두 수가 서로 달라야 함
                if ((a[i] == num || a[i + 1] == num) && a[i] != a[i + 1]) {
                    pair++;
                    i++;    // 사용한 원소는 다시 사용하지 않음
                }
            }

            answer = Math.max(answer, pair);
        }

        return answer * 2;
    }
}
