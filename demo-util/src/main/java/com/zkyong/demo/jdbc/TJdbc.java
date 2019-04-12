package com.zkyong.demo.jdbc;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TJdbc {
    public static void main(String[] args) {
        Connection con = null;
        try {

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            con = DriverManager.getConnection("jdbc:mysql://119.23.42.218:3306/test", "root", "f27b7abB");

            PreparedStatement ps = con.prepareStatement("insert into T_Group values (1001, 1000, 'hello', ?, 'lue')");
            ps.setDate(1, new Date(123456789));

            System.out.println(ps.executeUpdate() == 1 ? "111" : "222");
        } catch (SQLException e) { 
            e.printStackTrace();
        } finally {
            try {
                if(con != null && !con.isClosed()) {
                    con.close(); 
                    con = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
