package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NameNotes implements Parcelable {

    private int textIndex;
    private String name;
    private String text;
    private String date;





    public NameNotes(int textIndex, String name) {
        this.textIndex = textIndex;
        this.name = name;
    }

    public NameNotes( String name, String text) {
        this.name = name;
        this.text = text;
    }
    public NameNotes( String name, String text, String date) {
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public NameNotes(String date) {
        this.date = date;
    }

    protected NameNotes(Parcel in) {
        textIndex = in.readInt();
        name = in.readString();
        text = in.readString();
        date = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getTextIndex());
        dest.writeString(getName());
        dest.writeString(getText());
        dest.writeString(getDate());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NameNotes> CREATOR = new Creator<NameNotes>() {
        @Override
        public NameNotes createFromParcel(Parcel in){
            return new NameNotes(in);
        }

        @Override
        public NameNotes[] newArray(int size) {
            return new NameNotes[size];
        }

    };

    public int getTextIndex() {
        return textIndex;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
