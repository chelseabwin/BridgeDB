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
	
	// 实现BridgeListFragment.Callbacks接口的方法
	@Override
	public void onItemSelected(String bg_code) {
		// 创建Bundle，准备向Fragment传入参数
		Bundle bd = new Bundle();
		bd.putString("FROM", fromPage);
		bd.putString("BRIDGE_ID", bg_code);
		bd.putString("ITEM_NAME", itemName);
		bd.putString("PARTS_ID", partsId);
		bd.putString("ITEM_ID", itemId + "");
		
		// 创建DiseaseDetailFragment对象
		ShowDiseaseDetailFragment ddf = new ShowDiseaseDetailFragment();
		// 向Fragment传入参数
		ddf.setArguments(bd);
		// 使用fragment替换bridge_detail_container容器当前显示的Fragment
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.bridge_detail_container, ddf)
			.commit();
	}
}
