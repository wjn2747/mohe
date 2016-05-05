package com.duolaguanjia.activity.fragment.mohe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BindMoheActivity;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.view.WidgetInputItem;

/**
 * Created by Administrator on 2015/12/23.
 */
public class BindMoheOk extends BaseFragment  implements View.OnClickListener
{
RelativeLayout rl_add;
    WidgetInputItem bind_mohe,wi_style;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_mohe_ok  , null, false);
        rl_add= (RelativeLayout) view.findViewById(R.id.rl_add);
        bind_mohe= (WidgetInputItem) view.findViewById(R.id.bind_mohe);
        wi_style= (WidgetInputItem) view.findViewById(R.id.wi_style);
        rl_add.setOnClickListener(this);
        bind_mohe.setRightTxt(((BindMoheActivity)baseActivity).jiqiCode);
        wi_style.setRightTxt(((BindMoheActivity)baseActivity).styleName);
        return  view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_add:
                //添加
                ((BindMoheActivity)baseActivity).next(3);
                break;
        }

    }
}
