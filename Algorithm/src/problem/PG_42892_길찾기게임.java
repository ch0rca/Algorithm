package problem;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class PG_42892_길찾기게임 {

    static int[] left, right;
    static int[][] nodeinfo;
    static List<Integer> preList, postList;

    public int[][] solution(int[][] nodeinfo) {
        int N = nodeinfo.length;
        PG_42892_길찾기게임.nodeinfo = nodeinfo;

        // 노드 번호(1~N)를 y값 내림차순으로 정렬
        Integer[] order = new Integer[N];
        for (int i = 0; i < N; i++) {
            order[i] = i + 1; // 노드 번호는 1부터 시작
        }
        Arrays.sort(order, (a, b) -> {
            int ya = nodeinfo[a - 1][1];
            int yb = nodeinfo[b - 1][1];
            return yb - ya; // y값 내림차순
        });

        left = new int[N + 1];
        right = new int[N + 1];
        int root = -1;

        // 정렬된 순서대로 BST 삽입 방식으로 트리 구성
        for (int idx : order) {
            if (root == -1) {
                root = idx;
                continue;
            }

            int cur = root;
            int curX = nodeinfo[idx - 1][0]; // 삽입할 노드의 x값

            while (true) {
                int curNodeX = nodeinfo[cur - 1][0];

                if (curX < curNodeX) {
                    // 왼쪽으로 이동
                    if (left[cur] == 0) {
                        left[cur] = idx;
                        break;
                    }
                    cur = left[cur];
                } else {
                    // 오른쪽으로 이동
                    if (right[cur] == 0) {
                        right[cur] = idx;
                        break;
                    }
                    cur = right[cur];
                }
            }
        }

        preList = new ArrayList<>();
        postList = new ArrayList<>();
        preorder(root);
        postorder(root);

        int[][] answer = new int[2][N];
        for (int i = 0; i < N; i++) {
            answer[0][i] = preList.get(i);
            answer[1][i] = postList.get(i);
        }

        return answer;
    }

    // 전위 순회: 현재 노드 -> 왼쪽 -> 오른쪽
    static void preorder(int node) {
        if (node == 0) return;
        preList.add(node);
        preorder(left[node]);
        preorder(right[node]);
    }

    // 후위 순회: 왼쪽 -> 오른쪽 -> 현재 노드
    static void postorder(int node) {
        if (node == 0) return;
        postorder(left[node]);
        postorder(right[node]);
        postList.add(node);
    }
}