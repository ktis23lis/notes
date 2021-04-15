package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import java.util.ArrayList;

public class NameNotes implements Parcelable {

    private int textIndex;
    private String name;
    private String text;
    private String date;
    private int dateIndex;
    public ArrayList <String> dateArray;

    public int getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void arrayAdd(int textIndex, String date){
        dateArray.add(textIndex,date);
    }

    public void arrayGet(TextView textView, int i){
        textView.setText(dateArray.get(i));

    }

    public String getText() {
        return text;
    }

    public NameNotes(int textIndex, String name) {
        this.textIndex = textIndex;
        this.name = name;
    }

    public NameNotes( String name, String text) {
        this.name = name;
        this.text = text;
    }

    protected NameNotes(Parcel in) {
        textIndex = in.readInt();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getTextIndex());
        dest.writeString(getName());
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


}
