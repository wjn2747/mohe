package com.duolaguanjia.activity.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.app_sdk.ok_http.JsonObjectConve;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BindMoheActivity;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.activity.ProcessWebViewActivity;
import com.duolaguanjia.activity.fragment.user.GuaShiFragment;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.UserInfoRespone;
import com.duolaguanjia.view.WidgetMenuItem;

/**
 * Created by Night on 2015/11/2.
 */
public class UserFragment extends BaseFragment  implements View.OnClickListener
{
    View   view;
    RelativeLayout rl_user_setting;
    TextView tv_name,tv_phone,tv_yue;
    ImageView user_item_iv_avatar;
    WidgetMenuItem  widgt_help,widgt_wifi,widgt_about,widgt_account_anquan,widgt_address,widgt_bing,widgt_over,widgt_weixiu;



    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_my, container, false);
        rl_user_setting= (RelativeLayout) view.findViewById(R.id.rl_user_setting);
        widgt_account_anquan= (WidgetMenuItem) view.findViewById(R.id.widgt_account_anquan);
        widgt_address= (WidgetMenuItem) view.findViewById(R.id.widgt_address);
        widgt_wifi= (WidgetMenuItem) view.findViewById(R.id.widgt_wifi);
        widgt_bing= (WidgetMenuItem) view.findViewById(R.id.widgt_bing);
        user_item_iv_avatar= (ImageView) view.findViewById(R.id.user_item_iv_avatar);
        widgt_over= (WidgetMenuItem) view.findViewById(R.id.widgt_over);
        widgt_help= (WidgetMenuItem) view.findViewById(R.id.widgt_help);
        widgt_about= (WidgetMenuItem) view.findViewById(R.id.widgt_about);
        widgt_weixiu= (WidgetMenuItem) view.findViewById(R.id.widgt_weixiu);
        tv_phone= (TextView) view.findViewById(R.id.tv_phone);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
        tv_yue= (TextView) view.findViewById(R.id.tv_yue);
        rl_user_setting.setOnClickListener(this);
        widgt_help.setOnClickListener(this);
        widgt_wifi.setOnClickListener(this);
        widgt_weixiu.setOnClickListener(this);
        widgt_about.setOnClickListener(this);
        widgt_address.setOnClickListener(this);
        widgt_account_anquan.setOnClickListener(this);
        widgt_bing.setOnClickListener(this);
        widgt_over.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        httpGetData();
        super.onResume();
    }

    private void httpGetData()
    {
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/personEdit",baseActivity.params, UserInfoRespone.class,new JsonObjectConve.HttpCallback<UserInfoRespone>() {
            @Override
            public void onResponse(String s, UserInfoRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null&& response.getData()!=null)
                {
                    //设置昵称
                    tv_name.setText(response.getData().getCust_realname());
                    tv_phone.setText(baseActivity.application.getUserMoble());
                    //余额
                    tv_yue.setText(response.getData().getMon()+"");                    //头像
                    if (!TextUtils.isEmpty(response.getData().getCust_img()))
                    {
                        //下载头像
                        baseActivity.imageLoader.displayImage(response.getData().getCust_img(),user_item_iv_avatar );
                    }
                }
            }

            @Override
            public void onError(String msg) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
          switch (view.getId())
          {
              case R.id.widgt_wifi:
                  //配置wifi
                  bundle.putInt("type",10);
                   baseActivity.openActivity(BindMoheActivity.class,bundle);
                  return;
              case R.id.widgt_about:
                  bundle.putInt("branchType", BranhActivity.BRANCH_ABOUT);
                  break;
              case R.id.widgt_weixiu:
                  bundle.putInt("type", GuaShiFragment.WEIXIU);
                  bundle.putInt("branchType", BranhActivity.BRANCH_USER_GUASHI);
                  break;
              case R.id.widgt_over:
                  bundle.putInt("branchType", BranhActivity.BRANCH_USER_GUASHI);
                  break;
              case R.id.rl_user_setting:
                  bundle.putInt("branchType", BranhActivity.BRANCH_USER_SETTING);
                  break;
              case R.id.widgt_account_anquan:
                  bundle.putInt("branchType",BranhActivity.BRANCH_USER_ACCOUNT_ANQUAN);
                  break;
              case R.id.widgt_address:
                  bundle.putString("title","地址");
                  bundle.putInt("branchType",BranhActivity.BRANCH_USER_ADD_ADDRESS);
                  break;
              case R.id.widgt_bing:
                  //魔盒
                  baseActivity.openActivity(BindMoheActivity.class);
                  return;
              case R.id.widgt_help:
                  //帮助
                  //用户协议
                  bundle.putString("url", AppApplication.HOST+"user/myhelp");
                  bundle.putString("title","用户帮助");
                  baseActivity.openProcessActivity(ProcessWebViewActivity.class,bundle);
                  return;
          }
        baseActivity.openActivity(BranhActivity.class,bundle);
    }
}
