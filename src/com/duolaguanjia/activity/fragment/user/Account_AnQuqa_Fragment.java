package com.duolaguanjia.activity.fragment.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.WidgetMenuItem;

/**
 * Created by Administrator on 2015/12/31.
 */
public class Account_AnQuqa_Fragment extends BaseFragment  implements View.OnClickListener{
    TitleManager titleManager;
    WidgetMenuItem wm_setting_pws,wm_config_pws;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accout_anquan  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("账户安全");
        wm_setting_pws= (WidgetMenuItem) view.findViewById(R.id.wm_setting_pws);
        wm_setting_pws.setOnClickListener(this);
        wm_config_pws= (WidgetMenuItem) view.findViewById(R.id.wm_config_pws);
        wm_config_pws.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        if (!TextUtils.isEmpty(baseActivity.application.getPayPws()) && !baseActivity.application.getPayPws().equalsIgnoreCase("0"))
        {
            wm_setting_pws.setVisibility(View.GONE);
            wm_config_pws.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @Override
    public void onClick(View view)
    {
        Bundle bundle=new Bundle();
        switch (view.getId())
        {
            case R.id.wm_setting_pws:
                bundle.putInt("branchType",BranhActivity.BRANCH_USER_SETTING_PWS);
                break;
            case R.id.wm_config_pws:
                bundle.putInt("branchType",BranhActivity.BRANCH_USER_CLEAR_PWS);
                break;
        }
        baseActivity.openActivity(BranhActivity.class,bundle);

    }
}
