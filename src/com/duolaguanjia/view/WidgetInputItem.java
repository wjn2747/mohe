package com.duolaguanjia.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.duolaguanjia.R;

/**
 * Created by Administrator on 2015/12/18.
 */
public class WidgetInputItem  extends LinearLayout
{
    private Context mContext;
    EditTextWithDel et_input_content;
    TextView  titleLbl;
    TextView tv_right_val;
    View view_line;
    ImageView widget_menuitem_icon;
    public WidgetInputItem(Context context)
    {
        super(context);
        mContext = context;
        setBackgroundColor(Color.WHITE);
        initViews();
        et_input_content.setHintTextColor(Color.argb(110, 153, 153, 153));
        et_input_content.setTextColor(Color.argb(255, 153, 153, 153));
    }
    public void setNoOnClick()
    {
        this.et_input_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                 onClick(view);
            }
        });
    }

    public WidgetInputItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setBackgroundColor(Color.WHITE);
        initViews();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.InputView);
        String menu_txt = a.getString(R.styleable.InputView_menu_txt);//中间文字内容
        String input_hile = a.getString(R.styleable.InputView_input_hile);
        String left_txt = a.getString(R.styleable.InputView_left_txt);
        String right_txt = a.getString(R.styleable.InputView_right_txt);
        Drawable drawable = a.getDrawable(R.styleable.InputView_left_icon);
        boolean checkable = a.getBoolean(R.styleable.InputView_isline, true);
        boolean enable = a.getBoolean(R.styleable.InputView_enable, true);
        if (!checkable)
        {
            view_line.setVisibility(GONE);
        }

        if (drawable!=null)
        {
            widget_menuitem_icon.setVisibility(VISIBLE);
        }else {
            widget_menuitem_icon.setVisibility(GONE);
        }
        widget_menuitem_icon.setImageDrawable(drawable);
        if (!TextUtils.isEmpty(menu_txt))
        {
            et_input_content.setTextColor(Color.argb(255, 153, 153, 153));
            et_input_content.setText(menu_txt);
        }
        if (!TextUtils.isEmpty(input_hile))
        {
            et_input_content.setHintTextColor(Color.argb(110, 153, 153, 153));
            et_input_content.setHint(input_hile);
        }
        if (!TextUtils.isEmpty(left_txt))
        {
            titleLbl.setText(left_txt);
        }
        if (!TextUtils.isEmpty(right_txt))
        {
            et_input_content.setVisibility(GONE);
            tv_right_val.setVisibility(VISIBLE);
            tv_right_val.setText(right_txt);
        }
        if (!enable)
        {
            et_input_content.setEnabled(false);
            et_input_content.setFocusable(false);
            et_input_content.setHintTextColor(Color.argb(255, 153, 153, 153));
            et_input_content.setHint(input_hile);
        }
        a.recycle();
    }
    public  void  setLeftTxt(String  txt)
    {
        titleLbl.setText(txt);
    }
public  void  setRightTxt(String right_txt)
{
    if (!TextUtils.isEmpty(right_txt))
    {
        et_input_content.setVisibility(GONE);
        tv_right_val.setVisibility(VISIBLE);
        tv_right_val.setText(right_txt);
    }

}
    public  EditTextWithDel  getEditText()
    {
        return et_input_content;
    }

    private void initViews() {
        View.inflate(mContext, R.layout.widgrt_input_item, this);
        titleLbl = (TextView) findViewById(R.id.tv_input_name);
        et_input_content= (EditTextWithDel) findViewById(R.id.et_input_content);
        tv_right_val=(TextView) findViewById(R.id.tv_right_val);
        widget_menuitem_icon= (ImageView) findViewById(R.id.widget_menuitem_icon);
        view_line=findViewById(R.id.view_line);
    }
    public   void  setText(String  txt)
    {
        et_input_content.setText(txt);
    }

    public String getText()
    {
        return  et_input_content.getText().toString().trim();
    }

    public void addTextChangedListener(TextWatcher textChange)
    {
        et_input_content.addTextChangedListener(textChange);
    }
}
