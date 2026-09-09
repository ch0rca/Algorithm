package problem;
import java.util.*;

public class PG_42890_후보키 {

	    private List<Integer> candidateKeys = new ArrayList<>();

	    public int solution(String[][] relation) {

	        int columnCount = relation[0].length;

	        // 컬럼의 모든 부분집합 탐색
	        for (int mask = 1; mask < (1 << columnCount); mask++) {

	            // 최소성 검사
	            if (!isMinimal(mask)) {
	                continue;
	            }

	            // 유일성 검사
	            if (isUnique(relation, mask)) {
	                candidateKeys.add(mask);
	            }
	        }

	        return candidateKeys.size();
	    }

	    private boolean isMinimal(int mask) {

	        for (int key : candidateKeys) {

	            // 기존 후보키의 모든 컬럼이
	            // 현재 mask 안에 포함되어 있다면
	            // 현재 mask는 최소성을 만족하지 못함
	            if ((key & mask) == key) {
	                return false;
	            }
	        }

	        return true;
	    }

	    private boolean isUnique(String[][] relation, int mask) {

	        Set<String> set = new HashSet<>();

	        for (String[] row : relation) {

	            StringBuilder sb = new StringBuilder();

	            for (int col = 0; col < relation[0].length; col++) {

	                if ((mask & (1 << col)) != 0) {
	                    sb.append(row[col]).append("|");
	                }
	            }

	            set.add(sb.toString());
	        }

	        return set.size() == relation.length;
	    }
	}