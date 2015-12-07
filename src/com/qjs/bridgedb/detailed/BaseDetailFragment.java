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

	// 重写该方法，该方法返回的View将作为Fragment显示的组件
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		final View rootView = inflater.inflate(R.layout.fragment_detailed_baselist, container, false);
		
		ListView baseList = (ListView) rootView.findViewById(R.id.base_list);
		final Button btnEdit = (Button) rootView.findViewById(R.id.info_edit);
		
		final Bundle args = getArguments();
		final String bgCode = args.getString("BRIDGE_ID");

		ArrayList<String> data = new ArrayList<String>();
		data.add("桥梁基本数据-识别1");
		data.add("桥梁基本数据-识别2");
		data.add("桥梁基本数据-识别3");
		data.add("桥梁基本数据-结构");
		data.add("桥梁基本数据-部件1");
		data.add("桥梁基本数据-部件2");
		data.add("上部承重构件详情");
		data.add("上部一般构件详情");
		data.add("支座详情");
		data.add("桥墩详情");
		
		btnEdit.setVisibility(View.GONE);
		
		final MyArrayAdapter myArrayAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				data);
		
		// 设置列表
		baseList.setAdapter(myArrayAdapter);
		
		baseList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListView baseInfoList = (ListView) rootView.findViewById(R.id.base_info);
				List<Map<String, String>> ListItems = getBaseInfo(position, bgCode);
				
				myArrayAdapter.setSelectItem(position);  
				myArrayAdapter.notifyDataSetInvalidated();
				
				// 设置列表
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
			fieldNameList.add("桥梁名称");
			fieldNameList.add("路线号");
			fieldNameList.add("路线名称");
			fieldNameList.add("路线类型");
			fieldNameList.add("公路技术等级");
			fieldNameList.add("顺序号");
			fieldNameList.add("所在地");
			fieldNameList.add("中心桩号");
			fieldNameList.add("管养单位");
			fieldNameList.add("跨越地物名称");
			fieldNameList.add("跨越地物类型");
			fieldNameList.add("桥梁性质");
			break;
		case 1:
			tableName = "base2";
			fieldNameList.clear();
			fieldNameList.add("桥梁分类");
			fieldNameList.add("设计荷载等级");
			fieldNameList.add("桥梁用途");
			fieldNameList.add("桥梁状态");
			fieldNameList.add("材料编码");			
			fieldNameList.add("桥面板位");
			fieldNameList.add("受力型式");
			fieldNameList.add("支座类型");
			fieldNameList.add("桥型");
			break;
		case 2:
			tableName = "base3";
			fieldNameList.clear();
			fieldNameList.add("桥墩材料");
			fieldNameList.add("桥墩截面形式");
			fieldNameList.add("桥墩类型");			
			fieldNameList.add("桥台材料");
			fieldNameList.add("桥台类型");			
			fieldNameList.add("墩台基础材料");
			fieldNameList.add("墩台基础");
			fieldNameList.add("桥面铺装类型");
			fieldNameList.add("伸缩缝类型");
			break;
		case 3:
			tableName = "structure";
			fieldNameList.clear();
			fieldNameList.add("桥跨组合");
			fieldNameList.add("最大跨径");
			fieldNameList.add("桥梁全长");
			fieldNameList.add("桥宽组合");			
			fieldNameList.add("桥面全宽");
			fieldNameList.add("桥面净宽");
			fieldNameList.add("桥高");
			fieldNameList.add("桥梁限高");			
			fieldNameList.add("建桥时间");
			fieldNameList.add("通航等级");
			fieldNameList.add("跨中截面高");
			fieldNameList.add("桥面纵坡");
			break;
		case 4:
			tableName = "parts1";
			fieldNameList.clear();
			fieldNameList.add("翼墙、耳墙");
			fieldNameList.add("锥坡");
			fieldNameList.add("护坡个数");
			fieldNameList.add("桥台个数");			
			fieldNameList.add("墩台基础个数");
			fieldNameList.add("河床个数");
			fieldNameList.add("调治构造物个数");
			break;
		case 5:
			tableName = "parts2";
			fieldNameList.clear();
			fieldNameList.add("桥面铺装个数");
			fieldNameList.add("伸缩缝装置个数");
			fieldNameList.add("人行道");
			fieldNameList.add("栏杆、护栏");			
			fieldNameList.add("防排水系统个数");
			fieldNameList.add("照明、标志个数");
			break;
		case 6:
			tableName = "load_detail";
			fieldNameList.clear();
			fieldNameList.add("上部承重构件详情");
			fieldNameList.add("上部承重构件编号");
			break;
		case 7:
			tableName = "general_detail";
			fieldNameList.clear();
			fieldNameList.add("上部一般构件详情");
			fieldNameList.add("上部一般构件编号");
			break;
		case 8:
			tableName = "support_detail";
			fieldNameList.clear();
			fieldNameList.add("支座详情");
			fieldNameList.add("支座编号");
			break;
		case 9:
			tableName = "pier_detail";
			fieldNameList.clear();
			fieldNameList.add("桥墩详情");
			fieldNameList.add("桥墩编号");
			fieldNameList.add("盖梁编号");
			fieldNameList.add("系梁编号");
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
			for (int i = 2; i < cursor.getColumnCount()-1; i++) { // 去掉第一、第二和最后一项
				if (tableName == "parts1" && i == 5) // 去掉桥墩详情
					continue;
				if (tableName == "parts2" && i == 2) // 去掉上部承重构件详情
					continue;
				if (tableName == "parts2" && i == 3) // 去掉上部一般构件详情
					continue;
				if (tableName == "parts2" && i == 4) // 去掉支座详情
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
