package com.magusta.dealseeker.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JSoupController {


    public static Document controller(String url) throws IOException {
        String userAgent = "Web Browser";
        return Jsoup.connect(url).userAgent(userAgent).get();
    }
}
