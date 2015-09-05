package com.qjs.bridgedb.collection;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		
		Intent intent = getIntent();
		final Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // ������һҳ����ת
		final String fromNext = bundle.getString("toPrev"); // ������һҳ����ת
		
		final DbOperation db = new DbOperation(BaseActivity.this);
		
		// ��һҳ�������ʱ������id�ҵ���ҳ��Ӧ������
		if (fromNext != null) {
			int bg_id = bundle.getInt("toPrevId");
			
			// ����id��������
			Cursor cursor = db.queryData("*", "base1", "id=" + bg_id);
			
			// ҳ���ı���ֵ
			cursor.moveToNext();
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
		
		// ����
        Button back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(BaseActivity.this, MainActivity.class);
        		startActivity(intent);
        	}
        });
        
        // ��һ��
        Button next_step_btn = (Button) findViewById(R.id.next_step_btn);
        next_step_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {        		
        		// ��ȡ����
        		String bridge_name = ((EditText) findViewById(R.id.et_bridge_name)).getText().toString(); // ��������
        		String path_num = ((EditText) findViewById(R.id.et_path_num)).getText().toString(); // ·�ߺ�
        		String path_name = ((EditText) findViewById(R.id.et_path_name)).getText().toString(); // ·������
        		String path_type = ((Spinner) findViewById(R.id.sp_path_type)).getSelectedItem().toString(); // ·������
        		String rode_grade = ((Spinner) findViewById(R.id.sp_rode_grade)).getSelectedItem().toString(); // ��·�����ȼ�
        		String order_num = ((EditText) findViewById(R.id.et_order_num)).getText().toString(); // ˳���
        		
        		String location = ((EditText) findViewById(R.id.et_location)).getText().toString(); // ���ڵ�
        		String center_stake = ((EditText) findViewById(R.id.et_center_stake)).getText().toString(); // ����׮��
        		String custody_unit = ((EditText) findViewById(R.id.et_custody_unit)).getText().toString(); // ������λ
        		String across_name = ((EditText) findViewById(R.id.et_across_name)).getText().toString(); // ��Խ��������
        		String across_type = ((Spinner) findViewById(R.id.sp_across_type)).getSelectedItem().toString(); // ��Խ��������
        		String bridge_nature = ((Spinner) findViewById(R.id.sp_bridge_nature)).getSelectedItem().toString(); // ��������
        		
        		String bridge_code = path_num + path_type.substring(1, 2) + order_num; // ������������        		
        		
        		if (fromPrev != null) {
        			String key = "bridge_code, bridge_name, path_num, path_name, path_type, rode_grade, order_num,"
            				+ "location, center_stake, custody_unit, across_name, across_type, bridge_nature, flag";
            		
            		String values = "'" + bridge_code + "','" + bridge_name + "','" + path_num + "','" + path_name + "','" + path_type + "','"
            				+ rode_grade + "','" + order_num + "','" + location + "','" + center_stake + "','"
            				+ custody_unit + "','" + across_name + "','" + across_type + "','" + bridge_nature + "','0'";
            		
            		// ��������
        			int flag = db.insertData("base1", key, values);
            		
            		if (flag == 0)
            			Toast.makeText(BaseActivity.this, "���ʧ��", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(BaseActivity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(BaseActivity.this, Base2Activity.class);
            			intent.putExtra("toNextId", flag); // ����idֵ
            			intent.putExtra("toNext", "toNextBg"); // ���������ת
                		startActivity(intent);
            		}
        		}
        		else if (fromNext != null) {
        			String setValue = "bridge_code='" + bridge_code + "',bridge_name='" + bridge_name + "',path_num='" + path_num 
        					+ "',path_name='" + path_name + "',path_type='" + path_type + "',rode_grade='" + rode_grade + "',order_num='" + order_num
        					+ "',location='" + location + "',center_stake='" + center_stake + "',custody_unit='" + custody_unit
        					+ "',across_name='" + across_name + "',across_type='" + across_type + "',bridge_nature='" + bridge_nature + "',flag='0'";
        			
        			// �޸�����
        			int bg_id = bundle.getInt("toPrevId");
        			int flag = db.updateData("base1", setValue, "id=" + bg_id);
        			
        			if (flag == 0)
            			Toast.makeText(BaseActivity.this, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(BaseActivity.this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(BaseActivity.this, Base2Activity.class);
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
    	Toast.makeText(this, "���ؼ��ѽ��ã���ʹ�� �����ء� ��ť����", Toast.LENGTH_SHORT).show();
    	return;
    }
}
