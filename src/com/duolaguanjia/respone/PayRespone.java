package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.PayModel;

/**
 * Created by Administrator on 2016/1/25.
 */
public class PayRespone extends BaseRespone
{
    private PayModel data;
    private  String   txt;
    public PayModel getData() {
        return data;
    }
    public void setData(PayModel data) {
        this.data = data;
    }


    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
