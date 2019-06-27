package com.example.wang.day1.fragment;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.wang.day1.R;
import com.example.wang.day1.activity.TestActivity;
import com.example.wang.day1.net.ApiService;
import com.example.wang.day1.net.MyService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * okhttp上传文件
     */
    private Button bu1;
    /**
     * retrofit上传文件
     */
    private Button bu2;
    /**
     * server下载
     */
    private Button bu3;
    /**
     * 广播测试
     */
    private Button bu4;
    private ImageView f3_img;
    private MyBroadcast myBroadcast;
    private ProgressBar progressBar;
    private MyBroadcast myBroadcast1;

    public Fragment3() {
        // Required empty public constructor
    }

    private static final String TAG = "Fragment3";
    String uploadUrl = "http://yun918.cn/study/public/file_upload.php";

    public static String SERVER = "com.example.lianxi1.server";
    public static String TEST = "com.example.lianxi1.test";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = View.inflate(getContext(), R.layout.fragment_fragment3, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        bu1 = (Button) view.findViewById(R.id.bu1);
        bu1.setOnClickListener(this);
        bu2 = (Button) view.findViewById(R.id.bu2);
        bu2.setOnClickListener(this);
        bu3 = (Button) view.findViewById(R.id.bu3);
        bu3.setOnClickListener(this);
        bu4 = (Button) view.findViewById(R.id.bu4);
        bu4.setOnClickListener(this);
        f3_img = (ImageView) view.findViewById(R.id.f3_img);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        myBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SERVER);
        intentFilter.addAction(TEST);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(myBroadcast, intentFilter);



     /*   myBroadcast1 = new MyBroadcast();
        IntentFilter intentFilter1 = new IntentFilter();

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(myBroadcast1,intentFilter1);*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(myBroadcast);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(myBroadcast1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bu1:
                okhttpPost();
                break;
            case R.id.bu2:
                retrofitPost();
                break;
            case R.id.bu3:
                startServer();
                break;
            case R.id.bu4:
                Intent intent = new Intent();
                intent.setAction(TEST);
                intent.putExtra("test","我是广播事件");
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                break;
        }
    }

    private void startServer() {
        Intent intent = new Intent(getActivity(), MyService.class);
        getActivity().startService(intent);
    }

    private void retrofitPost() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "1811");

        String fileUrl = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "qwerqw.jpg";
        File file = new File(fileUrl);
        if (!file.exists()) {
            Log.d(TAG, "okhttpPost: 不存在");
            return;
        }
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part file1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);

        new Retrofit.Builder()
                .baseUrl(ApiService.uploadUrl)
                .build()
                .create(ApiService.class)
                .postImage(requestBody, file1)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        try {
                            String string = response.body().string();
                            JSONObject jsonObject = new JSONObject(string);
                            JSONObject data = jsonObject.getJSONObject("data");
                            final String url = data.optString("url");
                            Glide.with(getContext()).load(url).into(f3_img);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void okhttpPost() {
        OkHttpClient okHttpClient = new OkHttpClient();

        String fileUrl = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "qwerqw.jpg";
        File file = new File(fileUrl);
        if (!file.exists()) {
            Log.d(TAG, "okhttpPost: 不存在");
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody build = new MultipartBody.Builder()
                .addFormDataPart("file", file.getName(), requestBody)
                .addFormDataPart("key", "1811a")
                .setType(MultipartBody.FORM)
                .build();
        Request build1 = new Request.Builder()
                .url(uploadUrl)
                .post(build)
                .build();
        okHttpClient.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    final String url = data.optString("url");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getContext()).load(url).into(f3_img);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == SERVER) {
                int data = intent.getIntExtra("data", 0);
                progressBar.setProgress(data);
            }else if (intent.getAction() == TEST){
                String test = intent.getStringExtra("test");
                Intent intent1 = new Intent(getActivity(), TestActivity.class);
                intent1.putExtra("data",test);
                PendingIntent activity = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager systemService = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("通知")
                        .setContentText(test)
                        .setContentIntent(activity)
                        .setAutoCancel(true)
                        .build();
                systemService.notify(1,notification);
            }
        }
    }
}
