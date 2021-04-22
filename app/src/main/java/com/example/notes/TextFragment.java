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
    private DatePickerDialog.OnDateSetListener myDateSetListener;
    private TextView dateTextViewText;
    private EditText editTextOfText;
    private EditText notesNameView;
    private NameNotes nameNotes;
    private Publisher publisher;



    public static TextFragment newInstance(NameNotes nameNotes) {
        TextFragment f = new TextFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NAME, nameNotes);
        f.setArguments(args);
        return f;
    }

    public static TextFragment newInstance(){
        TextFragment textFragment = new TextFragment();
        return textFragment;
    };

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            nameNotes = getArguments().getParcelable(ARG_NAME);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text,container,false);
        initView(view);
        if (nameNotes != null){
            populateView();
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        nameNotes = collectNameNotes();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(nameNotes);
    }

    private NameNotes collectNameNotes(){
        String notesNameView = this.notesNameView.getText().toString();
        String editTextOfText = this.editTextOfText.getText().toString();
        String dateTextViewText = this.dateTextViewText.getText().toString();
        if (nameNotes != null){
            NameNotes answer;
            answer = new NameNotes(notesNameView, editTextOfText, dateTextViewText);
            answer.setId(nameNotes.getId());
            return answer;
        }else {
            return new NameNotes(notesNameView, editTextOfText, dateTextViewText);
        }
    }

    private void dateHandlerActivated(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        myDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String dateString = year +"/" + month +"/"+dayOfMonth;
                nameNotes = new NameNotes(dateString);
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

    private void initView(View view){
        editTextOfText = view.findViewById(R.id.text_fregment);//место для текста
        notesNameView = view.findViewById(R.id.textViewNameNotes); //имя заметки
        dateTextViewText = view.findViewById(R.id.dataTextViewText);
        dateHandlerActivated();

    }

    private void populateView(){
        notesNameView.setText(nameNotes.getName());
        editTextOfText.setText(nameNotes.getText());
        dateTextViewText.setText(nameNotes.getDate());
    }





}