package problem;

import java.util.ArrayList;
import java.util.List;

public class PG_150367_표현가능한이진트리 {

    static String binary;
    static int[] prefixSum; // prefixSum[i] = binary[0..i-1] 중 '1'의 개수

    public int[] solution(long[] numbers) {
        List<Integer> result = new ArrayList<>();

        for (long num : numbers) {
            binary = Long.toBinaryString(num);

            // 포화 이진트리 크기(2^k - 1)에 맞게 앞에 0 패딩
            int size = 1;
            while (size < binary.length()) {
                size = size * 2 + 1;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size - binary.length(); i++) {
                sb.append('0');
            }
            sb.append(binary);
            binary = sb.toString();

            prefixSum = new int[size + 1];
            for (int i = 0; i < size; i++) {
                prefixSum[i + 1] = prefixSum[i] + (binary.charAt(i) == '1' ? 1 : 0);
            }

            if (canRepresent(0, size - 1)) {
                result.add(1);
            } else {
                result.add(0);
            }
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    boolean canRepresent(int left, int right) {
        if (left > right) {
            return true;
        }

        int mid = (left + right) / 2;

        if (binary.charAt(mid) == '0') {
            // 구간 [left, mid-1]와 [mid+1, right]에 1이 있으면 불가능
            if (hasOne(left, mid - 1) || hasOne(mid + 1, right)) {
                return false;
            }
            return true;
        }

        return canRepresent(left, mid - 1) && canRepresent(mid + 1, right);
    }

    // 구간 합으로 O(1)에 '1' 존재 여부 판단
    boolean hasOne(int left, int right) {
        if (left > right) {
            return false;
        }
        // prefixSum[right+1] - prefixSum[left] = [left, right] 구간의 1의 개수
        return (prefixSum[right + 1] - prefixSum[left]) > 0;
    }
}