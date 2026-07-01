package problem;

import java.util.Arrays;

public class PG_60062_외벽점검 {

    static int answer;
    static int[] w;            // 원형을 직선으로 풀기 위해 두 배로 늘린 취약 지점 배열
    static int[] friendDist;   // 친구들이 이동 가능한 거리 배열
    static boolean[] used;     // 친구 사용 여부

    public int solution(int n, int[] weak, int[] dist) {

        int weakLen = weak.length;
        int distLen = dist.length;


        // 뒤쪽 절반은 원래 위치 + n으로 채움 (한 바퀴를 더 돈 것처럼 표현)
        w = new int[weakLen * 2];
        for (int i = 0; i < weakLen; i++) {
            w[i] = weak[i];
            w[i + weakLen] = weak[i] + n;
        }

        friendDist = dist;
        used = new boolean[distLen];
        answer = Integer.MAX_VALUE;

        // 모든 취약 지점을 시작점으로 한 번씩 시도
        for (int start = 0; start < weakLen; start++) {
            backtrack(start + weakLen, start, 0);
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    // end       : 이번 회전 구간에서 모두 점검 완료로 판단할 목표 인덱스
    // curIdx    : 아직 점검되지 않은 가장 앞쪽 취약 지점의 인덱스
    // friendCount : 지금까지 투입한 친구 수
    static void backtrack(int end, int curIdx, int friendCount) {

        // 이번 회전 구간의 취약 지점을 모두 점검했다면 정답 후보로 비교
        if (curIdx >= end) {
            answer = Math.min(answer, friendCount);
            return;
        }

        // 이미 찾은 정답보다 친구를 더 많이 쓰고 있다면 가지치기
        if (friendCount >= answer) {
            return;
        }

        for (int i = 0; i < friendDist.length; i++) {
            if (used[i]) continue;

            used[i] = true;

            // 친구는 현재 미점검 상태인 가장 앞 취약 지점에서 출발한다고 가정
            int coveredPos = w[curIdx] + friendDist[i];

            // coveredPos 이하 위치의 취약 지점들은 모두 점검 완료 처리
            int nextIdx = curIdx;
            while (nextIdx < end && w[nextIdx] <= coveredPos) {
                nextIdx++;
            }

            backtrack(end, nextIdx, friendCount + 1);

            used[i] = false;
        }
    }
}