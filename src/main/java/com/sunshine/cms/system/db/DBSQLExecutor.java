package com.sunshine.cms.system.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DESC：
 *  sql 执行器
 * @author handabing
 * DATE：2020/4/23
 * TIME：11:43 上午
 */
@Configuration
public class DBSQLExecutor {

    @Value("${spring.datasource.druid.url}")
    private String url;
    @Value("${spring.datasource.druid.username}")
    private String username;
    @Value("${spring.datasource.druid.password}")
    private String password;
    @Value("${spring.datasource.druid.driver-class-name}")
    private String driver;

    /**
     * @return 获取conn对象
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConn() throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }

    /**
     * 字段名列表
     *
     * @param conn
     * @param table
     * @return
     * @throws SQLException
     */
    public List<String> getFieldLsit(Connection conn, String table) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(table);
        pstmt.execute();
        List<String> columnList = new ArrayList<String>();    //存放字段
        ResultSetMetaData rsmd = pstmt.getMetaData();
        for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
            columnList.add(rsmd.getColumnName(i));            //把字段名放list里
        }
        return columnList;
    }
}
