package com.example.volleylibrary;

import java.util.List;

public class Confession_Pojo {

    public int categoryId;
    public List<Comment> comments;
    public String datetime;
    public int id;
    public String image;
    public int likes =0;
    public String message;
    public String userename;
    int like =0 ;
//    public List<Mycomments>myComment;


    public Confession_Pojo(String message,String image,int likes,int like) {

        this.message = message;
        this.image=image;
        this.likes=likes;
        this.like=like;

    }

    public int getCategoryId() {
        return categoryId;
    }

    public List<Comment> getComments() {
        return comments;
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

    public int getLikes() {
        return likes;
    }

    public String getMessage() {
        return message;
    }

    public String getUserename() {
        return userename;
    }

    public int getLike(){return like; }


    public  int getComment_count()
    {
        return comments.size();
    }

//    public int get_myCommentCount() {
//        return  myComment.size();
//    }
}
