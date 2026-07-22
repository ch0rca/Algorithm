package problem;

public class PG_87391_공이동시뮬레이션 {

    public long solution(int n, int m, int x, int y, int[][] queries) {
        long rowCount = simulate1D(n, x, queries, true);
        long colCount = simulate1D(m, y, queries, false);
        return rowCount * colCount;
    }

    // isRow가 true면 행 관련 쿼리(command 2,3)만 사용, false면 열 관련 쿼리(command 0,1)만 사용
    private long simulate1D(long size, long target, int[][] queries, boolean isRow) {

        long leftWeight = 0, leftValue = 0;   // 왼쪽 벽에 뭉친 그룹의 개수와 현재 좌표
        long rightWeight = 0, rightValue = 0; // 오른쪽 벽에 뭉친 그룹의 개수와 현재 좌표
        long lo = 0, hi = size - 1;           // 아직 벽에 부딪히지 않은 활성 구간

        for (int[] q : queries) {
            int command = q[0];
            long dx = q[1];
            long delta;

            if (isRow) {
                if (command == 2) delta = -dx;     
                else if (command == 3) delta = dx;  
                else continue;                     
            } else {
                if (command == 0) delta = -dx;      
                else if (command == 1) delta = dx;  
                else continue;                     
            }

            // 이미 벽에 뭉친 그룹들도 계속 이동 가능 (다시 반대쪽으로 밀릴 수 있음)
            if (leftWeight > 0) {
                leftValue = clamp(leftValue + delta, 0, size - 1);
            }
            if (rightWeight > 0) {
                rightValue = clamp(rightValue + delta, 0, size - 1);
            }

            // 활성 구간 이동 처리
            if (lo <= hi) {
                long nlo = lo + delta;
                long nhi = hi + delta;

                if (nhi < 0) {
                    // 활성 구간 전체가 왼쪽 벽 밖으로 나감 -> 전부 0으로 뭉침
                    leftWeight += (hi - lo + 1);
                    leftValue = 0;
                    lo = 1; hi = 0; // 활성 구간 비움
                } else if (nlo < 0) {
                    // 왼쪽 일부만 벽에 부딪힘
                    long clipCount = -nlo;
                    leftWeight += clipCount;
                    leftValue = 0;
                    lo = 0;
                    hi = nhi;
                } else if (nlo >= size) {
                    // 활성 구간 전체가 오른쪽 벽 밖으로 나감 -> 전부 size-1로 뭉침
                    rightWeight += (hi - lo + 1);
                    rightValue = size - 1;
                    lo = 1; hi = 0; // 활성 구간 비움
                } else if (nhi >= size) {
                    // 오른쪽 일부만 벽에 부딪힘
                    long clipCount = nhi - size + 1;
                    rightWeight += clipCount;
                    rightValue = size - 1;
                    lo = nlo;
                    hi = size - 1;
                } else {
                    // 벽에 부딪히지 않고 통째로 이동
                    lo = nlo;
                    hi = nhi;
                }
            }
        }

        // 목표 좌표에 도착한 원소 개수
        long result = 0;
        if (leftWeight > 0 && leftValue == target) {
            result += leftWeight;
        }
        if (rightWeight > 0 && rightValue == target) {
            result += rightWeight;
        }
        if (lo <= hi && lo <= target && target <= hi) {
            // 활성 구간 내부는 항상 1대1 대응이므로 목표가 포함되면 정확히 1개
            result += 1;
        }
        return result;
    }

    private long clamp(long value, long min, long max) {
        return Math.max(min, Math.min(max, value));
    }
}