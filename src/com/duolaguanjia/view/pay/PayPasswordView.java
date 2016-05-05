package com.duolaguanjia.view.pay;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.duolaguanjia.R;
import com.duolaguanjia.view.WidgetMenuItem;

public class PayPasswordView implements View.OnClickListener{
  WidgetMenuItem wm_more;
	private ImageView del;
	private ImageView zero;
	private ImageView one,iv_delete;
	private ImageView two;
	private ImageView three;
	private ImageView four;
	private ImageView five;
	private ImageView sex;
	private ImageView seven;
	private ImageView eight;
	private ImageView nine;
	private TextView box1;
	private TextView box2;
	private TextView box3;
	private TextView box4;
	private TextView box5;
	private TextView box6,tv_proName;
	TextView tv_price;
	private ArrayList<String> mList=new ArrayList<String>();
	private View mView;
	private OnPayListener listener;
	private Context mContext;
	public static  final int NORMAT=5;//默认状态
	public static  final int PWS_SURE=6;//密码确认状态
	public PayPasswordView(String  name,String patTag,String monney,Context mContext,OnPayListener listener,int type){
		getDecorView(name,patTag,monney, mContext, listener,type);
	}
	public static PayPasswordView getInstance(String  name,String  patTag,String monney,Context mContext,OnPayListener listener,int type){
		return  new PayPasswordView(name,patTag,monney,mContext,listener,type);
	}

	public void getDecorView(String  name,String patTag,String monney,Context mContext,OnPayListener listener,int type){
		this.listener=listener;
		this.mContext=mContext;
		mView=LayoutInflater.from(mContext).inflate(R.layout.item_paypassword, null);
		//设置商品名称
		tv_proName= (TextView) mView.findViewById(R.id.tv_proName);
		tv_proName.setText(name);
		iv_delete= (ImageView) mView.findViewById(R.id.iv_delete);
		 tv_price= (TextView) mView.findViewById(R.id.tv_price);
		wm_more= (WidgetMenuItem) mView.findViewById(R.id.wm_more);
		 if (patTag.equalsIgnoreCase("wx"))
		 {
			 //威信
			 wm_more.setText("微信");
		 }else if (patTag.equalsIgnoreCase("alipay"))
		 {
               //支付宝
			 wm_more.setText("支付宝");
		 }else if (patTag.equalsIgnoreCase("ye")){
			 //余额
			 wm_more.setText("余额");
		 }
		tv_price.setText(monney);
		wm_more.setOnClickListener(this);
		iv_delete.setOnClickListener(this);
	    del= (ImageView) mView.findViewById(R.id.pay_keyboard_del);
		zero= (ImageView) mView.findViewById(R.id.pay_keyboard_zero);
		one= (ImageView) mView.findViewById(R.id.pay_keyboard_one);
		two= (ImageView) mView.findViewById(R.id.pay_keyboard_two);
		three= (ImageView) mView.findViewById(R.id.pay_keyboard_three);
		four= (ImageView) mView.findViewById(R.id.pay_keyboard_four);
		five= (ImageView) mView.findViewById(R.id.pay_keyboard_five);
		sex= (ImageView) mView.findViewById(R.id.pay_keyboard_sex);
		seven= (ImageView) mView.findViewById(R.id.pay_keyboard_seven);
		eight= (ImageView) mView.findViewById(R.id.pay_keyboard_eight);
		nine= (ImageView) mView.findViewById(R.id.pay_keyboard_nine);
		box1= (TextView) mView.findViewById(R.id.pay_box1);
		box2= (TextView) mView.findViewById(R.id.pay_box2);
		box3= (TextView) mView.findViewById(R.id.pay_box3);
		box4= (TextView) mView.findViewById(R.id.pay_box4);
		box5= (TextView) mView.findViewById(R.id.pay_box5);
		box6= (TextView) mView.findViewById(R.id.pay_box6);
		del.setOnClickListener(this);
		zero.setOnClickListener(this);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		sex.setOnClickListener(this);
		seven.setOnClickListener(this);
		eight.setOnClickListener(this);
		nine.setOnClickListener(this);
		del.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				parseActionType(KeyboardEnum.longdel);
				return false;
			}
		});
		if (type==PWS_SURE)
		{
			wm_more.setVisibility(View.GONE);
		}

	}

	private void parseActionType(KeyboardEnum type) {
		// TODO Auto-generated method stub
		if(type.getType()== KeyboardEnum.ActionEnum.add){
			if(mList.size()<6){
				mList.add(type.getValue());
				updateUi();
			}
		}else if(type.getType()== KeyboardEnum.ActionEnum.delete){
			if(mList.size()>0){
				mList.remove(mList.get(mList.size()-1));
				updateUi();
			}
		}else if(type.getType()== KeyboardEnum.ActionEnum.cancel){
			listener.onCancelPay();
		}else if(type.getType()== KeyboardEnum.ActionEnum.sure){
			if(mList.size()<6){
				Toast.makeText(mContext, "支付密码必须6位", Toast.LENGTH_SHORT).show();
			}else{
				String payValue="";
				for (int i = 0; i < mList.size(); i++) {
					payValue+=mList.get(i);
				}
				listener.onSurePay(payValue);
			}
		}else if(type.getType()==KeyboardEnum.ActionEnum.longClick){
			mList.clear();
			updateUi();
		}

	}
	private void updateUi() {
		// TODO Auto-generated method stub
		if(mList.size()==0){
			box1.setText("");
			box2.setText("");
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==1){
			box1.setText(mList.get(0));
			box2.setText("");
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==2){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==3){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==4){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==5){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText(mList.get(4));
			box6.setText("");
		}else if(mList.size()==6){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText(mList.get(4));
			box6.setText(mList.get(5));
			//调用
			String payValue="";
			for (int i = 0; i < mList.size(); i++) {
				payValue+=mList.get(i);
			}
			listener.onSurePay(payValue);
		}
	}

	@Override
	public void onClick(View v) {
		if(v==zero){
			parseActionType(KeyboardEnum.zero);
		}else if(v==one){
			parseActionType(KeyboardEnum.one);
		}else if(v==two){
			parseActionType(KeyboardEnum.two);
		}else if(v==three){
			parseActionType(KeyboardEnum.three);
		}else if(v==four){
			parseActionType(KeyboardEnum.four);
		}else if(v==five){
			parseActionType(KeyboardEnum.five);
		}else if(v==sex){
			parseActionType(KeyboardEnum.sex);
		}else if(v==seven){
			parseActionType(KeyboardEnum.seven);
		}else if(v==eight){
			parseActionType(KeyboardEnum.eight);
		}else if(v==nine){
			parseActionType(KeyboardEnum.nine);
		}else if(v==del){
			parseActionType(KeyboardEnum.del);
		}else if (v==iv_delete)
		{
			listener.onCancelPay();
		}else if (v==wm_more)
		{
			listener.onMore();
		}
	}

	public interface OnPayListener{
		void onCancelPay();
		void onSurePay(String password);
		void  onMore();
	}

	public View getView(){
		return mView;
	}
}
