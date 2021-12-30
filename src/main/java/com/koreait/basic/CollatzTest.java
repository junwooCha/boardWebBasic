package com.koreait.basic;

public class CollatzTest {
    public static void main(String[] args) {
        int num = 6;
        int result = solution(num);
        System.out.printf("solution(%d) -> %d\n", num, result);

        num = 16;
        result = solution(num);
        System.out.printf("solution(%d) -> %d\n", num, result);

        System.out.println("----------------------------");
        num = 626331;
        result = solution(num);
        System.out.printf("solution(%d) -> %d\n", num, result);
    }

    public static int solution(int num) {
        int answer = 0;
        long val = num;
        while(val != 1) {
            if(answer > 500) {  return -1; }
            val = val % 2 == 0 ? val / 2 : val * 3 + 1;
            answer++;
        }
        return answer;
    }
}
