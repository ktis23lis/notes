package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class NameNotes implements Parcelable {

    private int textIndex;
    private String name;
    private String text;
    private String date;

    public NameNotes(int textIndex, String name) {
        this.textIndex = textIndex;
        this.name = name;
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
