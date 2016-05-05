package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.ServerMode;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/29.
 */
public class ServerListRespone extends BaseRespone
{
    private ArrayList<ServerMode> data;

    public ArrayList<ServerMode> getData() {
        return data;
    }

    public void setData(ArrayList<ServerMode> data) {
        this.data = data;
    }
}
