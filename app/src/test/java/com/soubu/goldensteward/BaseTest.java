package com.soubu.goldensteward;

import org.junit.Test;

/**
 * 作者：余天然 on 2016/12/13 上午11:33
 */
public class BaseTest {

    @Test
    public void test() {
        int a = 3 & 5 & 7;
        System.out.println("a=" + a);
    }

    @Test
    public void test2() {
        int a = 2 & 4 & 6;
        System.out.println("a=" + a);
    }

    @Test
    public void test3() {
        int[] arr = {3, 5, 7, 0};
        int a = calc(arr);
        System.out.println("a=" + a);
    }

    @Test
    public void test5() {
        System.out.println("a=" + (7 ^ 5));
    }

    private int calc(int[] arr) {
        int result = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == 0) continue;
            result = result & arr[i];
            System.out.println("integer=" + arr[i] + "\tresult=" + result);
        }
        return result;
    }
}
