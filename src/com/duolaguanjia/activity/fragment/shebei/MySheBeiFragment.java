package com.duolaguanjia.activity.fragment.shebei;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.tool.FileUtil;
import com.app_sdk.tool.ScreenUtils;
import com.app_sdk.tool.Util;
import com.app_sdk.view.SourcePanel;
import com.app_sdk.view.bean.TopSlideBean;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.fragment.mohe.BindMoheSelectStyleFragment;
import com.duolaguanjia.adapter.MySheBeiAdapter;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.MachineModel;
import com.duolaguanjia.respone.MachineRespone;
import com.duolaguanjia.view.MoHeImageListView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/29.
 */
public class MySheBeiFragment extends BaseFragment  implements MoHeImageListView.OnScrollChange
{
    MoHeImageListView mohe_view;
    TitleManager titleManager;
    SourcePanel sp_scroll;
    MySheBeiAdapter mySheBeiAdapter;
    ArrayList<TopSlideBean> listAdvert2;
    ArrayList<String> showData=new ArrayList<>();
    private ArrayList<ArrayList<String>> data=new ArrayList<ArrayList<String>>();
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shebei  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName("我的设备");
        sp_scroll= (SourcePanel) view.findViewById(R.id.sp_scroll);
        mohe_view= (MoHeImageListView) view.findViewById(R.id.mohe_view);
       listAdvert2=new ArrayList<TopSlideBean>();
        mohe_view.setOnScorllChange(this);
        httpGgetData();
        return view;
    }
    private ArrayList<String> mSelectPath;
    private void httpGgetData()
    {
        baseActivity.params.clear();
        baseActivity.showDiaog();
        baseActivity.params.put("cust_id",baseActivity.application.getUserId());
        baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"user/myMachine",baseActivity.params, MachineRespone.class,new JsonObjectConve.HttpCallback<MachineRespone>() {
            @Override
            public void onResponse(String s, MachineRespone response, int code) {
                baseActivity.hideDialog();
                if (response!=null)
                {
                    if (response.getCode()==1)
                    {
                        if (response.getData()!=null && response.getData().size()>0)
                        {
                            ArrayList<MachineModel>    machineModels=response.getData();
                            //设置图片
                            for (int i = 0; i <machineModels.size() ; i++)
                            {
                                MachineModel mac=    machineModels.get(i);
                                TopSlideBean topSlideBean=new TopSlideBean();
                                if (Integer.valueOf(mac.getScene_id())>3 && !TextUtils.isEmpty(mac.getMacpic()))
                                {
                                    //设置网络图片
                                }else if (Integer.valueOf(mac.getScene_id())<=3)
                                {
                                    //设置本地图片
                                    topSlideBean.setImage_id(getImageId(Integer.valueOf(mac.getScene_id())));

                                }else {
                                    //设置默认图片
                                }
                                listAdvert2.add(topSlideBean);
                                //文字
                                ArrayList<String> dd=new ArrayList<String>();
                                dd.add(mac.getMacname());
                                dd.add(mac.getMonth());
                                dd.add(mac.getZlp_temp());
                                dd.add(mac.getKq_temp());
                                dd.add(mac.getQishu());
                                dd.add(mac.getScene_title());

                                ArrayList<String> dd2=new ArrayList<String>();
                                dd2.add(mac.getMacname()+"1111");
                                dd2.add(mac.getMonth()+"1111");
                                dd2.add(mac.getZlp_temp()+"1111");
                                dd2.add(mac.getKq_temp()+"1111");
                                dd2.add(mac.getQishu()+"1111");
                                dd2.add(mac.getScene_title()+"1111");
                                data.add(dd);
                                data.add(dd2);
                            }
                            mohe_view.setViewData(listAdvert2);

                        }
                        showData.addAll(data.get(0));
                        initAdapter();
                    }

                }
            }

            @Override
            public void onError(String msg) {
                baseActivity.hideDialog();
                baseActivity.DisPlay(msg);
            }
        });

    }
    private  int  getImageId(int id)
    {
        switch (id)
        {
            case 2:
                return R.drawable.office;
            case 1:
                return R.drawable.home;
            case 3:
                return R.drawable.parents_home;
        }
return R.drawable.home;
    }
int  index=0;
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
                Bitmap bitmap = Util.getViewSmallBitmap(baseActivity, mSelectPath.get(0), ScreenUtils.getScreenWH(baseActivity)[0]/3+20,ScreenUtils.getScreenWH(baseActivity)[0]/3+20);
                Bitmap bigbitmap = Util.getViewSmallBitmap(baseActivity, mSelectPath.get(0));
                String str = FileUtil.saveFileToBitMapp(bigbitmap, mSelectPath.get(0));//大图路径
                bigbitmap.recycle();
                listAdvert2.get(index).setBitmap(bitmap);
                listAdvert2.get(index).setFilePath(str);
                mohe_view.updatImageView(index,listAdvert2.get(index));
            }
        }
    }
    private  void  initAdapter()
    {
        if (mySheBeiAdapter!=null)
        {
            mySheBeiAdapter.notifyDataSetChanged();
            return;
        }
        mySheBeiAdapter= new MySheBeiAdapter(baseActivity,showData,R.layout.adapter_sb_item,5);
        sp_scroll.setAdapter(mySheBeiAdapter);
    }


    @Override
    public void onPageChange(int id) {
        index=id;
        Log.e("mohe_viewmohe_view","mohe_view"+id);
         showData.clear();
        showData.addAll(   data.get(id));
        initAdapter();

    }
}
