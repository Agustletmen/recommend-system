package utils;

import java.io.FileWriter;
import java.sql.*;

public class DatabaseToCSV {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis(); // 获取程序开始执行的时间戳

        //连接MySQL数据库
        String url = "jdbc:mysql://localhost:3306/cf";
        String username = "root";
        String password = "123";
        Connection conn = DriverManager.getConnection(url, username, password);

        //设置SQL查询语句
        String query = "SELECT * FROM test";

        //执行查询
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        //打开CSV文件
        FileWriter writer = new FileWriter("src/main/resources/output.csv");

        //将结果写入CSV文件
        while (rs.next()) {
            String userId = rs.getString(1);
            String itemId = rs.getString(2);
            String rating = rs.getString(3);
            String csvLine = userId + "," + itemId + "," + rating + "\n";
            writer.write(csvLine);
        }

        //关闭流和连接
        writer.flush();
        writer.close();
        rs.close();
        stmt.close();
        conn.close();

        long end = System.currentTimeMillis(); // 获取程序结束执行的时间戳
        long timeUsed = end - start; // 计算程序执行耗时
        System.out.println("程序运行时间：" + timeUsed + "毫秒");
    }
}

