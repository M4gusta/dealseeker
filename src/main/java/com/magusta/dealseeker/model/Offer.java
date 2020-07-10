package com.magusta.dealseeker.model;



public class Offer {

    private final int id;
    private String title;
    private String price;
    private String url;
    private int searchId;

    public Offer(int id, String title, String price, String url, int searchId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.url = url;
        this.searchId = searchId;
    }

    public Offer(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getSearchId() {
        return searchId;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", searchId=" + searchId +
                '}';
    }
}
