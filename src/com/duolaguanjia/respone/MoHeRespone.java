package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.MoheBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/27.
 */
public class MoHeRespone  extends BaseRespone
{
    private ArrayList<MoheBean> data;


    public ArrayList<MoheBean> getData() {
        return data;
    }

    public void setData(ArrayList<MoheBean> data) {
        this.data = data;
    }
}
