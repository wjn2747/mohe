package com.duolaguanjia.activity.fragment.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app_sdk.tool.Util;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.activity.LoginActivity;
import com.duolaguanjia.activity.ProcessWebViewActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.WidgetMenuItem;

/**
 * Created by Administrator on 2016/1/18.
 */
public class AboutFragment extends BaseFragment implements View.OnClickListener{
    TitleManager titleManager;
    WidgetMenuItem   wm_account_name,wm_accont,wm_sex;
    Button   bt_save;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("关于");
        bt_save= (Button) view.findViewById(R.id.bt_save);
        wm_sex= (WidgetMenuItem) view.findViewById(R.id.wm_sex);
        wm_account_name= (WidgetMenuItem) view.findViewById(R.id.wm_account_name);
        wm_accont= (WidgetMenuItem) view.findViewById(R.id.wm_accont);
        wm_account_name.setAccount(Util.getAppVersionName(baseActivity));
        wm_accont.setOnClickListener(this);
        wm_sex.setOnClickListener(this);
        bt_save.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
        if (view==bt_save)
        {
            //推出
            //清空密码
            baseActivity.application.outLogin();
            baseActivity.openActivity(LoginActivity.class);
        }
        if (view==wm_accont)
        {
            //反馈
            bundle.putInt("branchType", BranhActivity.BRANCH_FANKUI);
            baseActivity.openActivity(BranhActivity.class,bundle);
        }
        if (view==wm_sex)
        {
            //关于我们
            bundle.putString("url", AppApplication.HOST+"user/aboutUs");
            bundle.putString("title","关于我们");
            baseActivity.openProcessActivity(ProcessWebViewActivity.class,bundle);
        }

    }
}
