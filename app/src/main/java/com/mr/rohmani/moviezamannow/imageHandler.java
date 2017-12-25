package com.mr.rohmani.moviezamannow;

/**
 * Created by USER on 24/12/2017.
 */

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import java.io.FileOutputStream;

public class imageHandler implements Target {
    private final String name;
    private ImageView imageView;
    public imageHandler(String name, ImageView imageView) {
        this.name = name;
    }
    @Override
    public void onPrepareLoad(Drawable arg0) {
    }
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + name);
        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
            ostream.close();
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBitmapFailed(Drawable arg0) {
    }
}
