package com.duolaguanjia.activity.fragment.preferential;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.CaptureActivity;
import com.duolaguanjia.activity.fragment.mohe.BindMoheZxingFragment;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;

/**
 * Created by Administrator on 2015/12/29.
 */
public class PreferentialCodeFragment extends BaseFragment implements View.OnClickListener
{
    Button bt_zxing,bt_next;
    TitleManager titleManager;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferentialcode  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("优惠" );
        bt_zxing= (Button) view.findViewById(R.id.bt_zxing);
        bt_next= (Button) view.findViewById(R.id.bt_next);
        bt_zxing.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        return  view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.bt_zxing:
                Intent intent = new Intent();
                intent.setClass(baseActivity,
                        CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                baseActivity.startActivityForResult(intent,
                        BindMoheZxingFragment.SCANNIN_GREQUEST_CODE);
                break;
            case R.id.bt_next:
                //完成
                //关闭软键盘
                baseActivity.hideKey();
                //弹出对话框
                baseActivity.showToastOK();
                break;
        }
    }
}
