package com.magusta.dealseeker.scraper;


import com.magusta.dealseeker.dao.OfferDaoImpl;
import com.magusta.dealseeker.dao.SearchDaoImpl;
import com.magusta.dealseeker.model.Offer;
import com.magusta.dealseeker.model.Search;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OlxScraper {


    private final OfferDaoImpl offerDao = new OfferDaoImpl();
    private final SearchDaoImpl searchDao = new SearchDaoImpl();



    /*protected void changeStringToDate(String stringDate) {
        ZoneId zoneId = ZoneId.of("Europe/Warsaw");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Instant now = Instant.now();
        if(stringDate.contains("dzisiaj")){

            ZonedDateTime zdt = ZonedDateTime.ofInstant(now, zoneId);
            String dateReplace = stringDate
                    .replaceAll("dzisiaj", zdt.format(formatter))
                    .replaceFirst(" ", "_");
        }else if(stringDate.contains("wczoraj")){
            Instant yesterday =  now.minus(1, ChronoUnit.DAYS);
            ZonedDateTime zdt = ZonedDateTime.ofInstant(yesterday, zoneId);
            String dateReplace = stringDate
                    .replaceFirst("wczoraj", zdt.format(formatter))
                    .replaceFirst(" ", "_");

        }else{

        }
    }*/
    protected void createSearchForFuture(String url) throws SQLException, IOException {
        String searchName = findName(url);
        Search search = new Search(url, searchName);
        ArrayList<Integer> searchIds = findLastIds(url);
        searchDao.insertSearch(search);
        int searchId = searchDao.getSearchId(url);
        for (int i: searchIds){
            offerDao.insertOfferIdAndSearchId(i, searchId);
            System.out.println("Print last 3 ids:");
            System.out.println(i);
        }

    }
    /*
     * Finding search name
     * Example url: https://www.olx.pl/elektronika/telefony-komorkowe/huawei/q-p20/
     * */
    private String findName(String url) {

        //Remove "http://www.olx.pl/"
        int olxIndex = url.indexOf("olx") + 7;

        String partOfUrl = url.substring(olxIndex);

        System.out.println("Part of url: " + partOfUrl);
        //Check if uri has "/" at the end
        if(!partOfUrl.endsWith("/")){
            partOfUrl = partOfUrl + "/";
        }
        System.out.println();
        System.out.println("Part of url with slash on the end: " + partOfUrl);
        ArrayList<Integer> listOfFrontSlashes = new ArrayList<>();
        //Find all front slashes indexes
        for(int index = partOfUrl.indexOf("/");
            index >= 0;
            index = partOfUrl.indexOf("/", index + 1)){
            listOfFrontSlashes.add(index);
        }
        //Find index of fourth slash from the end
        int fourthSlash = listOfFrontSlashes.get(listOfFrontSlashes.size() - 4);
        System.out.println();
        System.out.println("Fourth slash: " + fourthSlash);
        //Remove "elektronika/"
        String lastThreeVar = partOfUrl.substring(fourthSlash);
        System.out.println();
        System.out.println("Last3Var:" + lastThreeVar);
        //Replace all slashes to whitespaces, remove "q-" and whitespace at the end
        return lastThreeVar.substring(1, lastThreeVar.length()-1)
                .replaceAll("/", "_")
                .replaceFirst("q-", "");
    }

    /*
     * Finding page number
     * */
    /*protected int findNumberOfPages(String url) throws IOException {

        Document page = JSoupController.controller(url);

        //Get elements from class
        Elements pagesClass = page.getElementsByClass("pager rel clr");
        int numberOfPages;

        //Check if class is not empty
        if(!pagesClass.isEmpty()){
            //Get text within class
            String pages = pagesClass.text();
            //Split string by all non-digit chars
            String[] digitsArray = pages.split("\\D+");
            numberOfPages = Integer.parseInt(digitsArray[digitsArray.length - 1]);
        } else{
            numberOfPages = 1;
        }
        return numberOfPages;
    }*/

    private ArrayList<Integer> findLastIds(String url) throws IOException {
        ArrayList<Integer> lastThreeIds= new ArrayList<>();
        Document page = JSoupController.controller(url);

        Elements offersIds = page.select("table#offers_table div.offer-wrapper > table");
        for(int e = 0; e < 3;e++ ){
            int i = Integer.parseInt(offersIds.get(e).attr("data-id"));
            lastThreeIds.add(i);

        }
        return lastThreeIds;

    }


    protected ArrayList<Offer> findNewOffers(String searchingUrl, int searchId, ArrayList<Integer> offersIdsList) throws IOException {
        Document page = JSoupController.controller(searchingUrl);

        Elements offersTitles = page.select("table#offers_table .lheight22.margintop5");
        Elements offersPrices = page.select("table#offers_table td.wwnormal.tright.td-price");
        Elements offersUrls = page.select("table#offers_table h3.lheight22.margintop5 > a");
        //Elements offersTimes = page.select("table#offers_table small.breadcrumb.x-normal > span");
        Elements offersIds = page.select("table#offers_table div.offer-wrapper > table");

        ArrayList<Offer> offers = new ArrayList<>();
        for (int e = 0; e < offersIds.size(); e++){
            int id = Integer.parseInt(offersIds.get(e).attr("data-id"));
            if(offersIdsList.contains(id)){
                break;
            }
            String title = offersTitles.get(e).text();
            String price = offersPrices.get(e).text();
            String url = offersUrls.get(e).absUrl("href");
            Offer offer = new Offer(id, title, price, url, searchId);
            offerDao.insertOffer(offer);
            offers.add(offer);
        }
        return offers;
    }
}





