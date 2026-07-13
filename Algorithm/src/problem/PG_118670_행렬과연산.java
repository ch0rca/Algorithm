package problem;

import java.util.*;

public class PG_118670_행렬과연산 {


    public int[][] solution(int[][] rc, String[] operations) {

        int row = rc.length;
        int col = rc[0].length;

        Deque<Integer> left = new ArrayDeque<>();
        Deque<Integer> right = new ArrayDeque<>();
        Deque<Deque<Integer>> middle = new ArrayDeque<>();

        // 행렬을 3개의 deque로 분리
        for (int i = 0; i < row; i++) {

            left.offerLast(rc[i][0]);
            right.offerLast(rc[i][col - 1]);

            Deque<Integer> mid = new ArrayDeque<>();

            for (int j = 1; j < col - 1; j++) {
                mid.offerLast(rc[i][j]);
            }

            middle.offerLast(mid);
        }

        for (String op : operations) {

            if (op.equals("ShiftRow")) {

                left.offerFirst(left.pollLast());
                right.offerFirst(right.pollLast());
                middle.offerFirst(middle.pollLast());

            } else {

                // Rotate

                if (col == 2) {

                    right.offerFirst(left.pollFirst());
                    left.offerLast(right.pollLast());

                } else {

                    middle.peekFirst().offerFirst(left.pollFirst());

                    right.offerFirst(middle.peekFirst().pollLast());

                    middle.peekLast().offerLast(right.pollLast());

                    left.offerLast(middle.peekLast().pollFirst());
                }
            }
        }

        int[][] answer = new int[row][col];

        for (int i = 0; i < row; i++) {

            answer[i][0] = left.pollFirst();

            Deque<Integer> mid = middle.pollFirst();

            for (int j = 1; j < col - 1; j++) {
                answer[i][j] = mid.pollFirst();
            }

            answer[i][col - 1] = right.pollFirst();
        }

        return answer;
    }
}