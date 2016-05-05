package com.duolaguanjia.activity.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.DensityUtil;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.LoginRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.WidgetInputItem;

/**
 * Created by Administrator on 2015/12/31.
 */
public class ResetPwsFragment extends BaseFragment {
    LinearLayout ll_parent;
    TitleManager titleManager;
    TextView tv_forget_pws;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_button  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("重置支付密码");
        ll_parent= (LinearLayout) view.findViewById(R.id.ll_parent);
        tv_forget_pws= (TextView) view.findViewById(R.id.tv_forget_pws);
        tv_forget_pws.setVisibility(View.VISIBLE);
        tv_forget_pws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(baseActivity,BranhActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("branchType", BranhActivity.BRANCH_USER_FORGET_PWS);
                intent.putExtras(bundle);
                baseActivity.startActivityForResult(intent,30);
            }
        });
        final WidgetInputItem widgetInputItem=new WidgetInputItem(baseActivity);
        widgetInputItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(baseActivity,45)));
        widgetInputItem.setLeftTxt("原密码");
        widgetInputItem.getEditText().setHint("请输入原密码");
        final WidgetInputItem  widgetInputItem_sure=new WidgetInputItem(baseActivity);
        widgetInputItem_sure.setLeftTxt("新密码");
        widgetInputItem_sure.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(baseActivity,45)));
        widgetInputItem_sure.getEditText().setHint("请输入6位新密码");
        final WidgetInputItem  widgetInputItem_sure_pws=new WidgetInputItem(baseActivity);
        widgetInputItem_sure_pws.setLeftTxt("再次确认");
        widgetInputItem_sure_pws.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(baseActivity,45)));
        widgetInputItem_sure_pws.getEditText().setHint("请再次输入6位新密码");
        widgetInputItem_sure_pws.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ll_parent.addView(widgetInputItem);
        ll_parent.addView(widgetInputItem_sure);
        ll_parent.addView(widgetInputItem_sure_pws);
        view.findViewById(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pws= widgetInputItem.getEditText().getText().toString();
                String config=widgetInputItem_sure.getEditText().getText().toString();
                String  sure_pws=widgetInputItem_sure_pws.getEditText().getText().toString();
                if (TextUtils.isEmpty(pws))
                {
                    baseActivity.DisPlay("请输入原密码");
                    return;
                }
                if (TextUtils.isEmpty(config))
                {
                    baseActivity.DisPlay("请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(sure_pws))
                {
                    baseActivity.DisPlay("请输入确认密码");
                    return;
                }
                if (!config.equalsIgnoreCase(sure_pws))
                {
                    baseActivity.DisPlay("二次输入密码不同");
                    return;
                }
                httpPutData(config,pws);

            }
        });
        return view;
    }

    /**
     * 提交支付密码
     */
    private  void httpPutData(final String config,final String  pws)
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        //新密码
        baseActivity.params.put("pass",config);
        //原密码
        baseActivity.params.put("rpass",pws);
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/paySecret",baseActivity.params, LoginRespone.class,new JsonObjectConve.HttpCallback<LoginRespone>() {
            @Override
            public void onResponse(String s, LoginRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    baseActivity. DisPlay(response.getMsg());
                    //保存密码
                    baseActivity.application.setPayPws(config);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==30)
        {
            //打开设置密码
            Bundle bundle=new Bundle();
            bundle.putInt("branchType",BranhActivity.BRANCH_USER_SETTING_PWS);
            baseActivity.openActivity(BranhActivity.class,bundle);
              baseActivity.finish();
        }
    }
}
