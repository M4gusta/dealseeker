package com.magusta.dealseeker.dao;

import com.magusta.dealseeker.database.DataSource;
import com.magusta.dealseeker.model.Offer;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class OfferDaoImpl implements OfferDao {

    private static final String
            insert = "INSERT IGNORE INTO offers(offer_id, title, price, url, search_id) VALUES (?, ?, ?, ?, ?)";
    private static final String
            getById = "SELECT * FROM offers WHERE offer_id = ?";
    private static final String
            getAll = "SELECT * FROM offers";
    private static final String
            delete = "DELETE IGNORE FROM offers WHERE offer_id = ?";
    private static final String
            getAllBySearchId = "SELECT * FROM offers WHERE search_id = ?";
    private static final String
            insertWithOfferIdAndSearchId = "INSERT IGNORE INTO offers(offer_id, search_id) VALUES (?, ?)";
    private static final String
            getAllOffersIds = "SELECT offer_id FROM offers";
    private static final String
            deleteNulls = "DELETE FROM offers WHERE url IS NULL AND search_id = ?";

    @Override
    public Offer getOfferById(int id) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(getById)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return extractOfferFromRs(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Offer> getAllOffers() {
        ArrayList<Offer> offers = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(getAll)) {
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    offers.add(extractOfferFromRs(rs));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return offers;
    }

    @Override
    public void insertOffer(Offer offer) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(insert)) {
            ps.setInt(1, offer.getId());
            ps.setString(2, offer.getTitle());
            ps.setString(3, offer.getPrice());
            ps.setString(4, offer.getUrl());
            ps.setInt(5, offer.getSearchId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateOffer(Offer offer) {

    }

    @Override
    public void deleteOffersWithNulls(int id) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(deleteNulls)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteOffer(int id) {
        try(Connection connection = DataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(delete)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insertOfferIdAndSearchId(int offerId, int searchId) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(insertWithOfferIdAndSearchId)) {
            ps.setInt(1, offerId);
            ps.setInt(2, searchId);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<Integer> getAllOffersIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(getAllOffersIds)) {
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    ids.add(rs.getInt("offer_id"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ids;
    }

    @Override
    public ArrayList<Offer> getAllOffersBySearchId(int searchId) {
        ArrayList<Offer> offers = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(getAllBySearchId)) {
            ps.setInt(1, searchId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    offers.add(extractOfferFromRs(rs));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return offers;
    }

    private static Offer extractOfferFromRs(ResultSet rs) throws SQLException {
        int id = rs.getInt("offer_id");
        String title = rs.getString("title");
        String price = rs.getString("price");
        String url = rs.getString("url");
        int searchId = rs.getInt("search_id");

        return new Offer(id, title, price, url, searchId);
    }
}
