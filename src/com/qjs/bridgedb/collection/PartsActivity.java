package com.qjs.bridgedb.collection;

import com.qjs.bridgedb.DbOperation;
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

public class PartsActivity  extends Activity {	
	int bg_id; // 桥梁id
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parts);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // 来自上一页的跳转
		final String fromNext = bundle.getString("toPrev"); // 来自下一页的跳转
		
		final DbOperation db = new DbOperation(PartsActivity.this);
		
		if (fromPrev != null) {
			bg_id = bundle.getInt("toNextId"); // 获取从上一页面传递过来的id
		}
		else if (fromNext != null) {
			bg_id = bundle.getInt("toPrevId"); // 获取从下一页面传递过来的id
		}
		
		// 根据id查找数据
		final Cursor cursor = db.queryData("*", "parts1", "bg_id='" + bg_id + "'");
		
		// 如果有原始数据，则将原始数据填入文本框
		if (cursor.moveToFirst()) {
			String wing_wall = cursor.getString(cursor.getColumnIndex("wing_wall"));
			String conical_slope = cursor.getString(cursor.getColumnIndex("conical_slope"));
			
			if (wing_wall.charAt(0) == '1') {
				((CheckBox) findViewById(R.id.wall_l0)).setChecked(true);
			}
			if (wing_wall.charAt(1) == '1') {
				((CheckBox) findViewById(R.id.wall_r0)).setChecked(true);
			}
			if (wing_wall.charAt(2) == '1') {
				((CheckBox) findViewById(R.id.wall_l1)).setChecked(true);
			}
			if (wing_wall.charAt(3) == '1') {
				((CheckBox) findViewById(R.id.wall_r0)).setChecked(true);
			}
			
			if (conical_slope.charAt(0) == '1') {
				((CheckBox) findViewById(R.id.slope_l0)).setChecked(true);
			}
			if (conical_slope.charAt(1) == '1') {
				((CheckBox) findViewById(R.id.slope_r0)).setChecked(true);
			}
			if (conical_slope.charAt(2) == '1') {
				((CheckBox) findViewById(R.id.slope_l1)).setChecked(true);
			}
			if (conical_slope.charAt(3) == '1') {
				((CheckBox) findViewById(R.id.slope_r1)).setChecked(true);
			}
			
			((EditText) findViewById(R.id.et_protection_slope)).setText(cursor.getString(cursor.getColumnIndex("protection_slope")));
			((EditText) findViewById(R.id.et_abutment_num)).setText(cursor.getString(cursor.getColumnIndex("abutment_num")));
			((EditText) findViewById(R.id.et_pa_num)).setText(cursor.getString(cursor.getColumnIndex("pa_num")));
			((EditText) findViewById(R.id.et_bed_num)).setText(cursor.getString(cursor.getColumnIndex("bed_num")));
			((EditText) findViewById(R.id.et_reg_structure)).setText(cursor.getString(cursor.getColumnIndex("reg_structure")));
		}
		
		final EditText pier_detail = (EditText) findViewById(R.id.et_pier_detail); // 桥墩详情
		
		// 点击桥墩文本框触发事件
		pier_detail.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					pierShowView(v);
					pier_detail.setFocusable(true);
				}
				return true;
			}
		});
		
		// 上一步
        Button pa_last_btn = (Button)findViewById(R.id.pa_last_btn);
        pa_last_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(PartsActivity.this, StructureActivity.class);
        		intent.putExtra("toPrevId", bg_id); // 传给上一页的id
        		intent.putExtra("toPrev", "toPrevBg"); // 跳转上一页标识
        		startActivity(intent);
        	}
        });
        
        // 下一步
        Button pa_next_btn = (Button)findViewById(R.id.pa_next_btn);
        pa_next_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// 翼墙、耳墙复选框
        		CheckBox wall_l0 = (CheckBox) findViewById(R.id.wall_l0);
        		CheckBox wall_r0 = (CheckBox) findViewById(R.id.wall_r0);
        		CheckBox wall_l1 = (CheckBox) findViewById(R.id.wall_l1);
        		CheckBox wall_r1 = (CheckBox) findViewById(R.id.wall_r1);
        		
        		// 锥坡复选框
        		CheckBox slope_l0 = (CheckBox) findViewById(R.id.slope_l0);
        		CheckBox slope_r0 = (CheckBox) findViewById(R.id.slope_r0);
        		CheckBox slope_l1 = (CheckBox) findViewById(R.id.slope_l1);
        		CheckBox slope_r1 = (CheckBox) findViewById(R.id.slope_r1);
        		
        		String wing_wall = ""; // 翼墙、耳墙
        		String conical_slope = ""; // 锥坡
        		
        		// 翼墙、耳墙设置值
        		if (wall_l0.isChecked()) {
        			wing_wall += "1";
        		}
        		else {
        			wing_wall += "0";
        		}
        		
        		if (wall_r0.isChecked()) {
        			wing_wall += "1";
        		}
        		else {
        			wing_wall += "0";
        		}
        		
        		if (wall_l1.isChecked()) {
        			wing_wall += "1";
        		}
        		else {
        			wing_wall += "0";
        		}
        		
        		if (wall_r1.isChecked()) {
        			wing_wall += "1";
        		}
        		else {
        			wing_wall += "0";
        		}
        		
        		// 锥坡设置值
        		if (slope_l0.isChecked()) {
        			conical_slope += "1";
        		}
        		else {
        			conical_slope += "0";
        		}
        		
        		if (slope_r0.isChecked()) {
        			conical_slope += "1";
        		}
        		else {
        			conical_slope += "0";
        		}
        		
        		if (slope_l1.isChecked()) {
        			conical_slope += "1";
        		}
        		else {
        			conical_slope += "0";
        		}
        		
        		if (slope_r1.isChecked()) {
        			conical_slope += "1";
        		}
        		else {
        			conical_slope += "0";
        		}
        		
        		String protection_slope = ((EditText) findViewById(R.id.et_protection_slope)).getText().toString(); // 护坡个数
        		String abutment_num = ((EditText) findViewById(R.id.et_abutment_num)).getText().toString(); // 桥台个数
        		String pa_num = ((EditText) findViewById(R.id.et_pa_num)).getText().toString(); // 墩台基础个数
        		String bed_num = ((EditText) findViewById(R.id.et_bed_num)).getText().toString(); // 河床个数
        		String reg_structure = ((EditText) findViewById(R.id.et_reg_structure)).getText().toString(); // 调治构造物个数
        		
        		String pier_detail_id = "0"; // 桥墩id
        		
        		// 如果有原始数据，执行修改操作
        		if (cursor.moveToFirst()) {
        			String setValue = "wing_wall='" + wing_wall + "',conical_slope='" + conical_slope + "',protection_slope='" + protection_slope 
        					+ "',pier_detail='" + pier_detail_id + "',abutment_num='" + abutment_num + "',pa_num='" + pa_num
        					+ "',bed_num='" + bed_num + "',reg_structure='" + reg_structure + "'";
        			
        			// 修改数据
        			int flag = db.updateData("parts1", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0) {
            			Toast.makeText(PartsActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            		}
            		else {
            			Toast.makeText(PartsActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(PartsActivity.this, Parts2Activity.class);
            			intent.putExtra("toNextId", bg_id); // 传递id值
            			intent.putExtra("toNext", "toNextBg"); // 传递添加跳转
                		startActivity(intent);
            		}
        		}
        		else { // 没有则执行插入操作
        			String key = "bg_id, wing_wall, conical_slope, protection_slope, pier_detail,"
        					+ "abutment_num, pa_num, bed_num, reg_structure";
            		
            		String values = "'" + bg_id + "','" + wing_wall + "','" + conical_slope + "','" + protection_slope + "','" + pier_detail_id + "','"
            				+ abutment_num + "','" + pa_num + "','" + bed_num + "','" + reg_structure + "'";
            		
            		// 插入数据
        			int flag = db.insertData("parts1", key, values);
            		
            		if (flag == 0) {
            			Toast.makeText(PartsActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            		}
            		else {
            			Toast.makeText(PartsActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(PartsActivity.this, Parts2Activity.class);
            			intent.putExtra("toNextId", bg_id); // 传递id值
            			intent.putExtra("toNext", "toNextBg"); // 传递添加跳转
                		startActivity(intent);
            		}
        		}
        	}
        });
	}
	
	// 桥墩信息展示弹出框
	public void pierShowView (View source) {
		String pier_details = "";
		String pier_nums = "";
		String bent_cap_nums = "";
		String tie_beam_nums = "";
		String[] arr;
		
		DbOperation db = new DbOperation(PartsActivity.this);
		Cursor cursor = db.queryData("*", "pier_detail", "bg_id='" + bg_id + "'");
		
		if (cursor.moveToFirst()) {
			pier_details = cursor.getString(cursor.getColumnIndex("pier_details"));
			pier_nums = cursor.getString(cursor.getColumnIndex("pier_nums"));
			bent_cap_nums = cursor.getString(cursor.getColumnIndex("bent_cap_nums"));
			tie_beam_nums = cursor.getString(cursor.getColumnIndex("tie_beam_nums"));
		}
		
		// 判断是否有桥墩数据
		if (pier_details.length() == 0 && pier_nums.length() == 0 && bent_cap_nums.length() == 0 && tie_beam_nums.length() == 0) {
			arr = new String[] {"暂无桥墩信息"};
		}
		else  {
			arr = new String[] {
					"桥墩详情：\n" + pier_details, 
					"桥墩编号：\n" + pier_nums,
					"盖梁号：\n" + bent_cap_nums, 
					"系梁号：\n" + tie_beam_nums};
		}
		 
		final View v = source;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);			
		builder.setTitle("桥墩信息详情") // 设置标题
			// 设置列表项
			.setAdapter(new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr), null)
			// 设置添加按钮
			.setPositiveButton("添加", new DialogInterface.OnClickListener () {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					pierDetailView(v);
				}				
			})
			// 设置返回按钮
			.setNegativeButton("返回", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}				
			})
			.create()
			.show();			
	}
	
	// 新建桥墩信息弹出框
	public void pierDetailView (View source) {
		// 装载/res/layout/pier_detail.xml界面布局
		final TableLayout pierForm = (TableLayout) getLayoutInflater().inflate(R.layout.pier_detail, null);		
		
		new AlertDialog.Builder(this)
			// 设置对话框的标题
			.setTitle("新建桥墩信息")
			// 设置对话框显示的View对象
			.setView(pierForm)
			// 为对话框设置一个“确定”按钮
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					String start_pier = ((EditText) pierForm.findViewById(R.id.et_start_pier)).getText().toString(); // 起始桥墩号
					String end_pier = ((EditText) pierForm.findViewById(R.id.et_end_pier)).getText().toString(); // 终止桥墩号
					String per_pier = ((EditText) pierForm.findViewById(R.id.et_per_pier_num)).getText().toString(); // 每个桥墩墩身数
					
					CheckBox ck_bent_cap = (CheckBox) pierForm.findViewById(R.id.ck_bent_cap);
	        		CheckBox ck_tie_beam = (CheckBox) pierForm.findViewById(R.id.ck_tie_beam);
	        		
	        		String bent_cap = ""; // 盖梁
	        		String tie_beam = ""; // 系梁
	        		String tap = "0"; // 墩号标志位
	        		
	        		if (ck_bent_cap.isChecked()) {
	        			bent_cap = "1";
	        		}
	        		else {
	        			bent_cap = "0";
	        		}
	        		
	        		if (ck_tie_beam.isChecked()) {
	        			tie_beam = "1";
	        		}
	        		else {
	        			tie_beam = "0";
	        		}
	        		
	        		// 根据id查找数据
	        		DbOperation db = new DbOperation(PartsActivity.this);
	        		// 这样写是因为如果直接MAX的话会有一条空记录
	        		Cursor cursor_pa = db.queryData("*", "pier_add", "bg_id='" + bg_id + "' and id = (select max(id) from pier_add where bg_id='" + bg_id + "')"); // 查找桥墩添加信息
	        		
	        		// 控制墩号
	        		if (cursor_pa.moveToFirst()) {
	        			if (!cursor_pa.getString(cursor_pa.getColumnIndex("tap")).equals("")) {
	        				tap = cursor_pa.getString(cursor_pa.getColumnIndex("tap"));
	        			}
	        		}
	        		
	        		// 非空验证
	        		if (start_pier.length() != 0 && end_pier.length() != 0 && per_pier.length() != 0) {
	        			// 墩号验证
	        			if (Integer.parseInt(end_pier) < Integer.parseInt(start_pier)) {
		        			Toast.makeText(PartsActivity.this, "终止桥墩号不能小于起始桥墩号", Toast.LENGTH_SHORT).show();
		        		}
		        		else if(Integer.parseInt(start_pier) <= Integer.parseInt(tap) && tap != "0") {
		        			Toast.makeText(PartsActivity.this, "起始桥墩号须大于" + tap, Toast.LENGTH_SHORT).show();
		        		}
		        		else {
		        			// 验证通过，赋tap值
		        			tap = end_pier;
		        			
		        			// 开始插入数据操作
		        			String key = "bg_id, start_pier, end_pier, per_pier, bent_cap, tie_beam, tap";
		            		
		            		String values = "'" + bg_id + "','" + start_pier + "','" + end_pier + "','" + per_pier + "','"
		            				+ bent_cap + "','" + tie_beam + "','" + tap + "'";
		            		
		            		// 插入数据
		            		int flag1 = db.insertData("pier_add", key, values);
		            		
		            		if (flag1 == 0) {
		            			Toast.makeText(PartsActivity.this, "桥墩编号添加失败", Toast.LENGTH_SHORT).show();
		            		}
		            		else {
		            			String pier_details = "";
		            			String pier_nums = "";
		            			String bent_cap_nums = "";
		            			String tie_beam_nums = "";
		            			
		            			Cursor cursor = db.queryData("*", "pier_detail", "bg_id='" + bg_id + "'");
		            			if (cursor.moveToFirst()) {
		            				pier_details = cursor.getString(cursor.getColumnIndex("pier_details"));
		            				pier_nums = cursor.getString(cursor.getColumnIndex("pier_nums"));
		            				bent_cap_nums = cursor.getString(cursor.getColumnIndex("bent_cap_nums"));
		            				tie_beam_nums = cursor.getString(cursor.getColumnIndex("tie_beam_nums"));
		            			}            			
		            			
		            			String bc = (bent_cap == "1")?"有盖梁，":"无盖梁，";
		            			String tb = (tie_beam == "1")?"有系梁\n":"无系梁\n";
		            			
		            			// 设置桥墩细节
		            			pier_details += "从" + start_pier + "墩到" + end_pier + "墩，每墩" + per_pier + "个墩身，" + bc + tb;
		            			
		            			for(int i = Integer.parseInt(start_pier); i <= Integer.parseInt(end_pier); i++) {
		            				for (int j = 1; j <= Integer.parseInt(per_pier); j++) {
		            					pier_nums += i + "-" + j + "; "; // 设置桥墩编号                    			
		            				}
		            				pier_nums += "\n";
		            				
		            				if (bent_cap == "1") {
		            					bent_cap_nums += i + ", "; // 设置盖梁编号
		            				}
		            				
		            				if (tie_beam == "1") {
		            					tie_beam_nums += i + ", "; // 设置系梁编号
		            				}
		            			}
		            			
		            			int flag2 = 0;
		            			
		            			// 如果有原始数据，执行修改操作
		            			if (cursor.moveToFirst()) {
		            				String setValue = "pier_details='" + pier_details + "',pier_nums='" + pier_nums + "',bent_cap_nums='" + bent_cap_nums 
		                					+ "',tie_beam_nums='" + tie_beam_nums + "'";
		            				
		            				flag2 = db.updateData("pier_detail", setValue, "bg_id='" + bg_id + "'");
		            			}
		            			else { // 没有则执行插入操作
		            				String detail_key = "bg_id, pier_details, pier_nums, bent_cap_nums, tie_beam_nums";
			            			String detail_values = "'" + bg_id + "','" + pier_details + "','" + pier_nums + "','" + bent_cap_nums + "','" + tie_beam_nums + "'";
			            			
			            			flag2 = db.insertData("pier_detail", detail_key, detail_values);
		            			}
		            			
		            			if (flag2 == 0) {
		                			Toast.makeText(PartsActivity.this, "桥墩细节添加失败", Toast.LENGTH_SHORT).show();
		                		}
		            			else {
		            				Toast.makeText(PartsActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
		            			}
		            		}
		        		}
	        		}
	        		else {
	        			Toast.makeText(PartsActivity.this, "墩号不能为空", Toast.LENGTH_SHORT).show();
	        		}
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
