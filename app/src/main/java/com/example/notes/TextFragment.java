package com.example.notes;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

public class TextFragment extends Fragment {
    public TextView dateTextView1;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text,container,false);
        TextView editTextOfText = view.findViewById(R.id.text_fregment);
        TypedArray text = getResources().obtainTypedArray(R.array.text_of_notes);
        editTextOfText.setText(text.getResourceId(nameNotes.getTextIndex(), -1));
        dateTextView1 = (TextView)view.findViewById(R.id.dateTextView1);

        TextView notesNameView = view.findViewById(R.id.textViewNameNotes);
        notesNameView.setText(nameNotes.getName());
//        handleDateListener();

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleDateListener(){
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, day) -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.YEAR, year);
            calendar1.set(Calendar.MONTH, month);
            calendar1.set(Calendar.DATE, day);
//            nameNotes.setDate(DateFormat.format("MMM d, yyyy",calendar1).toString());


            dateTextView1.setText(nameNotes.getDate());
            nameNotes.dateArray.add(DateFormat.format("MMM d, yyyy",calendar1).toString());


        },YEAR, MONTH, DATE);
        datePickerDialog.show();

    }


}