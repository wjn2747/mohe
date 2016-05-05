package com.duolaguanjia.activity.fragment.shebei;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.ok_http.OkHttpClientManager;
import com.app_sdk.tool.DensityUtil;
import com.app_sdk.tool.FileUtil;
import com.app_sdk.tool.ScreenUtils;
import com.app_sdk.tool.Util;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.fragment.mohe.BindMoheSelectStyleFragment;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.bean.BindMoHeBean;
import com.duolaguanjia.manager.TitleManager;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/31.
 */
public class ServiceSheBeiFragment extends BaseFragment  implements View.OnClickListener
{
    public static  final  int SUBMIT_SERVER=5;//提交维修
    public static  final  int  SUBMIT_ING=6;//审核中
    TitleManager titleManager;
    String  applyId;
    LinearLayout ll_parent;
    EditText et_descript;
    Button bt_ok;
    int staticType;
    String mac;
    ArrayList<BindMoHeBean> bindList=new ArrayList<BindMoHeBean>();
    public static Fragment newInstance(String  applyId,String  mac,int    staticType) {
        ServiceSheBeiFragment fragment = new ServiceSheBeiFragment();
        Bundle args = new Bundle();
        args.putString("applyId",applyId);
        args.putString("mac",mac);
        args.putInt("staic",staticType);
        fragment.setArguments(args);
        return fragment;
    }

    private void httpPostData() throws IOException {
        String  content=et_descript.getText().toString();
        if (TextUtils.isEmpty(content))
        {
            baseActivity.DisPlay("填写问题内容!");
            return;
        }

        //获取到要上传的图片
        baseActivity.showDiaog();
        ArrayList<OkHttpClientManager.Param> paramArrayList=new ArrayList<OkHttpClientManager.Param>();
        paramArrayList.add(new OkHttpClientManager.Param("applyId",applyId));
        paramArrayList.add(new OkHttpClientManager.Param("cust_id",baseActivity.application.getUserId()));
        paramArrayList.add(new OkHttpClientManager.Param("maccode",mac));
        paramArrayList.add(new OkHttpClientManager.Param("content",content));
        final ArrayList<File> files= new ArrayList<>();
        final ArrayList<String> fileName= new ArrayList<>();
        //判断文件是否存在
        for (int i = 0; i <bindList.size() ; i++) {
            BindMoHeBean  bindMohe=   bindList.get(i);
            if (!TextUtils.isEmpty(bindMohe.getImagePath()))
            {
                //图片存在
                files.add(new File(bindMohe.getImagePath()));
                fileName.add("img"+(i+1));
            }
        }
        baseActivity.jsonObjectConve.httpPostMoreFile(AppApplication.HOST+"refund/applyRepair",files,fileName,new JsonObjectConve.HttpCallback<BaseRespone>()
        {

            @Override
            public void onResponse(String s, BaseRespone response, int code)
            {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    baseActivity.DisPlay(response.getMsg());
                    if (response.getCode()==1)
                    {
                        //提交成功
                       weiXiuIng();

                    }
                }

            }

            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
            }
        }, BaseRespone.class,paramArrayList);


    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        applyId=getArguments().getString("applyId");
        staticType=getArguments().getInt("staic");
        mac=getArguments().getString("mac");
        bindList.add(new BindMoHeBean(true,"/魔盒正面/",false,R.drawable.writing));
        bindList.add(new BindMoHeBean(true,"/魔盒背面/",false,R.drawable.writing));
        bindList.add(new BindMoHeBean(true,"/魔盒上面/",false,R.drawable.writing));
        bindList.add(new BindMoHeBean(true,"/魔盒下面/",false,R.drawable.writing));
        View view = inflater.inflate(R.layout.fragment_server_weixiu  , null, false);
        et_descript= (EditText) view.findViewById(R.id.et_descript);
        bt_ok= (Button) view.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    httpPostData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // weiXiuIng();
                }

        });
        et_descript.setTextColor(Color.argb(255, 153, 153, 153));
        et_descript.setHintTextColor(Color.argb(110, 153, 153, 153));
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("维修");
        ll_parent= (LinearLayout) view.findViewById(R.id.ll_parent);
        setMoHeStyle();
        if (staticType==SUBMIT_ING)
        {
            weiXiuIng();
        }
        return view;
    }
    private  void  weiXiuIng()
    {
        bt_ok.setEnabled(false);
        bt_ok.setText("审核中");
        int count=ll_parent.getChildCount();
        for (int i = 0; i <count ; i++) {
            ll_parent.getChildAt(i).setClickable(false);
            //隐藏删除图标
            ll_parent.getChildAt(i).findViewById(R.id.iv_delete).setVisibility(View.GONE);
        }
        et_descript.setEnabled(false);
    }
    int width=-1;
    private void setMoHeStyle()
    {
        ll_parent.removeAllViews();
        width= ScreenUtils.getScreenWH(baseActivity)[0]- DensityUtil.dip2px(baseActivity,20);
        for (int i = 0; i <bindList.size() ; i++)
        {
            final   BindMoHeBean bindMoHeBean=  bindList.get(i);
            //创建view
            View view=View.inflate(baseActivity,R.layout.style_item,null);
            ImageView   iv_delete= (ImageView) view.findViewById(R.id.iv_delete);
            iv_delete.setTag(i);
            iv_delete.setOnClickListener(this);
            view.setTag(i);
            view.setOnClickListener(ServiceSheBeiFragment.this);
            //设置文字
            EditText tv_name= (EditText) view.findViewById(R.id.tv_name);
            tv_name.setEnabled(false);
            tv_name.setTextSize(14);
            tv_name.setText(bindMoHeBean.getTitleName());
            //背景
            RelativeLayout rl_border= (RelativeLayout) view.findViewById(R.id.rl_border);
            //设置高度  大小
            LinearLayout.LayoutParams parms=new LinearLayout.LayoutParams(width/4,width/4+20);
            rl_border.setLayoutParams(parms);
            ImageView rl_border_image= (ImageView ) view.findViewById(R.id.rl_border_image);
            //图片
            if (bindMoHeBean.getBitmap()==null)
            {
                tv_name.setClickable(false);
                rl_border_image.setBackgroundResource(bindMoHeBean.getImageId());
                view.findViewById(R.id.iv_yy).setVisibility(View.GONE);
            }
            ll_parent.addView(view);
        }
    }

 int   index=-1;
    private ArrayList<String> mSelectPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == BindMoheSelectStyleFragment.REQUEST_IMAGE){
            if(resultCode == Activity.RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (mSelectPath==null || mSelectPath.size()==0)
                {
                    baseActivity.DisPlay("图片路径选择失败!");
                    return;
                }
                //获取到图片路径
                //拿到图片路径 进行压缩
                Bitmap bitmap = Util.getViewSmallBitmap(baseActivity, mSelectPath.get(0),width/3+20,width/3+20);
                Bitmap bigbitmap = Util.getViewSmallBitmap(baseActivity, mSelectPath.get(0));
                String str = FileUtil.saveFileToBitMapp(bigbitmap, mSelectPath.get(0));//大图路径
                bigbitmap.recycle();
                bindList.get(index).setImagePath(str);
                bindList.get(index).setBitmap(bitmap);
                //拿到之后
                ll_parent.getChildAt(index).findViewById(R.id.rl_border_image).setBackground(new BitmapDrawable(bitmap));
                //取消文字
                ll_parent.getChildAt(index).findViewById(R.id.tv_name).setVisibility(View.GONE);
                ll_parent.getChildAt(index).findViewById(R.id.iv_delete).setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public void onClick(View view) {
        index=Integer.valueOf(view.getTag().toString());
        if (view.getId()==R.id.iv_delete)
        {
         //删除
            //图片
            if (bindList.get(index).getBitmap()!=null)
            {
                bindList.get(index).getBitmap().recycle();
                bindList.get(index).setBitmap(null);
            }
            //删除图片路径
            bindList.get(index).setImagePath("");
            //显示文字
            ll_parent.getChildAt(index).findViewById(R.id.tv_name).setVisibility(View.VISIBLE);
            ll_parent.getChildAt(index).findViewById(R.id.iv_delete).setVisibility(View.GONE);
            //改变背景rl_border_image
            ll_parent.getChildAt(index).findViewById(R.id.rl_border_image).setBackgroundResource(bindList.get(index).getImageId());
        }else if (TextUtils.isEmpty(bindList.get(index).getImagePath())){

            //编辑
            Intent intent = new Intent(baseActivity, MultiImageSelectorActivity.class);
            // 是否显示拍摄图片
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
            // 选择模式
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
            baseActivity. startActivityForResult(intent, BindMoheSelectStyleFragment.REQUEST_IMAGE);

        }
    }
}
