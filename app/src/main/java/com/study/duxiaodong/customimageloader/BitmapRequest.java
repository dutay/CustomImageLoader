package com.study.duxiaodong.customimageloader;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

/**
 * 图片请求封装类
 */
public class BitmapRequest {

    //上下文
    private Context context;

    //占位图
    private int resId;

    //需要加载图片的控件
    private SoftReference<ImageView> imageView;

    //图片url
    private String url;

    //回调对象
    private RequestListener requestListener;

    //图片标识
    private String urlMD5;

    public BitmapRequest(Context context) {
        this.context = context;
    }



    public int getResId() {
        return resId;
    }


    public ImageView getImageView() {
        return imageView.get();
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }


    public String getUrlMD5() {
        return urlMD5 == null ? "" : urlMD5;
    }


    //加载网络url
    public BitmapRequest load(String url) {
        this.url = url;
        this.urlMD5 = MD5Utils.getMd5(url);
        return this;
    }

    //设置占位图
    public BitmapRequest loading(int resId) {
        this.resId = resId;
        return this;
    }

    //设置监听
    public BitmapRequest listener(RequestListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    //将图片显示到imageview控件上
    public void into(ImageView imageView) {
        imageView.setTag(this.urlMD5);
        this.imageView = new SoftReference<>(imageView);
        //将请求添加到框架之中
        RequestManage.getInstance().addBitmapRequest(this);
    }


}
