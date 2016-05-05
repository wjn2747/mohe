package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.ClockModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/29.
 */
public class ServerClockRespone  extends BaseRespone
{
    private ArrayList<ClockModel>  data;

    public ArrayList<ClockModel> getData() {
        return data;
    }

    public void setData(ArrayList<ClockModel> data) {
        this.data = data;
    }
}
