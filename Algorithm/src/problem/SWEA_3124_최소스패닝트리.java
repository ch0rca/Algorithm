package problem;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class SWEA_3124_최소스패닝트리 {
    static class Edge implements Comparable<Edge> {
        int from, to, w;
        public Edge(int from, int to, int w) {
            this.from = from;
            this.to = to;
            this.w = w;
        }
        public int compareTo(Edge o) {
            return Integer.compare(this.w, o.w);
        }
    }

    static int[] parents;

    public static void make(int V) {
        for (int i = 0; i <= V; i++) {
            parents[i] = i;
        }
    }

    public static int find(int a) {
        if (parents[a] == a) return a;
        return parents[a] = find(parents[a]);
    }

    public static boolean union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);
        if (aRoot == bRoot) return false;
        parents[bRoot] = aRoot;
        return true;
    }

    public static void main(String args[]) throws Exception {

    	System.setIn(new FileInputStream("src/problem/test/swea_3124.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            parents = new int[V + 1];
            Edge[] edgeList = new Edge[E];

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine().trim());
                int from   = Integer.parseInt(st.nextToken());
                int to     = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());
                edgeList[i] = new Edge(from, to, weight);
            }

            make(V);
            Arrays.sort(edgeList);

            long result = 0;
            int count = 0;

            for (Edge edge : edgeList) {
                if (union(edge.from, edge.to)) {
                    result += edge.w;
                    count++;
                    if (count == V - 1) break;
                }
            }

            sb.append("#").append(test_case).append(" ").append(result).append("\n");
        }

        System.out.print(sb);
    }
}