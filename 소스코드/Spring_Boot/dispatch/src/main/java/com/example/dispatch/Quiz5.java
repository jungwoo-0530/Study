package com.example.dispatch;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.of;

/**
 * fileName     : Quiz5
 * author       : jungwoo
 * description  :
 */
public class Quiz5 {

  private static final String[] STRING_ARR = {"aaa", "bb", "c", "dddd"};




//  public static void main(String[] args) {
//
//    int sum = stream(STRING_ARR)
//        .mapToInt(s -> s.length())
//        .sum();
//    System.out.println(sum);
//
//    int i = stream(STRING_ARR)
//        .mapToInt(s -> s.length())
//        .max()
//        .orElse(0);
//    System.out.println(i);
//
//
//
//
//
//
//  }

  public static void main(String[] args) {

    Stream<Student> studentStream = Stream.of(
        new Student("이자바", 3, 300),
        new Student("김자바", 1, 200),
        new Student("안자바", 2, 100),
        new Student("박자바", 2, 150),
        new Student("소자바", 1, 200),
        new Student("나자바", 3, 290),
        new Student("감자바", 3, 180)
    );

    studentStream.sorted(Comparator.comparing(Student::getBan) // 반별 정렬
        .thenComparing(Comparator.naturalOrder())) // 기본 정렬(Student에서 내가 정의한 compareTo())
        .forEach(System.out::println);
  }


  static class Student implements Comparable<Student> {

    String name;
    int ban;
    int totalScore;

    public Student(String name, int ban, int totalScore) {
      this.name = name;
      this.ban = ban;
      this.totalScore = totalScore;
    }
    public String getName() {return name;}

    public int getBan() {return ban;}

    public int getTotalScore() {return totalScore;}



    public String toString(){
      return String.format("[%s, %d, %d]", name, ban, totalScore);
    }

    // 총점 내림차순을 기본 정렬로 한다.
    public int compareTo(Student s) {
      return s.totalScore - this.totalScore;
    }

  }


}
