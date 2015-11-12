package com.qjs.bridgedb.disease;

import com.qjs.bridgedb.BaseFragmentActiviy;
import com.qjs.bridgedb.BridgeListFragment;
import com.qjs.bridgedb.MainActivity;
import com.qjs.bridgedb.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

public class DiseaseActivity extends BaseFragmentActiviy implements BridgeListFragment.Callbacks {
	public Bitmap bitmap = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_list);
	}
	
	@Override
    public void onBackPressed() {
		Intent intent = new Intent(DiseaseActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
    }
	
	// ʵ��BridgeListFragment.Callbacks�ӿڵķ���
	@Override
	public void onItemSelected(String bg_code) {
		// TODO Auto-generated method stub
		// ����Bundle��׼����Fragment�������
		Bundle bd = new Bundle();
		bd.putString("BRIDGE_ID", bg_code);
		
		// ����BridgeDetailFragment����
		DiseaseDetailFragment bdf = new DiseaseDetailFragment();
		// ��Fragment�������
		bdf.setArguments(bd);
		// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.bridge_detail_container, bdf)
			.commit();
	}
}
