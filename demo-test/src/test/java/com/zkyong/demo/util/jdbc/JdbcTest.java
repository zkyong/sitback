package com.zkyong.demo.util.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Jdbc测试类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:30:47
 */
public class JdbcTest {
    public static void main(String[] args) {
        Connection con = null;
        try {
            // 1. 加载驱动（Java6以上版本可以省略）
            // Class.forName("com.mysql.jdbc.Driver");
            // 2. 建立连接
            con = DriverManager.getConnection("jdbc:mysql://119.23.42.218:3306/test", "root",
                "f27b7abB");
            // 3. 创建语句对象
            PreparedStatement ps = con
                .prepareStatement("insert into T_Group values (1001, 1000, 'hello', ?, 'lue')");
            ps.setDate(1, new Date(123456789)); // 将SQL语句中第一个占位符换成字符串
            // 4. 发出SQL语句获得受影响行数
            System.out.println(ps.executeUpdate() == 1 ? "插入成功" : "插入失败");
        } catch (SQLException e) { // Java 7的多异常捕获
            e.printStackTrace();
        } finally { // 释放外部资源的代码都应当放在finally中保证其能够得到执行
            try {
                if (con != null && !con.isClosed()) {
                    con.close(); // 5. 释放数据库连接 
                    con = null; // 指示垃圾回收器可以回收该对象
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
