package com.duolaguanjia.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.duolaguanjia.R;

/**
 * Created by Administrator on 2015/12/29.
 */
public class BackMoneyLoding  extends LinearLayout {
    private Context mContext;
    ImageView iv_back_submit,iv_back_kefu,iv_back_success;
    View v_left_line,v_line_right;
    TextView tv_back_success;
    public enum BackStatic
    {
        NOT_CHULI,OK_CHULI,BACK_SUCCESS,BACK_FAIL
    }

    public BackMoneyLoding(Context context) {
        super(context);
        init(context);
    }

    public BackMoneyLoding(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BackMoneyLoding(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private  void  init(Context context)
    {
        mContext = context;
        View.inflate(mContext, R.layout.view_back_meney, this);
        iv_back_submit= (ImageView) findViewById(R.id.iv_back_submit);
        iv_back_kefu= (ImageView) findViewById(R.id.iv_back_kefu);
        tv_back_success= (TextView) findViewById(R.id.tv_back_success);
        iv_back_success= (ImageView) findViewById(R.id.iv_back_success);
        v_left_line=findViewById(R.id.v_left_line);
        v_line_right=findViewById(R.id.v_line_right);
    }
    public  void  setStaticType(BackStatic backStatic)
    {
        switch (backStatic)
        {
            case  NOT_CHULI:
                //客服未处理

                break;
            case OK_CHULI:
                //客服已处理
                iv_back_kefu.setImageResource(R.drawable.customer_service);
                v_line_right.setBackgroundColor(Color.parseColor("#ECB033"));
                break;
            case BACK_SUCCESS:
                //退款成功
                iv_back_success.setImageResource(R.drawable.complete);
                v_line_right.setBackgroundColor(Color.parseColor("#ECB033"));
                break;
            case  BACK_FAIL:
                //失败
                tv_back_success.setText("退款失败");
                iv_back_success.setImageResource(R.drawable.complete);
                v_line_right.setBackgroundColor(Color.parseColor("#ECB033"));
                break;
        }
    }
}
