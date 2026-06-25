package problem;

import java.util.ArrayDeque;
import java.util.Queue;

public class PG_43163_단어변환 {

    // 큐에 단어와 변환 단계를 함께 저장하기 위한 내부 클래스
    static class WordStep {
        String word;
        int step;

        WordStep(String word, int step) {
            this.word = word;
            this.step = step;
        }
    }

    public int solution(String begin, String target, String[] words) {
        int n = words.length;
        boolean[] visited = new boolean[n];

        Queue<WordStep> queue = new ArrayDeque<>();
        queue.add(new WordStep(begin, 0));

        while (!queue.isEmpty()) {
            WordStep current = queue.poll();

            // target에 도달하면 지금까지의 단계 수를 바로 반환
            if (current.word.equals(target)) {
                return current.step;
            }

            // words 배열을 돌면서 한 글자만 다른 단어를 찾아 큐에 추가
            for (int i = 0; i < n; i++) {
                if (!visited[i] && isOneCharDiff(current.word, words[i])) {
                    visited[i] = true;
                    queue.add(new WordStep(words[i], current.step + 1));
                }
            }
        }

        // 큐가 다 빌 때까지 target을 찾지 못한 경우 변환 불가능
        return 0;
    }

    // 두 단어가 정확히 한 글자만 다른지 확인
    private boolean isOneCharDiff(String a, String b) {
        int diffCount = 0;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diffCount++;
            }
        }

        return diffCount == 1;
    }
}
