package com.duolaguanjia.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/2.
 */
public class IconBean implements Serializable
{
    private  String   titleName;
    private  int icon;
  public  IconBean( String   titleName,int icon)
  {
      this.titleName=titleName;
      this.icon=icon;
  }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
