package com.duolaguanjia.activity.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.respone.MoHeGuaShiRespone;
import com.duolaguanjia.view.WidgetInputItem;

/**
 * Created by Administrator on 2015/12/31.
 */
public class GuaShiSettingFragment extends BaseFragment
{
    public final static  int   GUASHI=5;//挂失
    public final static  int  DELETEGUASHI=10;//解除挂失
    int type;
    TitleManager titleManager;
    LinearLayout ll_parent;
    String mac;
    public static Fragment newInstance(String  mac,int type) {
        GuaShiSettingFragment fragment = new GuaShiSettingFragment();
        Bundle args = new Bundle();
        args.putString("mac",mac);
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    private  void  httpGuaShi()
    {
        String pws=widgetInputItem.getText().toString();
        if (TextUtils.isEmpty(pws))
        {
            baseActivity.DisPlay("请输入支付密码!");
            return;
        }
        baseActivity.showDiaog();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.params.put("maccode",mac);
        if (type==GUASHI)
        {
            baseActivity.params.put("type","2");
        }else if (type==DELETEGUASHI)
        {
            baseActivity.params.put("type","1");
        }
        baseActivity.params.put("pass",pws);
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"refund/macDone",baseActivity.params, MoHeGuaShiRespone.class,new JsonObjectConve.HttpCallback<MoHeGuaShiRespone>() {
            @Override
            public void onResponse(String s, MoHeGuaShiRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null) {

                   baseActivity.DisPlay(response.getMsg());
                 if (response.getCode()==1)
                 {
                     baseActivity.finish();
                 }
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.hideDialog();
                baseActivity.DisPlay(msg);
            }
        });

    }
    WidgetInputItem widgetInputItem;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type=getArguments().getInt("type");
        mac=getArguments().getString("mac");
        View view = inflater.inflate(R.layout.fragment_view_button  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        ll_parent= (LinearLayout) view.findViewById(R.id.ll_parent);
       widgetInputItem=new WidgetInputItem(baseActivity);
        widgetInputItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(baseActivity,45)));
        widgetInputItem.setLeftTxt("支付密码");
        widgetInputItem.getEditText().setHint("请输入支付密码");
        ll_parent.addView(widgetInputItem);
        view.findViewById(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     httpGuaShi();
            }
        });


         if (type==GUASHI)
         {
             titleManager.setTitleName("挂失");
         }else if (type==DELETEGUASHI)
         {
             titleManager.setTitleName("解除挂失");
         }else
         {
             titleManager.setTitleName("解绑");
         }


        return view;
    }


}
