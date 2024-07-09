package com.example.td;

public class ItemModel {
    private String id;
    private String title;
    private String subtitle;

    public ItemModel(String title, String subtitle, String id) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getId() { return id; }
}
