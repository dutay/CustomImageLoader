package com.study.duxiaodong.customimageloader;


import java.util.concurrent.LinkedBlockingQueue;

public class RequestManage {

    private static RequestManage requestManage = new RequestManage();
    //创建队列 存放bitmap请求
    private LinkedBlockingQueue<BitmapRequest> requestQueue = new LinkedBlockingQueue<>();
    //定义"柜台"数组
    private BitmapDispatcher[] dispatchers;


    private RequestManage() {
        stop();
        startAllDispatcher();
    }

    private void stop() {
        if (dispatchers != null && dispatchers.length > 0) {
            for (BitmapDispatcher bitmapDispatcher : dispatchers) {
                bitmapDispatcher.interrupt();
            }
        }
    }

    private void startAllDispatcher() {
        //获取手机支持的单个应用最大线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        dispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.start();

            dispatchers[i] = bitmapDispatcher;
        }
    }

    public static RequestManage getInstance() {
        return requestManage;
    }

    //将图片请求添加到队列中
    public void addBitmapRequest(BitmapRequest bitmapRequest) {
        if (bitmapRequest == null) {
            return;
        }

        if (!requestQueue.contains(bitmapRequest)) {
            requestQueue.add(bitmapRequest);
        }

    }


}
