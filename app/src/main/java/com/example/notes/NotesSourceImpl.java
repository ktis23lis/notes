package com.example.notes;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class NotesSourceImpl implements NotesSource{
    private List<NameNotes> dataSource;
    private Resources resources;


    public NotesSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public NotesSourceImpl init(){
        String[]lists = resources.getStringArray(R.array.list);
        String[]text = resources.getStringArray(R.array.text_of_notes);
        for (int i =0; i < lists.length; i++){
            dataSource.add(new NameNotes(lists[i],text[i]));
        }
        return this;
    }



    @Override
    public NameNotes getNameNotes(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }
}
