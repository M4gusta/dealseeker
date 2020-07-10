package com.magusta.dealseeker;


import com.magusta.dealseeker.dao.SearchDaoImpl;
import com.magusta.dealseeker.database.CheckTables;
import com.magusta.dealseeker.model.Search;
import com.magusta.dealseeker.scraper.Scraper;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        Scraper scraper = new Scraper();
        scraper.checkIfThereIsAnyDataInDB();
    }
}
