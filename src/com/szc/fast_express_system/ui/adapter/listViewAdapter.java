package com.szc.fast_express_system.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;




import com.szc.fast_express_system.R;
import com.szc.fast_express_system.entities.OrderData;
import com.szc.fast_express_system.ui.fragment.NearFragment;


/*****
 * 
 * ����������ListView����ʾ
 * 
 * �Զ��������� BaseAdapter
 * 
 * *****/

public class listViewAdapter extends BaseAdapter{

	List<OrderData> list;

	private Context mContext;

	public listViewAdapter() {
		super();
	}

	public listViewAdapter(Context nearFragment,  List<OrderData> list) {
		this.mContext = nearFragment;
		this.list = list;
		
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int pos) {
		return list.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}

	public View getView(int pos, View v, ViewGroup p) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		v = (View) inflater.inflate(R.layout.item_listview, null);
		TextView tv1 = (TextView) v.findViewById(R.id.textView02);
		TextView tv2 = (TextView) v.findViewById(R.id.textView03);
		TextView tv3 = (TextView) v.findViewById(R.id.textView04);
		TextView tv4 = (TextView) v.findViewById(R.id.textView05);
		TextView tv5 = (TextView) v.findViewById(R.id.textView01);
		for (int i = 0; i < list.size(); i++) {

			tv1.setText("Username:" +list.get(pos).getUsername() );
			tv2.setText("Phone:" + list.get(pos).getPhone());
			tv3.setText("Adress:" + list.get(pos).getAdress());
			tv4.setText("Article:" + list.get(pos).getArticle());
			tv5.setText("Weight:" + list.get(pos).getWeight());
		}
		return v;
	}
}
