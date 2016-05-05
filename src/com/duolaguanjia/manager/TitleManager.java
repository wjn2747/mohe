package com.duolaguanjia.manager;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.duolaguanjia.R;

/**
 * Created by Administrator on 2015/12/17.
 */
public class TitleManager  implements View.OnClickListener

{
    private LinearLayout ll_other;//其他页面
    ImageView iv_return;
    Activity activity;
    TextView tv_title_name;
    Button tv_right_button;
    public TitleManager(View view, Activity activity)
    {
        ll_other= (LinearLayout) view.findViewById(R.id.ll_other);
        iv_return= (ImageView) view.findViewById(R.id.iv_return);
        tv_title_name= (TextView) view.findViewById(
                R.id.tv_title_name
        );
        tv_right_button= (Button) view.findViewById(R.id.tv_right_button);
        iv_return.setOnClickListener(this);
        tv_right_button.setOnClickListener(this);
        ll_other.setVisibility(View.VISIBLE);
        this.activity=activity;
    }
    public  void  showRightTxt(String  rightTxt)
    {
        if (TextUtils.isEmpty(rightTxt))
        {
            tv_right_button.setVisibility(View.GONE);
        }else {
            tv_right_button.setVisibility(View.VISIBLE);
            tv_right_button.setText(rightTxt);
        }

    }
    View.OnClickListener onClick;
    View.OnClickListener  left_onClick;
    public  void  setRightOnClick(View.OnClickListener onClick)
    {
        this.onClick=onClick;
    }
    public  void setLeftOnClick(View.OnClickListener left_onClick)
    {
        this.left_onClick=left_onClick;
    }
    public  void  setTitleName(String title)
    {

        tv_title_name.setText(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  R.id.iv_return:
                //返回
                if (left_onClick!=null)
                {
                    left_onClick.onClick(view);
                }else {
                    activity.finish();
                }
                break;
            case R.id.tv_right_button:
                //右边按钮
              if (onClick!=null)
              {
                  onClick.onClick(view);
              }
                break;
        }
    }
}
