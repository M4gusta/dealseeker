package com.magusta.dealseeker.scraper;

import com.magusta.dealseeker.dao.OfferDaoImpl;
import com.magusta.dealseeker.dao.SearchDaoImpl;
import com.magusta.dealseeker.database.CheckTables;
import com.magusta.dealseeker.model.Offer;
import com.magusta.dealseeker.model.Search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class Scraper {
    private final SearchDaoImpl searchDao = new SearchDaoImpl();
    private final OfferDaoImpl offerDao = new OfferDaoImpl();
    private final OlxScraper olxScraper = new OlxScraper();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void checkIfThereIsAnyDataInDB() throws SQLException, IOException {
        if (CheckTables.checkTables()) {
            //Looking for saved searches
            ArrayList<Search> searches = searchDao.getAllSearches();
            if (searches.size() > 0) {
                dataIsPresent(searches);
            }else{
                checkUrl(inputUrl());
            }
        } else {
                checkUrl(inputUrl());
            }
    }

    private String inputUrl() throws IOException {
        System.out.println("Input url:");

        return reader.readLine();
    }

    private void checkUrl(String url) throws SQLException, IOException {
        if (url.contains("olx")) {
            olxScraper.createSearchForFuture(url);
            System.out.println("Done for now!");
        }
    }
    /*
    * If saved searches found
    * */
    private void dataIsPresent(ArrayList<Search> searches) throws IOException, SQLException {
        System.out.println("Choose which search you want to check:");
        int i = 1;
        for (Search search : searches) {
            System.out.println(i + ". " + search.getSearchName());
            i++;
        }
        System.out.println("Type number of search or click enter to input url:");
        String number = reader.readLine();
        if(number.equals("")){
            checkUrl(inputUrl());
        }else {

            int chosen = Integer.parseInt(number);
            Search search = searches.get(chosen - 1);

            String searchUrl = search.getSearchUrl();
            int searchId = search.getSearchId();

            ArrayList<Offer> offerArrayList = olxScraper.findNewOffers(searchUrl, searchId, getThreeNewestOffersIds(searchId));
            showNewOffers(offerArrayList);
            // Delete first offers with only id from search
            offerDao.deleteOffersWithNulls(searchId);
        }
    }

    private ArrayList<Integer> getThreeNewestOffersIds(int searchId){
        ArrayList<Offer> offers = offerDao.getAllOffersBySearchId(searchId);
        ArrayList<Integer> offersIds = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            offersIds.add(offers.get(i).getId());
        }
        return offersIds;
    }
    private void showNewOffers(ArrayList<Offer> offers){
        int i = 1;
        for (Offer offer: offers){
            System.out.println(i + ". " + offer.toString());
            i++;
        }
    }




}






















