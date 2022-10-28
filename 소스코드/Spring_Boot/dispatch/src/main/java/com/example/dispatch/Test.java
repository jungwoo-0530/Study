package com.example.dispatch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * fileName     : Test
 * author       : jungwoo
 * description  :
 */
public class Test {

  static class Student{
    private int kor;
    private int eng;
    private int math;

    public Student(int kor, int eng, int math) {
      this.kor = kor;
      this.eng = eng;
      this.math = math;
    }

    public int getKor() {
      return kor;
    }

    public int getEng() {
      return eng;
    }

    public int getMath() {
      return math;
    }

  }

  public static void main(String[] args) {
    List<Student> students = Arrays.asList(
        new Student(80, 90, 75),
        new Student(70, 100, 75),
        new Student(85, 90, 85),
        new Student(80, 100, 90)
    );

    //Student의 요소들을 List<int>로 변경.
    //List<Student> -> Stream<Student> -> Stream<Stream<Integer>> -> Stream<Integer>
    // List<Integer>
    //모든 학생들의 점수 평균.
    //현재 2차원. 1차원으로 변경. 모든 학생 점수 나열.
    List<Integer> collect = students.stream() // 생성하기(Stream 객체로) Stream<Students>
        .map(Student::getMath) //Stream<Integer>
        .collect(Collectors.toList());// List<Integer>

    for (Integer a : collect) {
//      System.out.println(a);
    }

//    List<Integer> collect1 =
//        students.stream()
//        .flatMapToInt(student -> student.getMath(), )

//    students.stream()
//        .flatMapToInt(student ->
//            IntStream.of(student.getKor(), student.getEng(), student.getMath()))
//        .average()
//        .ifPresent(avg -> System.out.println(Math.round(avg * 10) / 10.0));

    //모든 학생의 점수
    //List<Student> -> List<Integer>
    List<Integer> collect1 = students.stream()
        .flatMapToInt(student ->
            IntStream.of(student.getKor(), student.getEng(), student.getMath()))
        .boxed()
        .collect((Collectors.toList()));

    for (Integer a : collect1) {
//      System.out.println(a);
    }

    //학생 별 점수 총 합
    //List<Student> -> List<Integer>
    List<Integer> collect2 = students.stream()
        .flatMapToInt(student ->
            IntStream.of(student.getKor() + student.getMath() + student.getEng()))
        .boxed()
        .collect(Collectors.toList());

    for (Integer a : collect2) {
      System.out.println(a);
    }

    //학생 별 평균
    //List<Student> -> List<Integer>
    List<Integer> collect3 = students.stream()
        .flatMapToInt(student ->
            IntStream.of((student.getKor() + student.getEng() + student.getMath()) / 3))
        .boxed()
        .collect(Collectors.toList());

    for (Integer a : collect3) {
      System.out.println(a);
    }
  }
}