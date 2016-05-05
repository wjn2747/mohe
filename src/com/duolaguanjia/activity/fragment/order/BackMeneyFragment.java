package com.duolaguanjia.activity.fragment.order;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.duolaguanjia.activity.fragment.user.UserFanKuiFragment;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.bean.BindMoHeBean;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.pop.BackSelectPopWindow;
import com.duolaguanjia.respone.TuiKuangYuanYinRespone;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/26.
 */
public class BackMeneyFragment extends BaseFragment  implements View.OnClickListener
{
    BackSelectPopWindow backSelectPopWindow;
RelativeLayout rl_back;
    View   view;
   EditText et_input,et_input_content;
    int width=-1;
    TextView tv_tuikuang,tv_max;
    Button bt_next;
    TitleManager titleManager;
    String  id;
    String money;
    LinearLayout ll_auto_style;
    LinearLayout.LayoutParams  auto_parasm;

    ArrayList<BindMoHeBean> imageSelect=new ArrayList<BindMoHeBean>();
    public static Fragment newInstance(String  id,String money) {
        BackMeneyFragment fragment = new BackMeneyFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        args.putString("money",money);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        id=getArguments().getString("id");//退款金额ID
        money=getArguments().getString("money");
        view =inflater.inflate(R.layout.fragment_order_back, container, false);
        tv_tuikuang= (TextView) view.findViewById(R.id.tv_tuikuang);
        tv_max= (TextView) view.findViewById(R.id.tv_max);
        bt_next= (Button) view.findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
        tv_max.setText("(最多可退款"+money+"元)");
        rl_back= (RelativeLayout) view.findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_tuikuang.setHintTextColor(Color.argb(110, 153, 153, 153));
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("申请退款");
        et_input= (EditText) view.findViewById(R.id.et_input);
        et_input_content= (EditText) view.findViewById(R.id.et_input_content);
        et_input_content.setHintTextColor(Color.argb(110, 153, 153, 153));
        et_input.setHintTextColor(Color.argb(110, 153, 153, 153));
        ll_auto_style= (LinearLayout) view.findViewById(R.id.ll_auto_style);
        imageSelect.add(new BindMoHeBean(false,"",false,R.drawable.icon_addpic_unfocused));
        setAutoStyle();
        et_input_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence.toString()))
                {
                    return;
                }
                if (Float.valueOf(charSequence.toString())>Float.valueOf(money))
                {
                    //设置
                    et_input_content.setText(money);
                    //设置光标位置
                    et_input_content .setSelection(money.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        httpGetTuiKYuanYan();
        return view;
    }
    ArrayList<String> data;
    private  void httpGetTuiKYuanYan()
    {
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"order/getRefundType",baseActivity.params, TuiKuangYuanYinRespone.class,new JsonObjectConve.HttpCallback<TuiKuangYuanYinRespone>() {
            @Override
            public void onResponse(String s, TuiKuangYuanYinRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null && response.getCode()==1)
                {
                    if (response.getCode()!=1)
                    {
                        baseActivity.DisPlay(response.getMsg());
                        return;
                    }
                     data=   response.getData();
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity. DisPlay(msg);
                baseActivity.hideDialog();
            }
        });
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
            view.setOnClickListener(BackMeneyFragment.this);
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
        if(requestCode == UserFanKuiFragment.REQUEST_IMAGE){
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

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.rl_border)
        {
            //图片选择
            if ((Integer.valueOf(view.getTag().toString())+1)==imageSelect.size())
            {
                //编辑
                Intent intent = new Intent(baseActivity, MultiImageSelectorActivity.class);
                // 是否显示拍摄图片
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // 选择模式
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                baseActivity. startActivityForResult(intent,UserFanKuiFragment. REQUEST_IMAGE);
            }
        }
         if (view==rl_back)
         {
             if (data==null)
             {

                 baseActivity.DisPlay("无退款原因请直接点击确认!");
                 return;
             }
             if (backSelectPopWindow==null)
             {
                 backSelectPopWindow=new BackSelectPopWindow(baseActivity,tv_tuikuang.getWidth(), DensityUtil.dip2px(baseActivity,150));
                 backSelectPopWindow.setData(data);
             }
             //设置文本框
             backSelectPopWindow.setEditView(tv_tuikuang);
             //退款原因
             //弹出POP
             backSelectPopWindow.showAsDropDown(tv_tuikuang,0,DensityUtil.dip2px(baseActivity,5));
         }
        if (view==bt_next)
        {
            //确定
            try {
                httpBackMeney();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 申请退款
     */
    private  void httpBackMeney() throws IOException {
        if (TextUtils.isEmpty(et_input_content.getText().toString()))
        {
            baseActivity.DisPlay("请输入退款金额!");
            return;
        }
        baseActivity.showDiaog();
        ArrayList<OkHttpClientManager.Param> paramArrayList=new ArrayList<OkHttpClientManager.Param>();
        paramArrayList.add(new OkHttpClientManager.Param("cust_id",baseActivity.application.getUserId()));
        paramArrayList.add(new OkHttpClientManager.Param("order_id",id));
        paramArrayList.add(new OkHttpClientManager.Param("refund_type",tv_tuikuang.getTag().toString()));
        paramArrayList.add(new OkHttpClientManager.Param("refund_content",et_input.getText().toString()));
        paramArrayList.add(new OkHttpClientManager.Param("refund_mon",et_input_content.getText().toString()));
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
        baseActivity.jsonObjectConve.httpPostMoreFile(AppApplication.HOST+"order/setRefund",files,fileName,new JsonObjectConve.HttpCallback<BaseRespone>()
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
                        baseActivity.setResult(50);
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
}
