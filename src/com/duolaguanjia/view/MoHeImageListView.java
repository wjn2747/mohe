package com.duolaguanjia.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.app_sdk.view.FixedSpeedScroller;
import com.app_sdk.view.bean.TopSlideBean;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.fragment.mohe.BindMoheSelectStyleFragment;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/29.
 */
public class MoHeImageListView extends LinearLayout  implements View.OnClickListener
{
    /**
     * 缓存的图片列表
     */
    private ArrayList<ImageView> imageList = new ArrayList<ImageView>();
    ViewPager  viewPager;
    ImageButton iv_camera;

    public MoHeImageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }
    ArrayList<TopSlideBean> listAdvert;
    public  void   updatImageView(int inex,TopSlideBean  topSlideBean)
    {
        if (topSlideBean.getBitmap()!=null)
        {
            imageList.get(inex).setImageBitmap(topSlideBean.getBitmap());
        }else {
            imageList.get(inex).setImageResource(topSlideBean.getImage_id());
        }
    }
    public void setViewData(ArrayList<TopSlideBean> listAdvert2)
    {
        if (listAdvert2 != null && listAdvert2.size() > 0) {
            this.listAdvert = listAdvert2;
            if (listAdvert2.size()>1)
            {
                ic_right_arrow.setVisibility(VISIBLE);
            }
            if (adapter!=null)
            {
                imageViewInit();
                adapter.notifyDataSetChanged();
            }else {
                initAdapter();
            }

        }
    }
private void imageViewInit()
{
    if(imageList!=null&&imageList.size()>0){
        imageList.clear();
    }
    for (int i = 0; i < listAdvert.size(); i++) {
        // 创建指示点
        ImageView imageView = new ImageView(getContext());
        imageView.setTag(listAdvert.get(i));
        imageList.add(imageView);
    }
}
    MyPagerAdapter adapter;
    private void initAdapter() {
        imageViewInit();
         adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        // 设置当前显示的位置
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            /**
             * 当页面发生改奕的时候
             */
            public void onPageSelected(int position) {
                // 对应的图片下标
                //最左边  隐藏左边的按钮
                id_left_arrow.setVisibility(VISIBLE);
                ic_right_arrow.setVisibility(VISIBLE);
                if (position==0)
                {
                    id_left_arrow.setVisibility(INVISIBLE);
                }
                if (position==imageList.size()-1)
                {
                    ic_right_arrow.setVisibility(INVISIBLE);
                }
                //最右边隐藏右边按钮
 if (onScrollChange!=null)
 {
     onScrollChange.onPageChange(position);
 }

            }

            @Override
            /**
             * 当页面滑动的时候，会不断的调用
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             * 当页面的状态发改变，
             * 状态：手指的按下，托拽，抬起。
             */
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    OnScrollChange onScrollChange;
    public  void setOnScorllChange( OnScrollChange onScrollChange)
    {
        this.onScrollChange=onScrollChange;
    }
    public  interface  OnScrollChange
    {
        void onPageChange(int id);
    }

    @Override
    public void onClick(View view) {
     if (view==ic_right_arrow)
     {
         //右边箭头
         // 将viewPager切换至下一页
         if (viewPager.getCurrentItem() == imageList.size() - 1) {
             viewPager.setCurrentItem(0);
         } else {
             viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
         }

     }else if (view==id_left_arrow)
     {
         //左边箭头
         if (viewPager.getCurrentItem() == 0) {
             viewPager.setCurrentItem(imageList.size() - 1);
         } else {
             viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
         }
     }else if (view==iv_camera)
     {
         //照片选择
         //编辑
         Intent intent = new Intent(context, MultiImageSelectorActivity.class);
         // 是否显示拍摄图片
         intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
         // 选择模式
         intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
         ((Activity)context). startActivityForResult(intent, BindMoheSelectStyleFragment.REQUEST_IMAGE);

     }

    }

    public void setHideCancel()
    {
        iv_camera.setVisibility(GONE);
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * 返回页面的个数
         */
        public int getCount() {
            return listAdvert.size();
        }

        @Override
        /**
         * 获得指定位置上的view
         * container 就是viewPager自身
         * position 是指定的位置
         */
        public Object instantiateItem(ViewGroup container, final int position) {
            // 给container添加一个指定位置上的view对象
            imageList.get(position).setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (listAdvert.get(position).getBitmap()!=null)
            {
                imageList.get(position).setImageBitmap(listAdvert.get(position).getBitmap());
            }else {
                imageList.get(position).setImageResource(listAdvert.get(position).getImage_id());
            }


            //ImageLoader.getInstance().displayImage(listAdvert.get(position).getBanner(), imageList.get(position), new AnimateFirstDisplayListener());
            container.addView(imageList.get(position));
            // 返回一个与该view相关的一个对象,在此为简化其间，我们直接返回view
            return imageList.get(position);
        }

        @Override
        /**
         * 判断指定的的view和object是否有关联关系
         * view 某一位置上的显示的页面
         * object 某一位置上返回的object 就是instantiateItem返回的object
         */
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        /**
         * 销毁指定位置上的view
         * object 就是instantiateItem 返回的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    Context context;
    ImageView id_left_arrow,ic_right_arrow;
    public MoHeImageListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        initView();
    }
    private void initView()
    {
        View.inflate(getContext(),
                R.layout.view_mohe_image, this);
        viewPager = (ViewPager) findViewById(com.app_sdk.R.id.view_pager);
        ic_right_arrow= (ImageView) findViewById(R.id.ic_right_arrow);
        id_left_arrow= (ImageView) findViewById(R.id.id_left_arrow);
        iv_camera= (ImageButton) findViewById(R.id.iv_camera);
        ic_right_arrow.setOnClickListener(this);
        id_left_arrow.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        ic_right_arrow.setVisibility(INVISIBLE);
        id_left_arrow.setVisibility(INVISIBLE);
        setViewPagerScrollSpeed();

    }


    public  void  setViewPageHeight()
    {
        //根据比例进行计算 先获取到屏幕的宽度
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = 1000;
        params.width=1000;
        viewPager.setLayoutParams(params);

    }
    /*
 * 设置ViewPager的滑动速度
 */
    private void setViewPagerScrollSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }
}
