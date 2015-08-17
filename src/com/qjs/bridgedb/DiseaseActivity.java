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
		Cursor cursor = db.queryData("*", "base1", null); // ��ȡbase1��������
		
		while (cursor.moveToNext()) 
		{
			String bg_id = cursor.getString(cursor.getColumnIndex("id"));
			String bg_name = cursor.getString(cursor.getColumnIndex("bridge_name"));
			bg_data.putString(bg_id, bg_name);
		}
		
		BridgeListFragment blf = new BridgeListFragment();
		blf.setArguments(bg_data); // �����ݴ���BridgeListFragment
		// �����ݴ���bridge_list
		getFragmentManager().beginTransaction()
			.add(R.id.bridge_list, blf)
			.commit();
	}

	@Override
	public void onItemSelected(Integer id) 
	{
		// TODO Auto-generated method stub
		// ����Bundle��׼����Fragment�������
		Bundle bd = new Bundle();
		DbOperation db = new DbOperation(this);
		// ����id��������
		Cursor cursor = db.queryData("*", "base1", "id=" + id);
		
		if (cursor.moveToFirst()) 
		{
			bd.putString("title", cursor.getString(cursor.getColumnIndex("bridge_name")));
		}		
		
		// ����BridgeDetailFragment����
		BridgeDetailFragment bdf = new BridgeDetailFragment();
		// ��Fragment�������
		bdf.setArguments(bd);
		// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
		getFragmentManager().beginTransaction()
			.replace(R.id.bridge_detail_container, bdf)
			.commit();		
	}
}
