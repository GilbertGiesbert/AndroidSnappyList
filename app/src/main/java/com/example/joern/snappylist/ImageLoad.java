package com.example.joern.snappylist;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by joern on 16.06.2016.
 */
public class ImageLoad {

    private String imageUrl;
    private Bitmap image;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
