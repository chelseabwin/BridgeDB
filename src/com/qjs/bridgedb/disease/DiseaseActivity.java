package com.qjs.bridgedb.disease;

import com.qjs.bridgedb.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DiseaseActivity extends FragmentActivity implements BridgeListFragment.Callbacks
{	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disease);
	}
	
	// ʵ��BridgeListFragment.Callbacks�ӿڵķ���
	@Override
	public void onItemSelected(Integer id) 
	{
		// TODO Auto-generated method stub
		// ����Bundle��׼����Fragment�������
		Bundle bd = new Bundle();
		bd.putInt("BRIDGE_ID", id);
		
		// ����BridgeDetailFragment����
		BridgeDetailFragment bdf = new BridgeDetailFragment();
		// ��Fragment�������
		bdf.setArguments(bd);
		// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.bridge_detail_container, bdf)
			.commit();		
	}
}
