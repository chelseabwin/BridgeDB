package com.qjs.bridgedb;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

public class DiseaseActivity extends Activity implements BridgeListFragment.Callbacks
{	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disease);
		
		Bundle bg_data = new Bundle();
		
		DbOperation db = new DbOperation(this);
		Cursor cursor = db.queryData("*", "base1", null); // 获取base1表中数据
		
		while (cursor.moveToNext()) 
		{
			String bg_id = cursor.getString(cursor.getColumnIndex("id"));
			String bg_name = cursor.getString(cursor.getColumnIndex("bridge_name"));
			bg_data.putString(bg_id, bg_name);
		}
		
		BridgeListFragment blf = new BridgeListFragment();
		blf.setArguments(bg_data); // 将数据传入BridgeListFragment
		// 将数据传入bridge_list
		getFragmentManager().beginTransaction()
			.add(R.id.bridge_list, blf)
			.commit();
	}

	@Override
	public void onItemSelected(Integer id) 
	{
		// TODO Auto-generated method stub
		// 创建Bundle，准备向Fragment传入参数
		Bundle bd = new Bundle();
		DbOperation db = new DbOperation(this);
		// 根据id查找数据
		Cursor cursor = db.queryData("*", "base1", "id=" + id);
		
		if (cursor.moveToFirst()) 
		{
			bd.putString("title", cursor.getString(cursor.getColumnIndex("bridge_name")));
		}		
		
		// 创建BridgeDetailFragment对象
		BridgeDetailFragment bdf = new BridgeDetailFragment();
		// 向Fragment传入参数
		bdf.setArguments(bd);
		// 使用fragment替换bridge_detail_container容器当前显示的Fragment
		getFragmentManager().beginTransaction()
			.replace(R.id.bridge_detail_container, bdf)
			.commit();		
	}
}
