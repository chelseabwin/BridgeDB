package com.qjs.bridgedb.detailed;

import com.qjs.bridgedb.MainActivity;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.BaseFragmentActiviy;
import com.qjs.bridgedb.BridgeListFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

public class DiseaseDetailedActivity extends BaseFragmentActiviy implements BridgeListFragment.Callbacks {
	public Bitmap bitmap = null;
	private String fromPage = null;
	private String itemName = null;
	private String partsId = null;
	private int itemId = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_list);
	}
	
	@Override
    public void onBackPressed() {
		Intent intent = new Intent(DiseaseDetailedActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
    }
	
	// ʵ��BridgeListFragment.Callbacks�ӿڵķ���
	@Override
	public void onItemSelected(String bg_code) {
		// ����Bundle��׼����Fragment�������
		Bundle bd = new Bundle();
		bd.putString("FROM", fromPage);
		bd.putString("BRIDGE_ID", bg_code);
		bd.putString("ITEM_NAME", itemName);
		bd.putString("PARTS_ID", partsId);
		bd.putString("ITEM_ID", itemId + "");
		
		// ����DiseaseDetailFragment����
		ShowDiseaseDetailFragment ddf = new ShowDiseaseDetailFragment();
		// ��Fragment�������
		ddf.setArguments(bd);
		// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.bridge_detail_container, ddf)
			.commit();
	}
}
