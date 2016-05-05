package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.MoHeGuaShiModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/29.
 */
public class MoHeGuaShiRespone extends BaseRespone
{
    private ArrayList<MoHeGuaShiModel> data;

    public ArrayList<MoHeGuaShiModel> getData() {
        return data;
    }

    public void setData(ArrayList<MoHeGuaShiModel> data) {
        this.data = data;
    }
}
