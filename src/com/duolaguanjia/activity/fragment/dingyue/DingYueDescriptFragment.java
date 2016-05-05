package com.duolaguanjia.activity.fragment.dingyue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.app_sdk.ok_http.JsonObjectConve;
import com.duolaguanjia.R;
import com.google.gson.Gson;
import com.duolaguanjia.activity.DingYueSettingActivity;
import com.duolaguanjia.activity.ListViewSelectActivity;
import com.duolaguanjia.adapter.NumberWheelAdapter;
import com.duolaguanjia.base.AppApplication;
import com.duolaguanjia.base.BaseFragment;
import com.duolaguanjia.bean.DingYueBean;
import com.duolaguanjia.manager.TitleManager;
import com.duolaguanjia.model.ClockModel;
import com.duolaguanjia.respone.DingYueRespone;
import com.duolaguanjia.view.ActionSheetDialog;
import com.duolaguanjia.view.WheelView;
import datetime.DateTime;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/4.
 */
public class DingYueDescriptFragment extends BaseFragment  implements View.OnClickListener{
    TitleManager titleManager;
    private WheelView mWheelHour;
    private WheelView mWheelMinute;
    private String province ;//省份名称
    private String city ;//城市名称
    private  int  selectTtpe=0;//5  选择省   10  选择市
    RelativeLayout rl_select,rl_xunhuan;
    ArrayList<TextView> dayS=new ArrayList<TextView>();
    TextView tv_add,tv_time_count,tv_jian,tv_name,tv_select_con,tv_sunday,tv_monday,tv_tuesday,tv_wednesday,tv_thursday,tv_friday,tv_saturday;
    /** 滚轮适配器：小时 */
    private NumberWheelAdapter mAdapterHour;
    /** 滚轮适配器：分钟 */
    private NumberWheelAdapter mAdapterMinute;
    private  int type;
    String title;
    String clock_id;
    ClockModel lingShengBean;
    public static Fragment newInstance(String  data,int  type,String  title,String clock_id) {
        DingYueDescriptFragment fragment = new DingYueDescriptFragment();
        Bundle args = new Bundle();
        args.putInt("type",type);
        args.putString("data",data);
        args.putString("title",title);
        args.putString("clock_id",clock_id);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type=getArguments().getInt("type");
        title=getArguments().getString("title");
        clock_id=getArguments().getString("clock_id");
        View view = inflater.inflate(R.layout.fragment_dingyue_setting  , null, false);
        rl_select= (RelativeLayout) view.findViewById(R.id.rl_select);
        tv_add= (TextView) view.findViewById(R.id.tv_add);
        tv_select_con= (TextView) view.findViewById(R.id.tv_select_con);
        tv_time_count= (TextView) view.findViewById(R.id.tv_time_count);
        tv_sunday= (TextView) view.findViewById(R.id.tv_sunday);
        tv_monday= (TextView) view.findViewById(R.id.tv_monday);
        tv_tuesday= (TextView) view.findViewById(R.id.tv_tuesday);
        tv_wednesday= (TextView) view.findViewById(R.id.tv_wednesday);
        tv_thursday= (TextView) view.findViewById(R.id.tv_thursday);
        tv_friday= (TextView) view.findViewById(R.id.tv_friday);
        rl_xunhuan= (RelativeLayout) view.findViewById(R.id.rl_xunhuan);
        tv_saturday= (TextView) view.findViewById(R.id.tv_saturday);
        tv_jian= (TextView) view.findViewById(R.id.tv_jian);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
        dayS.add(tv_sunday);
        dayS.add(tv_monday);
        dayS.add(tv_tuesday);
        dayS.add(tv_wednesday);
        dayS.add(tv_thursday);
        dayS.add(tv_friday);
        dayS.add(tv_saturday);
        view.findViewById(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //类别
                //判断时间是否有选择
                //周几
               StringBuffer sb=new StringBuffer();
                for (int i = 0; i <dayS.size() ; i++) {
                    TextView   textV=  dayS.get(i);
                    if (textV.isActivated())
                    {
                        sb.append(textV.getTag()+",");
                    }
            }
                if (TextUtils.isEmpty(sb.toString()))
                {
                    baseActivity.DisPlay("请选择星期!");
                    return;
                }
                String  week=sb.substring(0,sb.toString().lastIndexOf(","));
                DateTime time= getDateTime();
                baseActivity.params.clear();
                baseActivity.showDiaog();
                baseActivity.params.put("cust_id",baseActivity.application.getUserId());
                baseActivity.params.put("week",week);
                if (lingShengBean==null || TextUtils.isEmpty(lingShengBean.getClock_id()))
                {
                    baseActivity.params.put("clock_id",clock_id);
                }else {
                    baseActivity.params.put("clock_id",lingShengBean.getClock_id());
                }

                baseActivity.params.put("hour",time.getHour()+"");
                baseActivity.params.put("minutes",time.getMinute()+"");
                baseActivity.jsonObjectConve.httpPost(AppApplication.HOST+"service/setClock",baseActivity.params, DingYueRespone.class,new JsonObjectConve.HttpCallback<DingYueRespone>() {
                    @Override
                    public void onResponse(String s, DingYueRespone response, int code) {
                        baseActivity. hideDialog();
                        if (response!=null)
                        {
                            if (response.getCode()==1)
                            {
                                Bundle bundle=new Bundle();
                                bundle.putString("id",lingShengBean.getClock_id());
                                bundle.putInt("type",type);
                                bundle.putString("title",title);
                                bundle.putString("clock_id",lingShengBean.getClock_id());
                                bundle.putString("data",new Gson().toJson(lingShengBean));
                                 baseActivity.openActivity(DingYueSettingActivity.class,bundle);
                                baseActivity.finish();
                            }
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        baseActivity.DisPlay(msg);
                    }
                });
//
//
//                //时间
//                DateTime dateTime=   getDateTime();
//               // baseActivity.DisPlay("dateTime"+dateTime.getHour()+"ssss"+dateTime.getMinute()+"sssss"+dateTime.getTimeInMillis());
//                bundle.putLong("time",dateTime.getTimeInMillis());
//                bundle.putString("title",title);
//
//                //周几
//                for (int i = 0; i <dayS.size() ; i++) {
//                    TextView   textV=  dayS.get(i);
//                    if (textV.isEnabled())
//                    {
//                        selectDay.add("周"+textV.getText().toString());
//                    }
//                }
//                if (selectDay.size()==0)
//                {
//                    baseActivity.DisPlay("请选择日期!");
//                    return;
//                }
//                bundle.putSerializable("day",selectDay);

            }
        });
        tv_sunday.setActivated(true);
        tv_monday.setActivated(true);
        tv_tuesday.setActivated(true);
        tv_wednesday.setActivated(true);
        tv_thursday.setActivated(true);
        tv_friday.setActivated(true);
        tv_saturday.setActivated(true);
        tv_sunday.setOnClickListener(onClickListener);
        tv_monday.setOnClickListener(onClickListener);
        tv_tuesday.setOnClickListener(onClickListener);
        tv_wednesday.setOnClickListener(onClickListener);
        tv_thursday.setOnClickListener(onClickListener);
        tv_friday.setOnClickListener(onClickListener);
        tv_saturday.setOnClickListener(onClickListener);
        rl_select.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_jian.setOnClickListener(this);
        mWheelHour= (WheelView) view.findViewById(R.id.wheel_view_hour);
        mWheelMinute= (WheelView) view.findViewById(R.id.wheel_view_minute);
        titleManager=new TitleManager(view,baseActivity);
        titleManager.setTitleName(title);
        buildAdapters();
        mWheelHour.setAdapter(mAdapterHour);
        mWheelMinute.setAdapter(mAdapterMinute);
        setSelectTtpe();
        if (getArguments().getString("data")!=null)
        {
            //转换
            lingShengBean=new Gson().fromJson(getArguments().getString("data"),ClockModel.class);
            //设置铃声
            tv_select_con.setText(lingShengBean.getClock_title());
            tv_select_con.setEnabled(false);
        }
        return view;
    }
    /**
     * 获取选择的时间
     *
     * @return
     */
    public DateTime getDateTime() {
        DateTime time = new DateTime();
        computeHour(time);
        computeMinute(time);
        return time;
    }
    private void computeHour(DateTime time) {
        time.setHour(mWheelHour.getCurrentValue());
    }
    private void computeMinute(DateTime time) {
        time.setMinute(mWheelMinute.getCurrentValue());
    }
        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null)
        {
            return;
        }
        switch (requestCode)
        {
            case 5:
                //闹钟
//                LingShengBean lingShengBean= (LingShengBean) data.getSerializableExtra("data");
//                tv_select_con.setText(lingShengBean.getTxt());
                break;
        }
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setActivated(!view.isActivated());
        }
    };
    private   void    setSelectTtpe()
    {
        switch (type)
        {
            case DingYueBean.DUOLAXIQU:
                rl_select.setVisibility(View.GONE);
                break;
            case DingYueBean.DUOLAENGLISH:
                rl_select.setVisibility(View.GONE);
                break;
            case DingYueBean.SHIWANWHY:
                //十万个为什么
                rl_select.setVisibility(View.GONE);
                break;
            case DingYueBean.DUOLABOOK:
                //有声小说
                rl_select.setVisibility(View.GONE);
                break;
            case DingYueBean.TIANQI:
                //弹出地区选择
                tv_name.setText("地区");
                tv_select_con.setText("请选择");
                selectTtpe=5;
                initDialog();
                break;
            case DingYueBean.GUSHI:
                //儿童故事
                rl_select.setVisibility(View.GONE);
                break;
            case DingYueBean.NEWS:
                rl_select.setVisibility(View.GONE);
                break;
            case DingYueBean.XIAOGUO:
                rl_select.setVisibility(View.GONE);
                break;
            case DingYueBean.DUOLAXINGZUO:
                //星座
                tv_name.setText("星座");
                tv_select_con.setText("请选择");
                initXingZuo();
                break;
            case DingYueBean.NAOZHONG:
                //显示时间设置
                //rl_xunhuan.setVisibility(View.VISIBLE);
                tv_name.setText("铃声设置");
                tv_select_con.setText("请选择");
                break;
        }
    }

    ActionSheetDialog actionSheetDialog;
    ActionSheetDialog actionXingZuoDialog;
    String[] mDatas = new String[0];

    /**
     * 星座弹出框
     */
    private  void  initXingZuo()
    {
        actionXingZuoDialog=new ActionSheetDialog(baseActivity).builder();
        actionXingZuoDialog.setCancelable(true);
        actionXingZuoDialog.setCanceledOnTouchOutside(true);
        mDatas=baseActivity.application.appDataInit.xingzuoDataS;
        actionXingZuoDialog.addSheetList(mDatas, ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {

                            @Override
                            public void onClick(int which) {
                                // TODO Auto-generated method stub
                                if (actionXingZuoDialog.isShowing()) {
                                    actionXingZuoDialog.dismiss();
                                }
                                tv_select_con.setText(mDatas[which-1]);
                            }
                        });
        actionXingZuoDialog.setTitle(R.string.addressedit_dialog_xingzuo);
        if (!baseActivity.isFinishing()) {
            actionXingZuoDialog.show();
        }

    }
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
                                //选择城市
                                selectTtpe=10;
                                initDialog();
                                break;
                            case 10:
                                //获取到城市
                                city= mDatas[which-1];
                                tv_select_con.setText(city);
                                break;
                        }
                    }
                });
        actionSheetDialog.setTitle(R.string.addressedit_dialog_province);
        if (!baseActivity.isFinishing()) {
            actionSheetDialog.show();
        }
    }
    private void buildAdapters() {
        mAdapterHour = new NumberWheelAdapter(0, 24, 1, "点");
        mAdapterMinute = new NumberWheelAdapter(0, 60, 10, "分");
    }

    @Override
    public void onClick(View view) {
  switch ( view.getId())
  {
      case R.id.rl_select:
          switch (type)
          {
              case DingYueBean.DUOLAXINGZUO:
                  //星座
                  initXingZuo();
                  break;
              case DingYueBean.TIANQI:
                  //弹出地区选择
                  setSelectTtpe();
                  break;
              case DingYueBean.NAOZHONG:
                  //打开铃声选择
                  Intent intent=new Intent(baseActivity, ListViewSelectActivity.class);
                  intent.putExtra("title","铃声选择");
                  intent.putExtra("type",DingYueBean.NAOZHONG);
                  baseActivity.startActivityForResult(intent,5);
                  break;
          }

          break;
      case R.id.tv_jian:
          int  txt=  Integer.valueOf(tv_time_count.getText().toString());
          if (txt==5)
          {
              baseActivity.DisPlay("循环时间间隔不能小于5分钟");
              return;
          }
          tv_time_count.setText((txt-5)+"");
          break;
      case R.id.tv_add:
          tv_time_count.setText(( Integer.valueOf(tv_time_count.getText().toString())+5)+"");
          break;
  }
    }
}
