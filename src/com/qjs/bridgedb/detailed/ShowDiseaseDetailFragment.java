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
	private String itemName, acrossNum, bgCode; // 条目名称, 跨号, 桥梁id
	private int itemId, tableId; // 条目id, 第一个列表的选中的id
	private ArrayList<String> data = new ArrayList<String>();
	private ListView baseList, baseInfoList; // 两个list列表
	private List<Map<String, Object>> ListItems = null; // baseInfoList的项目
	private int jumpFlag = 0; // 跳转标志位(0为展示页，1为修改页)
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// 重写该方法，该方法返回的View将作为Fragment显示的组件
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
		//上部
		tableItemName.put("主梁", "disease_girder");
		tableItemName.put("空心板", "disease_girder");
		tableItemName.put("主拱圈", "disease_girder");
		tableItemName.put("钢、桁架拱片", "disease_girder");
		tableItemName.put("上部承重构件", "disease_girder");
		
		tableItemName.put("湿接缝", "disease_wetjoint");
		tableItemName.put("横隔板", "disease_wetjoint");
		tableItemName.put("铰缝", "disease_wetjoint");
		tableItemName.put("拱上结构", "disease_wetjoint");
		tableItemName.put("横向联结系", "disease_wetjoint");
		tableItemName.put("上部一般构件", "disease_wetjoint");
		
		tableItemName.put("支座", "disease_support");
		tableItemName.put("桥面板", "disease_support");
		
		//下部
		tableItemName.put("桥墩", "disease_pier");
		tableItemName.put("盖梁", "disease_bentcap");
		tableItemName.put("系梁", "disease_tiebeam");
		tableItemName.put("桥台身", "disease_atbody");
		tableItemName.put("桥台帽", "disease_atcapping");		
		tableItemName.put("墩台基础", "disease_pa");
		tableItemName.put("河床", "disease_bed");
		tableItemName.put("调治构造物", "disease_regstruc");
		tableItemName.put("翼墙、耳墙", "disease_wingwall");
		tableItemName.put("锥坡", "disease_conslope");
		tableItemName.put("护坡", "disease_proslope");
		
		//桥面系
		tableItemName.put("桥面铺装", "disease_deck");
		tableItemName.put("伸缩缝装置", "disease_joint");
		tableItemName.put("人行道", "disease_sidewalk");
		tableItemName.put("栏杆、护栏", "disease_fence");
		tableItemName.put("防排水系统", "disease_watertight");
		tableItemName.put("照明、标志", "disease_lighting");
		
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
		
		// 设置列表
		baseList.setAdapter(myArrayAdapter);
		
		baseList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				baseInfoList = (ListView) rootView.findViewById(R.id.base_info);
				String[] itemsStr = parent.getItemAtPosition(position).toString().split(": ");
				itemId= Integer.valueOf(itemsStr[2]); // 获取数据id
				acrossNum = itemsStr[1]; // 获取跨号
				itemName = itemsStr[0]; // 获取部件名称
				String tableName = tableItemName.get(itemName); // 在哈希表中找到表名
				
				
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
				
				// 自定义列表显示病害详情
				MyBaseAdapter myBaseAdapter = new MyBaseAdapter(ListItems, inflater);
				baseInfoList.setAdapter(myBaseAdapter);
				
				btnEdit.setVisibility(View.VISIBLE);
				btnDelete.setVisibility(View.VISIBLE);
				
				btnEdit.setOnClickListener(new OnClickListener() { // 修改按钮

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
				
				btnDelete.setOnClickListener(new OnClickListener() { // 删除按钮
					
					@Override
					public void onClick(View v) {
						
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("删除病害信息对话框") // 设置标题
							// 设置信息
							.setMessage("确认删除吗？该过程不可逆！")
							// 设置添加按钮
							.setPositiveButton("确认", new DialogInterface.OnClickListener () {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									DbOperation db = new DbOperation(getActivity());
									int flag = db.deleteData(tableNames[tableId], "id=" + itemId + " and bg_id='" + bgCode + "'" + " and parts_id='" + acrossNum + "'");
									
									if (flag == 1)
				            			Toast.makeText(getActivity(), "病害信息删除成功", Toast.LENGTH_SHORT).show();
				            		else
				            			Toast.makeText(getActivity(), "病害信息删除失败", Toast.LENGTH_SHORT).show();
									
									// 刷新列表
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
									
									// 清空baseInfoList列表
									LayoutInflater inflater = LayoutInflater.from(getActivity());						
									MyBaseAdapter myBaseAdapter = new MyBaseAdapter(new ArrayList<Map<String, Object>>(), inflater);
									baseInfoList.setAdapter(myBaseAdapter);									
								}				
							})
							// 设置返回按钮
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								
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
		
		if (jumpFlag == 1) { // 如果是从修改页而来的，则刷新第二个列表
			ListItems = getBaseInfo(tableId, bgCode, acrossNum, itemId);
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			
			// 自定义列表显示病害详情
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
			fieldNameArray = new String[] {"病害类型", "裂缝类型", "其他病害", "距本跨梁端起始距离", "距本跨梁端终止距离", 
					"病害面积", "裂缝长度", "裂缝宽度", "侧向距本跨梁端起始距离", "侧向距本跨梁端终止距离", "侧向裂缝长度", "侧向裂缝宽度", 
					"病害位置", "病害描述", "病害照片"};
			break;
		case 1:
			tableName = "disease_wetjoint";
			fieldNameArray = new String[] {"病害类型", "裂缝类型", "其他病害", "距本跨梁端起始距离", "距本跨梁端终止距离", 
					"病害面积", "裂缝距本跨梁端起始距离", "裂缝长度", "裂缝宽度", "病害描述", "病害照片"};
			break;
		case 2:
			tableName = "disease_support";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 3:
			tableName = "disease_pier";
			fieldNameArray = new String[] {"病害类型", "裂缝类型", "其他病害", "距本跨梁端起始距离", "距本跨梁端终止距离", 
					"病害面积", "裂缝距本跨梁端起始距离", "裂缝长度", "裂缝宽度", "病害描述", "病害照片"};
			break;
		case 4:
			tableName = "disease_bentcap";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 5:
			tableName = "disease_tiebeam";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 6:
			tableName = "disease_atbody";
			fieldNameArray = new String[] {"病害类型", "其他病害", "病害描述", "病害照片"};
			break;
		case 7:
			tableName = "disease_atcapping";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 8:
			tableName = "disease_pa";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 9:
			tableName = "disease_bed";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 10:
			tableName = "disease_regstruc";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 11:
			tableName = "disease_wingwall";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 12:
			tableName = "disease_conslope";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 13:
			tableName = "disease_proslope";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 14:
			tableName = "disease_deck";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 15:
			tableName = "disease_joint";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 16:
			tableName = "disease_sidewalk";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 17:
			tableName = "disease_fence";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 18:
			tableName = "disease_watertight";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		case 19:
			tableName = "disease_lighting";
			fieldNameArray = new String[] {"病害类型", "病害描述", "病害照片"};
			break;
		default:
			break;
		}
		
		cursor = db.queryData("*", tableName, "id=" + itemId + " and bg_id='" + bgCode + "'" + " and parts_id='" + partsId + "'");
		if (cursor.moveToFirst()) {
			for (int i = 4; i < cursor.getColumnCount()-1; i++) { // 去掉第一、第二、第三、第四和最后一项				
				if (tableName.equals("disease_girder")) {
					if (cursor.getString(4).equals("裂缝")) {
						if (cursor.getString(5).equals("网裂缝")) {
							if (i == 6 || i == 10 || i == 11)
								continue;
						}
						else {
							if (i == 6 || i == 9)
								continue;
						}
						
						if (!cursor.getString(16).equals("左翼板") && !cursor.getString(16).equals("右翼板")) {
							if (i == 12 || i == 13 || i == 14 || i == 15)
								continue;
						}
					}
					else if (cursor.getString(4).equals("其他病害")) {
						if (i == 5 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15)
							continue;
					}
					else {
						if (i == 5 || i == 6 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15)
							continue;
					}
				}
				
				if (tableName.equals("disease_wetjoint") || tableName.equals("disease_pier")) {
					if (cursor.getString(4).equals("裂缝")) {
						if (cursor.getString(5).equals("网裂缝")) {
							if (i == 6 || i == 10 || i == 11 || i == 12)
								continue;
						}
						else {
							if (i == 6 || i == 7 || i == 8 || i == 9)
								continue;
						}					
					}
					else if (cursor.getString(4).equals("其他病害")) {
						if (i == 5 || i == 7 || i == 8 || i == 9 || i == 10 || i == 11 || i == 12)
							continue;
					}
					else {
						if (i == 5 || i == 6 || i == 10 || i == 11 || i == 12)
							continue;
					}
				}
				
				if (tableName.equals("disease_atbody")) {
					if (!cursor.getString(4).equals("其他病害")) {
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
			
			if ("病害照片".equals(fieldNameList.get(i))) {
				listItem.put("fieldValue", null);
				Uri uri = Uri.parse(fieldValueList.get(i));
				if (uri != null) {					
					String mFileName = EditDiseaseActivity.getPath(this.getActivity().getApplicationContext(), uri); // 获取图片绝对路径
					Bitmap bitmap = EditDiseaseActivity.getBitmap(mFileName); // 根据绝对路径找到图片
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
