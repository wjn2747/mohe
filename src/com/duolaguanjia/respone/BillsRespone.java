package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.BillModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/29.
 */
public class BillsRespone extends BaseRespone
{
    private ArrayList<BillModel> data;

    public ArrayList<BillModel> getData() {
        return data;
    }

    public void setData(ArrayList<BillModel> data) {
        this.data = data;
    }
}
