package com.study.duxiaodong.customimageloader;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 处理图片的类
 */
public class BitmapDispatcher extends Thread {

    private Handler handler = new Handler(Looper.getMainLooper());


    //定义队列
    private LinkedBlockingQueue<BitmapRequest> requestQueue;

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                BitmapRequest br = requestQueue.take();
                //设置占位图片
                showLoadingImg(br);

                //加载图片
                Bitmap bitmap = findBitmap(br);

                //把图片显示到imageview
                showImageView(br, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }

    private void showLoadingImg(BitmapRequest br) {
        if (br != null && br.getImageView() != null && br.getResId() > 0) {
            final int resId = br.getResId();
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });
        }
    }

    private Bitmap findBitmap(BitmapRequest br) {
        if (br == null || br.getUrl() == null) {
            return null;
        }
        Bitmap bitmap;
        bitmap = downloadBitmap(br.getUrl());
        return bitmap;
    }

    private void showImageView(BitmapRequest br, final Bitmap bitmap) {
        if (br != null  &&  br.getImageView() != null && br.getUrlMD5().equals(br.getImageView().getTag())) {
            if (br.getRequestListener()!=null){
                RequestListener requestListener= br.getRequestListener();
                if (bitmap==null){
                    requestListener.onFailed();
                }else {
                    requestListener.onSuccess(bitmap);
                }
            }

            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    private Bitmap downloadBitmap(String uri) {
        InputStream is = null;
        Bitmap bitmap = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }
}
