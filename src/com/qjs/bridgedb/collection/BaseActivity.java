package com.qjs.bridgedb.collection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.MainActivity;
import com.qjs.bridgedb.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BaseActivity extends Activity {
	String bg_id; // 桥梁id
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		
		Intent intent = getIntent();
		final Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // 来自上一页的跳转
		final String fromNext = bundle.getString("toPrev"); // 来自下一页的跳转
		
		final DbOperation db = new DbOperation(BaseActivity.this);
		
		if (fromPrev != null)
			bg_id = bundle.getString("toNextId"); // 获取从上一页面传递过来的id
		else if (fromNext != null)
			bg_id = bundle.getString("toPrevId"); // 获取从下一页面传递过来的id
		
		// 根据id查找数据
		final Cursor cursor = db.queryData("*", "base1", "bridge_code='" + bg_id + "'");
		
		// 如果有原始数据，则将原始数据填入文本框
		if (cursor.moveToFirst()) {
			((EditText) findViewById(R.id.et_bridge_name)).setText(cursor.getString(cursor.getColumnIndex("bridge_name")));
			((EditText) findViewById(R.id.et_path_num)).setText(cursor.getString(cursor.getColumnIndex("path_num")));
			((EditText) findViewById(R.id.et_path_name)).setText(cursor.getString(cursor.getColumnIndex("path_name")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_path_type), cursor.getString(cursor.getColumnIndex("path_type")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_rode_grade), cursor.getString(cursor.getColumnIndex("rode_grade")));
			((EditText) findViewById(R.id.et_order_num)).setText(cursor.getString(cursor.getColumnIndex("order_num")));
			
			((EditText) findViewById(R.id.et_location)).setText(cursor.getString(cursor.getColumnIndex("location")));
			((EditText) findViewById(R.id.et_center_stake)).setText(cursor.getString(cursor.getColumnIndex("center_stake")));
			((EditText) findViewById(R.id.et_custody_unit)).setText(cursor.getString(cursor.getColumnIndex("custody_unit")));
			((EditText) findViewById(R.id.et_across_name)).setText(cursor.getString(cursor.getColumnIndex("across_name")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_across_type), cursor.getString(cursor.getColumnIndex("across_type")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_bridge_nature), cursor.getString(cursor.getColumnIndex("bridge_nature")));			
		}
		else if (bundle.getString("qr_add") != null && bundle.getString("qr_add").equals("qr_add")) {
			JSONObject jsonObj = null;
			try {
				jsonObj = new JSONObject(bundle.getString("qr_data"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Iterator<String> nameItr = jsonObj.keys();
			String name;
			Map<String, String> outMap = new HashMap<String, String>();
			while (nameItr.hasNext()) {
				name = nameItr.next();
				try {
					outMap.put(name, jsonObj.getString(name));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			((EditText) findViewById(R.id.et_bridge_name)).setText(outMap.get("bridge_name"));
			((EditText) findViewById(R.id.et_path_num)).setText(outMap.get("path_num"));
			((EditText) findViewById(R.id.et_path_name)).setText(outMap.get("path_name"));
			((EditText) findViewById(R.id.et_custody_unit)).setText(outMap.get("custody_unit"));
			((EditText) findViewById(R.id.et_location)).setText(outMap.get("location"));
		}
		
		// 返回
        Button back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(BaseActivity.this, MainActivity.class);
        		startActivity(intent);
        		finish();
        	}
        });
        
        // 下一步
        Button next_step_btn = (Button) findViewById(R.id.next_step_btn);
        next_step_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {        		
        		// 获取数据
        		String bridge_name = ((EditText) findViewById(R.id.et_bridge_name)).getText().toString(); // 桥梁名称
        		String path_num = ((EditText) findViewById(R.id.et_path_num)).getText().toString(); // 路线号
        		String path_name = ((EditText) findViewById(R.id.et_path_name)).getText().toString(); // 路线名称
        		String path_type = ((Spinner) findViewById(R.id.sp_path_type)).getSelectedItem().toString(); // 路线类型
        		String rode_grade = ((Spinner) findViewById(R.id.sp_rode_grade)).getSelectedItem().toString(); // 公路技术等级
        		String order_num = ((EditText) findViewById(R.id.et_order_num)).getText().toString(); // 顺序号
        		
        		String location = ((EditText) findViewById(R.id.et_location)).getText().toString(); // 所在地
        		String center_stake = ((EditText) findViewById(R.id.et_center_stake)).getText().toString(); // 中心桩号
        		String custody_unit = ((EditText) findViewById(R.id.et_custody_unit)).getText().toString(); // 管养单位
        		String across_name = ((EditText) findViewById(R.id.et_across_name)).getText().toString(); // 跨越地物名称
        		String across_type = ((Spinner) findViewById(R.id.sp_across_type)).getSelectedItem().toString(); // 跨越地物类型
        		String bridge_nature = ((Spinner) findViewById(R.id.sp_bridge_nature)).getSelectedItem().toString(); // 桥梁性质
        		
        		Date date = new Date();
        		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        		String[] bgCode = format.format(date).split(" ");
        		String[] time = bgCode[1].split(":");
        		String bridge_code = bgCode[0] + "-" + time[0] + time[1] + time[2]; // 用系统当前时间作为桥梁代码
        		
        		if (fromPrev != null) {
        			String key = "bridge_code, bridge_name, path_num, path_name, path_type, rode_grade, order_num,"
            				+ "location, center_stake, custody_unit, across_name, across_type, bridge_nature, flag";
            		
            		String values = "'" + bridge_code + "','" + bridge_name + "','" + path_num + "','" + path_name + "','" + path_type + "','"
            				+ rode_grade + "','" + order_num + "','" + location + "','" + center_stake + "','"
            				+ custody_unit + "','" + across_name + "','" + across_type + "','" + bridge_nature + "','0'";
            		
            		// 插入数据
        			int flag = db.insertData("base1", key, values);
            		
            		if (flag == 0)
            			Toast.makeText(BaseActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(BaseActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(BaseActivity.this, Base2Activity.class);
            			intent.putExtra("toNextId", bridge_code); // 传递id值
            			intent.putExtra("toNext", "toNextBg"); // 传递添加跳转
                		startActivity(intent);
            		}
        		}
        		else if (fromNext != null) {
        			String setValue = "bridge_code='" + bridge_code + "',bridge_name='" + bridge_name + "',path_num='" + path_num 
        					+ "',path_name='" + path_name + "',path_type='" + path_type + "',rode_grade='" + rode_grade + "',order_num='" + order_num
        					+ "',location='" + location + "',center_stake='" + center_stake + "',custody_unit='" + custody_unit
        					+ "',across_name='" + across_name + "',across_type='" + across_type + "',bridge_nature='" + bridge_nature + "',flag='2'";
        			
        			// 修改数据
        			int bg_id = bundle.getInt("toPrevId");
        			int flag = db.updateData("base1", setValue, "bridge_code=" + bg_id);
        			
        			if (flag == 0)
            			Toast.makeText(BaseActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(BaseActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(BaseActivity.this, Base2Activity.class);
            			intent.putExtra("toNextId", bg_id); // 传递id值
            			intent.putExtra("toNext", "toNextBg"); // 传递添加跳转
                		startActivity(intent);
            		}
        		}
    			finish();
        	}
        });
	}
    
    @Override
    public void onBackPressed() {
    	// 禁用返回键，让用户使用按钮键，控制程序正常运行
    	Toast.makeText(this, "返回键已禁用，请使用 “返回” 按钮返回", Toast.LENGTH_SHORT).show();
    	return;
    }
}
