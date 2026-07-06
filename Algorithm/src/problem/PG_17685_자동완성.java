package problem;

import java.util.HashMap;
import java.util.Map;

public class PG_17685_자동완성 {

	// Trie의 노드
    static class TrieNode {
        // 현재 노드를 지나는 단어의 개수
        int count = 0;

        // 자식 노드
        Map<Character, TrieNode> child = new HashMap<>();
    }

    TrieNode root = new TrieNode();

    public int solution(String[] words) {

        // 모든 단어를 Trie에 저장
        for (String word : words) {
            insert(word);
        }

        int answer = 0;

        // 각 단어마다 필요한 입력 횟수 계산
        for (String word : words) {
            answer += search(word);
        }

        return answer;
    }

    // Trie에 단어 삽입
    private void insert(String word) {

        TrieNode cur = root;

        for (char c : word.toCharArray()) {

            // 자식 노드가 없으면 생성
            cur.child.putIfAbsent(c, new TrieNode());

            // 다음 노드로 이동
            cur = cur.child.get(c);

            // 해당 노드를 지나는 단어 수 증가
            cur.count++;
        }
    }

    // 자동완성에 필요한 최소 입력 횟수 계산
    private int search(String word) {

        TrieNode cur = root;
        int cnt = 0;

        for (char c : word.toCharArray()) {

            cur = cur.child.get(c);
            cnt++;

            // 현재 접두사만으로 단어가 유일하게 결정됨
            if (cur.count == 1) {
                return cnt;
            }
        }

        // 끝까지 입력해야 하는 경우
        return cnt;
    }

}
