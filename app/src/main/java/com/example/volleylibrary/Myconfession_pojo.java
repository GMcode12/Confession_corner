package com.example.volleylibrary;

import java.util.List;

public class Myconfession_pojo {

    private String message;
    private int like;
    public int categoryid;
    public List<Mycomments> comment;
    String datetime;
    int id;
    String image;

    public Myconfession_pojo(int categoryid, List<Mycomments> comment, String datetime, int id, String image, int like, String message) {
        this.categoryid = categoryid;
        this.comment = comment;
        this.datetime = datetime;
        this.id = id;
        this.image = image;
        this.like = like;
        this.message = message;

    }



    public int getCategoryid() {
        return categoryid;
    }

    public List<Mycomments> getComment() {
        return comment;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public int getLike() {
        return like;
    }

    public String getMessage() {
        return message;
    }

    public int getcomment_count()
    {
        return comment.size();
    }



}
