package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.GongSiAddressModel;

/**
 * Created by Administrator on 2016/1/30.
 */
public class GongSiAddressRespone extends BaseRespone
{
    private GongSiAddressModel data;


    public GongSiAddressModel getData() {
        return data;
    }

    public void setData(GongSiAddressModel data) {
        this.data = data;
    }
}
