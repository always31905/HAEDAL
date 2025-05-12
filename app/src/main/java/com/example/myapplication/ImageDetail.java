package com.example.myapplication;

public class ImageDetail {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email;

    }

    public ImageItem getImageItem() {
        return imageItem;
    }

    public void setImageItem(ImageItem imageItem) {
        this.imageItem = imageItem;
    }

    private ImageItem imageItem;

    public ImageDetail(String email, ImageItem imageItem) {
        this.email = email;
        this.imageItem = imageItem;
    }

}
