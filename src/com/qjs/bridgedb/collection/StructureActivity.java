package com.qjs.bridgedb.collection;

import java.util.Calendar;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class StructureActivity extends Activity {	
	int bg_id; // 桥梁id	
	Spinner sp_nl; // 通航等级	
	boolean flag; // 建桥时间标志位
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_structure);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // 来自上一页的跳转
		final String fromNext = bundle.getString("toPrev"); // 来自下一页的跳转
		
		final DbOperation db = new DbOperation(StructureActivity.this);
		
		if (fromPrev != null)
			bg_id = bundle.getInt("toNextId"); // 获取从上一页面传递过来的id
		else if (fromNext != null)
			bg_id = bundle.getInt("toPrevId"); // 获取从下一页面传递过来的id
		
		// 根据id查找数据
		final Cursor cursor = db.queryData("*", "structure", "bg_id='" + bg_id + "'");
		
		// 如果有原始数据，则将原始数据填入文本框
		if (cursor.moveToFirst()) {
			// 获取数据
			((EditText) findViewById(R.id.et_bridge_span)).setText(cursor.getString(cursor.getColumnIndex("bridge_span")));
			((EditText) findViewById(R.id.et_longest_span)).setText(cursor.getString(cursor.getColumnIndex("longest_span")));
			((EditText) findViewById(R.id.et_total_len)).setText(cursor.getString(cursor.getColumnIndex("total_len")));
			((EditText) findViewById(R.id.et_bridge_wide)).setText(cursor.getString(cursor.getColumnIndex("bridge_wide")));
			((EditText) findViewById(R.id.et_full_wide)).setText(cursor.getString(cursor.getColumnIndex("full_wide")));
			((EditText) findViewById(R.id.et_clear_wide)).setText(cursor.getString(cursor.getColumnIndex("clear_wide")));
			
			((EditText) findViewById(R.id.et_bridge_high)).setText(cursor.getString(cursor.getColumnIndex("bridge_high")));
			((EditText) findViewById(R.id.et_high_limit)).setText(cursor.getString(cursor.getColumnIndex("high_limit")));
			((EditText) findViewById(R.id.et_building_time)).setText(cursor.getString(cursor.getColumnIndex("building_time")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_navigation_level), cursor.getString(cursor.getColumnIndex("navigation_level")));
    		((EditText) findViewById(R.id.et_section_high)).setText(cursor.getString(cursor.getColumnIndex("section_high")));
			((EditText) findViewById(R.id.et_deck_profile_grade)).setText(cursor.getString(cursor.getColumnIndex("deck_profile_grade")));
		}
				
		// 建桥时间
		EditText et_building_time = (EditText) findViewById(R.id.et_building_time);
		et_building_time.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {					
					int c_year, c_month, c_day;
					EditText et = (EditText) findViewById(R.id.et_building_time);
					String str = et.getText().toString();
					
					if(str == null || str.length() <= 0) { // 如果未选中日期，则将当天设为默认弹出日期
						Calendar c = Calendar.getInstance();
						
						c_year = c.get(Calendar.YEAR);
				        c_month = c.get(Calendar.MONTH);
				        c_day = c.get(Calendar.DAY_OF_MONTH);
					}
					else { // 如果已选中日期，则将选中日期设为默认弹出日期
						String[] date_arr = str.split(" - ");
						
						c_year = Integer.parseInt(date_arr[0]);
				        c_month = Integer.parseInt(date_arr[1]) - 1;
				        c_day = Integer.parseInt(date_arr[2]);
					}
			        
			        DatePickerDialog picker = new DatePickerDialog(StructureActivity.this, 
			        		// 绑定监听器
	        				new DatePickerDialog.OnDateSetListener() {
			        		
								@Override
								public void onDateSet(DatePicker dp, int year,
									int month, int dayOfMonth) {
									// 只有当状态位为真时才会修改editText
									if(flag) {
										EditText show = (EditText) findViewById(R.id.et_building_time);
										show.setText(year + " - " + (month + 1) + " - " + dayOfMonth);
										flag = false; // 将状态位置非
									}
								}
							}
			        		,c_year, c_month, c_day);
			        picker.setCancelable(true);
			        picker.setCanceledOnTouchOutside(true);
			        picker.setTitle("设置建桥时间");
			        picker.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
			                new DialogInterface.OnClickListener() {
			        		
			                    @Override
			                    public void onClick(DialogInterface dialog, int which) {
			                        Log.d("Picker", "Correct behavior!");
			                        flag = true;
			                    }
			                });
			        picker.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
			                new DialogInterface.OnClickListener() {
			        		
			                    @Override
			                    public void onClick(DialogInterface dialog, int which) {
			                        Log.d("Picker", "Cancel!");
			                        flag = false;
			                    }
			                });
			        picker.show();
				}
        		
				return true;
			}
		});
		
		// 上一步
        Button st_last_btn = (Button)findViewById(R.id.st_last_btn);
        st_last_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(StructureActivity.this, Base3Activity.class);
        		intent.putExtra("toPrevId", bg_id); // 传给上一页的id
        		intent.putExtra("toPrev", "toPrevBg"); // 跳转上一页标识
        		startActivity(intent);
        	}
        });
        
        // 下一步
        Button st_next_btn = (Button)findViewById(R.id.st_next_btn);
        st_next_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {        		
        		// 获取数据
        		String bridge_span = ((EditText) findViewById(R.id.et_bridge_span)).getText().toString(); // 桥跨组合
        		String longest_span = ((EditText) findViewById(R.id.et_longest_span)).getText().toString(); // 最大跨径
        		String total_len = ((EditText) findViewById(R.id.et_total_len)).getText().toString(); // 桥梁全长
        		String bridge_wide = ((EditText) findViewById(R.id.et_bridge_wide)).getText().toString(); // 桥宽组合
        		String full_wide = ((EditText) findViewById(R.id.et_full_wide)).getText().toString(); // 桥面全宽
        		String clear_wide = ((EditText) findViewById(R.id.et_clear_wide)).getText().toString(); // 桥面净宽
        		
        		String bridge_high = ((EditText) findViewById(R.id.et_bridge_high)).getText().toString(); // 桥高
        		String high_limit = ((EditText) findViewById(R.id.et_high_limit)).getText().toString(); // 桥梁限高
        		String building_time = ((EditText) findViewById(R.id.et_building_time)).getText().toString(); // 建桥时间
        		String navigation_level = ((Spinner) findViewById(R.id.sp_navigation_level)).getSelectedItem().toString(); // 通航等级
        		String section_high = ((EditText) findViewById(R.id.et_section_high)).getText().toString(); // 跨中截面高
        		String deck_profile_grade = ((EditText) findViewById(R.id.et_deck_profile_grade)).getText().toString(); // 桥面纵坡
        		
        		// 如果有原始数据，执行修改操作
        		if (cursor.moveToFirst()) {
        			String setValue = "bridge_span='" + bridge_span + "',longest_span='" + longest_span + "',total_len='" + total_len  + "',bridge_wide='" + bridge_wide 
        					+ "',full_wide='" + full_wide + "',clear_wide='" + clear_wide + "',bridge_high='" + bridge_high + "',high_limit='" + high_limit
        					+ "',building_time='" + building_time + "',navigation_level='" + navigation_level + "',section_high='" + section_high + "',deck_profile_grade='" + deck_profile_grade + "',flag='0'";
        			
        			// 修改数据
        			int flag = db.updateData("structure", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0)
            			Toast.makeText(StructureActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(StructureActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(StructureActivity.this, PartsActivity.class);
            			intent.putExtra("toNextId", bg_id); // 传递id值
            			intent.putExtra("toNext", "toNextBg"); // 传递添加跳转
                		startActivity(intent);
            		}
        		}
        		else { // 没有则执行插入操作
        			String key = "bg_id, bridge_span, longest_span, total_len, bridge_wide, full_wide, clear_wide,"
        					+ "bridge_high, high_limit, building_time, navigation_level, section_high, deck_profile_grade, flag";
            		
            		String values = "'" + bg_id + "','" + bridge_span + "','" + longest_span + "','" + total_len + "','"
            				+ bridge_wide + "','" + full_wide + "','" + clear_wide + "','" + bridge_high + "','" + high_limit + "','"
            				+ building_time + "','" + navigation_level + "','" + section_high + "','" + deck_profile_grade + "','0'";
            		
            		// 插入数据
        			int flag = db.insertData("structure", key, values);
            		
            		if (flag == 0)
            			Toast.makeText(StructureActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(StructureActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(StructureActivity.this, PartsActivity.class);
            			intent.putExtra("toNextId", bg_id); // 传递id值
            			intent.putExtra("toNext", "toNextBg"); // 传递添加跳转
                		startActivity(intent);
            		}
        		}        		
        	}
        });
	}
    
    @Override
    public void onBackPressed() {
    	// 禁用返回键，让用户使用按钮键，控制程序正常运行
    	Toast.makeText(this, "返回键已禁用，请使用 “上一步” 按钮返回", Toast.LENGTH_SHORT).show();
    	return;
    }
}
