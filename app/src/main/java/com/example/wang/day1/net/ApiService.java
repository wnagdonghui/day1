package com.example.wang.day1.net;


import com.example.wang.day1.bean.BannerBean;
import com.example.wang.day1.bean.Bean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    //http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1
    String itemUrl="http://gank.io/api/data/";
    @GET("%E7%A6%8F%E5%88%A9/20/{page}")
    Observable<Bean> getDataItem(@Path("page") int page);


    //https://www.wanandroid.com/banner/json
    String bannerUrl="https://www.wanandroid.com/";
    @GET("banner/json")
    Call<BannerBean> getDataBanner();


    String uploadUrl = "http://yun918.cn/";
    @POST("study/public/file_upload.php")
    @Multipart
    Call<ResponseBody>  postImage(@Part("key") RequestBody requestBody, @Part MultipartBody.Part mu);
}
