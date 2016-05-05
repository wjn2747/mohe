package com.duolaguanjia.activity.fragment.mohe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BindMoheActivity;
import com.duolaguanjia.base.BaseFragment;

/**
 * Created by Administrator on 2015/12/22.
 */
public class BindMoheFragment extends BaseFragment
{
Button bt_next;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_mohe_static  , null, false);
        bt_next= (Button) view.findViewById(R.id.bt_next);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baseActivity instanceof BindMoheActivity)
                {
                    ((BindMoheActivity)baseActivity).next(1);
                }
            }
        });
        return view;
    }

}
