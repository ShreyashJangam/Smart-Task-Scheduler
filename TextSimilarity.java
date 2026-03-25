package org.studyeasy;

import java.util.*;

public class TextSimilarity {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter first text:");
        String text1 = sc.nextLine();

        System.out.println("Enter second text:");
        String text2 = sc.nextLine();

        double similarity = calculateSimilarity(text1, text2);

        System.out.println("Similarity: " + (similarity * 100) + "%");
    }

    static double calculateSimilarity(String t1, String t2) {

        Set<String> set1 = new HashSet<>(Arrays.asList(t1.toLowerCase().split(" ")));
        Set<String> set2 = new HashSet<>(Arrays.asList(t2.toLowerCase().split(" ")));

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }
}