package com.magusta.dealseeker.scraper;

import com.magusta.dealseeker.database.DataSource;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OlxScraperTest {

    OlxScraper olxScraper;

    @Test
    void findName_test() throws IOException, SQLException {

    }

    @Test
    void findId() throws IOException {
        Document page = JSoupController.controller("https://www.olx.pl/elektronika/telefony-komorkowe/iphone/q-iphone-x/");

        Elements offersIds = page.select("table#offers_table div.offer-wrapper > table");
        String i = offersIds.get(0).attr("data-id");
        int e = Integer.parseInt(i);

        assertEquals(616035064, e);

    }

}