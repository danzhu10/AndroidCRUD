package com.android.crud.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.crud.R;
import com.android.crud.model.User;

import java.util.List;

/**
 * Created by EduSPOT on 22/07/2017.
 */

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {
    private List<User> userList;

    public DataListAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.nameTV.setText("Name : " + user.getName());
        holder.descTV.setText("Description : " + user.getDescription());
        holder.setData(user, position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void remove(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, descTV;
        User user;
        int pos;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            descTV = (TextView) itemView.findViewById(R.id.descriptionTV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickListener(user, pos);
                }
            });
        }

        public void setData(User user, int position) {
            this.user = user;
            this.pos = position;
        }
    }

    private OnItemClickListener listener;

    public void onItemClick(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClickListener(User user, int pos);
    }
}
