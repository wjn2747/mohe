package com.duolaguanjia.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		 
//		Toast.makeText(this, "分享成功12131331______________WXPayEntryActivity", 0).show();
		Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);
		// ConstantsAPI.COMMAND_PAY_BY_WX = 5;
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			String msg = "";
			switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				msg = resp.errCode + "认证被否决";
				break;
			case BaseResp.ErrCode.ERR_COMM:
				msg = resp.errCode + "一般错误";
				break;
			case BaseResp.ErrCode.ERR_OK:
				msg = resp.errCode + "正确返回";
//				SendAuth.Resp response = (SendAuth.Resp) resp;
//				if (response != null) {
//					String code = response.code;
//					
//					Intent intent = new Intent();
//					intent.setAction(WXLoginRunImpl.ACTION_WEIXIN_LOGIN_CODE);
//					intent.putExtra("code", code);
//					sendBroadcast(intent);
//				}
				break;
			case BaseResp.ErrCode.ERR_SENT_FAILED:
				msg = resp.errCode + "发送失败";
				break;
			case BaseResp.ErrCode.ERR_UNSUPPORT:
				msg = resp.errCode + "不支持错误";
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				msg = resp.errCode + "用户取消";
				break;
			default:
				break;
			}
		}
	}
	

}