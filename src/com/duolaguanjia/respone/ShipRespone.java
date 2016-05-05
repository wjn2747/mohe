package com.duolaguanjia.respone;

import com.duolaguanjia.bean.BaseRespone;
import com.duolaguanjia.model.OrderModel;

/**
 * Created by Administrator on 2016/1/23.
 */
public class ShipRespone extends BaseRespone
{
   private OrderModel data;


    public OrderModel getData() {
        return data;
    }

    public void setData(OrderModel data) {
        this.data = data;
    }
}
