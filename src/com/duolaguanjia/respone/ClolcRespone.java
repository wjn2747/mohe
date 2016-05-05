package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.ClockListModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/29.
 */
public class ClolcRespone extends BaseRespone
{
    private ArrayList<ClockListModel>  data;


    public ArrayList<ClockListModel> getData() {
        return data;
    }

    public void setData(ArrayList<ClockListModel> data) {
        this.data = data;
    }
}
