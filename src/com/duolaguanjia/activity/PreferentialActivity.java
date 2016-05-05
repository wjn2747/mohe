package com.duolaguanjia.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.app_sdk.tool.ScreenUtils;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.fragment.preferential.PreferentialFragment;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.CustomViewPager;
import com.duolaguanjia.view.IndicatorHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/27.
 */
public class PreferentialActivity extends BaseActivity implements
        RadioGroup.OnCheckedChangeListener{
    TitleManager titleManager;
    IndicatorHorizontalScrollView hsv;
    ImageView iv_scroll;
    CustomViewPager vp;
    RadioButton rb;
    private RadioGroup rg;
    public static String[] CHANELS = { "待使用", "已使用", "已过期" };
    private List<Fragment> fragmentS = new ArrayList<Fragment>();
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_order);
        findViewById(R.id.rl_all_order).setVisibility(View.GONE);
        titleManager=new TitleManager(findViewById(R.id.ll_title),this);
        titleManager.setTitleName("优惠券");
        hsv= (IndicatorHorizontalScrollView) findViewById(R.id.hsv);
        vp= (CustomViewPager) findViewById(R.id.viewpage);
        vp.setOffscreenPageLimit(5);
        iv_scroll= (ImageView) findViewById(R.id.iv_scroll);
        hsv.setTitleArray(CHANELS);
        hsv.setOnScrollClick(this);
        rg = hsv.ferRadioGroup();
        vp.setPagingEnabled(true);
        rg.setOnCheckedChangeListener(this);
        rb = (RadioButton) rg.getChildAt(0).findViewById(R.id.rg_item);
        android.view.ViewGroup.LayoutParams params = iv_scroll
                .getLayoutParams();
        params.width = ScreenUtils.getScreenWH(this)[0]/4;
        iv_scroll.setLayoutParams(params);
        lastInit();
    }

    private void lastInit() {
        for (int i = 0; i < CHANELS.length; i++) {
            fragmentS.add(getFragment(i));
        }
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // 滑动结束的时候需要改变iv_scroll 的宽度
                android.view.ViewGroup.LayoutParams params = iv_scroll
                        .getLayoutParams();
                params.width = rg.getChildAt(arg0).getWidth();
                iv_scroll.setLayoutParams(params);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                int total = (int) (arg0 + arg1) * rb.getWidth();
                int green = (vp.getWidth() - rb.getWidth()) / 2;
                // 得到图片滚动距离
                int scroll = total - green;
                hsv.scrollTo(scroll, 0);
                // 图片滑块
                imScroll(arg0, arg1);

            }

            private float fromX;
            private RadioButton bt;

            /**
             * 图片滚动
             */
            private void imScroll(int arg0, float arg1) {
                bt = (RadioButton) rg.getChildAt(arg0).findViewById(R.id.rg_item);
                int[] location = new int[2];
                bt.getLocationInWindow(location);
                float toXDelta = location[0] + arg1 * bt.getWidth();
                // 平移动画
                TranslateAnimation animation = new TranslateAnimation(fromX,
                        toXDelta, 0, 0);
                animation.setDuration(50);
                animation.setFillAfter(true);
                fromX = toXDelta;
                iv_scroll.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

        });
    }

    private Fragment getFragment(int id) {
        Fragment fragment = null;
                fragment =  PreferentialFragment.newInstance(id);
        return fragment;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int arg1) {
        // 控制viewPage翻页
        // 获取到被选中的按钮
        vp.setCurrentItem(arg1,false);
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentS.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentS.size();
        }

    }
    @Override
    public void httpError(String code, String response) {

    }


}
