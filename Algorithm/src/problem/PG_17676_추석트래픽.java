package problem;

public class PG_17676_추석트래픽 {

    static class Log {
        int start;
        int end;

        Log(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public int solution(String[] lines) {

        Log[] logs = new Log[lines.length];

        // 모든 로그를 시작/종료 시간(ms)으로 변환
        for (int i = 0; i < lines.length; i++) {
            logs[i] = parseLog(lines[i]);
        }

        int answer = 0;

        // 각 로그의 시작 시각과 종료 시각을
        // 1초 구간의 시작점으로 사용
        for (int i = 0; i < logs.length; i++) {

            // ① i번째 로그의 종료 시각을 기준으로 검사
            int start = logs[i].end;
            answer = Math.max(answer, countLogs(logs, start));

            // ② i번째 로그의 시작 시각을 기준으로 검사
            start = logs[i].start;
            answer = Math.max(answer, countLogs(logs, start));
        }

        return answer;
    }

    // 특정 시각부터 1초 동안 처리되는 요청 수
    private int countLogs(Log[] logs, int start) {

        int end = start + 999;
        int count = 0;

        for (Log log : logs) {

            // 1초 구간과 로그 처리 구간이 겹치는지 확인
            if (log.end >= start && log.start <= end) {
                count++;
            }
        }

        return count;
    }

    // 로그 하나를 파싱해서 시작/종료 시간을 밀리초로 변환
    private Log parseLog(String line) {

        String[] parts = line.split(" ");

        String time = parts[1];
        String duration = parts[2];

        int end = parseTime(time);

        // "2.0s" -> "2.0"
        double seconds = Double.parseDouble(
                duration.substring(0, duration.length() - 1)
        );

        int durationMs = (int) (seconds * 1000);

        // 시작시간과 종료시간 모두 포함
        int start = end - durationMs + 1;

        return new Log(start, end);
    }

    // "01:00:04.002" -> 밀리초
    private int parseTime(String time) {

        String[] parts = time.split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        String[] secondParts = parts[2].split("\\.");

        int second = Integer.parseInt(secondParts[0]);
        int millisecond = Integer.parseInt(secondParts[1]);

        return hour * 60 * 60 * 1000
                + minute * 60 * 1000
                + second * 1000
                + millisecond;
    }
}