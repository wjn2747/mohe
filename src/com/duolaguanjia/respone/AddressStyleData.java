package com.duolaguanjia.respone;

import com.duolaguanjia.model.AddressModel;
import com.duolaguanjia.model.MoHeStyleModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/25.
 */
public class AddressStyleData implements Serializable
{
    private AddressModel address;
    private ArrayList<MoHeStyleModel> scene;


    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public ArrayList<MoHeStyleModel> getScene() {
        return scene;
    }

    public void setScene(ArrayList<MoHeStyleModel> scene) {
        this.scene = scene;
    }
}
