package com.magusta.dealseeker.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CheckTablesTest {

    @BeforeAll
    static void dropTables() {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("DROP TABLE IF EXISTS searching_config, offers")){
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void checkTablesTest_tablesNotExist() {
        assertFalse(CheckTables.checkTables());
    }

    @Test
    void checkTablesTest_tablesExist() {
        assertTrue(CheckTables.checkTables());
    }
}