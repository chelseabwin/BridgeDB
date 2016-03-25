package com.qjs.bridgedb.detailed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.MyArrayAdapter;
import com.qjs.bridgedb.MyBaseAdapter;
import com.qjs.bridgedb.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ShowDiseaseDetailFragment extends Fragment{
	private String itemName, acrossNum, bgCode; // ��Ŀ����, ���, ����id
	private int itemId, tableId; // ��Ŀid, ��һ���б��ѡ�е�id
	private ArrayList<String> data = new ArrayList<String>();
	private ListView baseList, baseInfoList; // ����list�б�
	private List<Map<String, Object>> ListItems = null; // baseInfoList����Ŀ
	private int jumpFlag = 0; // ��ת��־λ(0Ϊչʾҳ��1Ϊ�޸�ҳ)
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// ��д�÷������÷������ص�View����ΪFragment��ʾ�����
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		final View rootView = inflater.inflate(R.layout.fragment_detailed_baselist, container, false);
		
		baseList = (ListView) rootView.findViewById(R.id.base_list);
		final Button btnEdit = (Button) rootView.findViewById(R.id.info_edit);
		final Button btnDelete = (Button) rootView.findViewById(R.id.info_delete);
		
		final Bundle args = getArguments();
		bgCode = args.getString("BRIDGE_ID");
		
		final DbOperation db = new DbOperation(this.getActivity());
		final String[] tableNames = {"disease_girder", "disease_wetjoint", "disease_support", "disease_pier",
				"disease_bentcap", "disease_tiebeam", "disease_atbody", "disease_atcapping", "disease_pa",
				"disease_bed", "disease_regstruc", "disease_wingwall", "disease_conslope", "disease_proslope",
				"disease_deck", "disease_joint", "disease_sidewalk", "disease_fence", "disease_watertight",
				"disease_lighting"};
		
		final HashMap<String, String> tableItemName = new HashMap<String, String>();
		//�ϲ�
		tableItemName.put("����", "disease_girder");
		tableItemName.put("���İ�", "disease_girder");
		tableItemName.put("����Ȧ", "disease_girder");
		tableItemName.put("�֡���ܹ�Ƭ", "disease_girder");
		tableItemName.put("�ϲ����ع���", "disease_girder");
		
		tableItemName.put("ʪ�ӷ�", "disease_wetjoint");
		tableItemName.put("�����", "disease_wetjoint");
		tableItemName.put("�·�", "disease_wetjoint");
		tableItemName.put("���Ͻṹ", "disease_wetjoint");
		tableItemName.put("��������ϵ", "disease_wetjoint");
		tableItemName.put("�ϲ�һ�㹹��", "disease_wetjoint");
		
		tableItemName.put("֧��", "disease_support");
		tableItemName.put("�����", "disease_support");
		
		//�²�
		tableItemName.put("�Ŷ�", "disease_pier");
		tableItemName.put("����", "disease_bentcap");
		tableItemName.put("ϵ��", "disease_tiebeam");
		tableItemName.put("��̨��", "disease_atbody");
		tableItemName.put("��̨ñ", "disease_atcapping");		
		tableItemName.put("��̨����", "disease_pa");
		tableItemName.put("�Ӵ�", "disease_bed");
		tableItemName.put("���ι�����", "disease_regstruc");
		tableItemName.put("��ǽ����ǽ", "disease_wingwall");
		tableItemName.put("׶��", "disease_conslope");
		tableItemName.put("����", "disease_proslope");
		
		//����ϵ
		tableItemName.put("������װ", "disease_deck");
		tableItemName.put("������װ��", "disease_joint");
		tableItemName.put("���е�", "disease_sidewalk");
		tableItemName.put("���ˡ�����", "disease_fence");
		tableItemName.put("����ˮϵͳ", "disease_watertight");
		tableItemName.put("��������־", "disease_lighting");
		
		for (int i = 0; i < tableNames.length; i++) {
			Cursor cursor = db.queryData("*", tableNames[i], "bg_id='" + bgCode + "'");
			while (cursor.moveToNext()) {
				data.add(cursor.getString(cursor.getColumnIndex("item_name")) 
						+ ": " + cursor.getString(cursor.getColumnIndex("parts_id"))
						+ ": " + cursor.getString(cursor.getColumnIndex("id")));				
			}
		}
		
		btnEdit.setVisibility(View.GONE);
		btnDelete.setVisibility(View.GONE);
		
		final MyArrayAdapter myArrayAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				data);
		
		// �����б�
		baseList.setAdapter(myArrayAdapter);
		
		baseList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				baseInfoList = (ListView) rootView.findViewById(R.id.base_info);
				String[] itemsStr = parent.getItemAtPosition(position).toString().split(": ");
				itemId= Integer.valueOf(itemsStr[2]); // ��ȡ����id
				acrossNum = itemsStr[1]; // ��ȡ���
				itemName = itemsStr[0]; // ��ȡ��������
				String tableName = tableItemName.get(itemName); // �ڹ�ϣ�����ҵ�����
				
				
				for (int i = 0; i < tableNames.length; i++) {
					if (tableNames[i].equals(tableName)) {
						tableId = i;
						break;
					}
				}
				
				ListItems = getBaseInfo(tableId, bgCode, acrossNum, itemId);
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				
				myArrayAdapter.setSelectItem(position);  
				myArrayAdapter.notifyDataSetInvalidated();
				
				// �Զ����б���ʾ��������
				MyBaseAdapter myBaseAdapter = new MyBaseAdapter(ListItems, inflater);
				baseInfoList.setAdapter(myBaseAdapter);
				
				btnEdit.setVisibility(View.VISIBLE);
				btnDelete.setVisibility(View.VISIBLE);
				
				btnEdit.setOnClickListener(new OnClickListener() { // �޸İ�ť

					@Override
					public void onClick(View v) {
						jumpFlag = 1;
						
						Intent intent = new Intent(getActivity(), EditDiseaseActivity.class);
						intent.putExtra("BRIDGE_ID", bgCode);
						intent.putExtra("ITEM_ID", itemId);
						intent.putExtra("ACROSS_NUM", acrossNum);
						intent.putExtra("ITEM_NAME", itemName);
						intent.putExtra("TABLE_ID", tableId);
		        		startActivity(intent);
					}					
				});
				
				btnDelete.setOnClickListener(new OnClickListener() { // ɾ����ť
					
					@Override
					public void onClick(View v) {
						
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("ɾ��������Ϣ�Ի���") // ���ñ���
							// ������Ϣ
							.setMessage("ȷ��ɾ���𣿸ù��̲����棡")
							// ������Ӱ�ť
							.setPositiveButton("ȷ��", new DialogInterface.OnClickListener () {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									DbOperation db = new DbOperation(getActivity());
									int flag = db.deleteData(tableNames[tableId], "id=" + itemId + " and bg_id='" + bgCode + "'" + " and parts_id='" + acrossNum + "'");
									
									if (flag == 1)
				            			Toast.makeText(getActivity(), "������Ϣɾ���ɹ�", Toast.LENGTH_SHORT).show();
				            		else
				            			Toast.makeText(getActivity(), "������Ϣɾ��ʧ��", Toast.LENGTH_SHORT).show();
									
									// ˢ���б�
									baseList.removeAllViewsInLayout();
									ArrayList<String> new_data = new ArrayList<String>();
									for (int i = 0; i < tableNames.length; i++) {
										Cursor cursor = db.queryData("*", tableNames[i], "bg_id='" + bgCode + "'");
										
										while (cursor.moveToNext()) {
											new_data.add(cursor.getString(cursor.getColumnIndex("item_name")) 
													+ ": " + cursor.getString(cursor.getColumnIndex("parts_id"))
													+ ": " + cursor.getString(cursor.getColumnIndex("id")));				
										}
									}
									
									MyArrayAdapter myArrayAdapter = new MyArrayAdapter(getActivity(),
											android.R.layout.simple_list_item_activated_1,
											new_data);						
									
									baseList.setAdapter(myArrayAdapter);
									
									btnEdit.setVisibility(View.GONE);
									btnDelete.setVisibility(View.GONE);
									
									// ���baseInfoList�б�
									LayoutInflater inflater = LayoutInflater.from(getActivity());						
									MyBaseAdapter myBaseAdapter = new MyBaseAdapter(new ArrayList<Map<String, Object>>(), inflater);
									baseInfoList.setAdapter(myBaseAdapter);									
								}				
							})
							// ���÷��ذ�ť
							.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
								}				
							})
							.create()
							.show();
					}
				});
			}
		});
		
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (jumpFlag == 1) { // ����Ǵ��޸�ҳ�����ģ���ˢ�µڶ����б�
			ListItems = getBaseInfo(tableId, bgCode, acrossNum, itemId);
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			
			// �Զ����б���ʾ��������
			MyBaseAdapter myBaseAdapter = new MyBaseAdapter(ListItems, inflater);
			baseInfoList.setAdapter(myBaseAdapter);
			
			jumpFlag = 0;
		}
	}
	
	private List<Map<String, Object>> getBaseInfo(int tableId, String bgCode, String partsId, int itemId) {
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
					"�������", "�ѷ쳤��", "�ѷ���", "����౾��������ʼ����", "����౾��������ֹ����", "�����ѷ쳤��", "�����ѷ���", 
					"����λ��", "��������", "������Ƭ"};
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
		
		cursor = db.queryData("*", tableName, "id=" + itemId + " and bg_id='" + bgCode + "'" + " and parts_id='" + partsId + "'");
		if (cursor.moveToFirst()) {
			for (int i = 4; i < cursor.getColumnCount()-1; i++) { // ȥ����һ���ڶ������������ĺ����һ��				
				if (tableName.equals("disease_girder")) {
					if (cursor.getString(4).equals("�ѷ�")) {
						if (cursor.getString(5).equals("���ѷ�")) {
							if (i == 6 || i == 10 || i == 11)
								continue;
						}
						else {
							if (i == 6 || i == 9)
								continue;
						}
						
						if (!cursor.getString(16).equals("�����") && !cursor.getString(16).equals("�����")) {
							if (i == 12 || i == 13 || i == 14 || i == 15)
								continue;
						}
					}
					else if (cursor.getString(4).equals("��������")) {
						if (i == 5 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15)
							continue;
					}
					else {
						if (i == 5 || i == 6 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15)
							continue;
					}
				}
				
				if (tableName.equals("disease_wetjoint") || tableName.equals("disease_pier")) {
					if (cursor.getString(4).equals("�ѷ�")) {
						if (cursor.getString(5).equals("���ѷ�")) {
							if (i == 6 || i == 10 || i == 11 || i == 12)
								continue;
						}
						else {
							if (i == 6 || i == 7 || i == 8 || i == 9)
								continue;
						}					
					}
					else if (cursor.getString(4).equals("��������")) {
						if (i == 5 || i == 7 || i == 8 || i == 9 || i == 10 || i == 11 || i == 12)
							continue;
					}
					else {
						if (i == 5 || i == 6 || i == 10 || i == 11 || i == 12)
							continue;
					}
				}
				
				if (tableName.equals("disease_atbody")) {
					if (!cursor.getString(4).equals("��������")) {
						if (i == 5)
							continue;
					}
				}
				
				fieldNameList.add(fieldNameArray[i-4]);
				fieldValueList.add(cursor.getString(i));
			}			
		}		
		
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();	
		
		for (int i = 0; i < fieldNameList.size(); i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("fieldName", fieldNameList.get(i));
			
			if ("������Ƭ".equals(fieldNameList.get(i))) {
				listItem.put("fieldValue", null);
				Uri uri = Uri.parse(fieldValueList.get(i));
				if (uri != null) {					
					String mFileName = EditDiseaseActivity.getPath(this.getActivity().getApplicationContext(), uri); // ��ȡͼƬ����·��
					Bitmap bitmap = EditDiseaseActivity.getBitmap(mFileName); // ���ݾ���·���ҵ�ͼƬ
					listItem.put("fieldImage", bitmap);
				}				
			}
			else {
				listItem.put("fieldValue", fieldValueList.get(i));
				listItem.put("fieldImage", null);				
			}
			
			listItems.add(listItem);
		}
		return listItems;		
	}
}
