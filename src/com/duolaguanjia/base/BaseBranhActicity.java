package com.duolaguanjia.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.duolaguanjia.R;

/**
 * Created by Night on 2015/11/2.
 */
public abstract class BaseBranhActicity extends  BaseActivity
{
    public static int branchType;// 传递过来的页面类型 根据类型展示不同的fragment
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_branch);
        branchType = getIntent().getIntExtra("branchType", -1);
        if (branchType == -1) {
            this.finish();
            return;
        }
        fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment= initBranchData();
        transaction.replace(R.id.branch_content_fragment, fragment);
        transaction.commit();
    }

    public  abstract Fragment  initBranchData();

    @Override
    public void httpError(String code, String response) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);
    }
//    @Override
//    public void httpOk(BaseResponse response) {
//
//    }
}
