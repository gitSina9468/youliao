package com.seahorse.youliao.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: sql 定时执行
 * @author: Mr.Song
 * @create: 2020-02-29 10:57
 **/
@Component
public class SqlJob {

    private static Logger logger = LoggerFactory.getLogger(SqlJob.class);

    /**
     * 批量执行sql语句
     * 每天6点执行
     * @throws SQLException
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public static void execute() throws SQLException {
        Statement stmt = null;
        List<String> sqlList = new ArrayList<String>();
        Connection conn = getConnection();
        try {
            sqlList = loadSql();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            for (String sql : sqlList) {
                logger.info(sql);
                stmt.addBatch(sql);
            }

            int[] rows = stmt.executeBatch();
            logger.info("Row count:" + Arrays.toString(rows));
            conn.commit();
            logger.info("数据更新成功");
        } catch (Exception e) {
            logger.error("数据定时更新异常:"+e.toString());
            conn.rollback();
        }finally{
            stmt.close();
            conn.close();
        }

    }

    /**
     * 读取sql文件,获取sql语句
     * 返回所有sql语句的list集合
     * @return
     * @throws Exception
     */
    private static List<String> loadSql() throws Exception {

        List<String> sqlList = new ArrayList<>();
        ClassPathResource classPathResource = new ClassPathResource("static/json/youliao-data.sql");
        InputStream inputStream =classPathResource.getInputStream();

        InputStreamReader InputStreamReader = new InputStreamReader(inputStream,"UTF-8");
        StringBuffer sqlSb = new StringBuffer();
        char[] buff = new char[12];
        int byteRead = 0;
        while ((byteRead = InputStreamReader.read(buff)) != -1) {
            sqlSb.append(new String(buff, 0, byteRead));
        }
        /*
         * windows下换行是/r/n，Linux下是/n，
         * 此处需要根据导出的sql文件进行具体的处理，我在处理的时候
         * 也遇到了很多的问题，如果我这个不行可以在网上找找别的解析方法
         * */
        String sqlArr[] = sqlSb.toString().split("(;\\s*\\rr\\n)|(;\\s*\\n)");
        for(int i = 0; i<sqlArr.length; i++) {
            String sql = sqlArr[i].replaceAll("--.*", "").trim();
            if(!"".equals(sql)) {
                sqlList.add(sql);
            }
        }
        InputStreamReader.close();
        inputStream.close();
        return sqlList;
    }

    /**
     * 获取sql连接
     * @return
     */
    private static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://172.16.8.187:3307/youliao-security", "root", "khan1128$");
            if(!conn.isClosed()) {
                logger.info("数据库连接成功!");
            }
        } catch (Exception e) {
            logger.error("数据库连接异常:"+e.toString());
        }
        return conn;
    }


    public static void main(String[] args)throws Exception {
        execute();
    }
}
