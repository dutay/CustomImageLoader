package com.study.duxiaodong.customimageloader;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView  = findViewById(R.id.img);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(MainActivity.this)
                        .loading(R.mipmap.ic_launcher)
                        .load("http://img5.imgtn.bdimg.com/it/u=3059747916,3445537491&fm=26&gp=0.jpg")
                        .listener(new RequestListener() {
                            @Override
                            public boolean onSuccess(Bitmap bitmap) {
                                Log.d(TAG,"图片加载成功回调"+bitmap);
                                return false;
                            }

                            @Override
                            public boolean onFailed() {
                                Log.d(TAG,"图片加载失败回调");
                                return false;
                            }
                        })
                        .into(imageView);
            }
        });


    }
}
