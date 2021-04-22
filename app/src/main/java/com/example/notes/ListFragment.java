package com.example.notes;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;


public class ListFragment extends Fragment {
    private static final int MY_DEFAULT_DURATION = 1000;
    public static final String CURRENT_TEXT = "CurrentText";;
    private NameNotes notesCurrent;
    private boolean isLandscape;
    private NotesSource data;
    private ListAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToFirstPosition;

    public static ListFragment newInstance(){
        return new ListFragment();
    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        data = new NotesSourceImpl(getResources()).init();
//    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container,
                false);
         initView(view);
         setHasOptionsMenu(true);;
         data = new NameNotesFirebaseImpi().init(new NameNotesResponse() {
             @Override
             public void initialized(NotesSource nameNotes) {
                 adapter.notifyDataSetChanged();
             }
         });
         adapter.setNameNotesSource(data);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    private boolean onItemSelected(int menuItemId){
        switch (menuItemId){
            case R.id.action_add:
                navigation.addFragment(TextFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(NameNotes nameNotes) {
                        data.addNotesData(nameNotes);
                        adapter.notifyItemInserted(data.size() - 1);
                        moveToFirstPosition = true;
                    }
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(TextFragment.newInstance
                        (data.getNameNotes(updatePosition)),true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(NameNotes nameNotes) {
                        data.updateNotesData(updatePosition, nameNotes);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.delete:
                int deletePosition = adapter.getMenuPosition();
                data.deleteNotesData(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.action_clear:
                data.clearNotesData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerViewList);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter( this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);
//        if (moveToLastPosition){
//            recyclerView.smoothScrollToPosition(data.size() - 1);
//            moveToLastPosition = false;
//        }
        if (moveToFirstPosition && data.size()>0){
            recyclerView.smoothScrollToPosition(0);
            moveToFirstPosition = false;
        }

        adapter.SetOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
      public void onItemClick(View view, int position) {
     Toast.makeText(getContext(), String.format("Position - %d ", position),
             Toast.LENGTH_LONG).show();
      }
   });
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
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
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