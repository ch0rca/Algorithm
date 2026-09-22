package problem;

public class PG_161988_연속펄스수열합 {
    public long solution(int[] sequence) {
        long answer = 0;

        long sum1 = 0;
        long sum2 = 0;

        for (int i = 0; i < sequence.length; i++) {

            // [1, -1, 1, -1, ...]
            long value1 = (i % 2 == 0)
                    ? sequence[i]
                    : -sequence[i];

            // [-1, 1, -1, 1, ...]
            long value2 = -value1;

            sum1 = Math.max(value1, sum1 + value1);
            sum2 = Math.max(value2, sum2 + value2);

            answer = Math.max(answer, sum1);
            answer = Math.max(answer, sum2);
        }

        return answer;
    }
}
