package problem;

import java.util.ArrayList;
import java.util.List;

public class PG_150366_표병합 {

	    static final int SIZE = 50;
	    static final int MAX = 2500;

	    int[] parent = new int[MAX + 1];
	    String[] value = new String[MAX + 1];

	    public String[] solution(String[] commands) {

	        // 모든 셀을 자기 자신을 부모로 초기화
	        for (int i = 1; i <= MAX; i++) {
	            parent[i] = i;
	        }

	        List<String> answer = new ArrayList<>();

	        for (String command : commands) {
	            String[] cmd = command.split(" ");

	            switch (cmd[0]) {

	                // UPDATE r c value
	                case "UPDATE":
	                    if (cmd.length == 4) {
	                        int idx = convert(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
	                        value[find(idx)] = cmd[3];
	                    }
	                    // UPDATE value1 value2
	                    else {
	                        String from = cmd[1];
	                        String to = cmd[2];

	                        for (int i = 1; i <= MAX; i++) {
	                            if (to.equals(value[i])) continue;

	                            if (from.equals(value[i])) {
	                                value[i] = to;
	                            }
	                        }
	                    }
	                    break;

	                // MERGE r1 c1 r2 c2
	                case "MERGE":
	                    merge(
	                            convert(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2])),
	                            convert(Integer.parseInt(cmd[3]), Integer.parseInt(cmd[4]))
	                    );
	                    break;

	                // UNMERGE r c
	                case "UNMERGE":
	                    unmerge(convert(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2])));
	                    break;

	                // PRINT r c
	                case "PRINT":
	                    int idx = convert(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
	                    String v = value[find(idx)];

	                    answer.add(v == null ? "EMPTY" : v);
	                    break;
	            }
	        }

	        return answer.toArray(new String[0]);
	    }

	    // (r,c)를 1차원 번호로 변환
	    private int convert(int r, int c) {
	        return (r - 1) * SIZE + c;
	    }

	    // Union-Find Find
	    private int find(int x) {
	        if (parent[x] == x) return x;
	        return parent[x] = find(parent[x]);
	    }

	    // 두 셀 병합
	    private void merge(int a, int b) {

	        int pa = find(a);
	        int pb = find(b);

	        // 이미 같은 그룹
	        if (pa == pb) return;

	        // pa의 값이 비어있으면 pb의 값을 유지
	        if (value[pa] == null && value[pb] != null) {
	            parent[pa] = pb;
	        }
	        // pa의 값이 있으면 pa의 값을 유지
	        else {
	            parent[pb] = pa;
	        }
	    }

	    // 병합 해제
	    private void unmerge(int idx) {

	        int root = find(idx);
	        String keep = value[root];

	        List<Integer> group = new ArrayList<>();

	        // 같은 그룹의 모든 셀 찾기
	        for (int i = 1; i <= MAX; i++) {
	            if (find(i) == root) {
	                group.add(i);
	            }
	        }

	        // 모두 독립된 셀로 분리
	        for (int cell : group) {
	            parent[cell] = cell;
	            value[cell] = null;
	        }

	        // 선택한 셀만 기존 값 유지
	        value[idx] = keep;
	    }
	}