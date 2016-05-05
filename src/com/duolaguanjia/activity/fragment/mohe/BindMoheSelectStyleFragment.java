package com.duolaguanjia.activity.fragment.mohe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.app_sdk.tool.*;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BindMoheActivity;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.BindMoHeBean;
import com.duolaguanjia.bean.CodeRespone;
import com.duolaguanjia.bean.LoginRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.AddressModel;
import com.duolaguanjia.model.MoHeStyleModel;
import com.duolaguanjia.respone.AddressStyleRespone;
import com.duolaguanjia.respone.MoHeStyleRespone;
import com.duolaguanjia.view.ActionSheetDialog;
import com.duolaguanjia.view.WidgetInputItem;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2015/12/22.
 */
public class BindMoheSelectStyleFragment extends BaseFragment  implements View.OnClickListener
{
    private String province ;//省份名称
    private String city ;//城市名称
    private String  area;//县名
    private String areaCode ;//县编码
    private  int  selectTtpe=0;//5  选择省   10  选择市   15选择县
    Button bt_sure;
    LinearLayout ll_guding_style,ll_auto_style;
    RelativeLayout rl_city,rl_xian,rl_sheng;
    BindMoHeBean[]   gudingStyle={new BindMoHeBean(true,"/家/",false,R.drawable.home),new BindMoHeBean(false,"/办公室/",false,R.drawable.office),new BindMoHeBean(false,"/父母家/",false,R.drawable.parents_home)};
    ArrayList<BindMoHeBean> autoStyle=new ArrayList<BindMoHeBean>();
    TextView tv_sheng,tv_city,tv_arce;
    WidgetInputItem  wi_address_descript,wi_input_name,wi_phone;
    public static final int REQUEST_IMAGE = 2;
    String  title;
    TitleManager titleManager;
    int  type=5;//5增加地址  10  编辑地址
    String addressiD;
    public static Fragment newInstance(String  addressiD,String  title,int  type) {
        BindMoheSelectStyleFragment fragment = new BindMoheSelectStyleFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        args.putInt("type",type);
        args.putString("addressiD",addressiD);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        autoStyle.add(new BindMoHeBean(false,"",false, R.drawable.writing));
        if (getArguments()!=null)
        {
            type=getArguments().getInt("type");
            addressiD=getArguments().getString("addressiD");
        }

        View view = inflater.inflate(R.layout.fragment_bind_select_style  , null, false);
        ll_guding_style= (LinearLayout) view.findViewById(R.id.ll_guding_style);
        wi_address_descript= (WidgetInputItem) view.findViewById(R.id.wi_address_descript);
        wi_input_name= (WidgetInputItem) view.findViewById(R.id.wi_input_name);
        wi_phone= (WidgetInputItem) view.findViewById(R.id.wi_phone);
        rl_city= (RelativeLayout) view.findViewById(R.id.rl_city);
        rl_xian= (RelativeLayout) view.findViewById(R.id.rl_xian);
        rl_sheng= (RelativeLayout) view.findViewById(R.id.rl_sheng);
        tv_sheng= (TextView) view.findViewById(R.id.tv_sheng);
        tv_city= (TextView) view.findViewById(R.id.tv_city);
        tv_arce= (TextView) view.findViewById(R.id.tv_arce);
        bt_sure= (Button) view.findViewById(R.id.bt_sure);
        bt_sure.setOnClickListener(this);
        rl_city.setOnClickListener(this);
        rl_xian.setOnClickListener(this);
        rl_sheng.setOnClickListener(this);
        ll_auto_style= (LinearLayout) view.findViewById(R.id.ll_auto_style);
        setGuDingStyle();
        if (type!=10)
        {
            httpGetAddressData();
        }
        if (getArguments()!=null && getArguments().getString("title")!=null)
        {
            title=getArguments().getString("title");
            //标题不为空
            titleManager=new TitleManager(view,baseActivity);
            titleManager.setTitleName(title);

        }
        if (type==10)
        {
            //获取地址详细信息
            baseActivity.params.clear();
            baseActivity.showDiaog();
            baseActivity.params.put("cust_id", baseActivity.application.getUserId());
            baseActivity.params.put("address_id",addressiD);
            baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/addressData",baseActivity.params, AddressStyleRespone.class,new JsonObjectConve.HttpCallback<AddressStyleRespone>() {
                @Override
                public void onResponse(String s, AddressStyleRespone response, int code) {
                   baseActivity. hideDialog();
                    if (response!=null && response.getData()!=null)
                    {
                        //拿到地址 进行编辑
                        ArrayList<MoHeStyleModel> scene=   response.getData().getScene();
                        AddressModel address= response.getData().getAddress();
                        if (scene!=null && scene.size()>0)
                        {
                            for (MoHeStyleModel mode : scene) {
                                BindMoHeBean bindMohe = new BindMoHeBean(false, mode.getScene_title(), true);
                                bindMohe.setNetImageUrl(mode.getScene_pic());
                                autoStyle.add(0, bindMohe);
                            }

                        }
                        setAutoStyle();
                        //设置用户选择的场景
                        int  styleId=Integer.valueOf(response.getData().getAddress().getScene_id());
                        if (styleId!=1)
                        {
                            //设置场景
                            if (styleId<4)
                            {
                                ll_guding_style.getChildAt(styleId-1).performClick();
                            }else {
                                ll_auto_style.getChildAt(styleId-4).performClick();
                            }
                        }
                        //设置姓名
                        wi_input_name.setText(address.getReceive_name());
                        //电话
                        wi_phone.setText(address.getReceive_mobile());
                        //省
                        tv_sheng.setText(address.getProvice());
                        //市
                        tv_city.setText(address.getCity());
                        //区
                        tv_arce.setText(address.getArea());
                        //详细地址
                        wi_address_descript.setText(address.getStreet());
                    }
                }

                @Override
                public void onError(String msg) {
                   baseActivity. DisPlay(msg);
                   baseActivity. hideDialog();
                }
            });
        }
        return view;
    }
    LinearLayout.LayoutParams  auto_parasm;
    Set<EditText> hashSet=new HashSet<EditText>();

    private  void  deleteStyle(final BindMoHeBean bindMohe)
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
       baseActivity. params.put("numId",bindMohe.getId()+"");
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/deAddress",baseActivity.params, CodeRespone.class,new JsonObjectConve.HttpCallback<CodeRespone>() {
            @Override
            public void onResponse(String s, CodeRespone response, int code) {
                 baseActivity.hideDialog();
                if (response!=null)
                {

                    baseActivity.DisPlay(response.getMsg());
                    autoStyle.remove(bindMohe);
                    setAutoStyle();
                }else {
                    baseActivity.DisPlay("删除失败!");
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.hideDialog();
                baseActivity.DisPlay(msg);
            }
        });

    }
    private void  setAutoStyle()
    {
        ll_auto_style.removeAllViews();
        hashSet.clear();
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
        for (int i = 0; i <autoStyle.size() ; i++)
        {
          final   BindMoHeBean bindMoHeBean=    autoStyle.get(i);
            //创建view
            View view=View.inflate(baseActivity,R.layout.style_item,null);
            view.setTag(i);
            view.setOnClickListener(BindMoheSelectStyleFragment.this);
            //是否显示删除
            if (bindMoHeBean.isDelete())
            {
                view.findViewById(R.id.iv_delete).setVisibility(View.VISIBLE);
            }
            //是否显示选择
            if (bindMoHeBean.isSelect())
            {
                view.findViewById(R.id.iv_select).setVisibility(View.VISIBLE);
            }
            //设置文字
            EditText tv_name= (EditText) view.findViewById(R.id.tv_name);
            tv_name.setText(bindMoHeBean.getTitleName());
            TextView look_name= (TextView) view.findViewById(R.id.look_name);

            //背景
            RelativeLayout rl_border= (RelativeLayout) view.findViewById(R.id.rl_border);
            //设置高度  大小
           LinearLayout.LayoutParams parms=new LinearLayout.LayoutParams(width/3,width/3);
           rl_border.setLayoutParams(parms);
            ImageView rl_border_image= (ImageView ) view.findViewById(R.id.rl_border_image);
            //图片
            if (bindMoHeBean.getBitmap()==null && TextUtils.isEmpty(bindMoHeBean.getNetImageUrl()))
            {
                look_name.setVisibility(View.VISIBLE);

                tv_name.setVisibility(View.GONE);
                rl_border_image.setBackgroundResource(bindMoHeBean.getImageId());
                view.findViewById(R.id.iv_yy).setVisibility(View.GONE);
            }
            else
            {
                //设置点击事件
                view.setTag(i+4);
                bindMoHeBean.setId(i+4);
                view.setOnClickListener(onClickListener);
                tv_name.setTag(bindMoHeBean);
                hashSet.add(tv_name);
                if(!TextUtils.isEmpty(bindMoHeBean.getNetImageUrl()))
                {
                    //设置网络图片
                    baseActivity.imageLoader.displayImage(bindMoHeBean.getNetImageUrl(),rl_border_image);
                }else {
                    rl_border_image.setBackground(new BitmapDrawable(bindMoHeBean.getBitmap()));
                }

                //显示删除
               view.findViewById(R.id.iv_delete).setVisibility(View.VISIBLE);
                view.findViewById(R.id.iv_delete).setTag(bindMoHeBean);
                view.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //删除
                        BindMoHeBean bindMoHeBean= (BindMoHeBean) view.getTag();
                        //判断是和服务器相关的还是本地的
                        if (TextUtils.isEmpty(bindMoHeBean.getNetImageUrl()))
                        {
                            //本地
                            autoStyle.remove(bindMoHeBean);
                            setAutoStyle();
                        }else {
                            //服务器
                            deleteStyle(bindMoHeBean);

                        }

                }
                });
            }

            ll_auto_style.addView(view);
            //最后一个模拟点击事件
            if (bindMoHeBean.getBitmap()!=null && i==autoStyle.size()-2)
            {
                //模拟点击
                view.performClick();
            }
        }
    }
    int width=-1;
    LinearLayout.LayoutParams  guding_parasm;
    /**
     * 固定场景
     */
    private void setGuDingStyle()
    {
        ll_guding_style.removeAllViews();
        if (width<0)
        {
            width= ScreenUtils.getScreenWH(baseActivity)[0]- DensityUtil.dip2px(baseActivity,10);
        }
        if (guding_parasm==null)
        {
            guding_parasm=    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,width/3);
            guding_parasm.setMargins(DensityUtil.dip2px(baseActivity,5),0,DensityUtil.dip2px(baseActivity,5),0);
            ll_guding_style.setLayoutParams(guding_parasm);
        }

        for (int i = 0; i <gudingStyle.length ; i++)
        {
            BindMoHeBean bindMoHeBean=    gudingStyle[i];
            //创建view
            View view=View.inflate(baseActivity,R.layout.style_item,null);
            view.setTag(i+1);
            view.setOnClickListener(onClickListener);
            //是否显示选择
            if (bindMoHeBean.isSelect())
            {
                view.findViewById(R.id.iv_select).setVisibility(View.VISIBLE);
            }
            //设置文字
            EditText tv_name= (EditText) view.findViewById(R.id.tv_name);
            TextView look_name= (TextView) view.findViewById(R.id.look_name);
            tv_name.setText(bindMoHeBean.getTitleName());
            look_name.setText(bindMoHeBean.getTitleName());
            look_name.setVisibility(View.VISIBLE
            );
            tv_name.setVisibility(View.GONE);
            //背景
            RelativeLayout rl_border= (RelativeLayout) view.findViewById(R.id.rl_border);
            //设置高度  大小
            LinearLayout.LayoutParams parms=new LinearLayout.LayoutParams(width/3,width/3);
            rl_border.setLayoutParams(parms);
            ImageView rl_border_image= (ImageView ) view.findViewById(R.id.rl_border_image);
            rl_border_image.setImageResource(bindMoHeBean.getImageId());
            ll_guding_style.addView(view);
        }
    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           //场景选择
          //清空所有已选列表
            //找固定场景 和自动场景
            int gudingCount=ll_guding_style.getChildCount();
            for (int i = 0; i <gudingCount ; i++) {
                ll_guding_style.getChildAt(i).findViewById(R.id.iv_select).setVisibility(View.GONE);
            }
            int  autoCount=ll_auto_style.getChildCount();
            if (autoCount>1)
            {
                //用户选择了图片
                for (int i = 0; i <autoCount ; i++) {
                    ll_auto_style.getChildAt(i).findViewById(R.id.iv_select).setVisibility(View.GONE);
                }
            }
            styleSelectId= (int) view.getTag();
            view.findViewById(R.id.iv_select).setVisibility(View.VISIBLE);
        }
    };

    ActionSheetDialog actionSheetDialog;
        String[] mDatas = new String[0];
    /**
     * 初始化省市区的弹出框
     */
    private void initDialog() {
        actionSheetDialog = new ActionSheetDialog(baseActivity).builder();
        actionSheetDialog.setCancelable(true);
        actionSheetDialog.setCanceledOnTouchOutside(true);

        switch (selectTtpe)
        {
            case 5:
                mDatas=baseActivity.application.appDataInit.mProvinceDatas;
                break;
            case 10:
                mDatas=baseActivity.application.appDataInit.mCitisDatasMap.get(province);
                break;
            case 15:
                mDatas=baseActivity.application.appDataInit.mDistrictDatasMap.get(city);
                break;
        }
        actionSheetDialog.addSheetList(mDatas, ActionSheetDialog.SheetItemColor.Blue,
                new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        // TODO Auto-generated method stub
                        if (actionSheetDialog.isShowing()) {
                            actionSheetDialog.dismiss();
                        }
                        switch (selectTtpe)
                        {
                            case 5:
                                //获取到城市
                                province=  mDatas[which-1];
                                //设置名称
                                tv_sheng.setText(province);
                                //打开城市选择
                                rl_city.performClick();
                                break;
                            case 10:
                                //获取到城市
                                city= mDatas[which-1];
                                tv_city.setText(city);
                                rl_xian.performClick();
                                break;
                            case 15:
                                //获取县
                                area=mDatas[which-1];
                                tv_arce.setText(area);
                              areaCode=baseActivity.application.appDataInit.mZipcodeDatasMap.get(area);
                                break;
                        }
                    }
                });
        actionSheetDialog.setTitle(R.string.addressedit_dialog_province);
        if (!baseActivity.isFinishing()) {
            actionSheetDialog.show();
        }
    }
    private ArrayList<String> mSelectPath;
    int  styleSelectId=1;
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
                autoStyle.add(0,bindMohe);
                //显示图片
                setAutoStyle();
            }
        }
    }
    ArrayList<MoHeStyleModel> data;
    /**
     * 绑定魔盒获取场景
     */
    private  void httpGetAddressData()
    {
        baseActivity.showDiaog();
        baseActivity.params.clear();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/bindScene",baseActivity.params, MoHeStyleRespone.class,new JsonObjectConve.HttpCallback<MoHeStyleRespone>() {
            @Override
            public void onResponse(String s, MoHeStyleRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    data= response.getData();
                    if (data!=null && data.size()>0) {
                        for (MoHeStyleModel mode : data) {
                            BindMoHeBean bindMohe = new BindMoHeBean(false, mode.getScene_title(), true);
                            bindMohe.setNetImageUrl(mode.getScene_pic());
                            autoStyle.add(0, bindMohe);
                        }
                        //设置场景
                    }

                        setAutoStyle();

                }else {
                    setAutoStyle();
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.hideDialog();
                baseActivity. DisPlay(msg);
                setAutoStyle();
            }
        });


    }


    //绑定码魔盒绑定
    private  void  codeBindMohe()
    {
        baseActivity.params.clear();
        if (baseActivity instanceof BindMoheActivity)
        {
            baseActivity.params.put("mac",((BindMoheActivity)baseActivity).mac);
            baseActivity.params.put("bdcode",((BindMoheActivity)baseActivity).jiqiCode);
        }
        //bdcode [string] [绑定码]
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/bindBox",baseActivity.params, CodeRespone.class,new JsonObjectConve.HttpCallback<CodeRespone>() {
            @Override
            public void onResponse(String s, CodeRespone response, int code) {

                if (response!=null)
                {
                    //绑定成功
                    Util.putPreferenceBoolean(baseActivity,Util.SAVE_BIND,true);
                    //传送成功
                    ((BindMoheActivity)baseActivity).next(4);
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
            }
        });
    }

    /**
     * 地址提交服务器
     */
    private  void httpPutData() throws IOException {
        baseActivity.showDiaog();
        ArrayList<OkHttpClientManager.Param> paramArrayList=new ArrayList<OkHttpClientManager.Param>();
        paramArrayList.add(new OkHttpClientManager.Param("cust_id",baseActivity.application.getUserId()));
        //receive_name [string] [收货人姓名]
        paramArrayList.add(new OkHttpClientManager.Param("receive_name",wi_input_name.getText().toString()));
        //联系电话receive_mobile [string] [收货人手机号]
        paramArrayList.add(new OkHttpClientManager.Param("receive_mobile",wi_phone.getText().toString()));
        //	provice [string] [收货人省份] 、	city [string] [收货人城市]	、	area [string] [收货人区县]
        paramArrayList.add(new OkHttpClientManager.Param("provice",tv_sheng.getText().toString()));
        paramArrayList.add(new OkHttpClientManager.Param("city",tv_city.getText().toString()));
        paramArrayList.add(new OkHttpClientManager.Param("area",tv_arce.getText().toString()));
        //	street [string] [收货人街道]
        paramArrayList.add(new OkHttpClientManager.Param("street",wi_address_descript.getText().toString()));
        //scene_id [int] [场景id]
        paramArrayList.add(new OkHttpClientManager.Param("scene_id",styleSelectId+""));
        //	type [int] [操作的类型，adds=添加，ups=更新]
        //判断是更新还是添加
        if (type==10)
        {
            paramArrayList.add(new OkHttpClientManager.Param("address_id",addressiD));
            paramArrayList.add(new OkHttpClientManager.Param("type","ups"));
        }else {
            paramArrayList.add(new OkHttpClientManager.Param("type","adds"));
            if (baseActivity instanceof BindMoheActivity )
            {
                paramArrayList.add(new OkHttpClientManager.Param("mac",((BindMoheActivity)baseActivity).mac));
            }

        }
       // scene_title [string] [当前选中场景名称]
        if (baseActivity  instanceof  BindMoheActivity)
        {
            ((BindMoheActivity)baseActivity).styleName= getSelectName(styleSelectId);
        }
        paramArrayList.add(new OkHttpClientManager.Param("scene_title", getSelectName(styleSelectId)));
        //判断选择的场景是固定的还是用户自定义的 自定义需要另外传图片
        File file = null;
        if (styleSelectId>3)
        {
            //设置选择图片
            BindMoHeBean bindMoHeBean= (BindMoHeBean) ll_auto_style.getChildAt(styleSelectId-4).findViewById(R.id.tv_name).getTag();
            //图片
            if (!TextUtils.isEmpty(bindMoHeBean.getImagePath()))
            {
                file=  new File(bindMoHeBean.getImagePath());
            }

            //设置选择名称
            paramArrayList.add(new OkHttpClientManager.Param("name"+styleSelectId,bindMoHeBean.getTitleName()));
        }
        baseActivity.jsonObjectConve.httpPostFile(AppApplication.HOST+"user/putAddress",file,"img"+styleSelectId,new JsonObjectConve.HttpCallback<LoginRespone>() {
            @Override
            public void onResponse(String s, LoginRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    baseActivity.DisPlay(response.getMsg());
                    //服务器再传送一次MAC地址
                    //进入下一个页面
                    if (baseActivity instanceof BindMoheActivity)
                    {
                        codeBindMohe();
                    }else {
                        baseActivity.finish();
                    }
                }else {
                    baseActivity.DisPlay("服务器JSON 异常");
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
                baseActivity.hideDialog();
            }
        }, LoginRespone.class,paramArrayList);
    }
    private String  getSelectName(int id)
    {
        switch (id)
        {
            case 1:
                return "/家/";
            case 2:
                return "/办公室/";
            case 3:
                return "/父母家/";
            default:
                //获取到第四个ITEMname
                return   getStyleName(id);

        }
    }
    private String  getStyleName(int id)
    {
      EditText tv_name= (EditText) ll_auto_style.getChildAt(id-4).findViewById(R.id.tv_name);
       return  tv_name.getText().toString();

    }

    @Override
    public void onClick(View view) {
         switch (view.getId())
         {
             case R.id.bt_sure:
                 //确定
                 //姓名
                 if (TextUtils.isEmpty(wi_input_name.getText()))
                 {
                     baseActivity.DisPlay("请输入姓名!");
                     wi_input_name.setFocusable(true);
                     return;
                 }
                 //联系电话
                 if (TextUtils.isEmpty(wi_phone.getText()))
                 {
                     baseActivity.DisPlay("请输入联系电话!");
                     wi_phone.setFocusable(true);
                     return;
                 }
                 //省份
                 if (TextUtils.isEmpty(tv_sheng.getText().toString()))
                 {

                     baseActivity.DisPlay("请选择省份!");
                     rl_sheng.performClick();
                     return;
                 }
                 //地址是否输入
                 if (TextUtils.isEmpty(wi_address_descript.getText()))
                 {
                     baseActivity.DisPlay("详细地址不能为空!");
                     wi_address_descript.setFocusable(true);
                     return;
                 }


                 Iterator<EditText> iterator=hashSet.iterator();
                 while(iterator.hasNext()){
                     EditText  editText=   iterator.next();
                     BindMoHeBean bindMoHeBean= (BindMoHeBean) editText.getTag();
                     if (TextUtils.isEmpty(editText.getText()))
                     {
                         baseActivity.DisPlay("场景名称不能为空!");
                         editText.setFocusable(true);
                         return;
                     }
                     bindMoHeBean.setTitleName(editText.getText().toString());
                 }
                 //下一步
                     baseActivity.hideKey();
                     //地址提交服务器
                     try {
                         httpPutData();
                     } catch (IOException e) {
                         e.printStackTrace();
                 }
                 break;
             case R.id.rl_border:
                 if ((Integer.valueOf(view.getTag().toString())+1)==autoStyle.size())
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
             case R.id.rl_sheng:
                 //选择省份
                 selectTtpe=5;
                 initDialog();
                 break;
             case R.id.rl_xian:
                 if (TextUtils.isEmpty(city))
                 {
                     ToastUtil.shortToast(baseActivity,R.string.addressedit_toast_choos);
                     return;
                 }
                 //选择县
                 selectTtpe=15;
                 initDialog();
                 break;
             case R.id.rl_city:
                 if (TextUtils.isEmpty(province)) {
                     ToastUtil.shortToast(baseActivity,R.string.addressedit_toast_choose_province);
                     return;
                 }
                 //选择城市
                 selectTtpe=10;
                 initDialog();
                 break;
         }
    }
}
