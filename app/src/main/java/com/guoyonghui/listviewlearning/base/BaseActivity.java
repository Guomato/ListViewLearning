package com.guoyonghui.listviewlearning.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        initDatas();
        initViews();
    }

    protected abstract int getLayoutResId();

    protected abstract void initDatas();

    protected abstract void initViews();

}
