package com.example.fitfreak;



public class TopFeedModel {
    String title,desc,link,author;

    public TopFeedModel(String title, String desc, String link, String author) {
        this.title = title;
        this.desc = desc;
        this.link=link;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }
}



