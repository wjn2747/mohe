package com.duolaguanjia.activity.fragment.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.ok_http.OkHttpClientManager;
import com.app_sdk.tool.FileUtil;
import com.app_sdk.tool.ScreenUtils;
import com.app_sdk.tool.Util;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.activity.ListItemActivity;
import com.duolaguanjia.activity.fragment.mohe.BindMoheSelectStyleFragment;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.AppDataInit;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.LoginRespone;
import com.duolaguanjia.bean.SelectValueBean;
import com.duolaguanjia.bean.UserInfoRespone;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.view.WidgetMenuItem;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/30.
 */
public class UserSettingFragment extends BaseFragment  implements View.OnClickListener
{
    public final  int NICK_REQUEST=5;//昵称请求
    public final  int SEX_REQUEST=6;//性别
    public final  int TITLE_REQUEST=7;//职业
    public final  int HANGYE_REQUEST=8;//行业
    String imagePath;
    Button bt_save;

    TitleManager titleManager;
    ImageView user_item_iv_avatar;
    WidgetMenuItem wm_accont,wm_account_name,wm_sex,wm_hangye,wm_zhiye;
    RelativeLayout rl_face;
View view;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_user_setting, container, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("个人设置");
        wm_accont= (WidgetMenuItem) view.findViewById(R.id.wm_accont);
        bt_save= (Button) view.findViewById(R.id.bt_save);
        bt_save.setOnClickListener(this);
        user_item_iv_avatar= (ImageView) view.findViewById(R.id.user_item_iv_avatar);
        rl_face= (RelativeLayout) view.findViewById(R.id.rl_face);
        wm_sex= (WidgetMenuItem) view.findViewById(R.id.wm_sex);
        wm_zhiye= (WidgetMenuItem) view.findViewById(R.id.wm_zhiye);
        wm_hangye= (WidgetMenuItem) view.findViewById(R.id.wm_hangye);
        wm_account_name= (WidgetMenuItem) view.findViewById(R.id.wm_account_name);
        wm_account_name.setAccount(baseActivity.application.getUserMoble());
        rl_face.setOnClickListener(this);
        wm_accont.setOnClickListener(this);
        wm_sex.setOnClickListener(this);
        wm_hangye.setOnClickListener(this);
        wm_zhiye.setOnClickListener(this);
        //获取数据
       httpGetData();
        return view;
    }

    private void httpGetData()
    {
        baseActivity.showDiaog();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/personEdit",baseActivity.params, UserInfoRespone.class,new JsonObjectConve.HttpCallback<UserInfoRespone>() {
            @Override
            public void onResponse(String s, UserInfoRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null&& response.getData()!=null)
                {
                    //设置昵称
                    wm_accont.setAccount(response.getData().getCust_realname());
                    //性别
                    wm_sex.setAccount(response.getData().getSex().equalsIgnoreCase("1")?"男":"女");
                    //行业
                    wm_hangye.setAccount(response.getData().getHy());
                    //职业
                    wm_zhiye.setAccount(response.getData().getJob());
                    //头像
                    if (!TextUtils.isEmpty(response.getData().getCust_img()))
                    {
                        //下载头像
                       baseActivity.imageLoader.displayImage(response.getData().getCust_img(),user_item_iv_avatar);
                    }
                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.DisPlay(msg);
                baseActivity.hideDialog();
            }
        });
    }

    /**
     * 用户资料上传
     */
    private void  httpPutData() throws IOException {
        baseActivity.showDiaog();
        ArrayList<OkHttpClientManager.Param> paramArrayList=new ArrayList<OkHttpClientManager.Param>();
        //iD
        paramArrayList.add(new OkHttpClientManager.Param("cust_id",baseActivity.application.getUserId()));
        //用户昵称
        paramArrayList.add(new OkHttpClientManager.Param("cust_realname",wm_accont.getAccount()));
        //性别
        paramArrayList.add(new OkHttpClientManager.Param("sex",wm_sex.getAccount().equalsIgnoreCase("男")?"1":"0"));
        //行业
        paramArrayList.add(new OkHttpClientManager.Param("hy",wm_hangye.getAccount()));
        //职业
        paramArrayList.add(new OkHttpClientManager.Param("job",wm_zhiye.getAccount()));
        File file = null;
        if (!TextUtils.isEmpty(imagePath))
        {
            file=  new File(imagePath) ;
        }
        //用户头像
        baseActivity.jsonObjectConve.httpPostFile(AppApplication.HOST+"user/setUserPost",file,"img",new JsonObjectConve.HttpCallback<LoginRespone>() {
            @Override
            public void onResponse(String s, LoginRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    baseActivity.DisPlay(response.getMsg());

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

    private ArrayList<String> mSelectPath;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null)
        {
            return;
        }
        String  value = null;
        switch (requestCode)
        {
            case NICK_REQUEST:
                //昵称返回
               value=   data.getStringExtra("value");
                wm_accont.setAccount(value);
                //设置
                break;
            case  SEX_REQUEST:
                value=   data.getStringExtra("value");
                wm_sex.setAccount(value);
                break;
            case TITLE_REQUEST:
                value=   data.getStringExtra("value");
                wm_zhiye.setAccount(value);

                break;
            case  HANGYE_REQUEST:
                SelectValueBean selectValueBean= (SelectValueBean) data.getSerializableExtra("data");
                wm_hangye.setAccount(selectValueBean.getName());
                break;
        }
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
                Bitmap bitmap = Util.getViewSmallBitmap(baseActivity, mSelectPath.get(0), ScreenUtils.getScreenWH(baseActivity)[0]/3+20,ScreenUtils.getScreenWH(baseActivity)[0]/3+20);
                Bitmap bigbitmap = Util.getViewSmallBitmap(baseActivity, mSelectPath.get(0));
                imagePath = FileUtil.saveFileToBitMapp(bigbitmap, mSelectPath.get(0));//大图路径
                bigbitmap.recycle();
                //设置头像
                user_item_iv_avatar.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(baseActivity, BranhActivity.class);
        intent.putExtra("branchType",BranhActivity.BRANCH_USER_INPUT_SETTNG);
        if (view==rl_face)
        {
            //头像
            //编辑
            Intent intent_phine = new Intent(baseActivity, MultiImageSelectorActivity.class);
            // 是否显示拍摄图片
            intent_phine.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
            // 选择模式
            intent_phine.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
            baseActivity. startActivityForResult(intent_phine, BindMoheSelectStyleFragment.REQUEST_IMAGE);
        }else if (view==wm_accont)
        {
            //昵称
            intent.putExtra("value",wm_accont.getAccount());
            intent.putExtra("type",User_input_fragment.INPUT_YUPE_NICK);
            baseActivity.startActivityForResult(intent,NICK_REQUEST);

        }else if (view==wm_sex)
        {
            //性别
            intent.putExtra("value",wm_sex.getAccount());
            intent.putExtra("type",User_input_fragment.INPUT_YUPE_SEX);
            baseActivity.startActivityForResult(intent,SEX_REQUEST);
        }else if (view==wm_hangye)
        {
            //行业
            Intent    selectIntent=new Intent(baseActivity, ListItemActivity.class);
            selectIntent.putExtra("data", AppDataInit.hangyeData);
            intent.putExtra("value",wm_hangye.getAccount());
            selectIntent.putExtra("title","行业");
            baseActivity.startActivityForResult(selectIntent,HANGYE_REQUEST);
        }else if (view==wm_zhiye)
        {
            //职业
            intent.putExtra("value",wm_zhiye.getAccount());
            intent.putExtra("type",User_input_fragment.INPUT_YUPE_TITLE);
            baseActivity.startActivityForResult(intent,TITLE_REQUEST);
        }else if (view==bt_save)
        {
            //提交
            try {
                httpPutData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
