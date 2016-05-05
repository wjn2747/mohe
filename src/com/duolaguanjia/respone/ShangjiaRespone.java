package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.CateModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ShangjiaRespone  extends BaseRespone
{
    private ArrayList<CateModel> data;


    public ArrayList<CateModel> getData() {
        return data;
    }

    public void setData(ArrayList<CateModel> data) {
        this.data = data;
    }
}
