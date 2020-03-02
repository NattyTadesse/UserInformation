package com.ahadu.userinformation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {
    public Context context;
    List<UserModel> mData;
    DatabaseHelper databaseHelper;

    public RecyclerViewAdapter(Context context, List<UserModel> mData) {
        this.context = context;
        this.mData= mData;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false);
        return new viewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.fullname.setText(mData.get(position).getFullname());
        databaseHelper = new DatabaseHelper(context);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            UserModel uM = mData.get(position);
            String sth = mData.get(position).getFullname();
            mData.remove(uM);
            notifyItemRemoved(position);
            databaseHelper.delete(sth);
            return false;
        }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView fullname;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.text_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,UserInfo.class);
                    intent.putExtra("USERNAME",fullname.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
