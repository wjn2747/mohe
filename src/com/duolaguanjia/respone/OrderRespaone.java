package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.OrderModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/23.
 */
public class OrderRespaone extends BaseRespone
{
    private ArrayList<OrderModel> data;


    public ArrayList<OrderModel> getData() {
        return data;
    }

    public void setData(ArrayList<OrderModel> data) {
        this.data = data;
    }
}
