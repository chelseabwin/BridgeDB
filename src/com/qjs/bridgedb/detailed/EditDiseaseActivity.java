package com.qjs.bridgedb.detailed;

import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.subfragment.sub1.girderEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub1.supportEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub1.wetJointEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.pierEditFragment;

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
		case 0:
			frt = new girderEditFragment();
			break;
		case 1:
			frt = new wetJointEditFragment();
			break;
		case 2:
			frt = new supportEditFragment();
			break;
		case 3:
			frt = new pierEditFragment();
			break;
//		case 4:
//			tableName = "disease_bentcap";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 5:
//			tableName = "disease_tiebeam";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 6:
//			tableName = "disease_atbody";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "∆‰À˚≤°∫¶", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 7:
//			tableName = "disease_atcapping";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 8:
//			tableName = "disease_pa";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 9:
//			tableName = "disease_bed";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 10:
//			tableName = "disease_regstruc";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 11:
//			tableName = "disease_wingwall";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 12:
//			tableName = "disease_conslope";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 13:
//			tableName = "disease_proslope";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 14:
//			tableName = "disease_deck";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 15:
//			tableName = "disease_joint";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 16:
//			tableName = "disease_sidewalk";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 17:
//			tableName = "disease_fence";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 18:
//			tableName = "disease_watertight";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
//		case 19:
//			tableName = "disease_lighting";
//			fieldNameArray = new String[] {"≤°∫¶¿‡–Õ", "≤°∫¶√Ë ˆ", "≤°∫¶’’∆¨"};
//			break;
		default:
			break;
		}
		
		frt.setArguments(bd);
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.edit_disease_dlg, frt)
			.commit();
	}

}
