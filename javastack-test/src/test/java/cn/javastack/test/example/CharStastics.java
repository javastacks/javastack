package cn.javastack.test.example;

/**
 * 字符个数统计
 * 更多请关注公众号：Java技术栈
 */
public class CharStastics {

    public static void main(String[] args) {
        String str = "19ds$^sljd　2341@06qdj8^&*Q e0sdcka2385 -1!~er,s92";
        char[] chars = str.toCharArray();

        int englishCount = 0;
        int spaceCount = 0;
        int digitCount = 0;
        int otherCount = 0;

        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(chars[i])) {
                englishCount++;
                continue;
            }
            if (Character.isDigit(chars[i])) {
                digitCount++;
                continue;
            }
            if (Character.isSpaceChar(chars[i])) {
                spaceCount++;
                continue;
            }
            otherCount++;
        }

        System.out.println("英语字母个数为：" + englishCount);
        System.out.println("数字个数为：" + digitCount);
        System.out.println("空格个数为：" + spaceCount);
        System.out.println("其他字符个数为：" + otherCount);
    }

}
