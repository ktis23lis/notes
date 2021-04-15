package com.example.notes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter
        extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final static String TAG = "ListAdapter";
//     private String [] list;
    private NotesSource dataSource;
//    private NameNotes nameNotes;
     private OnItemClickListener itemClickListener;


//    public ListAdapter(String[] list) {
//        this.list = list;
//    }


    public ListAdapter(NotesSource dataSource) {
        this.dataSource = dataSource;
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
//        holder.getTextView().setText(list[position]);
        holder.setData(dataSource.getNameNotes(position));
        Log.d(TAG, "onBindViewHolder");

    }



    @Override
    public int getItemCount() {
        return dataSource.size();
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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list = itemView.findViewById(R.id.textView);
            text = itemView.findViewById(R.id.textView2);
            list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener !=null)
                        itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

//        public TextView getTextView(){
//            return textView;
//        }
        public void setData(NameNotes nameNotes){
            list.setText(nameNotes.getName());
            text.setText(nameNotes.getText());
        }


    }
}
