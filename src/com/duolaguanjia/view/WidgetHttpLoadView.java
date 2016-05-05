
package com.duolaguanjia.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.duolaguanjia.R;


public class WidgetHttpLoadView extends LinearLayout {

    private Context mContext;
    private TextView noNetWorkLbl;
    private int status; 
    
    public static final int HTTPVIEW_DONE = 0;
    public static final int HTTPVIEW_LOADING = 1;
    public static final int HTTPVIEW_NONETWORK = 2;
    

    public WidgetHttpLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
        this.setVisibility(View.GONE);
    }

    private void initViews() {
        View.inflate(mContext, R.layout.widget_http_network_layout, this);
        noNetWorkLbl = (TextView) findViewById(R.id.http_no_network);
    }
    
    public void setReLoadListener(OnClickListener listener){
    	noNetWorkLbl.setOnClickListener(listener);
    }
    
    public void setStatus(int status){
    	this.status = status;
    	refreshView();
    }
    
    private void refreshView(){
    	switch (status) {
		case HTTPVIEW_LOADING:
			this.setVisibility(View.VISIBLE);
			noNetWorkLbl.setVisibility(View.GONE);
			break;
		case HTTPVIEW_NONETWORK:
			this.setVisibility(View.VISIBLE);
			noNetWorkLbl.setVisibility(View.VISIBLE);
			break;
		case HTTPVIEW_DONE:
			this.setVisibility(View.GONE);
			break;
		default:
			break;
		}
    }

}
