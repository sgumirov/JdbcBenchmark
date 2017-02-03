package com.gumirov.shamil.jdbc.benchmark;

import org.postgresql.pljava.annotation.*;

import java.sql.*;
import java.util.Random;

/**
 * Created by phoenix on 2/3/17.
 */
public class Benchmark {
  static final String insert = "INSERT INTO %s VALUES(?,?)";
  static final String select = "SELECT id, val FROM %s WHERE id = ?";
  static final String delete = "DELETE FROM %s";
  
  //@Mock
  public static String url = "jdbc:default:connection";
  
  @Function
  public static String fillTable(String table, int start, int end) throws SQLException {
    long t = System.currentTimeMillis();

    Connection con = DriverManager.getConnection(url);

    PreparedStatement pst = con.prepareStatement(String.format(insert, table));

    for (int i = start; i < end; ++i){
      pst.setInt(1, i);
      pst.setInt(2, i);
      pst.executeUpdate();
    }
    
    pst.close();
    con.close();

    t = System.currentTimeMillis() - t;

    return "Time used: "+t+" ms";
  }

  @Function
  public static String randomReads(String table, int number, int max) throws SQLException {
    long t = System.currentTimeMillis();

    Connection con = DriverManager.getConnection(url);

    PreparedStatement pst = con.prepareStatement(String.format(select, table));
    Random r = new Random(System.currentTimeMillis());
    int avg = 0;

    for (int i = 0; i < number; ++i){
      int id = r.nextInt(max-1)+1;
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();
      if (!rs.next()) throw new RuntimeException("No data at id="+id);
      avg = (avg + rs.getInt(2))>>1;
    }

    pst.close();
    con.close();
    
    t = System.currentTimeMillis() - t;

    return "Avg = "+avg+". Time used: "+t+" ms";
  }

  @Function
  public static String cleanTable(String table) throws SQLException {
    long t = System.currentTimeMillis();

    Connection con = DriverManager.getConnection(url);

    Statement s = con.createStatement();
    s.executeUpdate(String.format(delete, table));
    
    s.close();
    con.close();

    t = System.currentTimeMillis() - t;

    return "Time used: "+t+" ms";
  }
}
