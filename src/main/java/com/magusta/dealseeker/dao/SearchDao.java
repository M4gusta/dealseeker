package com.magusta.dealseeker.dao;

import com.magusta.dealseeker.model.Search;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SearchDao {

    ArrayList<Search> getAllSearches() throws SQLException;
    void insertSearch(Search search) throws SQLException;
    void deleteSearch(int searchId) throws SQLException;
    int getSearchId(String url) throws SQLException;

}
