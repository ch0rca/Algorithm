package problem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PG_67258_보석쇼핑 {
    public int[] solution(String[] gems) {

        // 전체 보석 종류 수 계산
        Set<String> kindSet = new HashSet<>();
        for (String gem : gems) {
            kindSet.add(gem);
        }
        int totalKinds = kindSet.size();

        // 현재 윈도우 내 보석별 개수를 관리
        Map<String, Integer> window = new HashMap<>();

        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int ansStart = 1;
        int ansEnd = gems.length;

        for (int right = 0; right < gems.length; right++) {
            // right 포인터의 보석을 윈도우에 추가
            window.put(gems[right], window.getOrDefault(gems[right], 0) + 1);

            // 모든 종류가 윈도우 안에 들어온 경우, left를 당겨 최소 구간 탐색
            while (window.size() == totalKinds) {
                int curLen = right - left + 1;

                // 현재 구간이 더 짧을 때만 갱신 (같은 길이면 시작 인덱스가 작은 것 유지)
                if (curLen < minLen) {
                    minLen = curLen;
                    ansStart = left + 1;  
                    ansEnd = right + 1;   
                }

                // left 포인터의 보석을 윈도우에서 제거
                String leftGem = gems[left];
                window.put(leftGem, window.get(leftGem) - 1);
                if (window.get(leftGem) == 0) {
                    window.remove(leftGem); 
                }
                left++;
            }
        }

        return new int[]{ansStart, ansEnd};
    }
}
