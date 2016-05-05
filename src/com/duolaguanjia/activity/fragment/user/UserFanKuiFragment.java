package com.duolaguanjia.activity.fragment.user;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
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
 * Created by Administrator on 2016/1/21.
 */
public class UserFanKuiFragment  extends BaseFragment implements View.OnClickListener{
    LinearLayout  ll_auto_style;
    EditText et_content;
    int width=-1;
    LinearLayout.LayoutParams  auto_parasm;
    TitleManager titleManager;
    Button  bt_ok;
    public static final int REQUEST_IMAGE = 2;
    ArrayList<BindMoHeBean> imageSelect=new ArrayList<BindMoHeBean>();
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_fankui  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("反馈");
        et_content= (EditText) view.findViewById(R.id.et_content);
        bt_ok= (Button) view.findViewById(R.id.bt_ok);
        et_content.setHintTextColor(Color.argb(110, 153, 153, 153));
        et_content.setTextColor(Color.argb(255, 153, 153, 153));
        bt_ok.setOnClickListener(UserFanKuiFragment.this);
        ll_auto_style= (LinearLayout) view.findViewById(R.id.ll_auto_style);
        imageSelect.add(new BindMoHeBean(false,"",false,R.drawable.icon_addpic_unfocused));
        setAutoStyle();
        return view;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void  setAutoStyle()
    {
        ll_auto_style.removeAllViews();
        if (width<0)
        {
            width= ScreenUtils.getScreenWH(baseActivity)[0]- DensityUtil.dip2px(baseActivity,10);
        }
        //设置高度
        if (auto_parasm==null)
        {
            auto_parasm=    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,width/3);
            auto_parasm.setMargins(DensityUtil.dip2px(baseActivity,5),0,DensityUtil.dip2px(baseActivity,5),0);
            ll_auto_style.setLayoutParams(auto_parasm);
        }
        for (int i = 0; i <imageSelect.size() ; i++)
        {
            //创建view
            View view=View.inflate(baseActivity,R.layout.style_item,null);
            view.findViewById(R.id.tv_name).setVisibility(View.GONE);
            view.findViewById(R.id.iv_yy).setVisibility(View.GONE);
            view.setTag(i);
            view.setOnClickListener(UserFanKuiFragment.this);
            RelativeLayout rl_border= (RelativeLayout) view.findViewById(R.id.rl_border);
            //设置高度  大小
            LinearLayout.LayoutParams parms=new LinearLayout.LayoutParams(width/4,width/4);
            rl_border.setLayoutParams(parms);
            ImageView rl_border_image= (ImageView ) view.findViewById(R.id.rl_border_image);
            BindMoHeBean userFanKuiBea=imageSelect.get(i);
            if (TextUtils.isEmpty(userFanKuiBea.getImagePath()))
            {
                rl_border_image.setBackgroundResource(userFanKuiBea.getImageId());
            }else {
                rl_border_image.setBackground(new BitmapDrawable(userFanKuiBea.getBitmap()));
                //显示删除
                view.findViewById(R.id.iv_delete).setVisibility(View.VISIBLE);
                view.findViewById(R.id.iv_delete).setTag(userFanKuiBea);
                view.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //删除
                        BindMoHeBean bindMoHeBean= (BindMoHeBean) view.getTag();
                        imageSelect.remove(bindMoHeBean);
                        setAutoStyle();
                    }
                });
            }

            ll_auto_style.addView(view);
        }
    }
    private ArrayList<String> mSelectPath;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
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
                BindMoHeBean  bindMohe=   new BindMoHeBean(true,"",true,str);
                bindMohe.setBitmap(bitmap);
                //压缩图片
                imageSelect.add(0,bindMohe);
                //显示图片
                setAutoStyle();

            }
        }
    }


    /**
     * 申请退款
     */
    private  void httpBackMeney(String  content) throws IOException {
        baseActivity.showDiaog();
        ArrayList<OkHttpClientManager.Param> paramArrayList=new ArrayList<OkHttpClientManager.Param>();
        paramArrayList.add(new OkHttpClientManager.Param("cust_id",baseActivity.application.getUserId()));
        paramArrayList.add(new OkHttpClientManager.Param("content",content));
        final ArrayList<File> files= new ArrayList<>();
        final ArrayList<String> fileName= new ArrayList<>();
        //判断文件是否存在
        for (int i = 0; i <imageSelect.size() ; i++) {
            BindMoHeBean  bindMohe=   imageSelect.get(i);
            if (!TextUtils.isEmpty(bindMohe.getImagePath()))
            {
                //图片存在
                files.add(new File(bindMohe.getImagePath()));
                fileName.add("img"+(i+1));
            }
        }
        baseActivity.jsonObjectConve.httpPostMoreFile(AppApplication.HOST+"user/putSuggest",files,fileName,new JsonObjectConve.HttpCallback<BaseRespone>()
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
                      baseActivity.finish();
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
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_ok:
                //反馈
                String content=et_content.getText().toString();
                if (TextUtils.isEmpty(content))
                {
                    baseActivity.DisPlay("请输入反馈内容");
                    return;
                }
                try {
                    httpBackMeney(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_border:
                //图片选择
                if ((Integer.valueOf(view.getTag().toString())+1)==imageSelect.size())
                {
                    //编辑
                    Intent intent = new Intent(baseActivity, MultiImageSelectorActivity.class);
                    // 是否显示拍摄图片
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                    // 选择模式
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                    baseActivity. startActivityForResult(intent, REQUEST_IMAGE);
                }
                break;
        }

    }
}
