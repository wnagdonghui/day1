package com.example.wang.day1.net;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.EventListener;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        startDownload();
                    }
                }
        ).start();
    }

    private void startDownload() {
        try {
            String fileUrl= Environment.getExternalStorageDirectory()+ File.separator +"UnknowApp-1.0.apk";
            File file = new File(fileUrl);
            if (!file.exists()){
                file.createNewFile();
            }
            URL url = new URL("http://yun918.cn/study/public/res/UnknowApp-1.0.apk");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int contentLength = urlConnection.getContentLength();
            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024*8];
            int len=0;
            int longLen=0;
            while ((len=inputStream.read(bytes))!=-1){
                fileOutputStream.write(bytes,0,len);
                longLen+=len;
                int a=longLen*100/contentLength;

                Intent intent = new Intent();
                intent.setAction("com.example.lianxi1.server");
                intent.putExtra("data",a);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
