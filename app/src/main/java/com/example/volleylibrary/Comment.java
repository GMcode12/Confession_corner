package com.example.volleylibrary;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    public String datetime;
    public int id;
    public String message;
    public String userename;

    protected Comment(Parcel in) {
        datetime = in.readString();
        id = in.readInt();
        message = in.readString();
        userename = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getDatetime() {
        return datetime;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getUserename() {
        return userename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(datetime);
        dest.writeInt(id);
        dest.writeString(message);
        dest.writeString(userename);
    }
}
