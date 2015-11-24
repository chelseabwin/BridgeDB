package com.qjs.bridgedb.detailed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.MyArrayAdapter;
import com.qjs.bridgedb.R;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DiseaseDetailFragment extends Fragment{
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// ��д�÷������÷������ص�View����ΪFragment��ʾ�����
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		final View rootView = inflater.inflate(R.layout.fragment_detailed_baselist, container, false);
		
		ListView baseList = (ListView) rootView.findViewById(R.id.base_list);
		final Button btnEdit = (Button) rootView.findViewById(R.id.info_edit);
		
		final Bundle args = getArguments();
		final String bgCode = args.getString("BRIDGE_ID");
		
		final DbOperation db = new DbOperation(this.getActivity());
		final String[] tableNames = {"disease_girder", "disease_wetjoint", "disease_support", "disease_pier",
				"disease_bentcap", "disease_tiebeam", "disease_atbody", "disease_atcapping", "disease_pa",
				"disease_bed", "disease_regstruc", "disease_wingwall", "disease_conslope", "disease_proslope",
				"disease_deck", "disease_joint", "disease_sidewalk", "disease_fence", "disease_watertight",
				"disease_lighting"};
		
		final String[] itemNames = {"�ϲ����ز���", "�ϲ�һ�㲿��", "֧��", "�Ŷ�", "����", "ϵ��", "��̨��", "��̨ñ", 
				"��̨����", "�Ӵ�", "���ι�����", "��ǽ����ǽ", "׶��", "����", "������װ", "������װ��", "���е�", 
				"���ˡ�����", "����ˮϵͳ", "��������־"};
		
		ArrayList<String> data = new ArrayList<String>();
		
		for (int i = 0; i < tableNames.length; i++) {
			Cursor cursor = db.queryData("*", tableNames[i], "bg_id='" + bgCode + "'");
			while (cursor.moveToNext()) {
				data.add(itemNames[i] + ": " + cursor.getString(cursor.getColumnIndex("parts_id")));				
			}
		}
		
		btnEdit.setVisibility(View.GONE);
		
		final MyArrayAdapter myArrayAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				data);
		
		// �����б�
		baseList.setAdapter(myArrayAdapter);
		
		baseList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListView baseInfoList = (ListView) rootView.findViewById(R.id.base_info);
				String[] itemsStr = parent.getItemAtPosition(position).toString().split(": ");
				
				int tableId = 0;
				for (int i = 0; i < itemNames.length; i++) {
					if (itemNames[i].equals(itemsStr[0])) {
						tableId = i;
						break;
					}					
				}
				
				List<Map<String, Object>> ListItems = getBaseInfo(tableId, bgCode, itemsStr[1]);
				
				myArrayAdapter.setSelectItem(position);  
				myArrayAdapter.notifyDataSetInvalidated();
				
				// �����б�
				SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), ListItems, 
						R.layout.simple_item, 
						new String[] {"fieldName", "fieldValue"},
						new int[] {R.id.field_name, R.id.field_value});
				
				btnEdit.setVisibility(View.VISIBLE);
				
				baseInfoList.setAdapter(simpleAdapter);
			}
		});
		
		return rootView;
	}
	
	private List<Map<String, Object>> getBaseInfo(int tableId, String bgCode, String partsId) {
		ArrayList<String> fieldNameList = new ArrayList<String>();
		ArrayList<String> fieldValueList = new ArrayList<String>();
		String tableName = null;
		String[] fieldNameArray = null;
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = null;
		
		switch (tableId) {
		case 0:
			tableName = "disease_girder";
			fieldNameArray = new String[] {"��������", "�ѷ�����", "��������", "�౾��������ʼ����", "�౾��������ֹ����", 
					"�������", "�ѷ�౾��������ʼ����", "�ѷ쳤��", "�ѷ���", "����λ��", "��������", "������Ƭ"};
			break;
		case 1:
			tableName = "disease_wetjoint";
			fieldNameArray = new String[] {"��������", "�ѷ�����", "��������", "�౾��������ʼ����", "�౾��������ֹ����", 
					"�������", "�ѷ�౾��������ʼ����", "�ѷ쳤��", "�ѷ���", "��������", "������Ƭ"};
			break;
		case 2:
			tableName = "disease_support";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 3:
			tableName = "disease_pier";
			fieldNameArray = new String[] {"��������", "�ѷ�����", "��������", "�౾��������ʼ����", "�౾��������ֹ����", 
					"�������", "�ѷ�౾��������ʼ����", "�ѷ쳤��", "�ѷ���", "��������", "������Ƭ"};
			break;
		case 4:
			tableName = "disease_bentcap";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 5:
			tableName = "disease_tiebeam";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 6:
			tableName = "disease_atbody";
			fieldNameArray = new String[] {"��������", "��������", "��������", "������Ƭ"};
			break;
		case 7:
			tableName = "disease_atcapping";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 8:
			tableName = "disease_pa";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 9:
			tableName = "disease_bed";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 10:
			tableName = "disease_regstruc";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 11:
			tableName = "disease_wingwall";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 12:
			tableName = "disease_conslope";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 13:
			tableName = "disease_proslope";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 14:
			tableName = "disease_deck";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 15:
			tableName = "disease_joint";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 16:
			tableName = "disease_sidewalk";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 17:
			tableName = "disease_fence";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 18:
			tableName = "disease_watertight";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		case 19:
			tableName = "disease_lighting";
			fieldNameArray = new String[] {"��������", "��������", "������Ƭ"};
			break;
		default:
			break;
		}
		
		cursor = db.queryData("*", tableName, "bg_id='" + bgCode + "'" + " and parts_id='" + partsId + "'");
		if (cursor.moveToFirst()) {
			for (int i = 3; i < cursor.getColumnCount()-1; i++) { // ȥ����һ���ڶ������������һ��
				if (tableName.equals("disease_girder") || tableName.equals("disease_wetjoint") 
						|| tableName.equals("disease_pier")) {
					if (cursor.getString(3).equals("�ѷ�")) {
						if (cursor.getString(4).equals("���ѷ�")) {
							if (i == 5 || i == 9 || i == 10 || i == 11)
								continue;
						}
						else {
							if (i == 5 || i == 6 || i == 7 || i == 8)
								continue;
						}					
					}
					else if (cursor.getString(3).equals("��������")) {
						if (i == 4 || i == 9 || i == 10 || i == 11)
							continue;
					}
					else {
						if (i == 4 || i == 5 || i == 9 || i == 10 || i == 11)
							continue;
					}
				}
				
				if (tableName.equals("disease_atbody")) {
					if (!cursor.getString(3).equals("��������")) {
						if (i == 4)
							continue;
					}
				}
				
				fieldNameList.add(fieldNameArray[i-3]);
				fieldValueList.add(cursor.getString(i));
			}			
		}		
		
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();	
		
		for (int i = 0; i < fieldNameList.size(); i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("fieldName", fieldNameList.get(i));
			listItem.put("fieldValue", fieldValueList.get(i));
			listItems.add(listItem);
		}
		return listItems;		
	}
}
