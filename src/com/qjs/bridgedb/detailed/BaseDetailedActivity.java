package com.qjs.bridgedb.detailed;

import com.qjs.bridgedb.MainActivity;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.BaseFragmentActiviy;
import com.qjs.bridgedb.BridgeListFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

public class BaseDetailedActivity extends BaseFragmentActiviy implements BridgeListFragment.Callbacks {
	public Bitmap bitmap = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_list);
	}
	
	@Override
    public void onBackPressed() {
		Intent intent = new Intent(BaseDetailedActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
    }
	
	// ʵ��BridgeListFragment.Callbacks�ӿڵķ���
	@Override
	public void onItemSelected(String bg_code) {
		// ����Bundle��׼����Fragment�������
		Bundle bd = new Bundle();
		bd.putString("BRIDGE_ID", bg_code);
		
		// ����BaselistDetailFragment����
		BaseDetailFragment bldf = new BaseDetailFragment();
		// ��Fragment�������
		bldf.setArguments(bd);
		// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.bridge_detail_container, bldf)
			.commit();
	}
}
