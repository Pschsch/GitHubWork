package ru.pschsch.pschschapps.githubwork.ExtraUtils;

import java.util.HashMap;
import java.util.Map;

public class JavaClass {

    public static void main(String[] args) {
        String first = "asasasasas";
        String second = "bsbsbsbsbs";
        char[] s1 = first.toCharArray();
        char[] s2 = second.toCharArray();
        Map<Character, Integer> numberOfChars1 = numberOfChars(s1);
        Map<Character, Integer> numberOfChars2 = numberOfChars(s2);
        StringBuilder sb = new StringBuilder();
        for(int a=97; a<123; a++){
            int countMap1 = numberOfChars1.get((char)a);
            int countMap2 = numberOfChars2.get((char)a);
            if(countMap1<=1&&countMap2<=1) continue;
            else if(countMap1>countMap2){
                appender(sb, numberOfChars1,"1:",(char)a);
            }
            else if(countMap1<countMap2){
                appender(sb, numberOfChars2, "2:",(char)a);
            }
            else if(countMap1==countMap2){
                appender(sb, numberOfChars2,"=:",(char)a);
            }
        }
        System.out.println(sb.toString());
    }

     private static Map<Character, Integer> numberOfChars(char[] arr) {
        Map<Character, Integer> numberOfCharsMap = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            int count = 0;
            for (char ch : arr) {
                if (ch == a) {
                    count++;
                }
            }
            numberOfCharsMap.put((char) a, count);
        }
        return numberOfCharsMap;
    }

    private static void appender(StringBuilder sb, Map<Character,Integer> map, String appending, char fromFor){
        sb.append(appending);
        for(int i = 0; i<map.get(fromFor);i++){
            sb.append(fromFor);
        }
        sb.append("/");
    }

}
