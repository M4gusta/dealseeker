package com.magusta.dealseeker.dao;

import com.magusta.dealseeker.model.Offer;

import java.util.ArrayList;

public interface OfferDao {
    Offer getOfferById(int id);
    ArrayList<Offer> getAllOffers();
    void insertOffer(Offer offer);
    void updateOffer(Offer offer);
    void deleteOffer(int id);
    void deleteOffersWithNulls(int id);
    void insertOfferIdAndSearchId(int offerId, int searchId);
    ArrayList<Integer> getAllOffersIds();
    ArrayList<Offer> getAllOffersBySearchId(int searchId);

}
