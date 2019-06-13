package com.nakul.meesho.view;


import android.widget.ImageView;
import android.databinding.BindingAdapter;

import com.nakul.meesho.GlideApp;


public class ImageBinder {

    @BindingAdapter({"imageURL"})
    public static void loadImage(ImageView img, String imageUrl) {
        GlideApp.with(img.getContext())
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(img);
    }
}
