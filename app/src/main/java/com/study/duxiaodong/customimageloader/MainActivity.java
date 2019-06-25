package com.study.duxiaodong.customimageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView  = findViewById(R.id.img);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(MainActivity.this).loading(R.mipmap.ic_launcher).load("http://img5.imgtn.bdimg.com/it/u=3059747916,3445537491&fm=26&gp=0.jpg").into(imageView);
            }
        });


    }
}
