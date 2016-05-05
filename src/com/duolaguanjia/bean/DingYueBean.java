package com.duolaguanjia.bean;

/**
 * Created by Night on 2015/11/2.
 */
public class DingYueBean
{
    public static final  int TIANQI=1;//天气
    public static final  int GUSHI=2;//故事
    public static final  int NAOZHONG=3;//闹铃
    public static final  int NEWS=4;//资讯
    public static final  int XIAOGUO=5;//笑果
    public static final  int DUOLAXINGZUO=6;//星座
    public static final  int DUOLABOOK=7;//书单
    public static final  int SHIWANWHY=8;//十万个为什么
    public static final  int DUOLAENGLISH=9;//英语
    public static final  int DUOLAXIQU=10;//戏曲

    private  String  name;
    private  int  src;
    private  String   num;
    private  String   descript;
    private  int  type;
    private  boolean  isDingyue=false;
    private  String  price;




    public  DingYueBean(String  name,int  src,String   num,String   descript,int type)
    {
        this.setType(type);
        this.setName(name);
        this.setDescript(descript);
        this.setSrc(src);
        this.setNum(num);
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


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isDingyue() {
        return isDingyue;
    }

    public void setDingyue(boolean dingyue) {
        isDingyue = dingyue;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
