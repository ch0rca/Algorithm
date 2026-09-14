package problem;

public class PG_60060_가사검색 {

    // 트라이 노드
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int count = 0; // 이 노드를 지나가는 단어 개수
    }

    public int[] solution(String[] words, String[] queries) {

        // 길이별 트라이
        // 인덱스 0은 사용하지 않고 1 ~ 10000 사용
        TrieNode[] prefixTrie = new TrieNode[10001];
        TrieNode[] suffixTrie = new TrieNode[10001];

        // 단어들을 길이별 트라이에 삽입
        for (String word : words) {
            int len = word.length();

            if (prefixTrie[len] == null) {
                prefixTrie[len] = new TrieNode();
            }

            if (suffixTrie[len] == null) {
                suffixTrie[len] = new TrieNode();
            }

            // 정방향 트라이
            insert(prefixTrie[len], word);

            // 역방향 트라이
            insert(suffixTrie[len], reverse(word));
        }

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            String query = queries[i];
            int len = query.length();

            if (query.charAt(len - 1) == '?') {
                // "fro??" 형태
                String fixedPart = query.substring(0, query.indexOf('?'));

                answer[i] = search(prefixTrie[len], fixedPart);

            } else {
                // "??odo" 형태
                String fixedPart = query.substring(query.lastIndexOf('?') + 1);

                // 뒤집어서 "odo"를 검색
                answer[i] = search(suffixTrie[len], reverse(fixedPart));
            }
        }

        return answer;
    }

    // 트라이에 단어 삽입
    private void insert(TrieNode root, String word) {
        TrieNode node = root;

        // ★ 루트도 해당 길이의 단어 개수를 저장
        // "?????" 같은 전체 와일드카드 처리에 필요
        node.count++;

        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';

            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }

            node = node.children[idx];

            // 이 노드를 지나가는 단어 개수
            node.count++;
        }
    }

    // 확정된 문자열을 따라가며 매치되는 단어 수 반환
    private int search(TrieNode root, String fixedPart) {

        if (root == null) {
            return 0;
        }

        TrieNode node = root;

        for (int i = 0; i < fixedPart.length(); i++) {
            int idx = fixedPart.charAt(i) - 'a';

            if (node.children[idx] == null) {
                return 0;
            }

            node = node.children[idx];
        }

        return node.count;
    }

    // 문자열 뒤집기
    private String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}