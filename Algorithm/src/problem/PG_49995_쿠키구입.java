package problem;

public class PG_49995_쿠키구입 {
    public int solution(int[] cookie) {
        int n = cookie.length;
        int answer = 0;

        // 경계선(mid와 mid+1 사이)을 하나씩 옮겨가며 탐색
        for (int mid = 0; mid < n - 1; mid++) {
            int l = mid;       
            int r = mid + 1;   
            int leftSum = cookie[l];
            int rightSum = cookie[r];

            while (true) {
                if (leftSum == rightSum) {
                    // 두 합이 같으면 정답 후보로 갱신
                    answer = Math.max(answer, leftSum);

                    // 더 큰 값을 찾기 위해 양쪽 다 한 칸씩 확장
                    l--;
                    r++;
                    if (l < 0 || r >= n) break;
                    leftSum += cookie[l];
                    rightSum += cookie[r];

                } else if (leftSum < rightSum) {
                    // 왼쪽 합이 작으면 왼쪽을 확장해서 따라잡기
                    l--;
                    if (l < 0) break;
                    leftSum += cookie[l];

                } else {
                    // 오른쪽 합이 작으면 오른쪽을 확장해서 따라잡기
                    r++;
                    if (r >= n) break;
                    rightSum += cookie[r];
                }
            }
        }

        return answer;
    }
}