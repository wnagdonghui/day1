package com.example.wang.day1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wang.day1.R;
import com.example.wang.day1.activity.BaiduActivity;
import com.example.wang.day1.bean.BannerBean;
import com.example.wang.day1.bean.ResultsBean;
import com.example.wang.day1.util.Utile;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Fragment fragment;
    private List<ResultsBean> list=new ArrayList<>();
    private List<BannerBean.DataBean> bannerList=new ArrayList<>();
    private PopupWindow popupWindow;
    private int index;

    public RecyclerViewAdapter(Fragment fragment, PopupWindow popupWindow) {
        this.fragment = fragment;
        this.popupWindow = popupWindow;
    }

    public RecyclerViewAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void initDataItem(List<ResultsBean> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void moreData(List<ResultsBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void initDataBanner(List<BannerBean.DataBean> bannerList){
        this.bannerList=bannerList;
        notifyDataSetChanged();
    }

    public void deleteItem(){
        list.remove(index);
        notifyDataSetChanged();
    }

    public void collectItem(){
        Utile.addItem(list.get(index));
        Toast.makeText(fragment.getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
    }

    public void deleteDao(){
        Utile.deleteItem(list.get(index));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==1){
            View view = View.inflate(fragment.getContext(), R.layout.recycycler_banner, null);
            return  new ViewHolder1(view);
        }else {
            View view = View.inflate(fragment.getContext(), R.layout.recycler_item, null);
            return new ViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        if (type==1){
            final ViewHolder1 holder1= (ViewHolder1) viewHolder;
            holder1.banner.setImages(bannerList).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    BannerBean.DataBean bean= (BannerBean.DataBean) path;
                    Glide.with(fragment.getContext()).load(bean.getImagePath()).into(imageView);
                }
            }).start();
        }else {
            if (bannerList.size()>0){
                i--;
            }
            ViewHolder2 holder2= (ViewHolder2) viewHolder;
            holder2.id.setText(list.get(i).get_id());
            RequestOptions requestOptions;
            if (i%2==0){
                requestOptions = RequestOptions.circleCropTransform();
            }else {
                RoundedCorners roundedCorners = new RoundedCorners(20);
                requestOptions = RequestOptions.bitmapTransform(roundedCorners);
            }
            Glide.with(fragment.getContext()).load(list.get(i).getUrl()).apply(requestOptions).into(holder2.img);


            final int finalI = i;
            holder2.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    index= finalI;
                    if (popupWindow!=null){
                        Intent intent = new Intent(fragment.getActivity(), BaiduActivity.class);
                        fragment.startActivity(intent);
                    }
                    return false;
                }
            });

            holder2.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    index= finalI;
                    popupWindow.showAtLocation(v, Gravity.CENTER,0,0);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (bannerList.size()>0){
            return list.size()+1;
        }else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (bannerList.size()>0&&position==0){
            return 1;
        }else {
            return 2;
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        Banner banner;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            banner=itemView.findViewById(R.id.rec_banner);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView img;
        TextView id;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.rec_img);
            id=itemView.findViewById(R.id.rec_tv);
        }
    }
}
