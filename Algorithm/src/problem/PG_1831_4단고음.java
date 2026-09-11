package problem;

import java.util.HashMap;
import java.util.Map;

public class PG_1831_4단고음 {

	    private int answer;

	    public int solution(int n) {
	        answer = 0;

	        // 마지막 ++는 반드시 존재하므로 먼저 제거
	        dfs(n - 2, 2);

	        return answer;
	    }

	    private void dfs(int current, int plusCount) {

	        // 더 이상 3까지 내려갈 수 없음
	        if (current < 3) {
	            return;
	        }

	        /*
	         * 현재 plusCount로 만들 수 있는
	         * 최소한의 3의 배수 연산 횟수를 고려했을 때
	         * 현재 값으로는 불가능한 경우 가지치기
	         */
	        int maxMultiply = 0;
	        int value = current;

	        while (value >= 3) {
	            value /= 3;
	            maxMultiply++;
	        }

	        if (maxMultiply * 2 < plusCount) {
	            return;
	        }

	        // 최초의 *까지 제거한 경우
	        if (current == 3) {
	            if (plusCount == 2) {
	                answer++;
	            }
	            return;
	        }

	        // '*'를 역으로 제거
	        if (current % 3 == 0 && plusCount >= 2) {
	            dfs(current / 3, plusCount - 2);
	        }

	        // '+'를 역으로 제거
	        dfs(current - 1, plusCount + 1);
	    }
	}