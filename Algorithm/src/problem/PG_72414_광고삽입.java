package problem;

import java.util.Arrays;

public class PG_72414_광고삽입 {

    public String solution(String play_time, String adv_time, String[] logs) {
        int total = toSeconds(play_time);
        int advLen = toSeconds(adv_time);

        long[] cnt = new long[total + 1];
        for (String log : logs) {
            String[] parts = log.split("-");
            int start = toSeconds(parts[0]);
            int end = toSeconds(parts[1]);
            cnt[start] += 1;
            cnt[end] -= 1;  // end는 포함하지 않으므로 end에서 차감
        }

        // cnt 누적합 -> cnt[i] = i초 시점에 재생 중인 시청자 수
        for (int i = 1; i <= total; i++) {
            cnt[i] += cnt[i - 1];
        }

        // prefixSum[i] = 0초 ~ (i-1)초까지의 누적 재생시간 합
        // prefixSum[i+1] - prefixSum[i] = i초에 재생 중인 시청자 수
        long[] prefixSum = new long[total + 1];
        for (int i = 0; i < total; i++) {
            prefixSum[i + 1] = prefixSum[i] + cnt[i];
        }

        // 슬라이딩 윈도우: [i, i + advLen) 구간의 누적 재생시간 계산
        long maxSum = prefixSum[advLen] - prefixSum[0];
        int answerSec = 0;

        for (int i = 1; i <= total - advLen; i++) {
            long curSum = prefixSum[i + advLen] - prefixSum[i];
            // 같은 값이면 더 빠른 시작 시간을 유지하므로 strictly greater일 때만 갱신
            if (curSum > maxSum) {
                maxSum = curSum;
                answerSec = i;
            }
        }

        return toTimeString(answerSec);
    }

    private int toSeconds(String time) {
        String[] parts = time.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int s = Integer.parseInt(parts[2]);
        return h * 3600 + m * 60 + s;
    }

    private String toTimeString(int seconds) {
        int h = seconds / 3600;
        int m = (seconds % 3600) / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}