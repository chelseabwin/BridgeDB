package com.qjs.bridgedb.collection;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Base3Activity extends Activity {	
	String bg_id; // 桥梁id
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base3);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // 来自上一页的跳转
		final String fromNext = bundle.getString("toPrev"); // 来自下一页的跳转
		
		final DbOperation db = new DbOperation(Base3Activity.this);
		
		if (fromPrev != null)
			bg_id = bundle.getString("toNextId"); // 获取从上一页面传递过来的id
		else if (fromNext != null)
			bg_id = bundle.getString("toPrevId"); // 获取从下一页面传递过来的id
		
		// 根据id查找数据
		final Cursor cursor = db.queryData("*", "base3", "bg_id='" + bg_id + "'");
		
		// 如果有原始数据，则将原始数据填入文本框
		if (cursor.moveToFirst()) {
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_pier_material), cursor.getString(cursor.getColumnIndex("pier_material")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_section_form), cursor.getString(cursor.getColumnIndex("section_form")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_pier_type), cursor.getString(cursor.getColumnIndex("pier_type")));
			
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_abutment_material), cursor.getString(cursor.getColumnIndex("abutment_material")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_abutment_type), cursor.getString(cursor.getColumnIndex("abutment_type")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_pier_abutment_material), cursor.getString(cursor.getColumnIndex("pier_abutment_material")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_pier_abutment_base), cursor.getString(cursor.getColumnIndex("pier_abutment_base")));
			
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_deck_type), cursor.getString(cursor.getColumnIndex("deck_type")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_joint_type), cursor.getString(cursor.getColumnIndex("joint_type")));
		}
		
		// 上一步
        Button b3_last_btn = (Button)findViewById(R.id.b3_last_btn);
        b3_last_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(Base3Activity.this, Base2Activity.class);
        		intent.putExtra("toPrevId", bg_id); // 传给上一页的id
        		intent.putExtra("toPrev", "toPrevBg"); // 跳转上一页标识
        		startActivity(intent);
        	}
        });
        
        // 下一步
        Button b3_next_btn = (Button)findViewById(R.id.b3_next_btn);
        b3_next_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {        		
        		// 获取数据
        		String pier_material = ((Spinner) findViewById(R.id.sp_pier_material)).getSelectedItem().toString(); // 桥墩材料
        		String section_form = ((Spinner) findViewById(R.id.sp_section_form)).getSelectedItem().toString(); // 桥墩截面形式
        		String pier_type = ((Spinner) findViewById(R.id.sp_pier_type)).getSelectedItem().toString(); // 桥墩类型
        		
        		String abutment_material = ((Spinner) findViewById(R.id.sp_abutment_material)).getSelectedItem().toString(); // 桥台材料
        		String abutment_type = ((Spinner) findViewById(R.id.sp_abutment_type)).getSelectedItem().toString(); // 桥台类型
        		String pier_abutment_material = ((Spinner) findViewById(R.id.sp_pier_abutment_material)).getSelectedItem().toString(); // 墩台基础材料
        		String pier_abutment_base = ((Spinner) findViewById(R.id.sp_pier_abutment_base)).getSelectedItem().toString(); // 墩台基础
        		
        		String deck_type = ((Spinner) findViewById(R.id.sp_deck_type)).getSelectedItem().toString(); // 桥面铺装类型
        		String joint_type = ((Spinner) findViewById(R.id.sp_joint_type)).getSelectedItem().toString(); // 伸缩缝类型
        		
        		// 如果有原始数据，执行修改操作
        		if (cursor.moveToFirst()) {
        			String setValue = "pier_material='" + pier_material + "',section_form='" + section_form + "',pier_type='" + pier_type 
        					+ "',abutment_material='" + abutment_material + "',abutment_type='" + abutment_type + "',pier_abutment_material='" + pier_abutment_material
        					+ "',pier_abutment_base='" + pier_abutment_base + "',deck_type='" + deck_type + "',joint_type='" + joint_type + "',flag='0'";
        			
        			// 修改数据
        			int flag = db.updateData("base3", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0)
            			Toast.makeText(Base3Activity.this, "修改失败", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(Base3Activity.this, "修改成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Base3Activity.this, StructureActivity.class);
            			intent.putExtra("toNextId", bg_id); // 传递id值
            			intent.putExtra("toNext", "toNextBg"); // 传递添加跳转
                		startActivity(intent);
            		}
        		}
        		else { // 没有则执行插入操作
        			String key = "bg_id, pier_material, section_form, pier_type, abutment_material, abutment_type,"
        					+ "pier_abutment_material, pier_abutment_base, deck_type, joint_type, flag";
            		
            		String values = "'" + bg_id + "','" + pier_material + "','" + section_form + "','" + pier_type + "','" + abutment_material + "','"
            				+ abutment_type + "','" + pier_abutment_material + "','" + pier_abutment_base + "','" + deck_type + "','" + joint_type + "','0'";
            		
            		// 插入数据
        			int flag = db.insertData("base3", key, values);
            		
            		if (flag == 0)
            			Toast.makeText(Base3Activity.this, "添加失败", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(Base3Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Base3Activity.this, StructureActivity.class);
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
