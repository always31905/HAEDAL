package com.example.myapplication;

public class ImageItem {
    private String imageUrl;
    private long timestamp;
    public ImageItem(){

    }
    public ImageItem(String imageUrl, long timestamp){
        this.imageUrl=imageUrl;
        this.timestamp=timestamp;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
