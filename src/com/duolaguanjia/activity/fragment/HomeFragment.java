package com.duolaguanjia.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.app_sdk.view.LamaAdViewPage;
import com.app_sdk.view.SourcePanel;
import com.app_sdk.view.bean.TopSlideBean;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.*;
import com.duolaguanjia.adapter.MainGridAdapter;
import com.duolaguanjia.base.BaseFragment;
import java.util.ArrayList;

/**
 * Created by Night on 2015/11/1.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener{
    LamaAdViewPage lamaAdViewPage;
    SourcePanel sp_scroll;
    TextView tv_left_txt,tv_yue,tv_bank,tv_band_mohe,tv_right_txt;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home  , null, false);
        view.findViewById(R.id.rl_home_title).setVisibility(View.VISIBLE);
        lamaAdViewPage= (LamaAdViewPage) view.findViewById(R.id.ll_ad);
        tv_left_txt= (TextView) view.findViewById(R.id.tv_left_txt);
        tv_yue= (TextView) view.findViewById(R.id.tv_yue);
        tv_bank= (TextView) view.findViewById(R.id.tv_bank);
        tv_right_txt= (TextView) view.findViewById(R.id.tv_right_txt);
        tv_band_mohe= (TextView) view.findViewById(R.id.tv_band_mohe);
        tv_yue.setOnClickListener(this);
        tv_bank.setOnClickListener(this);
        tv_band_mohe.setOnClickListener(this);
        tv_right_txt.setOnClickListener(this);
        tv_left_txt.setVisibility(View.GONE);
        sp_scroll= (SourcePanel) view.findViewById(R.id.sp_scroll);
        ArrayList<TopSlideBean> listAdvert2=new ArrayList<TopSlideBean>();
            TopSlideBean topSlideBean=new TopSlideBean();
            topSlideBean.setImage_id(R.drawable.banner_one);
            listAdvert2.add(topSlideBean);
        TopSlideBean topSlideBean2=new TopSlideBean();
        topSlideBean2.setImage_id(R.drawable.banner_two);
        listAdvert2.add(topSlideBean2);
        TopSlideBean topSlideBean3=new TopSlideBean();
        topSlideBean3.setImage_id(R.drawable.banner_three);
        listAdvert2.add(topSlideBean3);
        lamaAdViewPage.setViewData(listAdvert2);
        sp_scroll.setAdapter(new MainGridAdapter(baseActivity));
        sp_scroll.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Bundle bundle=new Bundle();
        //拿到图片
        switch (i)
        {
            case 0:
                baseActivity.openActivity(OrderActivity.class);
                break;
            case 1:
                bundle.putString("url","https://shop130438024.taobao.com/?spm=a1z10.3-c.0.0.KKqfT1");
                bundle.putString("title","朵拉小店");
                baseActivity.openProcessActivity(ProcessWebViewActivity.class,bundle);
                return;
            case 2:
                //优惠
//                bundle.putInt("branchType",BranhActivity.BRANCH_PREFERENTIAL_CODE);
//                baseActivity.openActivity(BranhActivity.class,bundle);
                break;
            case  3:
                //优惠卷
               //  baseActivity.openActivity(PreferentialActivity.class);
                break;
            case 4:
                //商家
                    baseActivity.openActivity(ShangJiaActivity.class);
                 break;
            case 5:
                //我的设备
                bundle.putInt("branchType",BranhActivity.BRANCH_MY_SHEBEI);
                baseActivity.openActivity(BranhActivity.class,bundle);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
        switch (view.getId())
        {
            case R.id.tv_band_mohe:
                //魔盒
                baseActivity.openActivity(BindMoheActivity.class);
                return;
            case R.id.tv_yue:
                //余额
                bundle.putInt("branchType",BranhActivity.BRANCH_ADD_BILL_BALANCE0);
                break;
            case  R.id.tv_bank:
                //银行卡
                baseActivity.DisPlay("敬请期待");
               // bundle.putInt("branchType",BranhActivity.BRANCH_BIND_BANK);
                return;
            case R.id.tv_right_txt:
                //账单
                bundle.putInt("branchType",BranhActivity.BRANCH_ADD_BILL);
                bundle.putString("title","");
                break;
        }
        baseActivity.openActivity(BranhActivity.class,bundle);

    }
}
