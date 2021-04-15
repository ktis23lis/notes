package com.example.notes;

import android.content.Intent;
import android.content.res.Configuration;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ListFragment extends Fragment {
    public static final String CURRENT_TEXT = "CurrentText";
    public static final String CURRENT_LIST = "CurrentLIST";
    private NameNotes notesCurrent;
    private boolean isLandscape;
    private String list;


    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewList);
//        String [] nameNotes = getResources().getStringArray(R.array.list);
        NotesSource data = new NotesSourceImpl(getResources()).init();
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, NotesSource data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                 = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ListAdapter adapter = new ListAdapter(data);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);

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
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void initList(View view){
//        LinearLayout layoutView = (LinearLayout) view;
//        String[]lists = getResources().getStringArray(R.array.list);
//        for (int i = 0; i < lists.length; i++){
//            list = lists[i];
//            TextView tv = new TextView(getContext());
//            tv.setText(list);
//            tv.setTextSize(30);
//            layoutView.addView(tv);
//
//
//            if (notesCurrent != null && notesCurrent.dateArray.size() > 0) {
//                TextView date = new TextView(getContext());
//                date.setText(notesCurrent.dateArray.get(0));
//                layoutView.addView(date);
//
//            }
//            final int finalInt = i;
//            tv.setOnClickListener(v -> {
//                notesCurrent = new NameNotes(finalInt,getResources().getStringArray(R.array.list)[finalInt]);
//                showText(notesCurrent);
//            });
//        }
//    }
//
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_TEXT, notesCurrent);
//        outState.putString(CURRENT_LIST,list);
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