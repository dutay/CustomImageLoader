package com.study.duxiaodong.customimageloader;

import android.graphics.Bitmap;

interface RequestListener {

    boolean onSuccess(Bitmap bitmap);

    boolean onFailed();


}
