package com.duolaguanjia.activity.fragment.user;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.DensityUtil;
import com.duolaguanjia.R;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.LoginRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.WidgetInputItem;

/**
 * Created by Administrator on 2015/12/31.
 */
public class SettingPwsFragment extends BaseFragment
{
    LinearLayout ll_parent;
    TitleManager titleManager;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_button  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("账户安全");
        ll_parent= (LinearLayout) view.findViewById(R.id.ll_parent);
        final WidgetInputItem  widgetInputItem=new WidgetInputItem(baseActivity);
        widgetInputItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(baseActivity,45)));
        widgetInputItem.setLeftTxt("设置密码");
        widgetInputItem.getEditText().setHint("请输入6位支付密码");
        final WidgetInputItem  widgetInputItem_sure=new WidgetInputItem(baseActivity);
        widgetInputItem_sure.setLeftTxt("确认密码");
        widgetInputItem_sure.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(baseActivity,45)));
        widgetInputItem_sure.getEditText().setHint("请再次输入6位支付密码");
        widgetInputItem_sure.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ll_parent.addView(widgetInputItem);
        ll_parent.addView(widgetInputItem_sure);
        view.findViewById(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String pws= widgetInputItem.getEditText().getText().toString();
                String config=widgetInputItem_sure.getEditText().getText().toString();
                if (TextUtils.isEmpty(pws) || TextUtils.isEmpty(config))
                {
                    baseActivity.DisPlay("请输入密码");
                    return;
                }
                //小于6
                if (pws.length()<6)
                {
                    baseActivity.DisPlay("密码长度不能小于6位");
                    return;
                }
                if (!pws.equalsIgnoreCase(config))
                {
                    baseActivity.DisPlay("二次输入密码不同");
                    return;
                }
                //提交服务器
                httpPutData(pws);
            }
        });
        return view;
    }

    /**
     * 提交支付密码
     */
    private  void httpPutData(final String  pws)
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        //新密码
        baseActivity.params.put("pass",pws);
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/paySecret",baseActivity.params, LoginRespone.class,new JsonObjectConve.HttpCallback<LoginRespone>() {
            @Override
            public void onResponse(String s, LoginRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    baseActivity. DisPlay(response.getMsg());
                    //保存密码
                    baseActivity.application.setPayPws(pws);
                    baseActivity.finish();
                }else {
                    baseActivity. DisPlay("设置密码失败!!");
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity. DisPlay(msg);
                baseActivity. hideDialog();
            }
        });
    }


}
