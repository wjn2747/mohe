package com.duolaguanjia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import com.app_sdk.tool.ScreenUtils;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.fragment.order.ListViewOrderFragment;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.CustomViewPager;
import com.duolaguanjia.view.IndicatorHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class OrderActivity extends BaseActivity  implements
        RadioGroup.OnCheckedChangeListener,View.OnClickListener
{
    View view;
    TitleManager titleManager;
    IndicatorHorizontalScrollView hsv;
    ImageView iv_scroll;
    CustomViewPager vp;
    RelativeLayout rl_all_order;
    RadioButton  rb;
    private RadioGroup rg;
    public static String[] CHANELS = { "待付款", "待收货", "待评价","售后/退款" };
    private List<Fragment> fragmentS = new ArrayList<Fragment>();
    public  void  setNum(int index,int count)
    {
        TextView textView= (TextView) rg.getChildAt(index).findViewById(R.id.unread_address_number);
        if (count>0)
        {
            //显示
            textView.setVisibility(View.VISIBLE);
            textView.setText(count+"");
        }else {
            //隐藏
            textView.setVisibility(View.GONE);
        }

    }
    public static final int REQUEST_CODE_PAYMENT = 10;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息\
                Log.e("errorMsg","errorMsg"+errorMsg);
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             */
                if (result.equalsIgnoreCase("success"))
                {
                    DisPlay("付款成功!");
                    ((ListViewOrderFragment)fragmentS.get(indexPag)).loadType=5;
                    fragmentS.get(indexPag).setUserVisibleHint(true);
                }else  if (result.equalsIgnoreCase("fail"))
                {
                    DisPlay("付款失败!");
                }else if (result.equalsIgnoreCase("cancel"))
                {
                    DisPlay("付款取消!");
                }


            }
        }

       super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_order);
        titleManager=new TitleManager(findViewById(R.id.ll_title),this);
        titleManager.setTitleName("订单");
        hsv= (IndicatorHorizontalScrollView) findViewById(R.id.hsv);
        rl_all_order= (RelativeLayout) findViewById(R.id.rl_all_order);
        vp= (CustomViewPager) findViewById(R.id.viewpage);
        vp.setOffscreenPageLimit(5);
        iv_scroll= (ImageView) findViewById(R.id.iv_scroll);
        hsv.setTitleArray(CHANELS);
        hsv.setOnScrollClick(this);
        rl_all_order.setOnClickListener(this);
        rg = hsv.ferRadioGroup();
        vp.setPagingEnabled(true);
        //rg.setOnCheckedChangeListener(this);
        rb = (RadioButton) rg.getChildAt(0).findViewById(R.id.rg_item);
        android.view.ViewGroup.LayoutParams params = iv_scroll
                .getLayoutParams();
        params.width = ScreenUtils.getScreenWH(this)[0]/4;
        iv_scroll.setLayoutParams(params);
        lastInit();
    }

    @Override
    public void httpError(String code, String response) {

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int arg1) {
        // 控制viewPage翻页
        // 获取到被选中的按钮
//        for (int i = 0; i < rg.getChildCount(); i++) {
//            View rb = radioGroup.getChildAt(i).findViewById(R.id.rg_item);
//            if (rb.getId() == arg1) {
                vp.setCurrentItem(arg1,false);
//                return;

//            }
      //  }
    }

    @Override
    public void onClick(View view)
    {
        if (view==rl_all_order)
        {
            //全部订单
            Bundle bundle=new Bundle();
            bundle.putInt("branchType",BranhActivity.BRANCH_ALL_ORDER);
            openActivity(BranhActivity.class,bundle);
        }

    }
    int indexPag;

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


    private void lastInit() {
        for (int i = 0; i < CHANELS.length; i++) {
            fragmentS.add(getFragment(i));
        }
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // 滑动结束的时候需要改变iv_scroll 的宽度
                indexPag=arg0;
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

    @Override
    protected void onResume() {
        //加载当前显示页面的数据
        if (fragmentS!=null)
        {
            ((ListViewOrderFragment)fragmentS.get(indexPag)).loadType=5;
            fragmentS.get(indexPag).setUserVisibleHint(true);
        }
        super.onResume();
    }

    private Fragment getFragment(int id) {
        Fragment fragment = null;
        switch (id)
        {
            case 0:
                fragment = ListViewOrderFragment.newInstance(id,ListViewOrderFragment.ORDER_WAIT_PAY);
                break;
            case 1:
                fragment = ListViewOrderFragment.newInstance(id,ListViewOrderFragment.ORDER_WAIT_DEND);
                break;
            case 2:
                fragment = ListViewOrderFragment.newInstance(id,ListViewOrderFragment.ORDER_WAIT_SHOUHUO);
                break;
            case 3:
                fragment = ListViewOrderFragment.newInstance(id,ListViewOrderFragment.ORDER_WAIT_PINGJIA);
                break;
            case 4:
                fragment = ListViewOrderFragment.newInstance(id,ListViewOrderFragment.ORDER_WAIT_BACK);
                break;
        }

        return fragment;
    }

}
