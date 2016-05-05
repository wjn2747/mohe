package com.duolaguanjia.activity.fragment.balance;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.duolaguanjia.R;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.EditTextWithDel;

/**
 * Created by Administrator on 2015/12/19.
 */
public class RechargeFragment extends BaseFragment  implements View.OnClickListener{
    TitleManager titleManager;
    EditTextWithDel et_input_content;
    Button bt_ok;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recharge  , null, false);
        bt_ok= (Button) view.findViewById(R.id.bt_ok);
        et_input_content= (EditTextWithDel) view.findViewById(R.id.et_input_content);
        et_input_content.setHintTextColor(Color.argb(110, 153, 153, 153));
        et_input_content.addTextChangedListener(new TextChange());
        bt_ok.setOnClickListener(this);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("充值");
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_ok:
                //PayDialog.showPayView(baseActivity,baseActivity);
                break;
            case  R.id.wm_yue:
                baseActivity.DisPlay("余额");
                break;
            case R.id.wm_zhifubao:
                baseActivity.DisPlay("支付宝");
                break;
            case R.id.wm_duolacz:
                baseActivity.DisPlay("朵拉支付");
                break;
            case  R.id.wm_bank:
                baseActivity.DisPlay("银行卡支付");
                break;
            case R.id.ll_add_new:
                baseActivity.DisPlay("使用新卡");
                break;
        }
    }


    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {
            boolean Sign1 = et_input_content.getText().length() > 0;
            if (Sign1) {
                bt_ok.setTextColor(0xFFFFFFFF);
                bt_ok.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                bt_ok.setTextColor(0xFFD0EFC6);
                bt_ok.setEnabled(false);
            }
        }
    }
}
