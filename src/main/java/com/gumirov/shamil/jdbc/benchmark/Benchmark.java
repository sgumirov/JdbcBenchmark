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
  public static String fillTable(String tName, int start, int end) throws SQLException {
    System.out.println("fillTable(): "+start+", "+end);
    
    long t = System.currentTimeMillis();

    Connection con = DriverManager.getConnection(url);

    PreparedStatement pst = con.prepareStatement(String.format(insert, tName));

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
  public static String randomReads(String tName, int number, int max) throws SQLException {
    System.out.println("randomReads(): "+number+", "+max);
    
    long t = System.currentTimeMillis();

    Connection con = DriverManager.getConnection(url);

    PreparedStatement pst = con.prepareStatement(String.format(select, tName));
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
  public static String cleanTable(String tName) throws SQLException {

    System.out.println("cleanTable()");
    
    long t = System.currentTimeMillis();

    Connection con = DriverManager.getConnection(url);

    Statement s = con.createStatement();
    s.executeUpdate(String.format(delete, tName));
    
    s.close();
    con.close();

    t = System.currentTimeMillis() - t;

    return "Time used: "+t+" ms";
  }
}
