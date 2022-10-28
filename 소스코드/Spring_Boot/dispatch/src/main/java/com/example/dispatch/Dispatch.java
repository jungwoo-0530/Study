package com.example.dispatch;

import java.util.Arrays;
import java.util.List;

/**
 * fileName     : Dispatch
 * author       : jungwoo
 * description  :
 */
public class Dispatch {

  static abstract class Service{
    abstract void run();
  }

  static class MyService1 extends Service{
    @Override
    void run() {
      System.out.println("run1");
    }
  }
  static class MyService2 extends Service{
    @Override
    void run() {
      System.out.println("run2");
    }
  }

  public static void main(String[] args) {

    Service svc = new MyService1();

    svc.run();

    List<Service> serviceList = Arrays.asList(new MyService1(), new MyService2());

    serviceList.forEach(Service::run);

    List<String> list = Arrays.asList("a1", "b1", "c1", "c3");

    long result = list
        .stream()
        .filter(s -> s.startsWith("c"))
        .map(String::toUpperCase)
        .sorted()
        .count();

    System.out.println(result);

  }


}
