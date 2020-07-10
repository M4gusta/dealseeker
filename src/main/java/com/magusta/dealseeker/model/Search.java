package com.magusta.dealseeker.model;

public class Search {

    private int searchId;
    private final String searchUrl;
    private final String searchName;

    public int getSearchId() {
        return searchId;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public String getSearchName() {
        return searchName;
    }

    public Search(String searchUrl, String searchName) {
        this.searchUrl = searchUrl;
        this.searchName = searchName;
    }

    public Search(int searchId, String searchUrl, String searchName) {
        this.searchId = searchId;
        this.searchUrl = searchUrl;
        this.searchName = searchName;
    }
}
