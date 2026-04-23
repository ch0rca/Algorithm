package problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

//https://jungol.co.kr/problem/2247
public class Jungol_2247_도서관 {
	
    public static void main(String[] args) throws IOException {
    	
    	System.setIn(new FileInputStream("src/problem/test/jungol_2247.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        List<long[]> events = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long s = Long.parseLong(st.nextToken());
            long e = Long.parseLong(st.nextToken());
            events.add(new long[]{s, 1});
            events.add(new long[]{e, -1});
        }

        // 시간 오름차순, 같은 시간이면 퇴장(-1)을 먼저 처리
        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0]) return Long.compare(a[0], b[0]);
            return Long.compare(a[1], b[1]);
        });

        long maxStay = 0;
        long maxEmpty = 0;
        long count = 0;
        long segStart = -1;

        int i = 0;
        while (i < events.size()) {
            long time = events.get(i)[0];
            boolean wasEmpty = (count == 0);

            // 같은 시각의 모든 이벤트를 한 번에 처리
            while (i < events.size() && events.get(i)[0] == time) {
                count += events.get(i)[1];
                i++;
            }

            if (wasEmpty && count > 0) {
                // 비어있다가 학생이 생긴 경우: 비어있는 구간 길이 갱신
                if (segStart != -1) {
                    maxEmpty = Math.max(maxEmpty, time - segStart);
                }
                segStart = time;
            } else if (!wasEmpty && count == 0) {
                // 학생이 있다가 모두 나간 경우: 머무는 구간 길이 갱신
                maxStay = Math.max(maxStay, time - segStart);
                segStart = time;
            }
        }

        System.out.println(maxStay + " " + maxEmpty);
    }
}