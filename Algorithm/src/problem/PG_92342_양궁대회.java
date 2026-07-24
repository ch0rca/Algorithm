package problem;

import java.util.Arrays;

public class PG_92342_양궁대회 {

	    int maxDiff = 0;
	    int[] answer = {-1};

	    public int[] solution(int n, int[] info) {
	        dfs(0, n, info, new int[11]);
	        return answer;
	    }

	    // idx : 현재 점수(0 -> 10점, 10 -> 0점)
	    // remain : 남은 화살 수
	    private void dfs(int idx, int remain, int[] apeach, int[] lion) {

	        // 모든 점수를 확인한 경우
	        if (idx == 11) {

	            // 남은 화살은 모두 0점에 사용
	            if (remain > 0) {
	                lion[10] += remain;
	            }

	            int lionScore = 0;
	            int apeachScore = 0;

	            // 점수 계산
	            for (int i = 0; i < 11; i++) {

	                if (lion[i] == 0 && apeach[i] == 0) continue;

	                if (lion[i] > apeach[i]) {
	                    lionScore += (10 - i);
	                } else {
	                    apeachScore += (10 - i);
	                }
	            }

	            int diff = lionScore - apeachScore;

	            // 라이언이 이긴 경우만 고려
	            if (diff > 0) {

	                if (diff > maxDiff) {
	                    maxDiff = diff;
	                    answer = lion.clone();
	                }
	                // 점수 차이가 같으면 낮은 점수를 더 많이 맞힌 경우 선택
	                else if (diff == maxDiff) {
	                    if (isBetter(lion, answer)) {
	                        answer = lion.clone();
	                    }
	                }
	            }

	            // 되돌리기
	            if (remain > 0) {
	                lion[10] -= remain;
	            }
	            return;
	        }

	        // 현재 점수를 가져가는 경우
	        int need = apeach[idx] + 1;

	        if (remain >= need) {
	            lion[idx] = need;
	            dfs(idx + 1, remain - need, apeach, lion);
	            lion[idx] = 0;
	        }

	        // 현재 점수를 포기하는 경우
	        dfs(idx + 1, remain, apeach, lion);
	    }

	    // 우선순위 비교
	    // 낮은 점수(0점)부터 더 많이 맞힌 경우가 우선
	    private boolean isBetter(int[] cur, int[] best) {

	        for (int i = 10; i >= 0; i--) {
	            if (cur[i] != best[i]) {
	                return cur[i] > best[i];
	            }
	        }

	        return false;
	    }
	}