package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.LoadParams;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.common.cache.LoadingCache;
import android.os.Handler;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.ViewHolder> {
    private Context context;
    private List<ImageItem> imageItemList;
    public imageAdapter(Context context, List<ImageItem> imageItemList){
        this.context = context; //context는 내가 어디있는지를 알려줌
        this.imageItemList=imageItemList;
    }
    @NonNull
    @Override
    public imageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image,parent,false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageItem item = imageItemList.get(position);
        loadImage(item.getImageUrl(),holder.imageview);
    }

    @Override
    public int getItemCount() {
        return imageItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageView);
        }
    }
    private void loadImage(String urlStr, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(urlStr)
                .into(imageView);
    }
}
