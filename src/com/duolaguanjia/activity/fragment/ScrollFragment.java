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
 * Created by Night on 2015/12/14.
 */
public class ScrollFragment extends BaseFragment
{
    int  image;
    ImageView imageView;

    public static Fragment newInstance(int  image_id) {
        ScrollFragment fragment = new ScrollFragment();
        Bundle args = new Bundle();
        args.putInt("image",image_id);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        image=getArguments().getInt("image");
        View view = inflater.inflate(R.layout.frgament_scroll_image  , null, false);
        imageView= (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(image);
        return view;
    }


}
