package com.guoyonghui.listviewlearning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.guoyonghui.listviewlearning.base.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private List<String> mDatas;

    private ImageListAdapter mAdapter;

    private Map<ImageView, String> mRequestMap;

    private ServiceAPI mAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDatas() {
        mDatas = new ArrayList<>();
        Collections.addAll(mDatas, getResources().getStringArray(R.array.images_paths));

        mAdapter = new ImageListAdapter(this);

        mRequestMap = new LinkedHashMap<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ooo.0o0.ooo/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mAPI = retrofit.create(ServiceAPI.class);
    }

    @Override
    protected void initViews() {
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);
    }

    private void downloadImage(final String imageUrl, final ImageView imageView) {
        mRequestMap.put(imageView, imageUrl);

        mAPI.download(imageUrl)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap call(ResponseBody responseBody) {
                        if (!imageUrl.equals(mRequestMap.get(imageView))) {
                            return null;
                        }
                        try {
                            byte[] data = responseBody.bytes();
                            return BitmapFactory.decodeByteArray(data, 0, data.length);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null && imageUrl.equals(mRequestMap.get(imageView))) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
    }

    private class ImageListAdapter extends ArrayAdapter<String> {

        public ImageListAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public String getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String imagePath = getItem(position);
            downloadImage(imagePath, holder.image);

            return convertView;
        }

        class ViewHolder {

            ImageView image;

        }
    }

}
