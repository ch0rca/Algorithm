package problem;

import java.util.HashMap;
import java.util.Map;

public class PG_77486_다단계칫솔판매 {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int n = enroll.length;

        // 이름을 인덱스로 변환하기 위한 맵
        Map<String, Integer> idMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            idMap.put(enroll[i], i);
        }

        // parent[i] = i번 판매원의 추천인 인덱스, 추천인이 없으면 -1
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            String ref = referral[i];
            parent[i] = ref.equals("-") ? -1 : idMap.get(ref);
        }

        // 각 판매원이 최종적으로 얻는 이익
        long[] profit = new long[n];

        // 판매 기록을 하나씩 처리
        for (int i = 0; i < seller.length; i++) {
            int cur = idMap.get(seller[i]);
            long money = (long) amount[i] * 100;

            // 추천인 체인을 따라 위로 올라가며 이익을 분배
            while (cur != -1 && money > 0) {
                long share = money / 10; 
                profit[cur] += money - share; // 본인은 나머지를 가짐
                money = share; // 남은 10퍼센트는 추천인에게 전달
                cur = parent[cur]; // 다음은 추천인 차례
            }
        }

        int[] answer = new int[n];
        for (int i = 0; i < n; i++) {
            answer[i] = (int) profit[i];
        }

        return answer;
    }
}