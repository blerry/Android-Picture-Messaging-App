package com.example.messagingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowCaptureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_capture);
        //construct image in bitmap
        //get image from bundle

        Bundle extras = getIntent().getExtras();
        assert extras != null; //in case you send something that is null
        byte[] b = extras.getByteArray("capture"); //move variable from main to capture activity

        if(b != null){//to avoid app stall/crash
            ImageView image = findViewById(R.id.imageCaputured);

            //convert bytes to bitmap
            Bitmap decodeBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

            //image is distorted and different angle we have to rotate
            //rotate bitmap

            Bitmap rotateBitmap = rotate(decodeBitmap);


            image.setImageBitmap(decodeBitmap);


        }
    }

    private Bitmap rotate(Bitmap decodeBitmap) {
        int width = decodeBitmap.getWidth();
        int height = decodeBitmap.getHeight();

        Matrix matrix = new Matrix();//saying how much to rotate
        matrix.setRotate(90);

        return Bitmap.createBitmap(decodeBitmap, 0, 0, width,height,matrix,true);
    }

}