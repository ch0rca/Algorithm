package problem;

import java.util.*;

public class PG_81305_시험장나누기 {

    int[] num;
    int[][] links;
    int groupCnt;

    public int solution(int k, int[] num, int[][] links) {

        this.num = num;
        this.links = links;

        int root = findRoot(links);

        int left = 0;
        int right = 0;

        // 이분 탐색 범위 설정
        for (int n : num) {
            left = Math.max(left, n);
            right += n;
        }

        while (left < right) {

            int mid = (left + right) / 2;

            // 현재는 최소 1개의 그룹이 존재
            groupCnt = 1;

            dfs(root, mid);

            // k개 이하로 그룹을 만들 수 있다면 더 작은 값 탐색
            if (groupCnt <= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // 후위 순회
    private int dfs(int node, int limit) {

        int leftSum = 0;
        int rightSum = 0;

        if (links[node][0] != -1)
            leftSum = dfs(links[node][0], limit);

        if (links[node][1] != -1)
            rightSum = dfs(links[node][1], limit);

        int cur = num[node];

        // 모두 합칠 수 있는 경우
        if (leftSum + rightSum + cur <= limit) {
            return leftSum + rightSum + cur;
        }

        // 하나만 합칠 수 있는 경우
        if (Math.min(leftSum, rightSum) + cur <= limit) {
            groupCnt++;
            return Math.min(leftSum, rightSum) + cur;
        }

        // 둘 다 합칠 수 없는 경우
        groupCnt += 2;
        return cur;
    }

    // 루트 찾기
    private int findRoot(int[][] links) {

        boolean[] child = new boolean[num.length];

        for (int[] link : links) {

            if (link[0] != -1)
                child[link[0]] = true;

            if (link[1] != -1)
                child[link[1]] = true;
        }

        for (int i = 0; i < child.length; i++) {
            if (!child[i])
                return i;
        }

        return -1;
    }
}