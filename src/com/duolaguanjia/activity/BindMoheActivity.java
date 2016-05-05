package com.duolaguanjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.fragment.mohe.*;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.manager.TitleManager;

import java.util.Stack;

/**
 * Created by Administrator on 2015/12/22.
 */
public class BindMoheActivity extends BaseActivity  implements View.OnClickListener
{
    private static String[] TITLES = {"0","1", "2","3","4","5"};
    public  String  mac;
    public String jiqiCode;
    public String  styleName;
    TitleManager titleManager;
    public  int type=0;


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_mohe);
        type=getIntent().getIntExtra("type",0);
        titleManager=new TitleManager(findViewById(R.id.ll_title),this);
        titleManager.setTitleName("绑定魔盒");
        titleManager.setLeftOnClick(this);
        onItemSelected(5,false);
    }

    @Override
    public void httpError(String code, String response) {

    }
    public  void next(int  id)
    {
     onItemSelected(id,false);
    }

    private int tabIndex=0;
    private Fragment currentFragment;
    private Fragment lastFragment;
    private Stack<String> stack=new Stack<String>();
    private void onItemSelected(int tabId,boolean  isBrack) {
        if (tabId==4)
        {
            //显示右边的解绑按钮
            titleManager.showRightTxt("解绑");
            titleManager.setRightOnClick(this);
        }else {
            titleManager.showRightTxt("");
            titleManager.setRightOnClick(this);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        currentFragment = fragmentManager.findFragmentByTag(TITLES[tabId]);
       // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!isBrack)
        {
            ft. setCustomAnimations(R.animator.slide_right_in, R.animator.slide_left_out);
        }else {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
        if (currentFragment == null) {
            currentFragment = getFragment(tabId);
            ft.add(R.id.container, currentFragment, TITLES[tabId]);
        }
        if (lastFragment != null) {
            if (!isBrack)
            {
                //判断栈中是否存在记录
                if (stack.contains(tabIndex+""))
                {
                    stack.remove(tabIndex+"");

                }
                stack.push(tabIndex+"");
            }
            ft.hide(lastFragment);
        }
        if (currentFragment.isDetached()) {
            ft.attach(currentFragment);
        }
        ft.show(currentFragment);
        currentFragment.setUserVisibleHint(true);
        lastFragment = currentFragment;
        tabIndex=tabId;
        if (stack.contains(tabIndex+""))
        {
            stack.remove(tabIndex+"");
            stack.push(tabIndex+"");
        }

        ft.commitAllowingStateLoss();

    }
    private void  setTitleName(int id)
    {
        switch (id)
        {
            case 0:
                //修改标题
                titleManager.setTitleName("连接wifi");
                break;
            case 1:
                titleManager.setTitleName("连接wifi");
                break;
            case 2:
                titleManager.setTitleName("场景选择");
                break;
            case 3:
                titleManager.setTitleName("绑定魔盒");
                break;
            case 4:
                titleManager.setTitleName("绑定魔盒");
                break;
            case 5:
                titleManager.setTitleName("绑定魔盒");
                break;
        }

    }
    int index=0;
    private  Fragment getFragment(int id)
    {
        Fragment  fragment = null;
        setTitleName( id);
        index=id;
        switch (id)
        {
            case 0:
                //修改标题
                titleManager.setTitleName("连接wifi");
                fragment= new BindMoheFragment();
                break;
            case 1:
                titleManager.setTitleName("连接wifi");
                fragment= new BindMoheWifiFragment();
                break;
            case 2:
                titleManager.setTitleName("场景选择");
                fragment= new BindMoheSelectStyleFragment();
                break;
            case 3:
                titleManager.setTitleName("绑定魔盒");
                fragment=new BindMoheZxingFragment();
                break;
            case 4:
                titleManager.setTitleName("绑定魔盒");
                fragment=new BindMoheOk();
                break;
            case 5:
                titleManager.setTitleName("绑定魔盒");
                fragment=new BindMoheListFragment();
                break;
        }
        return fragment ;
    }

    @Override
    public void onBackPressed() {
        //判断栈中是否还有数据
        if (index==4)
        {
            super.onBackPressed();
        }
        if (!stack.empty())
        {
            int id=Integer.valueOf(stack.pop());
            Log.e("tabIndex","tabIndex"+tabIndex);
            if (tabIndex==id)
            {
                id=Integer.valueOf(stack.pop());
            }
            setTitleName( id);
            onItemSelected(id,true);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        currentFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
        switch (view.getId())
        {
            case R.id.tv_right_button:
                //解绑
                bundle.putString("id","4");
                bundle.putInt("branchType",BranhActivity.BRANCH_BAND_JIEBANG);
                openActivity(BranhActivity.class,bundle);
                break;
            case  R.id.iv_return:
                //返回
                onBackPressed();
                break;
        }
    }
}
