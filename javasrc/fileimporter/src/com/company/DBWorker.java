package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class DBWorker {
    private static final String url = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
    private static final String user = "root";
    private static final String password = "q3653223";

    public static boolean insert(String localPath,String remotePath, long size, LocalDateTime start_time, LocalDateTime end_time)
    {
        Connection connection;
        Statement stmt;
        try {
            connection = DriverManager.getConnection(url, user, password);
            stmt = connection.createStatement();
            String req= "INSERT INTO mydb.files(local_name,remote_name,size,start_download,end_download) VALUES('%s','%s',%s,'%s','%s')";
            req = String.format(req, localPath, remotePath, Long.toString(size), start_time.toString(), end_time.toString());
            stmt.execute(req);
            connection.close();
            stmt.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
