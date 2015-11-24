package com.qjs.bridgedb;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MyArrayAdapter extends ArrayAdapter<String> {

	private int itemClicked = -1;

	public MyArrayAdapter(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = super.getView(position, convertView, parent);
		
		//如果是被点击的项,变换颜色
		if (position == this.itemClicked) {
			convertView.setBackgroundColor(Color.CYAN);
		}
		else {
			convertView.setBackgroundColor(Color.WHITE);
		}		
		return convertView;
	}

	public void setSelectItem(int selectItem) {
		this.itemClicked = selectItem;
	}
}
