package com.qjs.bridgedb.collection;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.MainActivity;
import com.qjs.bridgedb.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

public class Parts2Activity  extends Activity {	
	String bg_id; // 桥梁id
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parts2);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // 来自上一页的跳转
		final String fromNext = bundle.getString("toPrev"); // 来自下一页的跳转
		
		final DbOperation db = new DbOperation(Parts2Activity.this);
		
		if (fromPrev != null)
			bg_id = bundle.getString("toNextId"); // 获取从上一页面传递过来的id
		else if (fromNext != null)
			bg_id = bundle.getString("toPrevId"); // 获取从下一页面传递过来的id
		
		// 根据id查找数据
		final Cursor cursor = db.queryData("*", "parts2", "bg_id='" + bg_id + "'");
		
		// 如果有原始数据，则将原始数据填入文本框
		if (cursor.moveToFirst()) {
			String sidewalk = cursor.getString(cursor.getColumnIndex("sidewalk"));
			String guardrail = cursor.getString(cursor.getColumnIndex("guardrail"));
			
			if (sidewalk.charAt(0) == '1') {
				((CheckBox) findViewById(R.id.sidewalk_l)).setChecked(true);
			}
			if (sidewalk.charAt(1) == '1') {
				((CheckBox) findViewById(R.id.sidewalk_r)).setChecked(true);
			}
			
			if (guardrail.charAt(0) == '1') {
				((CheckBox) findViewById(R.id.guardrail_l)).setChecked(true);
			}
			if (guardrail.charAt(1) == '1') {
				((CheckBox) findViewById(R.id.guardrail_r)).setChecked(true);
			}
			
			((EditText) findViewById(R.id.et_deck_num)).setText(cursor.getString(cursor.getColumnIndex("deck_num")));
			((EditText) findViewById(R.id.et_joint_num)).setText(cursor.getString(cursor.getColumnIndex("joint_num")));
			((EditText) findViewById(R.id.et_drainage_system)).setText(cursor.getString(cursor.getColumnIndex("drainage_system")));
			((EditText) findViewById(R.id.et_illuminated_sign)).setText(cursor.getString(cursor.getColumnIndex("illuminated_sign")));			
		}
		
		EditText load_detail = (EditText) findViewById(R.id.et_load_detail); // 上部承重构件详情
		EditText general_detail = (EditText) findViewById(R.id.et_general_detail); // 上部一般构件详情
		EditText support_detail = (EditText) findViewById(R.id.et_support_detail); // 支座详情
		
		// 点击上部承重构件文本框触发事件
		load_detail.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					loadShowView(v);
				}
				return true;
			}
		});
		
		// 点击上部一般构件文本框触发事件
		general_detail.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					generalShowView(v);
				}
				return true;
			}
		});
		
		// 点击支座文本框触发事件
		support_detail.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					supportShowView(v);
				}
				return true;
			}
		});
		
		// 上一步
        Button pa2_last_btn = (Button)findViewById(R.id.pa2_last_btn);
        pa2_last_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(Parts2Activity.this, PartsActivity.class);
        		intent.putExtra("toPrevId", bg_id); // 传给上一页的id
        		intent.putExtra("toPrev", "toPrevBg"); // 跳转上一页标识
        		startActivity(intent);
    			finish();
        	}
        });
        
        // 下一步
        Button pa2_finish_btn = (Button)findViewById(R.id.pa2_finish_btn);
        pa2_finish_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {        		
        		// 人行道复选框
        		CheckBox sidewalk_l = (CheckBox) findViewById(R.id.sidewalk_l);
        		CheckBox sidewalk_r = (CheckBox) findViewById(R.id.sidewalk_r);
        		
        		// 栏杆、护栏复选框
        		CheckBox guardrail_l = (CheckBox) findViewById(R.id.guardrail_l);
        		CheckBox guardrail_r = (CheckBox) findViewById(R.id.guardrail_r);
        		
        		String sidewalk = ""; // 人行道
        		String guardrail = ""; // 栏杆、护栏
        		
        		// 人行道设置值
        		if (sidewalk_l.isChecked()) {
        			sidewalk += "1";
        		}
        		else {
        			sidewalk += "0";
        		}
        		
        		if (sidewalk_r.isChecked()) {
        			sidewalk += "1";
        		}
        		else {
        			sidewalk += "0";
        		}
        		
        		// 栏杆、护栏设置值
        		if (guardrail_l.isChecked()) {
        			guardrail += "1";
        		}
        		else {
        			guardrail += "0";
        		}
        		
        		if (guardrail_r.isChecked()) {
        			guardrail += "1";
        		}
        		else {
        			guardrail += "0";
        		}
        		
        		String deck_num = ((EditText) findViewById(R.id.et_deck_num)).getText().toString(); // 桥面铺装个数
        		String joint_num = ((EditText) findViewById(R.id.et_joint_num)).getText().toString(); // 伸缩缝装置个数
        		String drainage_system = ((EditText) findViewById(R.id.et_drainage_system)).getText().toString(); // 排水系统个数
        		String illuminated_sign = ((EditText) findViewById(R.id.et_illuminated_sign)).getText().toString(); // 照明、标志个数
        		
        		// 如果为空，赋值为“0”
        		if (deck_num.equals(""))
        			deck_num = "0";
        		if (joint_num.equals(""))
        			joint_num = "0";
        		if (drainage_system.equals(""))
        			drainage_system = "0";
        		if (illuminated_sign.equals(""))
        			illuminated_sign = "0";
        		
        		String load_detail_id = "0"; // 上部承重构件id
        		String general_detail_id = "0"; // 上部一般构件id
        		String support_detail_id = "0"; // 支座id
        		
        		// 如果有原始数据，执行修改操作
        		if (cursor.moveToFirst()) {
        			String setValue = "load_detail='" + load_detail_id + "',general_detail='" + general_detail_id + "',support_detail='" + support_detail_id 
        					+ "',deck_num='" + deck_num + "',joint_num='" + joint_num + "',sidewalk='" + sidewalk + "',guardrail='" + guardrail 
        					+ "',drainage_system='" + drainage_system + "',illuminated_sign='" + illuminated_sign + "',flag='2'";
        			
        			// 修改数据
        			int flag = db.updateData("parts2", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0)
            			Toast.makeText(Parts2Activity.this, "修改失败", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(Parts2Activity.this, "修改成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Parts2Activity.this, MainActivity.class);
                		startActivity(intent);
            		}
        		}
        		else { // 没有则执行插入操作
        			String key = "bg_id, load_detail, general_detail, support_detail, deck_num, joint_num,"
        					+ "sidewalk, guardrail, drainage_system, illuminated_sign, flag";
            		
            		String values = "'" + bg_id + "','" + load_detail_id + "','" + general_detail_id + "','" + support_detail_id + "','" + deck_num + "','"
            				+ joint_num + "','" + sidewalk + "','" + guardrail + "','" + drainage_system + "','" + illuminated_sign + "','0'";
            		
            		// 插入数据
        			int flag = db.insertData("parts2", key, values);
            		
            		if (flag == 0)
            			Toast.makeText(Parts2Activity.this, "添加失败", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(Parts2Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Parts2Activity.this, MainActivity.class);
                		startActivity(intent);
            		}
        		}
    			finish();        		
        	}
        });
	}
	
	// 上部承重构件信息展示弹出框
	public void loadShowView(View source) {
		String load_details = "";
		String load_nums = "";
		String[] arr;
		
		DbOperation db = new DbOperation(Parts2Activity.this);
		Cursor cursor = db.queryData("*", "load_detail", "bg_id='" + bg_id + "'");
		
		if (cursor.moveToFirst()) {
			load_details = cursor.getString(cursor.getColumnIndex("load_details"));
			load_nums = cursor.getString(cursor.getColumnIndex("load_nums"));
		}
		
		// 判断是否有上部承重构件数据
		if (load_details.length() == 0 && load_nums.length() == 0) {
			arr = new String[] {"暂无上部承重构件信息"};
		}
		else {
			arr = new String[] {
					"上部承重构件详情：\n" + load_details, 
					"上部承重构件编号：\n" + load_nums};
		}
		 
		final View v = source;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);			
		builder.setTitle("上部承重构件信息详情") // 设置标题
			// 设置列表项
			.setAdapter(new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr), null)
			// 设置添加按钮
			.setPositiveButton("添加", new DialogInterface.OnClickListener () {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					loadDetailView(v);
				}				
			})
			// 设置返回按钮
			.setNegativeButton("返回", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}				
			})
			// 设置清空按钮
			.setNeutralButton("清空", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DbOperation db = new DbOperation(Parts2Activity.this);
					int flag = db.deleteData("load_add", null);
					int flag1 = db.deleteData("load_detail", null);
					
					if (flag == 1 && flag1 == 1)
            			Toast.makeText(Parts2Activity.this, "上部承重构件信息删除成功", Toast.LENGTH_SHORT).show();
            		else
            			Toast.makeText(Parts2Activity.this, "上部承重构件信息删除失败", Toast.LENGTH_SHORT).show();
				}
			})
			.create()
			.show();			
	}
	
	// 新建上部承重构件信息弹出框
	public void loadDetailView(View source) {
		// 装载/res/layout/load_general_detail.xml界面布局
		final TableLayout loadForm = (TableLayout)getLayoutInflater()
			.inflate( R.layout.load_general_detail, null);		
		new AlertDialog.Builder(this)
			// 设置对话框的标题
			.setTitle("新建上部承重构件信息")
			// 设置对话框显示的View对象
			.setView(loadForm)
			// 为对话框设置一个“确定”按钮
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String start_load = ((EditText) loadForm.findViewById(R.id.et_start_stride)).getText().toString(); // 起始跨号
					String end_load = ((EditText) loadForm.findViewById(R.id.et_end_stride)).getText().toString(); // 终止跨号
					String per_load = ((EditText) loadForm.findViewById(R.id.et_per_stride_num)).getText().toString(); // 每跨个数
					
					String tap = "0"; // 跨号标志位
					
					// 根据id查找数据
	        		DbOperation db = new DbOperation(Parts2Activity.this);
	        		// 这样写是因为如果直接MAX的话会有一条空记录
	        		Cursor cursor_la = db.queryData("*", "load_add", "bg_id='" + bg_id + "' and id = (select max(id) from load_add where bg_id='" + bg_id + "')"); // 查找桥跨添加信息
					
	        		// 控制跨号
	        		if (cursor_la.moveToFirst()) {
	        			if (!cursor_la.getString(cursor_la.getColumnIndex("tap")).equals("")) {
	        				tap = cursor_la.getString(cursor_la.getColumnIndex("tap"));
	        			}
	        		}
	        		
	        		// 非空验证
	        		if (start_load.length() != 0 && end_load.length() != 0 && per_load.length() != 0) {
	        			// 垮号验证
	        			if (Integer.parseInt(end_load) < Integer.parseInt(start_load)) {
		        			Toast.makeText(Parts2Activity.this, "终止跨号不能小于起始跨号", Toast.LENGTH_SHORT).show();
		        		}
		        		else if(Integer.parseInt(start_load) <= Integer.parseInt(tap)) {
		        			Toast.makeText(Parts2Activity.this, "起始跨号须大于" + tap, Toast.LENGTH_SHORT).show();
		        		}
		        		else {
		        			// 验证通过，赋tap值
		        			tap = end_load;
		        			
		        			// 开始插入数据操作
		        			String key = "bg_id, start_load, end_load, per_load, tap, flag";
		            		
		            		String values = "'" + bg_id + "','" + start_load + "','" + end_load + "','" + per_load + "','" + tap + "','0'";
		            		
		            		// 插入数据
		            		int flag1 = db.insertData("load_add", key, values);
		            		
		            		if (flag1 == 0) {
		            			Toast.makeText(Parts2Activity.this, "跨号添加失败", Toast.LENGTH_SHORT).show();
		            		}
		            		else {
		            			String load_details = "";
		            			String load_nums = "";
		            			
		            			Cursor cursor = db.queryData("*", "load_detail", "bg_id='" + bg_id + "'");
		            			if (cursor.moveToFirst()) {
		            				load_details = cursor.getString(cursor.getColumnIndex("load_details"));
		            				load_nums = cursor.getString(cursor.getColumnIndex("load_nums"));
		            			}
		            			
		            			// 设置桥跨细节
		            			load_details += "从" + start_load + "跨到" + end_load + "跨，每跨" + per_load + "个\n";
		            			
		            			for (int i = Integer.parseInt(start_load); i <= Integer.parseInt(end_load); i++) {
		            				for (int j = 1; j <= Integer.parseInt(per_load); j++) {
		            					load_nums += i + "-" + j + "; "; // 设置桥跨编号                    			
		            				}
		            				load_nums += "\n";
		            			}
		            			
		            			int flag2 = 0;
		            			
		            			// 如果有原始数据，执行修改操作
		            			if (cursor.moveToFirst()) {
		            				String setValue = "load_details='" + load_details + "',load_nums='" + load_nums + "',flag='2'";
		            				
		            				flag2 = db.updateData("load_detail", setValue, "bg_id='" + bg_id + "'");
		            			}
		            			else { // 没有则执行插入操作
		            				String detail_key = "bg_id, load_details, load_nums, flag";
			            			String detail_values = "'" + bg_id + "','" + load_details + "','" + load_nums + "','0'";
			            			
			            			flag2 = db.insertData("load_detail", detail_key, detail_values);
		            			}
		            			
		            			if (flag2 == 0)
		                			Toast.makeText(Parts2Activity.this, "桥跨细节添加失败", Toast.LENGTH_SHORT).show();
		            			else
		            				Toast.makeText(Parts2Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
		            		}
		        		}
	        		}
	        		else
	        			Toast.makeText(Parts2Activity.this, "跨号不能为空", Toast.LENGTH_SHORT).show();
				}
			})
			// 为对话框设置一个“取消”按钮
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			// 创建、并显示对话框
			.create()
			.show();
	}
	
	// 上部一般构件信息展示弹出框
	public void generalShowView(View source) {
		String general_details = "";
		String general_nums = "";
		String[] arr;
		
		DbOperation db = new DbOperation(Parts2Activity.this);
		Cursor cursor = db.queryData("*", "general_detail", "bg_id='" + bg_id + "'");
		
		if (cursor.moveToFirst()) {
			general_details = cursor.getString(cursor.getColumnIndex("general_details"));
			general_nums = cursor.getString(cursor.getColumnIndex("general_nums"));
		}
		
		// 判断是否有上部一般构件数据
		if (general_details.length() == 0 && general_nums.length() == 0) {
			arr = new String[] {"暂无上部一般构件信息"};
		}
		else {
			arr = new String[] {
					"上部一般构件详情：\n" + general_details, 
					"上部一般构件编号：\n" + general_nums};
		}
		 
		final View v = source;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);			
		builder.setTitle("上部一般构件信息详情") // 设置标题
			// 设置列表项
			.setAdapter(new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr), null)
			// 设置添加按钮
			.setPositiveButton("添加", new DialogInterface.OnClickListener () {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					generalDetailView(v);
				}				
			})
			// 设置返回按钮
			.setNegativeButton("返回", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}				
			})
			// 设置清空按钮
			.setNeutralButton("清空", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DbOperation db = new DbOperation(Parts2Activity.this);
					int flag = db.deleteData("general_add", null);
					int flag1 = db.deleteData("general_detail", null);
					
					if (flag == 1 && flag1 == 1)
            			Toast.makeText(Parts2Activity.this, "上部一般构件信息删除成功", Toast.LENGTH_SHORT).show();
            		else
            			Toast.makeText(Parts2Activity.this, "上部一般构件信息删除失败", Toast.LENGTH_SHORT).show();
				}
			})
			.create()
			.show();			
	}
	
	// 新建上部一般构件信息弹出框
	public void generalDetailView(View source) {
		// 装载/res/layout/load_general_detail.xml界面布局
		final TableLayout generalForm = (TableLayout)getLayoutInflater().inflate(R.layout.load_general_detail, null);
		
		new AlertDialog.Builder(this)
			// 设置对话框的标题
			.setTitle("新建上部一般构件信息")
			// 设置对话框显示的View对象
			.setView(generalForm)
			// 为对话框设置一个“确定”按钮
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String start_general = ((EditText) generalForm.findViewById(R.id.et_start_stride)).getText().toString(); // 起始跨号
					String end_general = ((EditText) generalForm.findViewById(R.id.et_end_stride)).getText().toString(); // 终止跨号
					String per_general = ((EditText) generalForm.findViewById(R.id.et_per_stride_num)).getText().toString(); // 每跨个数
					
					String tap = "0"; // 跨号标志位
					
					// 根据id查找数据
	        		DbOperation db = new DbOperation(Parts2Activity.this);
	        		// 这样写是因为如果直接MAX的话会有一条空记录
	        		Cursor cursor_ga = db.queryData("*", "general_add", "bg_id='" + bg_id + "' and id = (select max(id) from general_add where bg_id='" + bg_id + "')"); // 查找桥跨添加信息
					
	        		// 控制跨号
	        		if (cursor_ga.moveToFirst()) {
	        			if (!cursor_ga.getString(cursor_ga.getColumnIndex("tap")).equals("")) {
	        				tap = cursor_ga.getString(cursor_ga.getColumnIndex("tap"));
	        			}
	        		}
	        		
	        		// 非空验证
	        		if (start_general.length() != 0 && end_general.length() != 0 && per_general.length() != 0) {
	        			// 垮号验证
	        			if (Integer.parseInt(end_general) < Integer.parseInt(start_general)) {
		        			Toast.makeText(Parts2Activity.this, "终止跨号不能小于起始跨号", Toast.LENGTH_SHORT).show();
		        		}
		        		else if(Integer.parseInt(start_general) <= Integer.parseInt(tap) && tap != "0") {
		        			Toast.makeText(Parts2Activity.this, "起始跨号须大于" + tap, Toast.LENGTH_SHORT).show();
		        		}
		        		else {
		        			// 验证通过，赋tap值
		        			tap = end_general;
		        			
		        			// 开始插入数据操作
		        			String key = "bg_id, start_general, end_general, per_general, tap, flag";
		            		
		            		String values = "'" + bg_id + "','" + start_general + "','" + end_general + "','" + per_general + "','" + tap + "','0'";
		            		
		            		// 插入数据
		            		int flag1 = db.insertData("general_add", key, values);
		            		
		            		if (flag1 == 0) {
		            			Toast.makeText(Parts2Activity.this, "跨号添加失败", Toast.LENGTH_SHORT).show();
		            		}
		            		else {
		            			String general_details = "";
		            			String general_nums = "";
		            			
		            			Cursor cursor = db.queryData("*", "general_detail", "bg_id='" + bg_id + "'");
		            			if (cursor.moveToFirst()) {
		            				general_details = cursor.getString(cursor.getColumnIndex("general_details"));
		            				general_nums = cursor.getString(cursor.getColumnIndex("general_nums"));
		            			}
		            			
		            			// 设置桥跨细节
		            			general_details += "从" + start_general + "跨到" + end_general + "跨，每跨" + per_general + "个\n";
		            			
		            			for (int i = Integer.parseInt(start_general); i <= Integer.parseInt(end_general); i++) {
		            				for (int j = 1; j <= Integer.parseInt(per_general); j++) {
		            					general_nums += i + "-" + j + "; "; // 设置桥跨编号                    			
		            				}
		            				general_nums += "\n";
		            			}
		            			
		            			int flag2 = 0;
		            			
		            			// 如果有原始数据，执行修改操作
		            			if (cursor.moveToFirst()) {
		            				String setValue = "general_details='" + general_details + "',general_nums='" + general_nums + "',flag='2'";
		            				
		            				flag2 = db.updateData("general_detail", setValue, "bg_id='" + bg_id + "'");
		            			}
		            			else { // 没有则执行插入操作
		            				String detail_key = "bg_id, general_details, general_nums, flag";
			            			String detail_values = "'" + bg_id + "','" + general_details + "','" + general_nums + "','0'";
			            			
			            			flag2 = db.insertData("general_detail", detail_key, detail_values);
		            			}
		            			
		            			if (flag2 == 0)
		                			Toast.makeText(Parts2Activity.this, "桥跨细节添加失败", Toast.LENGTH_SHORT).show();
		            			else
		            				Toast.makeText(Parts2Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
		            		}
		        		}
	        		}
	        		else
	        			Toast.makeText(Parts2Activity.this, "跨号不能为空", Toast.LENGTH_SHORT).show();
				}
			})
			// 为对话框设置一个“取消”按钮
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			// 创建、并显示对话框
			.create()
			.show();
	}
	
	// 支座信息展示弹出框
	public void supportShowView(View source) {
		String support_details = "";
		String support_nums = "";
		String[] arr;
		
		DbOperation db = new DbOperation(Parts2Activity.this);
		Cursor cursor = db.queryData("*", "support_detail", "bg_id='" + bg_id + "'");
		
		if (cursor.moveToFirst()) {
			support_details = cursor.getString(cursor.getColumnIndex("support_details"));
			support_nums = cursor.getString(cursor.getColumnIndex("support_nums"));
		}
		
		// 判断是否有支座数据
		if (support_details.length() == 0 && support_nums.length() == 0) {
			arr = new String[] {"暂无支座信息"};
		}
		else {
			arr = new String[] {
					"支座详情：\n" + support_details, 
					"支座编号：\n" + support_nums};
		}
		 
		final View v = source;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);			
		builder.setTitle("支座信息详情") // 设置标题
			// 设置列表项
			.setAdapter(new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr), null)
			// 设置添加按钮
			.setPositiveButton("添加", new DialogInterface.OnClickListener () {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					supportDetailView(v);
				}				
			})
			// 设置返回按钮
			.setNegativeButton("返回", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}				
			})
			// 设置清空按钮
			.setNeutralButton("清空", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DbOperation db = new DbOperation(Parts2Activity.this);
					int flag = db.deleteData("support_add", null);
					int flag1 = db.deleteData("support_detail", null);
					
					if (flag == 1 && flag1 == 1)
            			Toast.makeText(Parts2Activity.this, "支座信息删除成功", Toast.LENGTH_SHORT).show();
            		else
            			Toast.makeText(Parts2Activity.this, "支座信息删除失败", Toast.LENGTH_SHORT).show();
				}
			})
			.create()
			.show();			
	}
	
	// 新建支座信息弹出框
	public void supportDetailView(View source) {
		// 装载/res/layout/support_detail.xml界面布局
		final TableLayout supportForm = (TableLayout)getLayoutInflater().inflate(R.layout.support_detail, null);
		
		new AlertDialog.Builder(this)
			// 设置对话框的标题
			.setTitle("新建支座信息")
			// 设置对话框显示的View对象
			.setView(supportForm)
			// 为对话框设置一个“确定”按钮
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String start_support = ((EditText) supportForm.findViewById(R.id.et_start_spt)).getText().toString(); // 起始墩号
					String end_support = ((EditText) supportForm.findViewById(R.id.et_end_spt)).getText().toString(); // 终止墩号
					String raw_num = ((EditText) supportForm.findViewById(R.id.et_per_row_num)).getText().toString(); // 每墩排数
					String support_num = ((EditText) supportForm.findViewById(R.id.et_per_spt_num)).getText().toString(); // 每排支座数
					
					String tap = "0"; // 跨号标志位
					
					// 根据id查找数据
	        		DbOperation db = new DbOperation(Parts2Activity.this);
	        		// 这样写是因为如果直接MAX的话会有一条空记录
	        		Cursor cursor_sa = db.queryData("*", "support_add", "bg_id='" + bg_id + "' and id = (select max(id) from support_add where bg_id='" + bg_id + "')"); // 查找支座添加信息
					
	        		// 控制墩（台）号
	        		if (cursor_sa.moveToFirst()) {
	        			if (!cursor_sa.getString(cursor_sa.getColumnIndex("tap")).equals("")) {
	        				tap = cursor_sa.getString(cursor_sa.getColumnIndex("tap"));
	        			}
	        		}
	        		
	        		// 非空验证
	        		if (start_support.length() != 0 && end_support.length() != 0 && raw_num.length() != 0 && support_num.length() != 0) {
	        			// 墩（台）号验证
	        			if (Integer.parseInt(end_support) < Integer.parseInt(start_support)) {
		        			Toast.makeText(Parts2Activity.this, "终止墩（台）号不能小于起始墩（台）号", Toast.LENGTH_SHORT).show();
		        		}
		        		else if(Integer.parseInt(start_support) <= Integer.parseInt(tap) && tap != "0") {
		        			Toast.makeText(Parts2Activity.this, "起始墩（台）号须大于" + tap, Toast.LENGTH_SHORT).show();
		        		}
		        		else {
		        			// 验证通过，赋tap值
		        			tap = end_support;
		        			
		        			// 开始插入数据操作
		        			String key = "bg_id, start_support, end_support, raw_num, support_num, tap, flag";
		            		
		            		String values = "'" + bg_id + "','" + start_support + "','" + end_support + "','" + raw_num + "','" + support_num + "','" + tap + "','0'";
		            		
		            		// 插入数据
		            		int flag1 = db.insertData("support_add", key, values);
		            		
		            		if (flag1 == 0) {
		            			Toast.makeText(Parts2Activity.this, "墩（台）号添加失败", Toast.LENGTH_SHORT).show();
		            		}
		            		else {
		            			String support_details = "";
		            			String support_nums = "";
		            			
		            			Cursor cursor = db.queryData("*", "support_detail", "bg_id='" + bg_id + "'");
		            			if (cursor.moveToFirst()) {
		            				support_details = cursor.getString(cursor.getColumnIndex("support_details"));
		            				support_nums = cursor.getString(cursor.getColumnIndex("support_nums"));
		            			}
		            			
		            			// 设置支座细节
		            			support_details += "从" + start_support + "墩台到" + end_support + "墩台，每墩台" + raw_num + "排，每排" + support_num + "个\n";
		            			
		            			for (int i = Integer.parseInt(start_support); i <= Integer.parseInt(end_support); i++) {
		            				for (int j = 1; j <= Integer.parseInt(raw_num); j++) {
		            					for (int k = 1; k <= Integer.parseInt(support_num); k++) {
		            						support_nums += i + "-" + j + "-" + k + "; "; // 设置支座编号
		            					}		            					
		            				}
		            				support_nums += "\n";
		            			}
		            			
		            			int flag2 = 0;
		            			
		            			// 如果有原始数据，执行修改操作
		            			if (cursor.moveToFirst()) {
		            				String setValue = "support_details='" + support_details + "',support_nums='" + support_nums + "',flag='2'";
		            				
		            				flag2 = db.updateData("support_detail", setValue, "bg_id='" + bg_id + "'");
		            			}
		            			else { // 没有则执行插入操作
		            				String detail_key = "bg_id, support_details, support_nums, flag";
			            			String detail_values = "'" + bg_id + "','" + support_details + "','" + support_nums + "','0'";
			            			
			            			flag2 = db.insertData("support_detail", detail_key, detail_values);
		            			}
		            			
		            			if (flag2 == 0)
		                			Toast.makeText(Parts2Activity.this, "支座细节添加失败", Toast.LENGTH_SHORT).show();
		            			else
		            				Toast.makeText(Parts2Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
		            		}
		        		}
	        		}
	        		else
	        			Toast.makeText(Parts2Activity.this, "墩（台）号不能为空", Toast.LENGTH_SHORT).show();
				}
			})
			// 为对话框设置一个“取消”按钮
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			// 创建、并显示对话框
			.create()
			.show();
	}
    
    @Override
    public void onBackPressed() {
    	// 禁用返回键，让用户使用按钮键，控制程序正常运行
    	Toast.makeText(this, "返回键已禁用，请使用 “上一步” 按钮返回", Toast.LENGTH_SHORT).show();
    	return;
    }
}
