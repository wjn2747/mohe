package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.ProBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ProRespone extends BaseRespone
{
    private ArrayList<ProBean> data;


    public ArrayList<ProBean> getData() {
        return data;
    }

    public void setData(ArrayList<ProBean> data) {
        this.data = data;
    }
}
