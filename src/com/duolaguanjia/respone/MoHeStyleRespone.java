package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.MoHeStyleModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/24.
 */
public class MoHeStyleRespone extends BaseRespone
{
    private ArrayList<MoHeStyleModel> data;


    public ArrayList<MoHeStyleModel> getData() {
        return data;
    }

    public void setData(ArrayList<MoHeStyleModel> data) {
        this.data = data;
    }
}
