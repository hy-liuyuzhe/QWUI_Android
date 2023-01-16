package com.qwuiteam.project.algorithm;

import com.blankj.utilcode.util.TimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlgorithmCode {

    public static void main(String[] args) {
//        String[] strs  = {"dog","racecar","car"};
//        String[] strs = {"1flow", "flower flower ", "flight", "flexiblef"};
//        String prefix = longestCommonPrefix(strs);
//        String prefix = longestCommonPrefix2(strs, 0, strs.length - 1);
//        String prefix = longestCommonPrefix2(strs);
//        String prefix = longestCommonPrefix3(strs);

//        boolean r = palindrome(1221);
//        twoNumbersMain();
//        System.out.println("algorithm:" + addStrings("51189", "967895"));

//        System.out.println("algorithm:" + multiply("123", "456"));

//        long time = System.currentTimeMillis();
//        System.out.println("正确的转换time: " + TimeUtils.millis2String(time));
//        System.out.println("以前方法转换time: " + getYearMonthDayHourMinuteSecond(time));

        System.out.println(sum(new int[]{3,0,-2,-1,1,2}));
//        System.out.println(sum(new int[]{1,0,-1}));
//        long start = System.currentTimeMillis();
//        System.out.println(sum(new int[]{-5,14,1,-2,11,11,-10,3,-6,0,3,-4,-9,-13,-8,-7,9,8,-7,11,12,-7,4,-7,-1,-5,13,1,-2,8,-13,0,-1,3,13,-13,-1,10,5,1,-13,-15,12,-7,-13,-11,-7,3,13,1,0,2,1,11,10,8,-8,1,-14,-3,-6,-12,12,0,6,2,2,-9,-3,14,-1,-9,14,-4,-1,8,-8,7,-4,12,-14,3,-9,2,0,-13,-13,-1,3,-12,11,4,-9,8,11,5,-5,-10,3,-1,-11,-13,5,-12,-10,11,11,-3,-5,14,-13,-4,-5,-7,6,2,-13,0,8,-3,4,4,-14,2}));
//        System.out.println("time: " + (System.currentTimeMillis() - start));
    }


//    Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
//    such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
//    Notice that the solution set must not contain duplicate triplets.

//    Input: nums = [-1,0,1,2,-1,-4]
//    Output: [[-1,-1,2],[-1,0,1]]
//    Explanation:
//    nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
//    nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
//    nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
//    The distinct triplets are [-1,0,1] and [-1,-1,2].
//    Notice that the order of the output and the order of the triplets does not matter.
//

    public static List<List<Integer>> sum(int[] nums) {
//        List<List<Integer>> numList = new ArrayList<>();
//        ArrayList<Integer> childNum = null;
////        Arrays.sort(ints);
//        int n = ints.length;
//        for (int i = 0; i < n; i++) {
//            if (i > 0 && ints[i] == ints[i - 1]) {
//                continue;
//            }
//            int one = ints[i];
//            if(one>0)break;
//            for (int j = i+1; j < n; j++) {
//                int two = ints[j];
//                if (ints[j] == ints[j - 1] && two!=0) {
//                    continue;
//                }
//                for (int k = j+1; k < n; k++) {
//                    int three = ints[k];
//                    if (ints[k] == ints[k - 1] && three!=0) {
//                        continue;
//                    }
//                    if (one + two + three == 0 && i != j && j != k ) {
//                        childNum = new ArrayList<>();
//                        childNum.add(one);
//                        childNum.add(two);
//                        childNum.add(three);
//                        if (isNewData(numList,childNum)){
//                            numList.add(childNum);
//                            break;
//                        }
//                    }
//                }
//            }
//        }


//        int n = nums.length;
//        Arrays.sort(nums);
//        List<List<Integer>> ans = new ArrayList<List<Integer>>();
//        // 枚举 a
//        for (int first = 0; first < n; ++first) {
//            // 需要和上一次枚举的数不相同
//            if (first > 0 && nums[first] == nums[first - 1]) {
//                continue;
//            }
//            // c 对应的指针初始指向数组的最右端
//            int third = n - 1;
//            int target = -nums[first];
//            // 枚举 b
//            for (int second = first + 1; second < n; ++second) {
//                // 需要和上一次枚举的数不相同
//                if (second > first + 1 && nums[second] == nums[second - 1]) {
//                    continue;
//                }
//                // 需要保证 b 的指针在 c 的指针的左侧
//                while (second < third && nums[second] + nums[third] > target) {
//                    --third;
//                }
//                // 如果指针重合，随着 b 后续的增加
//                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了，可以退出循环
//                if (second == third) {
//                    break;
//                }
//                if (nums[second] + nums[third] == target) {
//                    List<Integer> list = new ArrayList<Integer>();
//                    list.add(nums[first]);
//                    list.add(nums[second]);
//                    list.add(nums[third]);
//                    ans.add(list);
//                }
//            }
//        }
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;
        for (int first = 0; first < n; first++) {
            if (first > 0 && nums[first] == nums[first-1]){
                continue;
            }
            int third = n-1;
            int target = -nums[first]; // a+b+c = 0 , b+c = -a
            for (int second = first+1; second < n; second++) {
                if (second > first +1 && nums[second] ==nums[second-1]){
                    continue;
                }
                //third 的值太大，还需要向左移动
                while (second<third && nums[second]+nums[third]>target){
                    --third;
                }

                if (second == third)break;

                if (nums[second] + nums[third] == target){
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

    private static boolean isNewData(List<List<Integer>> numList, ArrayList<Integer> childNum) {
        boolean isNew = true;
        for (int i = 0; i < numList.size(); i++) {
            if (listEqualsIgnoreOrder(numList.get(i),childNum)){
                return false;
            }
        }
        return isNew;
    }


    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public static String getYearMonthDayHourMinuteSecond(long timeMillis) {
        int timezone = 8; // 时区
        long totalSeconds = timeMillis / 1000;
        totalSeconds += 60 * 60 * timezone;
        int second = (int) (totalSeconds % 60);// 秒
        long totalMinutes = totalSeconds / 60;
        int minute = (int) (totalMinutes % 60);// 分
        long totalHours = totalMinutes / 60;
        int hour = (int) (totalHours % 24);// 时
        int totalDays = (int) (totalHours / 24);
        int _year = 1970;
        int year = _year + totalDays / 366;
        int month = 1;
        int day = 1;
        int diffDays;
        boolean leapYear;
        while (true) {
            int diff = (year - _year) * 365;
            diff += (year - 1) / 4 - (_year - 1) / 4;
            diff -= ((year - 1) / 100 - (_year - 1) / 100);
            diff += (year - 1) / 400 - (_year - 1) / 400;
            diffDays = totalDays - diff;
            leapYear = (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
            if (!leapYear && diffDays < 365 || leapYear && diffDays < 366) {
                break;
            } else {
                year++;
            }
        }

        int[] monthDays;
        if (diffDays >= 59 && leapYear) {
            monthDays = new int[]{-1, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
        } else {
            monthDays = new int[]{-1, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        }
        for (int i = monthDays.length - 1; i >= 1; i--) {
            if (diffDays >= monthDays[i]) {
                month = i;
                day = diffDays - monthDays[i] + 1;
                break;
            }
        }
        String hours;
        String minutes;
        String seconds;
        if (hour < 10) {
            hours = "0" + hour;
        } else {
            hours = "" + hour;
        }
        if (minute < 10) {
            minutes = "0" + minute;
        } else {
            minutes = "" + minute;
        }
        if (second < 10) {
            seconds = "0" + second;
        } else {
            seconds = "" + second;
        }

//        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        return hours + ":" + minutes + ":" + seconds;
    }

    private static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int m = num1.length();
        int n = num2.length();
        int[] res = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int num = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j; //进位使用的索引
                int p2 = i + j + 1; //这次结果计算的结果存储位置

                int sum = num + res[p2];
                res[p2] = sum % 10;
                res[p1] += sum / 10;
            }
        }

        System.out.println("res: " + Arrays.toString(res));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            if (res[i] == 0 && i == 0) continue;
            result.append(res[i]);
        }
        return result.toString();
    }

    private static String addStrings(String num1, String num2) {
        StringBuilder res = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;
        while (i >= 0 || j >= 0) {
            int n1 = i >= 0 ? num1.charAt(i) - '0' : 0; // -'0' 操作是获取 char对应的数字
            int n2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int tmp = n1 + n2 + carry;
            carry = tmp / 10;
            res.append(tmp % 10);
            i--;
            j--;
        }
        if (carry > 0) res.append("1");
        return res.reverse().toString();
    }


    ///////////////////////////////////////////////////////////////////////////

    private static void twoNumbersMain() {
        ListNode l1 = new ListNode(2);
        ListNode node2 = new ListNode(4);
        l1.after(node2);
        ListNode node3 = new ListNode(9);
        node2.after(node3);
        ListNode node4 = new ListNode(9);
        node3.after(node4);


        ListNode l2 = new ListNode(5);
        ListNode node5 = new ListNode(6);
        l2.after(node5);
        ListNode node6 = new ListNode(4);
        node5.after(node6);

        l1.sequenceShow();
        System.out.println();
        l2.sequenceShow();
        System.out.println();
        ListNode listNode = addTwoNumbers(l1, l2);

        listNode.sequenceShow();
    }


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode head = dummy.next;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int sum = (n1 + n2) + carry;
            if (head == null) {
                dummy.next = head = new ListNode(sum % 10);
            } else {
                head.next = new ListNode(sum % 10);
                head = head.next;
            }
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        if (carry > 0) {
            head.next = new ListNode(carry);
        }
        return dummy.next;
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
