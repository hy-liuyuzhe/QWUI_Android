package com.qwuiteam.project.algorithm;

public class AlgorithmCode {

    public static void main(String[] args) {
//        String[] strs  = {"dog","racecar","car"};
        String[] strs = {"1flow", "flower flower ", "flight", "flexiblef"};
//        String prefix = longestCommonPrefix(strs);
//        String prefix = longestCommonPrefix2(strs, 0, strs.length - 1);
//        String prefix = longestCommonPrefix2(strs);
//        String prefix = longestCommonPrefix3(strs);

        boolean r = palindrome(1221);
        System.out.println("algorithm:" + r);
    }

    ///////////////////////////////////////////////////////////////////////////
    private static boolean palindrome(int x) {
        if (x < 0) return false;
        int curt = 0;
        int num = x;
        while (num > curt) {
            curt = curt * 10 + num % 10;
            num = num / 10;
        }
        return num == curt || num == curt / 10;//当是奇数时需要除以10来判断是否一样
    }
    ///////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////
    //拿第一个单词的每一列和每个单词的每一列比较,
    //如果完全一致就返回第一个单词，如果不一致就返回当前匹配的字符前缀
    private static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        int firstWordN = strs[0].length();
        int wordNumber = strs.length;
        for (int i = 0; i < firstWordN; i++) {
            char letter = strs[0].charAt(i);
            for (int j = 1; j < wordNumber; j++) {
                if (i == strs[j].length()) {
                    //当前这个单词已经没有可匹配的字符了
                    return strs[0].substring(0, i);
                }
                if (strs[j].charAt(i) != letter) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    //分治， 找到中间节点，分成2组，分别进行判断公共前缀，递归直到不能再分

    /**
     * 当数量是奇数比如3个，树左边会分0，1，右边只有一个就是2
     * 当0，1merge后得出结果和2进行merge。
     */
    private static String longestCommonPrefix2(String[] strs, int start, int end) {
        if (start == end) {
            return strs[start];
        } else {
            int mid = start + ((end - start) / 2);
            System.out.println("mid: " + mid);
            String lcpLeft = longestCommonPrefix2(strs, start, mid);
            String lcpRight = longestCommonPrefix2(strs, mid + 1, end);
            return findLongestCommonPrefix(lcpLeft, lcpRight);
        }
    }

    private static String findLongestCommonPrefix(String lcpLeft, String lcpRight) {
        System.out.println("lcpLeft: " + lcpLeft + ", lcpRight: " + lcpRight);
        int minN = Math.min(lcpLeft.length(), lcpRight.length());
        for (int i = 0; i < minN; i++) {
            if (lcpLeft.charAt(i) != lcpRight.charAt(i)) {
                return lcpLeft.substring(0, i);
            }
        }
        return lcpLeft.substring(0, minN);
    }

    private static String longestCommonPrefix2(String[] strs) {
        //min length
        int min = Integer.MAX_VALUE;
        for (String s : strs) {
            min = Math.min(min, s.length());
        }
        //二分查找mid，尝试去匹配
        int low = 0, high = min;
        //一旦匹配失败就返回
        while (low < high) {
            int mid = (high - low + 1) / 2 + low;
            if (isCommonPrefix(strs, mid)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return strs[0].substring(0, low);
    }

    private static String longestCommonPrefix3(String[] strs) {
//        第一个单词
//        分别和每一个单词匹配，如果没找到就拆剪一个字
        String prefix = strs[0];
        int n = strs.length;
        for (int i = 1; i < n; i++) {
            String word = strs[i];
            //完全匹配时会返回0
            while (word.indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }

    private static boolean isCommonPrefix(String[] strs, int length) {
        String str0 = strs[0].substring(0, length);
        int n = strs.length;
        for (int i = 1; i < n; i++) {
            String str = strs[i];
            for (int j = 0; j < length; j++) {
                if (str0.charAt(j) != str.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////

}
