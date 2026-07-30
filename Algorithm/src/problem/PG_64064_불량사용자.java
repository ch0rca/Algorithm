package problem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PG_64064_불량사용자 {

    static String[] userId;
    static List<List<Integer>> candidates; // banned_id 별 매칭 가능한 user_id 인덱스 목록
    static Set<Integer> resultSet; // 최종적으로 나온 사용자 조합을 중복 없이 저장

    public int solution(String[] user_id, String[] banned_id) {
        userId = user_id;
        candidates = new ArrayList<>();
        resultSet = new HashSet<>();

        // banned_id 각각에 대해 매칭되는 user_id 인덱스들을 미리 계산
        for (String banned : banned_id) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < userId.length; i++) {
                if (isMatch(banned, userId[i])) {
                    list.add(i);
                }
            }
            candidates.add(list);
        }

        backtrack(0, 0);

        return resultSet.size();
    }

    // depth: 현재 처리 중인 banned_id
    // usedMask: 지금까지 선택된 user_id
    static void backtrack(int depth, int usedMask) {

        // 모든 banned_id에 대해 매칭을 끝낸 경우
        if (depth == candidates.size()) {
            // 비트마스크 자체를 저장하면 매칭 순서가 달라도 같은 조합은 자동으로 중복 제거됨
            resultSet.add(usedMask);
            return;
        }

        // 현재 banned_id의 후보들 중 아직 사용되지 않은 사용자를 선택
        for (int idx : candidates.get(depth)) {
            if ((usedMask & (1 << idx)) == 0) {
                backtrack(depth + 1, usedMask | (1 << idx));
            }
        }
    }

    // banned 패턴과 user 문자열이 매칭되는지 검사
    // 길이가 같아야 하고 '*'가 아닌 위치는 문자가 같아야 함
    static boolean isMatch(String banned, String user) {
        if (banned.length() != user.length()) {
            return false;
        }

        for (int i = 0; i < banned.length(); i++) {
            char b = banned.charAt(i);
            if (b != '*' && b != user.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}