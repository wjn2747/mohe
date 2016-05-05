package com.duolaguanjia.activity.fragment.bank;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.app_sdk.tool.Util;
import com.duolaguanjia.R;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.tool.CountTimer;
import com.duolaguanjia.view.EditTextWithDel;

/**
 * Created by Administrator on 2015/12/18.
 */
public class AddBankFragment extends BaseFragment  implements View.OnClickListener
{
    TitleManager titleManager;
    TextView tv_xieyi;
    Button bt_send_code;
    EditTextWithDel et_phone;
    String xieyi = "<font color=" + "\"" + "#AAAAAA" + "\">" + "点击上面的"
            + "\"" + "确定" + "\"" + "按钮,即表示你同意" + "</font>" + "<u>"
            + "<font color=" + "\"" + "#576B95" + "\">" + "《朵拉用户协议》"
            + "</font>" + "</u>";
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_bank  , null, false);
        tv_xieyi= (TextView) view.findViewById(R.id.tv_xieyi);
        bt_send_code= (Button) view.findViewById(R.id.bt_send_code);
        et_phone= (EditTextWithDel) view.findViewById(R.id.et_phone);
        //设置颜色
        et_phone.setHintTextColor(Color.argb(110, 153, 153, 153));
        bt_send_code.setOnClickListener(this);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("添加银行卡");
        tv_xieyi.setText(Html.fromHtml(xieyi));
        return  view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
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
