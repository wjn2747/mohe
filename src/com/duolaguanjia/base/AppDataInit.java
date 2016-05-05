package com.duolaguanjia.base;

import android.content.Context;
import android.content.res.AssetManager;
import com.app_sdk.view.mobel.CityModel;
import com.app_sdk.view.mobel.DistrictModel;
import com.app_sdk.view.mobel.ProvinceModel;
import com.app_sdk.view.server.XmlParserHandler;
import com.duolaguanjia.bean.SelectValueBean;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/23.
 */
public class AppDataInit
{
  public  String[]  xingzuoDataS;
    /**
     * 所有省
     */
    public String[] mProvinceDatas;
    /**
     * key - 区 values - 邮编
     */
    public Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    /**
     * key - 市 values - 区
     */
    public Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 省 value - 市
     */
    public Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();

    public  static ArrayList<SelectValueBean>  hangyeData=new ArrayList<SelectValueBean>();
    public AppDataInit(Context context)
    {
        //初始化地址
        initProvinceDatas(context);
        //初始化行业
        initHangYe();
        //星座
        initXingZuo();
    }

    private void initXingZuo()
    {
        xingzuoDataS = new String[12];
        xingzuoDataS[0]="白羊座";
        xingzuoDataS[1]="金牛座";
        xingzuoDataS[2]="双子座";
        xingzuoDataS[3]="巨蟹座";
        xingzuoDataS[4]="狮子座";
        xingzuoDataS[5]="处女座";
        xingzuoDataS[6]="天秤座";
        xingzuoDataS[7]="天蝎座";
        xingzuoDataS[8]="射手座";
        xingzuoDataS[9]="摩羯座";
        xingzuoDataS[10]="水瓶座";
        xingzuoDataS[11]="双鱼座";


    }

    private void initHangYe() {
        hangyeData.add(new SelectValueBean("互联网-软件",""));
        hangyeData.add(new SelectValueBean("通信-硬件",""));
        hangyeData.add(new SelectValueBean("房地产-建筑",""));
        hangyeData.add(new SelectValueBean("文化传媒",""));
        hangyeData.add(new SelectValueBean("金融类",""));
        hangyeData.add(new SelectValueBean("消费品",""));
        hangyeData.add(new SelectValueBean("汽车-机械",""));
        hangyeData.add(new SelectValueBean("教育-翻译",""));
        hangyeData.add(new SelectValueBean("贸易-物流",""));
        hangyeData.add(new SelectValueBean("生物-医疗",""));
        hangyeData.add(new SelectValueBean("能源-化工",""));
        hangyeData.add(new SelectValueBean("政府机构",""));
        hangyeData.add(new SelectValueBean("服务业",""));
        hangyeData.add(new SelectValueBean("互其他行业",""));

    }

    /**
     * 解析省市区的XML数据
     */

    private void initProvinceDatas(Context context)
    {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList!= null && !provinceList.isEmpty()) {
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k=0; k<districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
}
