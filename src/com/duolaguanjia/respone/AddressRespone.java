package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.AddressModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/24.
 */
public class AddressRespone extends BaseRespone
{
    private ArrayList<AddressModel> data;


    public ArrayList<AddressModel> getData() {
        return data;
    }

    public void setData(ArrayList<AddressModel> data) {
        this.data = data;
    }
}
