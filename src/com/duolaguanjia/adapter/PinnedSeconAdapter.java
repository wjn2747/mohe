 package com.duolaguanjia.adapter;

 import android.content.Context;
 import android.graphics.Color;
 import android.view.View;
 import android.view.ViewGroup.LayoutParams;
 import android.widget.TextView;
 import com.app_sdk.adapter.CommonBaseAdapter;
 import com.app_sdk.adapter.CommonBaseViewHolder;
 import com.app_sdk.tool.DensityUtil;
 import com.app_sdk.view.PinnedSectionListView;
 import com.duolaguanjia.R;
 import com.duolaguanjia.bean.Item;
 import com.duolaguanjia.bean.SelectBean;

 import java.util.*;

 public class PinnedSeconAdapter extends CommonBaseAdapter<Item> implements PinnedSectionListView.PinnedSectionListAdapter
{
	private Context context;
	public PinnedSeconAdapter(Context context, HashMap<String, ArrayList<SelectBean>> mDatas,
							  int itemLayoutId)
	{
		super(context, generateDataset(mDatas), itemLayoutId);
		//排序
		this.context=context;
	}
	public  void  setNotifyDataSetChanged( HashMap<String, ArrayList<SelectBean>> Datas)
	{
		mDatas.clear();
		mDatas.addAll(generateDataset(Datas));
		notifyDataSetChanged();
	}

	@Override
	public boolean isItemViewTypePinned(int viewType) {
		return viewType == Item.SECTION;
	}

	@Override
	public int getItemViewType(int position) {
		return getItem(position).type;
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	@Override
	public void convert(CommonBaseViewHolder helper, Item item, int position)
	{
		helper.getView(R.id.tv_name).setTag(item);
		helper.setText(R.id.tv_name, item.text);
		TextView tv=helper.getView(R.id.tv_name);
		tv.setTextColor(Color.BLACK);
		LayoutParams params=	helper.getConvertView().getLayoutParams();
		if (item.type == Item.SECTION) {
			params.height= DensityUtil.dip2px(context, 40);
			tv.setTextSize(12);
			tv.setTextColor(Color.parseColor("#5B69C5"));
			helper.getConvertView().setBackgroundColor(Color.parseColor("#F4F5F6"));
		}else {
			params.height= DensityUtil.dip2px(context, 50);
			tv.setTextSize(14);
			helper.getConvertView().setBackgroundColor(Color.WHITE);
			helper.getView(R.id.view).setVisibility(View.VISIBLE);
		}
	}

	private static List<Item> generateDataset( HashMap<String, ArrayList<SelectBean>> hashData) {
	
		ArrayList<Item> items=new ArrayList<Item>();
		int sectionPosition = 0, listPosition = 0;
		Iterator iter = hashData.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Item section = new Item(Item.SECTION, key.toString());
			ArrayList<SelectBean> val = (ArrayList<SelectBean>) entry
					.getValue();
			section.sectionPosition = sectionPosition;
			section.listPosition = listPosition++;
			items.add(section);
			// 遍历
			for (SelectBean crtyBean : val) {
				Item item = new Item(Item.ITEM, crtyBean.name);
				item.sectionPosition = sectionPosition;
				item.listPosition = listPosition++;
				items.add(item);
			}
		}
		return items;
	}
    int  postion=-1;
	public void setSelect(int arg2) {
		postion=arg2;
		
	}

}
