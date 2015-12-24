package com.qjs.bridgedb;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyBaseAdapter extends BaseAdapter {
	public List<Map<String, Object>> ListItems = null;
	public LayoutInflater inflater = null;
	
	public MyBaseAdapter(List<Map<String, Object>> ListItems, LayoutInflater inflater) {
		this.ListItems = ListItems;
		this.inflater = inflater;
	}

	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.simple_item_2, parent, false);
		if (convertView != null) {
			// ≤°∫¶±ÍÃ‚
			TextView view1 = (TextView) convertView.findViewById(R.id.field_name);
			if (ListItems.get(position).get("fieldName") != null) {
				view1.setText(ListItems.get(position).get("fieldName").toString());
			}
			
			// ≤°∫¶œÍ«È
			TextView view2 = (TextView) convertView.findViewById(R.id.field_value);
			if (ListItems.get(position).get("fieldValue") != null && !(ListItems.get(position).get("fieldImage") instanceof Bitmap)) {
				view2.setVisibility(View.VISIBLE);
				view2.setText(ListItems.get(position).get("fieldValue").toString());
			}
			else {
				view2.setVisibility(View.GONE);
			}
			
			// ≤°∫¶Õº∆¨
			ImageView view3 = (ImageView) convertView.findViewById(R.id.field_image);
			if (ListItems.get(position).get("fieldImage") != null && ListItems.get(position).get("fieldImage") instanceof Bitmap) {
				view2.setVisibility(View.GONE);
				view3.setVisibility(View.VISIBLE);
				view3.setImageBitmap((Bitmap) ListItems.get(position).get("fieldImage"));
			}
			else {
				view3.setVisibility(View.GONE);
			}
		}
		
		return convertView;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public Object getItem(int position) {
		return ListItems.get(position);
	}
	
	@Override
	public int getCount() {
		return ListItems.size();
	}
}
