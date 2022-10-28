package com.example.dispatch;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * fileName     : Quiz4
 * author       : jungwoo
 * description  :
 */
public class Quiz4 {

  private static List<Transaction> transactions;

  static class Trader {
    private String name;
    private String city;

    public String getName() {
      return name;
    }

    public String getCity() {
      return city;
    }

    public Trader(String name, String city) {
      this.name = name;
      this.city = city;
    }
  }

  static class Transaction {
    private Trader trader;
    private int year;
    private int value;

    public Trader getTrader() {
      return trader;
    }

    public int getYear() {
      return year;
    }

    public int getValue() {
      return value;
    }

    public Transaction(Trader trader, int year, int value) {
      this.trader = trader;
      this.year = year;
      this.value = value;
    }
  }

    static void init() {
    Trader kyu = new Trader("Kyu", "Seoul");
    Trader ming = new Trader("Ming", "Gyeonggi");
    Trader hyuk = new Trader("Hyuk", "Seoul");
    Trader hwan = new Trader("Hwan", "Busan");

    transactions = Arrays.asList(
        new Transaction(kyu, 2019, 30000),
        new Transaction(kyu, 2020, 12000),
        new Transaction(ming, 2020, 40000),
        new Transaction(ming, 2020, 7100),
        new Transaction(hyuk, 2019, 5900),
        new Transaction(hwan, 2020, 4900)
    );
  }


  public static void main(String[] args) {

    init();

    System.out.println("-4.1");
    transactions.stream().map(Transaction::getValue)
        .sorted().forEach(System.out::println);


    System.out.println("-4.2");
    transactions.stream().map(t -> t.getTrader().getCity())
        .distinct().forEach(System.out::println);

    System.out.println("-4.3");
    transactions.stream().filter(t -> t.getTrader().getCity().equals("Seoul"))
        .map(t -> t.getTrader().getName()).sorted().forEach(System.out::println);

  }

}
