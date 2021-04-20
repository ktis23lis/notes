package com.example.notes;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

public class ListAdapter
        extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final static String TAG = "ListAdapter";
//     private String [] list;
    private NotesSource dataSource;
//    private NameNotes nameNotes;
     private OnItemClickListener itemClickListener;
     private final Fragment fragment;
     private int menuPosition;


    public ListAdapter(NotesSource dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent, false);
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.setData(dataSource.getNameNotes(position));
        Log.d(TAG, "onBindViewHolder");

    }



    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public int getMenuPosition(){
        return menuPosition;
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView list;
        private TextView text;
        private TextView data;



        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            list = itemView.findViewById(R.id.textView);
            text = itemView.findViewById(R.id.textView2);
            data = itemView.findViewById(R.id.dateTextViewList);
            registerContextMenu(itemView);
            list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener !=null)
                        itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            text.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu();
                    return true;
                }
            });
        }


        private void registerContextMenu(@NonNull View itemView){
            if(fragment !=null){
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                       menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }


        public void setData(NameNotes nameNotes){
            list.setText(nameNotes.getName());
            text.setText(nameNotes.getText());
            data.setText(nameNotes.getDate());
        }
    }
}
