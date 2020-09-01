package com.zjq.codedemo.test;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static boolean dfs(int v,int[][] a, int n,int p,int mid,boolean[] used,int[] match) {
        for (int i = 0; i < n; i++) {
            if (a[v][i] >= p && a[v][i] <= p + mid && !used[i]) {
                used[i] = true;
                if (match[i] == -1 || dfs(v, a, n, p, mid, used, match)) {
                    match[i] = v;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hungary(boolean[] used,int[] match,int[][] a,int n,int p,int mid) {
        Arrays.fill(match, -1);
        for (int i = 0; i < n; i++) {
            Arrays.fill(used, false);
            if (!dfs(i, a, n, p, mid, used, match)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            int[][] a = new int[105][105];
            boolean[] used = new boolean[105];
            int[] match = new int[105];
            int n = scanner.nextInt();
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    a[j][k] = scanner.nextInt();
                    max = Integer.max(max, a[j][k]);
                    min = Integer.min(min, a[j][k]);
                }
            }
            int r = max - min;
            int l = 0;
            int res = 0;
            int mid = 0;
            while (l <= r) {
                mid = (l + r) / 2;
                boolean flag = false;
                for (int p = min;p + mid <= max; p++) {
                    if (hungary(used, match, a, n, p, mid)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    res = mid;
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            System.out.println(res);
        }
    }
}
