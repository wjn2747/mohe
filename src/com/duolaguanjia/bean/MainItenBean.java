package com.duolaguanjia.bean;

import java.io.Serializable;

/**
 * Created by Night on 2015/11/1.
 */
public class MainItenBean implements Serializable
{
    private  String name;
    private  int src;

    public MainItenBean(String name,int src)
    {
        this.name=name;
        this.src=src;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }
}
