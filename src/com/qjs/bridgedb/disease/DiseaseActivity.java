package com.qjs.bridgedb.disease;

import com.qjs.bridgedb.R;

import android.graphics.Bitmap;
import android.os.Bundle;

public class DiseaseActivity extends BaseFragmentActiviy implements BridgeListFragment.Callbacks {
	public Bitmap bitmap = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disease);
	}
	
	// 实现BridgeListFragment.Callbacks接口的方法
	@Override
	public void onItemSelected(Integer id) {
		// TODO Auto-generated method stub
		// 创建Bundle，准备向Fragment传入参数
		Bundle bd = new Bundle();
		bd.putInt("BRIDGE_ID", id);
		
		// 创建BridgeDetailFragment对象
		BridgeDetailFragment bdf = new BridgeDetailFragment();
		// 向Fragment传入参数
		bdf.setArguments(bd);
		// 使用fragment替换bridge_detail_container容器当前显示的Fragment
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.bridge_detail_container, bdf)
			.commit();
	}
}
