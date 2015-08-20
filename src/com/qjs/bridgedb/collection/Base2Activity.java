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
	Spinner sp_bt; // ��������һ��
	Spinner sp_bt2; // �������Ͷ���
	
	int bg_id; // ����id
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base2);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // ������һҳ����ת
		final String fromNext = bundle.getString("toPrev"); // ������һҳ����ת
		
		final DbOperation db = new DbOperation(Base2Activity.this);
		
		if (fromPrev != null)
		{
			bg_id = bundle.getInt("toNextId"); // ��ȡ����һҳ�洫�ݹ�����id
		}
		else if (fromNext != null)
		{
			bg_id = bundle.getInt("toPrevId"); // ��ȡ����һҳ�洫�ݹ�����id
		}
		
		// ��������Spinner
		sp_bt = (Spinner) findViewById(R.id.sp_bridge_type);
		sp_bt2 = (Spinner) findViewById(R.id.sp_bridge_type2);
		
		final String[][] BridgeTypeData = new String[][] {
				this.getResources().getStringArray(R.array.slab_bridge),
				this.getResources().getStringArray(R.array.girder_bridge),
				this.getResources().getStringArray(R.array.rigid_frame),
				this.getResources().getStringArray(R.array.arch_bridge),
				this.getResources().getStringArray(R.array.cable_stayed)};
		
		// ����id��������
		final Cursor cursor = db.queryData("*", "base2", "bg_id='" + bg_id + "'");
		
		sp_bt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) // ѡ��ı��ʱ�򴥷�
			{
				// TODO Auto-generated method stub				
				ArrayAdapter<String> adapterType = new ArrayAdapter<String>(Base2Activity.this,
                        android.R.layout.simple_spinner_item, // ��ʾ���
                        BridgeTypeData[position]); // ���б���ͼ��������Ķ���
				adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //���ô�ʱ�����
				
				sp_bt2.setAdapter(adapterType); // ��adapterType��ӵ�sp_bt2
				
				// Ϊ�����˵��ĵڶ�����ֵ������setAdapter�������б��positionֵ�����������жϸ�ֵ
				if (cursor.moveToFirst())
				{
					String num2 = cursor.getString(cursor.getColumnIndex("bridge_type")).substring(3, 4); // ��ȡ�����˵��ڶ����ı��
					int key2 = Integer.parseInt(num2) - 1; // �����ת��Ϊ����(��Ŵ�0��ʼ����-1����)
					try
					{
						sp_bt2.setSelection(key2, true); // �������ü����˵��ڶ���ѡ���key
					}
					catch (Exception e)
					{
						sp_bt2.setSelection(0, true); // ���Խ��������Ϊ��һ��
					}					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		// �����ԭʼ���ݣ���ԭʼ���������ı���
		if (cursor.moveToFirst())
		{
			String num1 = cursor.getString(cursor.getColumnIndex("bridge_type")).substring(1, 2); // ��ȡ�����˵���һ���ı��
			int key1 = Integer.parseInt(num1) - 1; // �����ת��Ϊ����(��Ŵ�0��ʼ����-1����)
			
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_bridge_classify), cursor.getString(cursor.getColumnIndex("bridge_classify")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_design_load), cursor.getString(cursor.getColumnIndex("design_load")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_bridge_use), cursor.getString(cursor.getColumnIndex("bridge_use")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_bridge_status), cursor.getString(cursor.getColumnIndex("bridge_status")));
			
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_material_code), cursor.getString(cursor.getColumnIndex("material_code")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_bridge_panel), cursor.getString(cursor.getColumnIndex("bridge_panel")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_stress_pattern), cursor.getString(cursor.getColumnIndex("stress_pattern")));
			DbOperation.setSpinnerItemSelectedByValue((Spinner) findViewById(R.id.sp_support_type), cursor.getString(cursor.getColumnIndex("support_type")));
			((Spinner) findViewById(R.id.sp_bridge_type)).setSelection(key1, true); // ���ü����˵���һ��ѡ���key
		}
		
		// ��һ��
        Button b2_last_btn = (Button)findViewById(R.id.b2_last_btn);
        b2_last_btn.setOnClickListener(new OnClickListener()
        {        	
        	@Override
        	public void onClick(View v)
        	{
        		Intent intent = new Intent(Base2Activity.this, BaseActivity.class);
        		intent.putExtra("toPrevId", bg_id); // ������һҳ��id
        		intent.putExtra("toPrev", "toPrevBg"); // ��ת��һҳ��ʶ
        		startActivity(intent);
        	}
        });
        
        // ��һ��
        Button b2_next_btn = (Button)findViewById(R.id.b2_next_btn);
        b2_next_btn.setOnClickListener(new OnClickListener() 
        {        	
        	@Override
        	public void onClick(View v)
        	{        		
        		// ��ȡ����
        		String bridge_classify = ((Spinner) findViewById(R.id.sp_bridge_classify)).getSelectedItem().toString(); // ��������
        		String design_load = ((Spinner) findViewById(R.id.sp_design_load)).getSelectedItem().toString(); // ��ƺ��صȼ�
        		String bridge_use = ((Spinner) findViewById(R.id.sp_bridge_use)).getSelectedItem().toString(); // ������;
        		String bridge_status = ((Spinner) findViewById(R.id.sp_bridge_status)).getSelectedItem().toString(); // ����״̬
        		
        		String material_code = ((Spinner) findViewById(R.id.sp_material_code)).getSelectedItem().toString(); // ���ϱ���
        		String bridge_panel = ((Spinner) findViewById(R.id.sp_bridge_panel)).getSelectedItem().toString(); // �����λ
        		String stress_pattern = ((Spinner) findViewById(R.id.sp_stress_pattern)).getSelectedItem().toString(); // ������ʽ
        		String support_type = ((Spinner) findViewById(R.id.sp_support_type)).getSelectedItem().toString(); // ֧������
        		String bridge_type = ((Spinner) findViewById(R.id.sp_bridge_type2)).getSelectedItem().toString(); // ����
        		
        		// �����ԭʼ���ݣ�ִ���޸Ĳ���
        		if (cursor.moveToFirst())
        		{
        			String setValue = "bridge_classify='" + bridge_classify + "',design_load='" + design_load + "',bridge_use='" + bridge_use 
        					+ "',bridge_status='" + bridge_status + "',material_code='" + material_code + "',bridge_panel='" + bridge_panel
        					+ "',stress_pattern='" + stress_pattern + "',support_type='" + support_type + "',bridge_type='" + bridge_type + "'";
        			
        			// �޸�����
        			int flag = db.updateData("base2", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0)
            		{
            			Toast.makeText(Base2Activity.this, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
            		}
            		else
            		{
            			Toast.makeText(Base2Activity.this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Base2Activity.this, Base3Activity.class);
            			intent.putExtra("toNextId", bg_id); // ����idֵ
            			intent.putExtra("toNext", "toNextBg"); // ���������ת
                		startActivity(intent);
            		}
        		}
        		else // û����ִ�в������
        		{
        			String key = "bg_id, bridge_classify, design_load, bridge_use, bridge_status,"
            				+ "material_code, bridge_panel, stress_pattern, support_type, bridge_type";
            		
            		String values = "'" + bg_id + "','" + bridge_classify + "','" + design_load + "','" + bridge_use + "','" + bridge_status + "','"
            				+ material_code + "','" + bridge_panel + "','" + stress_pattern + "','" + support_type + "','" + bridge_type + "'";
            		
            		// ��������
        			int flag = db.insertData("base2", key, values);
            		
            		if (flag == 0)
            		{
            			Toast.makeText(Base2Activity.this, "���ʧ��", Toast.LENGTH_SHORT).show();
            		}
            		else
            		{
            			Toast.makeText(Base2Activity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Base2Activity.this, Base3Activity.class);
            			intent.putExtra("toNextId", bg_id); // ����idֵ
            			intent.putExtra("toNext", "toNextBg"); // ���������ת
                		startActivity(intent);
            		}
        		}
        	}
        });
	}
    
    @Override
    public void onBackPressed() {
    	// ���÷��ؼ������û�ʹ�ð�ť�������Ƴ�����������
    	Toast.makeText(this, "���ؼ��ѽ��ã���ʹ�� ����һ���� ��ť����", Toast.LENGTH_SHORT).show();
    	return;
    }
}
