package com.duolaguanjia.activity.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app_sdk.ok_http.JsonObjectConve;
import com.app_sdk.view.bean.TopSlideBean;
import com.duolaguanjia.R;
import com.duolaguanjia.activity.BranhActivity;
import com.duolaguanjia.activity.fragment.shebei.ServiceSheBeiFragment;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.MoHeGuaShiModel;
import com.duolaguanjia.respone.MoHeGuaShiRespone;
import com.duolaguanjia.view.MoHeImageListView;
import com.duolaguanjia.view.WidgetMenuItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/31.
 */
public class GuaShiFragment extends BaseFragment  implements View.OnClickListener,MoHeImageListView.OnScrollChange
{
    TitleManager titleManager;
    WidgetMenuItem wm_id,wm_style;
    MoHeImageListView mohe_view;
    ArrayList<TopSlideBean> listAdvert2;
    Button bt_ok;
    public static  final  int   WEIXIU=5;//维修
     int  type=0;
    public static Fragment newInstance(int  type_id) {
        GuaShiFragment fragment = new GuaShiFragment();
        Bundle args = new Bundle();
        args.putInt("type_id",type_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        httpGetDat();
        super.onResume();
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type=getArguments().getInt("type_id",0);
        View view = inflater.inflate(R.layout.fragment_guashi  , null, false);
        titleManager=new TitleManager(view,baseActivity);
        mohe_view= (MoHeImageListView) view.findViewById(R.id.mohe_view);
        //隐藏相册
        mohe_view.setHideCancel();
        titleManager.setTitleName("挂失");
        wm_id= (WidgetMenuItem) view.findViewById(R.id.wm_id);
        mohe_view.setOnScorllChange(this);
        bt_ok= (Button) view.findViewById(R.id.bt_ok);
        wm_id.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
        wm_style= (WidgetMenuItem) view.findViewById(R.id.wm_style);
        wm_style.setOnClickListener(this);
        listAdvert2=new ArrayList<TopSlideBean>();
        if (type==WEIXIU)
        {
            titleManager.setTitleName("维修");
            bt_ok.setText("维修");
        }else {
            bt_ok.setText("挂失");
        }

        return view;
    }
    ArrayList<MoHeGuaShiModel> data;
private   void httpGetDat()
{
    if (data==null || data.size()==0)
    {
        baseActivity.showDiaog();
    }

    baseActivity.params.clear();
    baseActivity.params.put("cust_id",baseActivity.application.getUserId());
    baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"refund/getLostList",baseActivity.params, MoHeGuaShiRespone.class,new JsonObjectConve.HttpCallback<MoHeGuaShiRespone>() {
        @Override
        public void onResponse(String s, MoHeGuaShiRespone response, int code) {
            baseActivity.hideDialog();
            if (response!=null&& response.getData()!=null)
            {
                data=    response.getData();
                if (data.size()>0)
                {
                    if (listAdvert2.size()==0)
                    {
                        //设置图片
                        for (int i = 0; i < data.size(); i++) {
                            MoHeGuaShiModel mode=   data.get(i);
                            TopSlideBean topSlideBean=new TopSlideBean();
                            if (Integer.valueOf(mode.getScene_id())>3 && !TextUtils.isEmpty(mode.getScene_pic()))
                            {
                                //设置网络图片
                            }else if (Integer.valueOf(mode.getScene_id())<=3)
                            {
                                //设置本地图片
                                topSlideBean.setImage_id(getImageId(Integer.valueOf(mode.getScene_id())));

                            }else {
                                //设置默认图片
                            }
                            listAdvert2.add(topSlideBean);
                        }
                        mohe_view.setViewData(listAdvert2);
                    }
                    //设置魔盒ID 和场景
                    MoHeGuaShiModel dd=data.get(0);
                    wm_id.setAccount(dd.getMaccode());
                    wm_style.setAccount(dd.getScene_title());
                    //设置按钮名称
                    if (dd.getStatus()==2)
                    {
                        bt_ok.setText("挂失中");
                    }else if (dd.getStatus()==13)
                    {
                        bt_ok.setText("新货已发出");
                    }else if (dd.getStatus()==12)
                    {
                        bt_ok.setText("已寄回");
                    }else if (dd.getStatus()==11)
                    {
                        bt_ok.setText("待寄回");
                    }else if (dd.getStatus()==10)
                    {
                        bt_ok.setText("待审核");
                    }else if (dd.getStatus()==3)
                    {
                        bt_ok.setText("维修中");
                    }else if (dd.getStatus()==1)
                    {
                        if (type==WEIXIU)
                        {
                            bt_ok.setText("维修");
                        }else {
                            bt_ok.setText("挂失");
                        }

                    }

                }
            }
        }

        @Override
        public void onError(String msg) {
            baseActivity.hideDialog();
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

    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
  if (view==bt_ok)
  {
          switch (data.get(index).getStatus())
          {
              case 1:
                  //正常状态分2种
                  if (type==WEIXIU)
                  {
                      bundle.putInt("branchType", BranhActivity.BRANCH_SERVER_MOHE);
                      bundle.putString("applyId",data.get(index).getApplyId());
                      bundle.putInt("staic", ServiceSheBeiFragment.SUBMIT_SERVER);
                  }else {
                      bundle.putInt("type",GuaShiSettingFragment.GUASHI);
                      bundle.putInt("branchType", BranhActivity.BRANCH_DELETE_MOHE);
                  }
                  break;
              case 2:
                  if (type==WEIXIU)
                  {
                      return;
                  }else {
                      bundle.putInt("type",GuaShiSettingFragment.DELETEGUASHI);
                      bundle.putInt("branchType", BranhActivity.BRANCH_DELETE_MOHE);
                      break;
                  }

              case 3:
              //维修中

              return;
              case 10:
                  //待审核
                  return;
              case 11:
                  //待寄回
                  //填写收获地址
                  bundle.putString("mac",data.get(index).getMaccode());
                  bundle.putString("applyId",data.get(index).getApplyId());
                  bundle.putInt("branchType", BranhActivity.BRANCH_SHENHE_SUCCESS);
                  bundle.putInt("staic", ServiceSheBeiFragment.SUBMIT_ING);
                  break;
              default:
                  return;
          }
          bundle.putString("mac",data.get(index).getMaccode());
         baseActivity.openActivity(BranhActivity.class,bundle);
  }
    }
int index=0;
    @Override
    public void onPageChange(int id) {
        index=id;
        MoHeGuaShiModel mode=   data.get(id);
        //设置
        wm_id.setText(mode.getMaccode());
        wm_style.setText(mode.getScene_title());
    }
}
