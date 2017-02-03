package com.gumirov.shamil.jdbc.benchmark;

import java.sql.SQLException;

/**
 * Created by phoenix on 2/3/17.
 */
public class LocalTestRun{
  public static void main(String[] args) throws SQLException {
//    mysqlTest();
    postgresTest();
  }

  private static void mysqlTest() throws SQLException {
    Benchmark.url = "jdbc:mysql://127.0.0.1:3306/test?user=root&password=gfhjkm";
    final String table = "test_table";
    final int MAX = 100;
    System.out.println("Starting test..");
    System.out.println(Benchmark.fillTable(table, 1, MAX));
    System.out.println(Benchmark.randomReads(table, 10000, MAX));
    Benchmark.cleanTable(table);
  }

  private static void postgresTest() throws SQLException {
    Benchmark.url = "jdbc:postgresql://192.168.3.100/test?user=user1&password=Qwewq12";
    final String table = "test_table";
    final int MAX = 100;
    System.out.println("Starting test..");
    System.out.println(Benchmark.fillTable(table, 1, MAX));
    System.out.println(Benchmark.randomReads(table, 10000, MAX));
    Benchmark.cleanTable(table);
  }
}
