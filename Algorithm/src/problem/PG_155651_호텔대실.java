package problem;

import java.util.Arrays;

public class PG_155651_호텔대실 {
    public int solution(String[][] book_time) {
        int n = book_time.length;

        // 이벤트를 (시각, 종류) 쌍으로 저장. 종류: +1 입실, -1 청소완료(퇴실)
        int[][] events = new int[n * 2][2];

        for (int i = 0; i < n; i++) {
            int start = toMinutes(book_time[i][0]);
            int end = toMinutes(book_time[i][1]) + 10; // 청소 시간 10분 추가

            events[i * 2][0] = start;
            events[i * 2][1] = 1; // 입실

            events[i * 2 + 1][0] = end;
            events[i * 2 + 1][1] = -1; // 청소 완료(퇴실)
        }

        // 시각 오름차순 정렬, 같은 시각이면 퇴실(-1)을 입실(+1)보다 먼저 처리
        Arrays.sort(events, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });

        int count = 0;
        int maxRoom = 0;

        // 이벤트를 순서대로 누적하며 최대 동시 사용 객실 수 추적
        for (int[] event : events) {
            count += event[1];
            maxRoom = Math.max(maxRoom, count);
        }

        return maxRoom;
    }

    // "HH:MM" 형태의 문자열을 분 단위 정수로 변환
    static int toMinutes(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        return hour * 60 + minute;
    }
}