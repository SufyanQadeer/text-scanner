package com.ltapps.textscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;


public class CropAndRotate extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener{
    private Toolbar toolbar;
    private FloatingActionButton mFab;
    public static Bitmap croppedImage;
    private String message;
    CropImageView cropImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_and_rotate);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        cropImageView = (CropImageView) findViewById(R.id.cropImageView);

        try {
            cropImageView.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(message))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mFab = (FloatingActionButton) findViewById(R.id.nextStep);
        mFab.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextStep){
            cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
                @Override
                public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                    croppedImage = result.getBitmap();
                    Intent intent = new Intent(CropAndRotate.this, Umbralization.class);
                    startActivity(intent);
                }
            });
            cropImageView.getCroppedImageAsync();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rotate, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rotate_left:
                cropImageView.rotateImage(-90);
                break;
            case R.id.rotate_right:
                cropImageView.rotateImage(90);
                break;
        }
        return false;
    }
}
