package com.example.volleylibrary;

import android.os.Parcel;
import android.os.Parcelable;

public class Mycomments implements Parcelable{

    String comment;
    String datetime;
    int id;







    public Mycomments(String comment, String datetime, int id) {
        this.comment = comment;
        this.datetime = datetime;
        this.id = id;
    }

    public String getComment() {
        return comment;
    }


    protected Mycomments(Parcel in) {
        comment = in.readString();
        datetime = in.readString();
        id = in.readInt();
    }

    public static final Creator<Mycomments> CREATOR = new Creator<Mycomments>() {
        @Override
        public Mycomments createFromParcel(Parcel in) {
            return new Mycomments(in);
        }

        @Override
        public Mycomments[] newArray(int size) {
            return new Mycomments[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeString(datetime);
        dest.writeInt(id);
    }
}
