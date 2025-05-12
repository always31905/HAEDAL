package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private List<ImageDetail> detailList;
    //생성자
    public DetailAdapter(List<ImageDetail> detailList) {
        this.detailList = detailList;
    }

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new DetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.ViewHolder holder, int position) {

        ImageDetail item = detailList.get(position);
        holder.emailView.setText(item.getEmail());
        Glide.with(holder.itemView.getContext())
                .load(item.getImageItem().getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileView;
        TextView emailView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileView = itemView.findViewById(R.id.detailViewItem_profile_image);
            emailView = itemView.findViewById(R.id.detailViewItem_profile_textView);
            imageView = itemView.findViewById(R.id.detailViewItem_imageView_content);
        }
    }
}
