package com.example.notes;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

public class TextFragment extends Fragment {
    public static final String ARG_NAME = "name";
    private NameNotes nameNotes;
    private DatePickerDialog.OnDateSetListener myDateSetListener;
    private TextView dateTextViewText;
    private Date date = new Date();
    public static final String TAG = "mylog";
    private TextView notesNameView;
    private TextView editTextOfText;
    private String dateString;

    private Publisher publisher;


    public static TextFragment newInstance(NameNotes nameNotes) {
        TextFragment f = new TextFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NAME, nameNotes);
        f.setArguments(args);
        return f;
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text,container,false);
        editTextOfText = view.findViewById(R.id.text_fregment);//место для текста
        TypedArray text = getResources().obtainTypedArray(R.array.text_of_notes);// array для текста заметок
        notesNameView = view.findViewById(R.id.textViewNameNotes); //имя заметки
        editTextOfText.setText(text.getResourceId(nameNotes.getTextIndex(), -1));
        notesNameView.setText(nameNotes.getName());
        dateHandlerActivated();
        dateTextViewText = view.findViewById(R.id.dataTextViewText);


        return view;
    }



    private void dateHandlerActivated(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        Log.d(TAG, "hello");
        myDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                dateString = year +"/" + month +"/"+dayOfMonth;
                nameNotes = new NameNotes(dateString);
                Log.d("qqq", "aaaa" + year +"/" + month +"/"+dayOfMonth);
                dateTextViewText.setText(nameNotes.getDate());
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                myDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}