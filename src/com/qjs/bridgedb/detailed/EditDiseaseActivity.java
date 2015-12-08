package com.qjs.bridgedb.detailed;

import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.subfragment.sub1.girderEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub1.supportEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub1.wetJointEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.abutmentEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.beamEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.pierEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.sub2OtherEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub3.deckEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub3.sub3OtherEditFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class EditDiseaseActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_disease_edit_dlg);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		Bundle bd = new Bundle();
		bd.putString("BRIDGE_ID", bundle.getString("BRIDGE_ID"));
		bd.putInt("ITEM_ID", bundle.getInt("ITEM_ID"));
		bd.putString("ACROSS_NUM", bundle.getString("ACROSS_NUM"));
		bd.putString("ITEM_NAME", bundle.getString("ITEM_NAME"));
		
		int tableId = bundle.getInt("TABLE_ID");
		Fragment frt = null;
		
		switch (tableId) {
		// 上部
		case 0:
			frt = new girderEditFragment();
			break;
		case 1:
			frt = new wetJointEditFragment();
			break;
		case 2:
			frt = new supportEditFragment();
			break;
		// 下部
		case 3:
			frt = new pierEditFragment();
			break;
		case 4:
		case 5:
			frt = new beamEditFragment();
			break;
		case 6:
		case 7:
		case 8:
			frt = new abutmentEditFragment();
			break;
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
			frt = new sub2OtherEditFragment();
			break;
		// 桥面系
		case 14:
		case 15:
			frt = new deckEditFragment();
			break;
		case 16:
		case 17:
			frt = new sub3OtherEditFragment();
			break;
		case 18:
		case 19:
			frt = new deckEditFragment();
			break;
		default:
			break;
		}
		
		frt.setArguments(bd);
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.edit_disease_dlg, frt)
			.commit();
	}

}
