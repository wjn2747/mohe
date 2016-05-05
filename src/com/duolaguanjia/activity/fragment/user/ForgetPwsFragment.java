package com.duolaguanjia.activity.fragment.user;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app_sdk.tool.Util;
import com.duolaguanjia.R;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.tool.CountTimer;
import com.duolaguanjia.view.EditTextWithDel;

/**
 * Created by Administrator on 2015/12/31.
 */
public class ForgetPwsFragment  extends BaseFragment  implements View.OnClickListener{
    TitleManager titleManager;
    Button bt_send_code;
    EditTextWithDel et_phone;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_pws  , null, false);
        bt_send_code= (Button) view.findViewById(R.id.bt_send_code);
        view.findViewById(R.id.bt_ok).setOnClickListener(this);
        et_phone= (EditTextWithDel) view.findViewById(R.id.et_phone);
        //设置颜色
        et_phone.setHintTextColor(Color.argb(110, 153, 153, 153));
        bt_send_code.setOnClickListener(this);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("重置支付密码");
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_ok:
                //ok
                baseActivity.setResult(30);
                baseActivity.finish();
                break;
            case R.id.bt_send_code:
                //验证码
                String phone=et_phone.getText().toString();
                if (TextUtils.isEmpty(phone))
                {
                    baseActivity.DisPlay("请输入手机号");
                    return;
                }
                //判断手机号码是否正确
                if (!Util.isMobileNum(phone))
                {
                    baseActivity.DisPlay("请输入正确的手机号");
                    return;
                }
                //发送验证码
                CountTimer timeCount= new CountTimer(bt_send_code);
                timeCount.start();
                break;
        }
    }
}
