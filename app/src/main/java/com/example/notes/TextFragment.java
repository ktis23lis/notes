package com.example.notes;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TextFragment extends Fragment {
    public static final String ARG_NAME = "name";
    private NameNotes nameNotes;

    public static TextFragment newInstance(NameNotes nameNotes) {
        TextFragment f = new TextFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_NAME, nameNotes);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            nameNotes = getArguments().getParcelable(ARG_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_text,container,
               false);
        TextView editTextOfText = view.findViewById(R.id.text_fregment);
        TypedArray text =
                getResources().obtainTypedArray(R.array.text_of_notes);


     editTextOfText.setText(text.getResourceId(nameNotes.getTextIndex(), -1));


        TextView notesNameView = view.findViewById(R.id.textViewNameNotes);
        notesNameView.setText(nameNotes.getName());

        return view;
    }
}