package com.example.tan.finalproject.utilities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Tan on 5/9/2017.
 */

public class ImageUtils {
    Context context;
    ImageView ivAvatar;

    public ImageUtils(Context context, ImageView ivAvatar) {
        this.context = context;
        this.ivAvatar = ivAvatar;
    }

    private void takeImageFromGallery(Intent data) {
        Uri pickedImage = data.getData();
        // Let's read picked image path using content resolver
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        ivAvatar.setImageBitmap(bitmap);
        bitmap.recycle();
        cursor.close();
    }
    private void takeImageFromCamera(Intent data) {
        ivAvatar.setImageBitmap(null);
        Bitmap bmp = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        // convert byte array to Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);
        ivAvatar.setImageBitmap(bitmap);
        bmp.recycle();
    }
}
