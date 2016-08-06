package com.guoyonghui.listviewlearning.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

public class CustomListView extends ListView {

    private static final String TAG = CustomListView.class.getSimpleName();

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout called");

        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void layoutChildren() {
        Log.d(TAG, "layoutChildren called");
        Log.d(TAG, "Child count: " + getChildCount());

        super.layoutChildren();
    }
}
