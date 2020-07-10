package com.magusta.dealseeker.database;

import java.sql.*;

public class CheckTables {


    private static final String[] tables = {"search_config", "offers"};

    public static boolean checkTables(){
        boolean tablesExisted = false;
        try(Connection connection = DataSource.getConnection()) {
            for (String table : tables) {
                try (ResultSet rs = connection.getMetaData().getTables(null, null, table, null)) {
                    if (!rs.next()) {
                        createTable(table);
                        System.out.println("Table \"" + table + "\" created.");
                    } else {
                        tablesExisted = true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tablesExisted;
    }

    private static void createTable(String tableName) throws SQLException {
        try(Connection connection = DataSource.getConnection()){
            try(Statement st = connection.createStatement()){
                if (tableName.equals(tables[1])){
                st.executeUpdate("CREATE TABLE " + tableName +
                        "(offer_id BIGINT," +
                        "title VARCHAR(50), " +
                        "price VARCHAR(20), " +
                        "url VARCHAR(300), " +
                        "search_id INT, " +
                        "PRIMARY KEY (offer_id))");
                }else{
                    st.executeUpdate("CREATE TABLE " + tableName +
                            "(search_id INT AUTO_INCREMENT, " +
                            "search_url VARCHAR(300)," +
                            "create_time TIMESTAMP, " +
                            "search_name VARCHAR(100), " +
                            "PRIMARY KEY (search_id))");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
