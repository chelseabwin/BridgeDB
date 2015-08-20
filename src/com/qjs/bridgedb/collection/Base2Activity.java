package com.qjs.bridgedb.collection;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Base2Activity extends Activity
{	
	Spinner sp_bt; // 桥梁类型一级
	Spinner sp_bt2; // 桥梁类型二级
	
	int bg_id; // 桥梁id
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base2);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // 来自上一页的跳转
		final String fromNext = bundle.getString("toPrev"); // 来自下一页的跳转
		
		final DbOperation db = new DbOperation(Base2Activity.this);
		
		if (fromPrev != null)
		{
			bg_id = bundle.getInt("toNextId"); // 获取从上一页面传递过来的id
		}
		else if (fromNext != null)
		{
			bg_id = bundle.getInt("toPrevId"); // 获取从下一页面传递过来的id
		}
		
		// 桥梁类型Spinner
		sp_bt = (Spinner) findViewById(R.id.sp_bridge_type);
		sp_bt2 = (Spinner) findViewById(R.id.sp_bridge_type2);
		
		final String[][] BridgeTypeData = new String[][] {
				this.getResources().getStringArray(R.array.slab_bridge),
				this.getResources().getStringArray(R.array.girder_bridge),
				this.getResources().getStringArray(R.array.rigid_frame),
				this.getResources().getStringArray(R.array.arch_bridge),
				this.getResources().getStringArray(R.array.cable_stayed)};
		
		// 根据id查找数据
		final Cursor cursor = db.queryData("*", "base2", "bg_id='" + bg_id + "'");
		
		sp_bt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) // 选项改变的时候触发
			{
				// TODO Auto-generated method stub				
				ArrayAdapter<String> adapterType = new ArrayAdapter<String>(Base2Activity.this,
                        android.R.layout.simple_spinner_item, // 显示风格
                        BridgeTypeData[position]); // 在列表视图中所代表的对象
				adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //设置打开时的外观
				
				sp_bt2.setAdapter(adapterType); // 把adapterType添加到sp_bt2
				
				// 为级联菜单的第二联赋值，由于setAdapter会重置列表的position值，故在这里判断赋值
				if (cursor.moveToFirst())
				{
					String num2 = cursor.getString(cursor.getColumnIndex("bridge_type")).substring(3, 4); // 获取级联菜单第二联的编号
					int key2 = Integer.parseInt(num2) - 1; // 将编号转换为整型(编号从0开始，故-1处理)
					try
					{
						sp_bt2.setSelection(key2, true); // 尝试设置级联菜单第二联选择的key
					}
					catch (Exception e)
					{
						sp_bt2.setSelection(0, true); // 如果越界则设置为第一个
					}					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		// 如果有原始数据，则将原始数据填入文本框
		if (cursor.moveToFirst())
		{
			String num1 = cursor.getString(cursor.getColumnIndex("bridge_type")).substring(1, 2); // 获取级联菜单第一联的编号
			int key1 = Integer.parseInt(num1) - 1; // 将编号转换为整型(编号从0开始，故-1处理)
			
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_bridge_classify), cursor.getString(cursor.getColumnIndex("bridge_classify")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_design_load), cursor.getString(cursor.getColumnIndex("design_load")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_bridge_use), cursor.getString(cursor.getColumnIndex("bridge_use")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_bridge_status), cursor.getString(cursor.getColumnIndex("bridge_status")));
			
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_material_code), cursor.getString(cursor.getColumnIndex("material_code")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_bridge_panel), cursor.getString(cursor.getColumnIndex("bridge_panel")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_stress_pattern), cursor.getString(cursor.getColumnIndex("stress_pattern")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_support_type), cursor.getString(cursor.getColumnIndex("support_type")));
			((Spinner) findViewById(R.id.sp_bridge_type)).setSelection(key1, true); // 设置级联菜单第一联选择的key
		}
		
		// 上一步
        Button b2_last_btn = (Button)findViewById(R.id.b2_last_btn);
        b2_last_btn.setOnClickListener(new OnClickListener()
        {        	
        	@Override
        	public void onClick(View v)
        	{
        		Intent intent = new Intent(Base2Activity.this, BaseActivity.class);
        		intent.putExtra("toPrevId", bg_id); // 传给上一页的id
        		intent.putExtra("toPrev", "toPrevBg"); // 跳转上一页标识
        		startActivity(intent);
        	}
        });
        
        // 下一步
        Button b2_next_btn = (Button)findViewById(R.id.b2_next_btn);
        b2_next_btn.setOnClickListener(new OnClickListener() 
        {        	
        	@Override
        	public void onClick(View v)
        	{        		
        		// 获取数据
        		String bridge_classify = ((Spinner) findViewById(R.id.sp_bridge_classify)).getSelectedItem().toString(); // 桥梁分类
        		String design_load = ((Spinner) findViewById(R.id.sp_design_load)).getSelectedItem().toString(); // 设计荷载等级
        		String bridge_use = ((Spinner) findViewById(R.id.sp_bridge_use)).getSelectedItem().toString(); // 桥梁用途
        		String bridge_status = ((Spinner) findViewById(R.id.sp_bridge_status)).getSelectedItem().toString(); // 桥梁状态
        		
        		String material_code = ((Spinner) findViewById(R.id.sp_material_code)).getSelectedItem().toString(); // 材料编码
        		String bridge_panel = ((Spinner) findViewById(R.id.sp_bridge_panel)).getSelectedItem().toString(); // 桥面板位
        		String stress_pattern = ((Spinner) findViewById(R.id.sp_stress_pattern)).getSelectedItem().toString(); // 受力型式
        		String support_type = ((Spinner) findViewById(R.id.sp_support_type)).getSelectedItem().toString(); // 支座类型
        		String bridge_type = ((Spinner) findViewById(R.id.sp_bridge_type2)).getSelectedItem().toString(); // 桥型
        		
        		// 如果有原始数据，执行修改操作
        		if (cursor.moveToFirst())
        		{
        			String setValue = "bridge_classify='" + bridge_classify + "',design_load='" + design_load + "',bridge_use='" + bridge_use 
        					+ "',bridge_status='" + bridge_status + "',material_code='" + material_code + "',bridge_panel='" + bridge_panel
        					+ "',stress_pattern='" + stress_pattern + "',support_type='" + support_type + "',bridge_type='" + bridge_type + "'";
        			
        			// 修改数据
        			int flag = db.updateData("base2", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0)
            		{
            			Toast.makeText(Base2Activity.this, "修改失败", Toast.LENGTH_SHORT).show();
            		}
            		else
            		{
            			Toast.makeText(Base2Activity.this, "修改成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Base2Activity.this, Base3Activity.class);
            			intent.putExtra("toNextId", bg_id); // 传递id值
            			intent.putExtra("toNext", "toNextBg"); // 传递添加跳转
                		startActivity(intent);
            		}
        		}
        		else // 没有则执行插入操作
        		{
        			String key = "bg_id, bridge_classify, design_load, bridge_use, bridge_status,"
            				+ "material_code, bridge_panel, stress_pattern, support_type, bridge_type";
            		
            		String values = "'" + bg_id + "','" + bridge_classify + "','" + design_load + "','" + bridge_use + "','" + bridge_status + "','"
            				+ material_code + "','" + bridge_panel + "','" + stress_pattern + "','" + support_type + "','" + bridge_type + "'";
            		
            		// 插入数据
        			int flag = db.insertData("base2", key, values);
            		
            		if (flag == 0)
            		{
            			Toast.makeText(Base2Activity.this, "添加失败", Toast.LENGTH_SHORT).show();
            		}
            		else
            		{
            			Toast.makeText(Base2Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Base2Activity.this, Base3Activity.class);
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
