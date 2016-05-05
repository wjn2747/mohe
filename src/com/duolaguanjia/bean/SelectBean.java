package com.duolaguanjia.bean;

import java.io.Serializable;

/**
 * Created by Night on 2015/11/5.
 */
public class SelectBean implements Serializable
{
    public  String  name;
    public  String  value;
    public  int id;
    public  String  tag;
    public  boolean   isSelect=false;
    private  Object  data;



    public SelectBean(String  name)
    {
        this.name=name;
    }
    public SelectBean(String  name, int  id)
    {
        this.name=name;
        this.id=id;
    }
    public SelectBean(String  name, String  value)
    {
        this.name=name;
        this.value=value;

    }
   public SelectBean(String  name, String  value, String  tag)
   { this.name=name;
       this.value=value;
       this.tag=tag;
   }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
