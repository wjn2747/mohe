package com.duolaguanjia.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.duolaguanjia.R;
import com.duolaguanjia.base.BaseFragment;

/**
 * Created by Night on 2015/11/2.
 */
public class imageFragment extends BaseFragment {

    int  image;
    ImageView iv_image;
    public static Fragment newInstance(int  image_id) {
        imageFragment fragment = new imageFragment();
        Bundle args = new Bundle();
        args.putInt("image",image_id);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        image=getArguments().getInt("image");
        View view = inflater.inflate(R.layout.fragment_image  , null, false);
        iv_image= (ImageView) view.findViewById(R.id.iv_image);
        iv_image.setImageResource(image);
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseActivity.finish();
            }
        });
        return view;
    }


}
