package problem;
import java.util.*;

public class PG_12946_하노이탑 {

	    private List<int[]> answer = new ArrayList<>();

	    public int[][] solution(int n) {

	        hanoi(n, 1, 2, 3);

	        return answer.toArray(new int[answer.size()][]);
	    }

	    private void hanoi(int n, int from, int via, int to) {

	        // 원판이 1개라면 바로 이동
	        if (n == 1) {
	            answer.add(new int[]{from, to});
	            return;
	        }

	        // 1. n-1개의 원판을 보조 기둥으로 이동
	        hanoi(n - 1, from, to, via);

	        // 2. 가장 큰 원판을 목적지로 이동
	        answer.add(new int[]{from, to});

	        // 3. n-1개의 원판을 목적지로 이동
	        hanoi(n - 1, via, from, to);
	    }
	}
