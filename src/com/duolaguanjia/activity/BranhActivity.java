package com.duolaguanjia.activity;

import android.support.v4.app.Fragment;
import com.duolaguanjia.activity.fragment.ScrollFragment;
import com.duolaguanjia.activity.fragment.account.BillDescriptFragment;
import com.duolaguanjia.activity.fragment.account.BillFragment;
import com.duolaguanjia.activity.fragment.balance.BallanceFragment;
import com.duolaguanjia.activity.fragment.balance.RechargeFragment;
import com.duolaguanjia.activity.fragment.bank.AddBankFragment;
import com.duolaguanjia.activity.fragment.bank.BandBankFragment;
import com.duolaguanjia.activity.fragment.dingyue.AlreadDingYueFragment;
import com.duolaguanjia.activity.fragment.dingyue.DingYueDescriptFragment;
import com.duolaguanjia.activity.fragment.imageFragment;
import com.duolaguanjia.activity.fragment.mohe.BindMoheFragment;
import com.duolaguanjia.activity.fragment.mohe.BindMoheSelectStyleFragment;
import com.duolaguanjia.activity.fragment.mohe.DeleteBindMoHeFragment;
import com.duolaguanjia.activity.fragment.order.BackMeneyFragment;
import com.duolaguanjia.activity.fragment.order.BackOrderFragment;
import com.duolaguanjia.activity.fragment.order.ListViewOrderFragment;
import com.duolaguanjia.activity.fragment.preferential.PreferentialCodeFragment;
import com.duolaguanjia.activity.fragment.shebei.MySheBeiFragment;
import com.duolaguanjia.activity.fragment.shebei.ServiceSheBeiFragment;
import com.duolaguanjia.activity.fragment.user.*;
import com.duolaguanjia.base.BaseBranhActicity;

/**
 * Created by Night on 2015/11/2.
 */
public class BranhActivity extends BaseBranhActicity {
    public static final int BRANCH_IMAGE = 1;//打开图片页面
    public static final int BRANCH_BIND_BANK = 3;//绑定银行卡
    public  static final  int BRANCH_ADD_BANK=4;//添加银行卡
    public  static final  int BRANCH_ADD_BILL=5;//账单
    public  static final  int BRANCH_ADD_BILL_DESCRIPT=6;//账单详情
    public  static final  int BRANCH_ADD_BILL_BALANCE0=7;//余额
    public  static final  int BRANCH_ADD_BILLRECHARGE=8;//充值
    public  static final  int BRANCH_BAND_MOHE=9;//绑定魔盒
    public  static final  int BRANCH_BAND_JIEBANG=10;//解绑魔盒
    public  static final  int BRANCH_ORDER_BACK=11;//申请退款
    public  static final  int BRANCH_ORDER_BACK_OK=12;//申请退款OK
    public  static final  int BRANCH_PREFERENTIAL_CODE=13;//优惠码
    public  static final  int BRANCH_MY_SHEBEI=14;//我的设备
    public  static final  int BRANCH_USER_SETTING=15;//个人设置
    public  static final  int BRANCH_USER_INPUT_SETTNG=17;//用户单输入
    public  static final  int BRANCH_USER_ACCOUNT_ANQUAN=18;//账户安全
    public  static final  int BRANCH_USER_SETTING_PWS=19;//设置支付密码
    public  static final  int BRANCH_USER_CLEAR_PWS=20;//重置支付密码
    public  static final  int BRANCH_USER_ADD_ADDRESS=21;//添加地址
    public  static final  int BRANCH_USER_FORGET_PWS=22;//忘记密码
    public  static final  int BRANCH_USER_GUASHI=23;//挂失
    public  static final  int BRANCH_DELETE_MOHE=24;//解除绑定魔盒
    public  static final  int BRANCH_SERVER_MOHE=25;//维修魔盒
    public  static final  int BRANCH_ADDRESS_ADD=26;//添加地址DingYueDescriptFragment
    public  static final  int BRANCH_SHENHE_SUCCESS=27;//维修 审核通过 请填写收货地址
    public  static final  int BRANCH_DINGYUE_SETTING=28;//订阅设置
    public  static final  int BRANCH_ALREAD_DINGYUE=29;//已订阅
    public static final int  BRANCH_ALL_ORDER=30;//全部订单
    public  static  final  int BRANCH_ABOUT=31;//关于我们
    public  static  final  int   BRANCH_FANKUI=32;//反馈

    @Override
    public Fragment initBranchData()
    {
        switch (branchType) {
            case BRANCH_FANKUI:
                return  new UserFanKuiFragment();
            case BRANCH_ABOUT:
                return  new  AboutFragment();
            case BRANCH_ALL_ORDER:
                return   ListViewOrderFragment.newInstance(-5,ListViewOrderFragment.ORDER_ALL);

            case BRANCH_ALREAD_DINGYUE:
                return new AlreadDingYueFragment();
            case BRANCH_DINGYUE_SETTING:
                return DingYueDescriptFragment.newInstance(getIntent().getStringExtra("data"),getIntent().getIntExtra("type",0),getIntent().getStringExtra("title"),getIntent().getStringExtra("clock_id"));
            case  BRANCH_SHENHE_SUCCESS:
                return ShenHeSuccessAddressFragment.newInstance(getIntent().getStringExtra("mac"),getIntent().getStringExtra("applyId"));
            case  BRANCH_ADDRESS_ADD:
                return BindMoheSelectStyleFragment.newInstance(getIntent().getStringExtra("addressiD"),getIntent().getStringExtra("title"),getIntent().getIntExtra("type",5));
            case  BRANCH_SERVER_MOHE:
                return ServiceSheBeiFragment.newInstance(getIntent().getStringExtra("applyId"),getIntent().getStringExtra("mac"),getIntent().getIntExtra("staic",0));
            case  BRANCH_DELETE_MOHE:
                return  GuaShiSettingFragment.newInstance(getIntent().getStringExtra("mac"),getIntent().getIntExtra("type",5));
            case  BRANCH_USER_GUASHI:
                return  GuaShiFragment.newInstance(getIntent().getIntExtra("type",0));
            case BRANCH_USER_FORGET_PWS:
                return  new ForgetPwsFragment();
            case BRANCH_USER_ADD_ADDRESS:
               return    AddAddressFragment.newInstance(getIntent().getStringExtra("title"));
            case BRANCH_USER_CLEAR_PWS:
                return  new ResetPwsFragment();
            case BRANCH_USER_SETTING_PWS:
                return new SettingPwsFragment();
            case BRANCH_USER_ACCOUNT_ANQUAN:
                return new Account_AnQuqa_Fragment();
            case BRANCH_USER_INPUT_SETTNG:
                return User_input_fragment.newInstance(getIntent().getIntExtra("type",User_input_fragment.INPUT_YUPE_NICK),getIntent().getStringExtra("value"));
            case BRANCH_USER_SETTING:
                return  new UserSettingFragment();
            case BRANCH_MY_SHEBEI:
                return  new MySheBeiFragment();
            case BRANCH_PREFERENTIAL_CODE:
                //优惠码
                return new PreferentialCodeFragment();
            case  BRANCH_ORDER_BACK_OK:
                return BackOrderFragment.newInstance(getIntent().getStringExtra("id"));
            case BRANCH_ORDER_BACK:
                return BackMeneyFragment.newInstance(getIntent().getStringExtra("id"),getIntent().getStringExtra("money"));
            case BRANCH_BAND_JIEBANG:
                return  DeleteBindMoHeFragment.newInstance(getIntent().getStringExtra("id"));
            case BRANCH_BAND_MOHE:
                return new BindMoheFragment();
            case BRANCH_ADD_BILLRECHARGE:
                return new RechargeFragment();
            case BRANCH_ADD_BILL_BALANCE0:
                return   new BallanceFragment();
            case BRANCH_BIND_BANK:
                return   new BandBankFragment();
            case BRANCH_IMAGE:
                return  imageFragment.newInstance(getIntent().getIntExtra("image",0));
            case 2:
                return  ScrollFragment.newInstance(getIntent().getIntExtra("image", 0));
            case BRANCH_ADD_BANK:
                return  new AddBankFragment();
            case BRANCH_ADD_BILL:
                return   BillFragment.newInstance(getIntent().getStringExtra("title"));
            case BRANCH_ADD_BILL_DESCRIPT:
                return   BillDescriptFragment.newInstance(getIntent().getStringExtra("id"),getIntent().getStringExtra("order_status"),getIntent().getStringExtra("title"),getIntent().getStringExtra("isPay"));

        }
         return  null;
    }
}
