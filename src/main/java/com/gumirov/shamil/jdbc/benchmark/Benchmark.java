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
  public static String fillTable(String tName, int startPos, int endPos) throws SQLException {
    System.out.println("fillTable(): "+startPos+", "+endPos);
    
    long t = System.currentTimeMillis();

    Connection con = DriverManager.getConnection(url);

    PreparedStatement pst = con.prepareStatement(String.format(insert, tName));

    for (int i = startPos; i < endPos; ++i){
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
  public static String randomReads(String tName, int totalNum, int maxVal) throws SQLException {
    System.out.println("randomReads(): "+totalNum+", "+maxVal);
    

    Connection con = DriverManager.getConnection(url);

    PreparedStatement pst = con.prepareStatement(String.format(select, tName));
    Random r = new Random(System.currentTimeMillis());
    int avg = 0;

    long t = System.currentTimeMillis();

    for (int i = 0; i < totalNum; ++i){
      int id = r.nextInt(maxVal-1)+1;
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();
      if (!rs.next()) throw new RuntimeException("No data at id="+id);
      avg = (avg + rs.getInt(2))/2;
      rs.close();
    }

    t = System.currentTimeMillis() - t;

    pst.close();
    con.close();

    return "Num = "+totalNum+". Avg = "+avg+". Time used: "+t+" ms";
  }

  @Function
  public static String randomReadsNoPrep(String tName, int totalNum, int maxVal) throws SQLException {
    System.out.println("randomReadsNoPrep(): "+totalNum+", "+maxVal);
    

    Connection con = DriverManager.getConnection(url);

    Statement st = con.createStatement();
    Random r = new Random(System.currentTimeMillis());
    int avg = 0;
    String q = String.format(select, tName);

    long t = System.currentTimeMillis();

    for (int i = 0; i < totalNum; ++i){
      int id = r.nextInt(maxVal-1)+1;
      ResultSet rs = st.executeQuery(q.replace("?", ""+id));
      if (!rs.next()) throw new RuntimeException("No data at id="+id);
      avg = (avg + rs.getInt(2))/2;
      rs.close();
    }

    t = System.currentTimeMillis() - t;

    st.close();
    con.close();

    return "Num = "+totalNum+" Avg = "+avg+". Time used: "+t+" ms";
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
