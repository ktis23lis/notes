package com.example.notes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class ListFragment extends Fragment {
    private static final int MY_DEFAULT_DURATION = 1000;
    public static final String CURRENT_TEXT = "CurrentText";;
    private NameNotes notesCurrent;
    private boolean isLandscape;
    private NotesSource data;
    private ListAdapter adapter;
    private RecyclerView recyclerView;
    private TextView dateTextView;
    private DatePickerDialog.OnDateSetListener myDateSetListener;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new NotesSourceImpl(getResources()).init();
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewList);
         initView(view);
         setHasOptionsMenu(true);;
        return view;
    }
     public static ListFragment newInstance(){
        return new ListFragment();
}


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                data.addNotesData(new NameNotes("Заголовок" + data.size(),
                        "Описание" + data.size()));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() -1);
                return true;
            case R.id.action_clear:
                data.clearNotesData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerViewList);
        data = new NotesSourceImpl(getResources()).init();
        initRecyclerView();
        dateTextView = view.findViewById(R.id.dateTextViewList);
        if (notesCurrent!= null) {
            dateTextView.setText(notesCurrent.getDate());
        }
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                 = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(data, this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

//не забыть реализацию даты
        adapter.SetOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                final int fi = position;
                notesCurrent = new NameNotes(fi,((TextView)view).getText().toString());
                showText(notesCurrent);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()){
            case R.id.action_update:
                data.updateNotesData(position,
                        new NameNotes(
                                data.getNameNotes(position).getName(),
                                data.getNameNotes(position).getText()));
                adapter. notifyItemChanged(position);
            return true;
            case R.id.delete:
                data.deleteNotesData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_TEXT, notesCurrent);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState !=null){
            notesCurrent = savedInstanceState.getParcelable(CURRENT_TEXT);
        }else {
            notesCurrent = new NameNotes(0,
                    getResources().getStringArray(R.array.list)[0]);
        }
        if(isLandscape){
            showLandText(notesCurrent);
        }
    }

    private void showText(NameNotes notesCurrent){
       if (isLandscape){
           showLandText(notesCurrent);
       }else {
           showPortText(notesCurrent);
       }
    }

    private void showLandText(NameNotes notesCurrent){
        TextFragment textFragment = TextFragment.newInstance(notesCurrent);
        FragmentManager fragmentManager =
                requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.text_fregment,textFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showPortText(NameNotes notesCurrent){
        Intent intent = new Intent();
        intent.setClass(getActivity(), TextActivity.class);
        intent.putExtra(TextFragment.ARG_NAME, notesCurrent);
        startActivity(intent);
    }


}