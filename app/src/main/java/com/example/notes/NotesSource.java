package com.example.notes;

public interface NotesSource {
    NameNotes getNameNotes(int position);
    int size();
    void deleteNotesData (int position);
    void updateNotesData (int position, NameNotes nameNotes);
    void addNotesData (NameNotes nameNotes);
    void clearNotesData ();
    }

