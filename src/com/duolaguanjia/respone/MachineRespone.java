package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.MachineModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/29.
 */
public class MachineRespone  extends BaseRespone
{
    private ArrayList<MachineModel> data;

    public ArrayList<MachineModel> getData() {
        return data;
    }

    public void setData(ArrayList<MachineModel> data) {
        this.data = data;
    }
}
