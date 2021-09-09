package main.stringUtils;

import java.util.StringTokenizer;

public class MyStringTokenizer {

    /**
     * 复杂的parse string功能的封装? 还需要多尝试
     */
    public static void StringTokenizerBasic() {
        StringTokenizer stringTokenizer = new StringTokenizer("Hello World");

        while(stringTokenizer.hasMoreTokens()) {
            System.out.println(stringTokenizer.nextToken());
        }
    }
}
