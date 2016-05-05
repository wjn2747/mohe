package com.duolaguanjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.app_sdk.tool.Util;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.fragment.DingYueFragment;
import com.duolaguanjia.activity.fragment.HomeFragment;
import com.duolaguanjia.activity.fragment.UserFragment;
import com.duolaguanjia.base.BaseActivity;
import com.umeng.update.UmengUpdateAgent;

/**
 * Created by Night on 2015/10/31.
 */
public class MainActivity  extends BaseActivity implements View.OnClickListener{
    private static String[] TITLES = { "首页", "订阅","我的" };
    RelativeLayout re_home;
    RelativeLayout re_dingyue;
    RelativeLayout re_my;
    public  static  final  String FIST_OPEN="first_open";
    RelativeLayout rl_yindao;
    ImageButton ib_bind_mohe,ib_pass;
    @Override
    public void initView(Bundle savedInstanceState) {
        UmengUpdateAgent.update(this);
        if (savedInstanceState!=null) {
            Bundle pBundle = new Bundle();
            openActivity(MainActivity.class, pBundle);
            this.finish();
            return ;
        }
        setContentView(R.layout.activity_main);
        re_my= (RelativeLayout) findViewById(R.id.re_my);
        re_dingyue= (RelativeLayout) findViewById(R.id.re_dingyue);
        re_home= (RelativeLayout) findViewById(R.id.re_home);
        rl_yindao= (RelativeLayout) findViewById(R.id.rl_yindao);
        ib_pass= (ImageButton) findViewById(R.id.ib_pass);
        ib_bind_mohe= (ImageButton) findViewById(R.id.ib_bind_mohe);
        ib_pass.setOnClickListener(this);
        ib_bind_mohe.setOnClickListener(this);
        re_my.setOnClickListener(this);
        re_dingyue.setOnClickListener(this);
        re_home.setOnClickListener(this);
        re_home.setTag(0);
        re_dingyue.setTag(1);
        re_my.setTag(2);
        onItemSelected(0);
        re_home.setSelected(true);
        if (Util.getPreferenceBoolean(this,FIST_OPEN,true))
        {
            //显示引导页面
            rl_yindao.setVisibility(View.VISIBLE);
            Util.putPreferenceBoolean(this,FIST_OPEN,false);
        }
     //   openActivity(LoginActivity.class);


    }

    @Override
    public void httpError(String code, String response) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ib_pass:
                rl_yindao.setVisibility(View.GONE);
               return ;
            case R.id.re_home:
                break;
            case R.id.re_my:

                break;
            case R.id.re_dingyue:
                break;
            case R.id.ib_bind_mohe:
                //绑定魔盒
                openActivity(BindMoheActivity.class);
                rl_yindao.setVisibility(View.GONE);
                return ;
        }
        setSelectedToFalse();
        view.setSelected(true);
        int  tabId= Integer.valueOf( view.getTag().toString() );
        onItemSelected(tabId);
    }

    private int tabIndex=0;
    private Fragment currentFragment;
    private Fragment lastFragment;
    private void onItemSelected(int tabId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        tabIndex=tabId;
        currentFragment = fragmentManager.findFragmentByTag(TITLES[tabId]);
        if (currentFragment == null) {
            currentFragment = getFragment(tabId);
            ft.add(R.id.container, currentFragment, TITLES[tabId]);
        }
        if (lastFragment != null) {
            ft.hide(lastFragment);
        }
        if (currentFragment.isDetached()) {
            ft.attach(currentFragment);
        }
        ft.show(currentFragment);
        currentFragment.setUserVisibleHint(true);
        lastFragment = currentFragment;
        ft.commitAllowingStateLoss();

    }
    private  Fragment getFragment(int id)
    {
        Fragment  fragment = null;
        switch (id)
        {
            case 0:
                fragment= new HomeFragment();
                break;
            case 1:
                fragment= new DingYueFragment();
                break;
            case 2:
                fragment= new UserFragment();
                break;
        }
        return fragment ;
    }
    private void setSelectedToFalse() {
        re_my.setSelected(false);
        re_dingyue.setSelected(false);
        re_home.setSelected(false);
    }
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {

                    Toast.makeText(getApplicationContext(), "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(false);
                finish();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (tabIndex==1)
        {
            currentFragment.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
