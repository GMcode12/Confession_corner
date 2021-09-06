package com.example.volleylibrary;

import java.util.List;

public class Confession_Pojo {

    public int categoryId;
    public List<Comment> comments=null;
    public List<Mycomments>mycomments=null;
    public String datetime;
    public int id;
    public String image;
    public int likes =0;
    public String message;
    public String userename;




    public Confession_Pojo(String image,int likes,int like) {


        this.image=image;
        this.likes=likes;


    }

    public List<Mycomments> getMycomments(){
        return  mycomments;
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



    public  int getComment_count()
    {
        return comments.size();
    }


//    public int get_myCommentCount() {
//        return  myComment.size();
//    }
}
