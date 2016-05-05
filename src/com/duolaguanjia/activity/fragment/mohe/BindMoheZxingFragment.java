package com.duolaguanjia.activity.fragment.mohe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app_sdk.ok_http.JsonObjectConve;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BindMoheActivity;
import com.duolaguanjia.activity.CaptureActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.CodeRespone;
import com.duolaguanjia.view.WidgetInputItem;

/**
 * Created by Administrator on 2015/12/23.
 */
public class BindMoheZxingFragment extends BaseFragment  implements View.OnClickListener
{
    Button bt_zxing,bt_next;
    WidgetInputItem wi_address_descript;
    public final static int SCANNIN_GREQUEST_CODE = 1;// 条码扫描的返回码
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_mohe_zxing  , null, false);
        bt_zxing= (Button) view.findViewById(R.id.bt_zxing);
        bt_next= (Button) view.findViewById(R.id.bt_next);
        wi_address_descript= (WidgetInputItem) view.findViewById(R.id.wi_address_descript);
        bt_zxing.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SCANNIN_GREQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getBundleExtra("bundle");
                String resultString = bundle.getString("result");
                //设置
                wi_address_descript.getEditText().setText(resultString);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.bt_zxing:
                Intent intent = new Intent();
                intent.setClass(baseActivity,
                        CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                baseActivity.startActivityForResult(intent,
                       SCANNIN_GREQUEST_CODE);
                break;
            case R.id.bt_next:
                //完成
                //绑定码是否输入
             String code=   wi_address_descript.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(code))
                {
                    baseActivity.DisPlay("请输入机器绑定码!");
                    return;
                }
                //关闭软键盘
                baseActivity.hideKey();
                //绑定码魔盒绑定
                codeBindMohe(code);
                break;
        }
    }
    //绑定码魔盒绑定
    private  void  codeBindMohe(String bdcode)
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
//        baseActivity.params.put("mac",((BindMoheActivity)baseActivity).mac);
        //bdcode [string] [绑定码]
        baseActivity.params.put("bdcode",bdcode);
        if ( ((BindMoheActivity)baseActivity).type!=10)
        {
            baseActivity.params.put("checkbox","1");
        }

        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        ((BindMoheActivity)baseActivity).jiqiCode=bdcode;
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/bindBox",baseActivity.params, CodeRespone.class,new JsonObjectConve.HttpCallback<CodeRespone>() {
            @Override
            public void onResponse(String s, CodeRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    if (response.getCode()!=1)
                    {
                         baseActivity.DisPlay(response.getMsg());
                    }else {
                       //绑定成功
                        baseActivity.DisPlay("绑定成功,请激活魔盒wifi!");
                        ((BindMoheActivity)baseActivity).next(0);
                    }
                }else {
                    baseActivity.DisPlay("绑定机器失败!");
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.hideDialog();
                baseActivity.DisPlay(msg);
            }
        });
    }
}
