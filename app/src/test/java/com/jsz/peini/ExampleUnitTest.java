package com.jsz.peini;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    int g = 1000001;
    int p = 3000;
    int x = 200;
    int y = 34;
    int a = 0;
    int b = 0;
    int aa = 0;
    int bb = 0;

    @Test
    public void addition_isCorrect() throws Exception {
        a = g ^ x % p;

        b = g ^ y % p;

        aa = b ^ x;

        bb = a ^ y;

        System.out.println(aa + "===" + bb);
    }
}