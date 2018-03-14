package com.zhaoyao.miaomiao;

import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        String url = "http://p55p7zgj6.bkt.clouddn.com/%E8%BF%87%E6%BB%A4_1.jpg";
        String decode = URLDecoder.decode(url);
        System.out.println(decode);
        String encode = "http://p55p7zgj6.bkt.clouddn.com/" + URLEncoder.encode(decode.replace("http://p55p7zgj6.bkt.clouddn.com/", ""));
        System.out.println(encode);
    }

}