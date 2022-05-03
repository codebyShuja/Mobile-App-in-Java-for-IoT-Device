package com.irex.appspark.irexr_03;

/**
 * Created by Ali Shuja Sardar on 2/22/2018.
 */

import android.graphics.Bitmap;

public class Model {
    String name;
    Bitmap imaBitmap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return imaBitmap;
    }

    public void setImage(Bitmap imaBitmap) {
        this.imaBitmap = imaBitmap;
    }
}
