package problem;

import java.util.PriorityQueue;
import java.util.Comparator;

public class CT_현대오토에버1 {
	public long solution(int[] dataSize, int[] processingTime) {
        int n = dataSize.length;

        PriorityQueue<Long> freeServers = new PriorityQueue<>();

        // 사용 중인 서버들을 [해제시각, 누적처리량] 형태로 저장
        PriorityQueue<long[]> busyServers = new PriorityQueue<>(
            Comparator.comparingLong(a -> a[0])
        );

        long maxLoad = 0;

        for (int i = 0; i < n; i++) {

            // 현재 시각 i 이하로 해제되는 서버들을 free
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= i) {
                long[] server = busyServers.poll();
                freeServers.offer(server[1]);
            }

            long newLoad;

            if (!freeServers.isEmpty()) {
                // 사용 가능한 서버 중 누적 처리량이 가장 작은 서버 선택
                long currentLoad = freeServers.poll();
                newLoad = currentLoad + dataSize[i];
            } else {
                newLoad = dataSize[i];
            }

            long releaseTime = i + processingTime[i];
            busyServers.offer(new long[]{releaseTime, newLoad});

            maxLoad = Math.max(maxLoad, newLoad);
        }

        return maxLoad;
    }
}
