package cn.javastack.test.example;

import org.junit.jupiter.api.Test;

/**
 * 表白代码
 * 来源微信公众号：Java技术栈
 */
public class ExpressingLove {

    /**
     * 打印心型我爱你
     * 来源微信公众号：Java技术栈
     */
    @Test
    public void printHeart() {
        int[] arrays = {0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 4, 5, 2, 9, 4, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrays.length; i++) {
            if (i % 7 == 0) {
                stringBuffer.append("\n");
            }

            if (arrays[i] == 0 || arrays[i] == 4) {
                stringBuffer.append("    ");
            } else if (arrays[i] == 5) {
                stringBuffer.append(" I ");
            } else if (arrays[i] == 2) {
                stringBuffer.append(" love ");
            } else if (arrays[i] == 9) {
                stringBuffer.append("you");
            } else {
                stringBuffer.append("  * ");
            }
        }
        System.out.println(stringBuffer);
    }

}
 