package com.duolaguanjia.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import com.litesuits.android.async.Log;

/**
 * Created by Administrator on 2016/1/19.
 */
public class NoFoucsEditView extends EditText {
    public NoFoucsEditView(Context context) {
        super(context);
    }

    public NoFoucsEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoFoucsEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("onTouchEvent","onTouchEvent");
        return true;
    }
}
