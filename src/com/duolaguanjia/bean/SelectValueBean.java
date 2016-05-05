package com.duolaguanjia.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/31.
 */
public class SelectValueBean  implements Serializable
{
    private String name;
    private  String  value;
    private Object obj;

  public  SelectValueBean( String name,String  value)
  {
      this.name=name;
      this.value=value;
  }
    public  SelectValueBean( String name,String  value,Object obj)
    {
        this.name=name;
        this.value=value;
        this.obj=obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
