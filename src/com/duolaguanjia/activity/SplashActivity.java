package com.duolaguanjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.app_sdk.tool.Util;
import com.duolaguanjia.R;
import com.litesuits.common.utils.HandlerUtil;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.view.MyScrollLayout;
import com.duolaguanjia.view.OnViewChangeListener;
import com.umeng.update.UmengUpdateAgent;

/**
 * Created by Administrator on 2016/1/12.
 */
public class SplashActivity  extends BaseActivity  implements
        OnViewChangeListener
{
  private RelativeLayout navigation;
  private MyScrollLayout mScrollLayout;
  private RelativeLayout rl_main;
  private ImageView[] imgs;
  private int count;
  private int currentItem;
  private LinearLayout pointLLayout;
    @Override
    public void initView(Bundle savedInstanceState) {

       setContentView(R.layout.activity_splash);
      if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
        finish();
        return;
      }
      navigation = (RelativeLayout) findViewById(R.id.navigation);
      mScrollLayout = (MyScrollLayout) navigation.findViewById(R.id.ScrollLayout);
      rl_main = (RelativeLayout) findViewById(R.id.rl_main);
      HandlerUtil.runOnUiThreadDelay(new Runnable() {
        @Override
        public void run() {
          if (Util.getPreferenceBoolean(SplashActivity.this,MainActivity.FIST_OPEN,true))
          {
            rl_main.setVisibility(View.GONE);
            //进入引导页面
            navigation.setVisibility(View.VISIBLE);
            // 加载引导页面
            showNavigationView();
          }else {
            StartMainActivity();
          }
        }
      },2000);
    }
  /**
   * 显示引导页面
   */
  private void showNavigationView() {
    // 是否是第一次加载
    rl_main.setVisibility(View.GONE);
    navigation.setVisibility(View.VISIBLE);
    // 加载引导页面
    initNavigationView();
  }
  public void startmain(View view) {
    StartMainActivity();
  }

  /**
   * 进入主界面
   */
  private void StartMainActivity()
  {
    //判断是否登录状态
    if (application.isLogin())
    {
      //直接进入主界面
      openActivity(MainActivity.class);
    }else {
      //进入登录界面
      //openActivity(MainActivity.class);
      openActivity(LoginActivity.class);
    }
    finish();
  }


  /**
   * 加载引导页面view
   */
  private void initNavigationView() {
    mScrollLayout = (MyScrollLayout) navigation
            .findViewById(R.id.ScrollLayout);
    pointLLayout = (LinearLayout) navigation.findViewById(R.id.llayout);
    count = mScrollLayout.getChildCount();
    imgs = new ImageView[count];
    for (int i = 0; i < count; i++) {
      imgs[i] = (ImageView) pointLLayout.getChildAt(i);
      imgs[i].setEnabled(true);
      imgs[i].setTag(i);
    }
    currentItem = 0;
    imgs[currentItem].setEnabled(false);
    mScrollLayout.SetOnViewChangeListener(this);
  }


    @Override
    public void httpError(String code, String response) {

    }



  @Override
  public void OnViewChange(int position) {
    if (position < 0 || position > count - 1 || currentItem == position) {
      return;
    }
    imgs[currentItem].setEnabled(true);
    imgs[position].setEnabled(false);
    currentItem = position;
  }
}
