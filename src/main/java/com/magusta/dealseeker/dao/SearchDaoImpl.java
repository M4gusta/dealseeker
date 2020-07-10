package com.magusta.dealseeker.dao;

import com.magusta.dealseeker.database.DataSource;
import com.magusta.dealseeker.model.Search;

import java.sql.*;
import java.util.ArrayList;

public class SearchDaoImpl implements SearchDao {

    private static final String
            getAll = "SELECT * FROM search_config";
    private static final String
            insert = "INSERT IGNORE INTO search_config(search_url, search_name) VALUES (?, ?)";
    private static final String
            delete = "DELETE IGNORE FROM search_config WHERE search_id = ?";
    private static final String
            getSearchId = "SELECT search_id FROM search_config WHERE search_url = ?";

    @Override
    public ArrayList<Search> getAllSearches() throws SQLException {
        ArrayList<Search> searches = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(getAll)){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    searches.add(extractSearchFromRs(rs));
                }
            }
        }
        return searches;
    }

    @Override
    public void insertSearch(Search search) throws SQLException {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(insert)){
            ps.setString(1, search.getSearchUrl());
            ps.setString(2, search.getSearchName());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteSearch(int searchId) throws SQLException {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(delete)){
            ps.setInt(1, searchId);
            ps.executeUpdate();
        }
    }

    @Override
    public int getSearchId(String url) throws SQLException {
        int id = 0;
        try(Connection connection = DataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(getSearchId)){
            ps.setString(1, url);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    id = rs.getInt("search_id");
                }
            }
        }
        return id;
    }

    private Search extractSearchFromRs(ResultSet rs) throws SQLException {
        int searchId = rs.getInt("search_id");
        String searchUrl = rs.getString("search_url");
        String searchName = rs.getString("search_name");

        return new Search(searchId, searchUrl, searchName);
    }
}
