package com.qjs.bridgedb.detailed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.MyArrayAdapter;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.collection.Base2Activity;
import com.qjs.bridgedb.collection.Base3Activity;
import com.qjs.bridgedb.collection.BaseActivity;
import com.qjs.bridgedb.collection.Parts2Activity;
import com.qjs.bridgedb.collection.PartsActivity;
import com.qjs.bridgedb.collection.StructureActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BaseDetailFragment extends Fragment{
    
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

		ArrayList<String> data = new ArrayList<String>();
		data.add("������������-ʶ��1");
		data.add("������������-ʶ��2");
		data.add("������������-ʶ��3");
		data.add("������������-�ṹ");
		data.add("������������-����1");
		data.add("������������-����2");
		data.add("�ϲ����ع�������");
		data.add("�ϲ�һ�㹹������");
		data.add("֧������");
		data.add("�Ŷ�����");
		
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
				List<Map<String, String>> ListItems = getBaseInfo(position, bgCode);
				
				myArrayAdapter.setSelectItem(position);  
				myArrayAdapter.notifyDataSetInvalidated();
				
				// �����б�
				SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), ListItems, 
						R.layout.simple_item, 
						new String[] {"fieldName", "fieldValue"},
						new int[] {R.id.field_name, R.id.field_value});
				
				btnEdit.setVisibility(View.VISIBLE);
				final int item = position;
				
				btnEdit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						switch (item) {
						case 0:
							Intent baseIntent = new Intent(getActivity(), BaseActivity.class);
							baseIntent.putExtra("toPrevId", bgCode);
							baseIntent.putExtra("toPrev", "toPrevBg");
			        		startActivity(baseIntent);
			    			getActivity().finish();
							break;
						case 1:
							Intent base2Intent = new Intent(getActivity(), Base2Activity.class);
							base2Intent.putExtra("toPrevId", bgCode);
							base2Intent.putExtra("toPrev", "toPrevBg");
			        		startActivity(base2Intent);
			    			getActivity().finish();
							break;
						case 2:
							Intent base3Intent = new Intent(getActivity(), Base3Activity.class);
							base3Intent.putExtra("toPrevId", bgCode);
							base3Intent.putExtra("toPrev", "toPrevBg");
			        		startActivity(base3Intent);
			    			getActivity().finish();
							break;
						case 3:
							Intent strIntent = new Intent(getActivity(), StructureActivity.class);
							strIntent.putExtra("toPrevId", bgCode);
							strIntent.putExtra("toPrev", "toPrevBg");
			        		startActivity(strIntent);
			    			getActivity().finish();
							break;
						case 4:
							Intent partsIntent = new Intent(getActivity(), PartsActivity.class);
							partsIntent.putExtra("toPrevId", bgCode);
							partsIntent.putExtra("toPrev", "toPrevBg");
			        		startActivity(partsIntent);
			    			getActivity().finish();
							break;
						case 5:
						case 6:
						case 7:
						case 8:
							Intent parts2Intent = new Intent(getActivity(), Parts2Activity.class);
							parts2Intent.putExtra("toPrevId", bgCode);
							parts2Intent.putExtra("toPrev", "toPrevBg");
			        		startActivity(parts2Intent);
			    			getActivity().finish();
							break;
						case 9:
							Intent partsIntent1 = new Intent(getActivity(), PartsActivity.class);
							partsIntent1.putExtra("toPrevId", bgCode);
							partsIntent1.putExtra("toPrev", "toPrevBg");
			        		startActivity(partsIntent1);
			    			getActivity().finish();
							break;

						default:
							break;
						}
						
					}
				});
				
				baseInfoList.setAdapter(simpleAdapter);
			}
		});
		
		return rootView;
	}
	
	private List<Map<String, String>> getBaseInfo(int position, String bgCode) {
		ArrayList<String> fieldNameList = new ArrayList<String>();
		ArrayList<String> fieldValueList = new ArrayList<String>();
		String tableName = null;
		
		switch (position) {
		case 0:
			tableName = "base1";
			fieldNameList.clear();
			fieldNameList.add("��������");
			fieldNameList.add("·�ߺ�");
			fieldNameList.add("·������");
			fieldNameList.add("·������");
			fieldNameList.add("��·�����ȼ�");
			fieldNameList.add("˳���");
			fieldNameList.add("���ڵ�");
			fieldNameList.add("����׮��");
			fieldNameList.add("������λ");
			fieldNameList.add("��Խ��������");
			fieldNameList.add("��Խ��������");
			fieldNameList.add("��������");
			break;
		case 1:
			tableName = "base2";
			fieldNameList.clear();
			fieldNameList.add("��������");
			fieldNameList.add("��ƺ��صȼ�");
			fieldNameList.add("������;");
			fieldNameList.add("����״̬");
			fieldNameList.add("���ϱ���");			
			fieldNameList.add("�����λ");
			fieldNameList.add("������ʽ");
			fieldNameList.add("֧������");
			fieldNameList.add("����");
			break;
		case 2:
			tableName = "base3";
			fieldNameList.clear();
			fieldNameList.add("�Ŷղ���");
			fieldNameList.add("�Ŷս�����ʽ");
			fieldNameList.add("�Ŷ�����");			
			fieldNameList.add("��̨����");
			fieldNameList.add("��̨����");			
			fieldNameList.add("��̨��������");
			fieldNameList.add("��̨����");
			fieldNameList.add("������װ����");
			fieldNameList.add("����������");
			break;
		case 3:
			tableName = "structure";
			fieldNameList.clear();
			fieldNameList.add("�ſ����");
			fieldNameList.add("���羶");
			fieldNameList.add("����ȫ��");
			fieldNameList.add("�ſ����");			
			fieldNameList.add("����ȫ��");
			fieldNameList.add("���澻��");
			fieldNameList.add("�Ÿ�");
			fieldNameList.add("�����޸�");			
			fieldNameList.add("����ʱ��");
			fieldNameList.add("ͨ���ȼ�");
			fieldNameList.add("���н����");
			fieldNameList.add("��������");
			break;
		case 4:
			tableName = "parts1";
			fieldNameList.clear();
			fieldNameList.add("��ǽ����ǽ");
			fieldNameList.add("׶��");
			fieldNameList.add("���¸���");
			fieldNameList.add("��̨����");			
			fieldNameList.add("��̨��������");
			fieldNameList.add("�Ӵ�����");
			fieldNameList.add("���ι��������");
			break;
		case 5:
			tableName = "parts2";
			fieldNameList.clear();
			fieldNameList.add("������װ����");
			fieldNameList.add("������װ�ø���");
			fieldNameList.add("���е�");
			fieldNameList.add("���ˡ�����");			
			fieldNameList.add("����ˮϵͳ����");
			fieldNameList.add("��������־����");
			break;
		case 6:
			tableName = "load_detail";
			fieldNameList.clear();
			fieldNameList.add("�ϲ����ع�������");
			fieldNameList.add("�ϲ����ع������");
			break;
		case 7:
			tableName = "general_detail";
			fieldNameList.clear();
			fieldNameList.add("�ϲ�һ�㹹������");
			fieldNameList.add("�ϲ�һ�㹹�����");
			break;
		case 8:
			tableName = "support_detail";
			fieldNameList.clear();
			fieldNameList.add("֧������");
			fieldNameList.add("֧�����");
			break;
		case 9:
			tableName = "pier_detail";
			fieldNameList.clear();
			fieldNameList.add("�Ŷ�����");
			fieldNameList.add("�Ŷձ��");
			fieldNameList.add("�������");
			fieldNameList.add("ϵ�����");
			break;
		default:
			break;
		}
		
		String bgCodeName = null;
		if (tableName.equals("base1"))
			bgCodeName = "bridge_code";
		else
			bgCodeName = "bg_id";
		
		
		
		DbOperation db = new DbOperation(this.getActivity());		
		Cursor cursor = db.queryData("*", tableName, bgCodeName + "='" + bgCode + "'");
		if (cursor.moveToFirst()) {
			for (int i = 2; i < cursor.getColumnCount()-1; i++) { // ȥ����һ���ڶ������һ��
				if (tableName == "parts1" && i == 5) // ȥ���Ŷ�����
					continue;
				if (tableName == "parts2" && i == 2) // ȥ���ϲ����ع�������
					continue;
				if (tableName == "parts2" && i == 3) // ȥ���ϲ�һ�㹹������
					continue;
				if (tableName == "parts2" && i == 4) // ȥ��֧������
					continue;
				fieldValueList.add(cursor.getString(i));
			}			
		}
		
		List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
		
		System.out.println(fieldNameList);
		System.out.println(fieldValueList);
		
		for (int i = 0; i < fieldNameList.size(); i++) {
			Map<String, String> listItem = new HashMap<String, String>();
			listItem.put("fieldName", fieldNameList.get(i));
			if (!fieldValueList.isEmpty())
				listItem.put("fieldValue", fieldValueList.get(i));
			listItems.add(listItem);
		}
		return listItems;		
	}
}
