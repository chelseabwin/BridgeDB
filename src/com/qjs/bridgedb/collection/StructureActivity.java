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
	int bg_id; // ����id	
	Spinner sp_nl; // ͨ���ȼ�	
	boolean flag; // ����ʱ���־λ
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_structure);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // ������һҳ����ת
		final String fromNext = bundle.getString("toPrev"); // ������һҳ����ת
		
		final DbOperation db = new DbOperation(StructureActivity.this);
		
		if (fromPrev != null)
			bg_id = bundle.getInt("toNextId"); // ��ȡ����һҳ�洫�ݹ�����id
		else if (fromNext != null)
			bg_id = bundle.getInt("toPrevId"); // ��ȡ����һҳ�洫�ݹ�����id
		
		// ����id��������
		final Cursor cursor = db.queryData("*", "structure", "bg_id='" + bg_id + "'");
		
		// �����ԭʼ���ݣ���ԭʼ���������ı���
		if (cursor.moveToFirst()) {
			// ��ȡ����
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
				
		// ����ʱ��
		EditText et_building_time = (EditText) findViewById(R.id.et_building_time);
		et_building_time.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {					
					int c_year, c_month, c_day;
					EditText et = (EditText) findViewById(R.id.et_building_time);
					String str = et.getText().toString();
					
					if(str == null || str.length() <= 0) { // ���δѡ�����ڣ��򽫵�����ΪĬ�ϵ�������
						Calendar c = Calendar.getInstance();
						
						c_year = c.get(Calendar.YEAR);
				        c_month = c.get(Calendar.MONTH);
				        c_day = c.get(Calendar.DAY_OF_MONTH);
					}
					else { // �����ѡ�����ڣ���ѡ��������ΪĬ�ϵ�������
						String[] date_arr = str.split(" - ");
						
						c_year = Integer.parseInt(date_arr[0]);
				        c_month = Integer.parseInt(date_arr[1]) - 1;
				        c_day = Integer.parseInt(date_arr[2]);
					}
			        
			        DatePickerDialog picker = new DatePickerDialog(StructureActivity.this, 
			        		// �󶨼�����
	        				new DatePickerDialog.OnDateSetListener() {
			        		
								@Override
								public void onDateSet(DatePicker dp, int year,
									int month, int dayOfMonth) {
									// ֻ�е�״̬λΪ��ʱ�Ż��޸�editText
									if(flag) {
										EditText show = (EditText) findViewById(R.id.et_building_time);
										show.setText(year + " - " + (month + 1) + " - " + dayOfMonth);
										flag = false; // ��״̬λ�÷�
									}
								}
							}
			        		,c_year, c_month, c_day);
			        picker.setCancelable(true);
			        picker.setCanceledOnTouchOutside(true);
			        picker.setTitle("���ý���ʱ��");
			        picker.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��",
			                new DialogInterface.OnClickListener() {
			        		
			                    @Override
			                    public void onClick(DialogInterface dialog, int which) {
			                        Log.d("Picker", "Correct behavior!");
			                        flag = true;
			                    }
			                });
			        picker.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��",
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
		
		// ��һ��
        Button st_last_btn = (Button)findViewById(R.id.st_last_btn);
        st_last_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(StructureActivity.this, Base3Activity.class);
        		intent.putExtra("toPrevId", bg_id); // ������һҳ��id
        		intent.putExtra("toPrev", "toPrevBg"); // ��ת��һҳ��ʶ
        		startActivity(intent);
        	}
        });
        
        // ��һ��
        Button st_next_btn = (Button)findViewById(R.id.st_next_btn);
        st_next_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {        		
        		// ��ȡ����
        		String bridge_span = ((EditText) findViewById(R.id.et_bridge_span)).getText().toString(); // �ſ����
        		String longest_span = ((EditText) findViewById(R.id.et_longest_span)).getText().toString(); // ���羶
        		String total_len = ((EditText) findViewById(R.id.et_total_len)).getText().toString(); // ����ȫ��
        		String bridge_wide = ((EditText) findViewById(R.id.et_bridge_wide)).getText().toString(); // �ſ����
        		String full_wide = ((EditText) findViewById(R.id.et_full_wide)).getText().toString(); // ����ȫ��
        		String clear_wide = ((EditText) findViewById(R.id.et_clear_wide)).getText().toString(); // ���澻��
        		
        		String bridge_high = ((EditText) findViewById(R.id.et_bridge_high)).getText().toString(); // �Ÿ�
        		String high_limit = ((EditText) findViewById(R.id.et_high_limit)).getText().toString(); // �����޸�
        		String building_time = ((EditText) findViewById(R.id.et_building_time)).getText().toString(); // ����ʱ��
        		String navigation_level = ((Spinner) findViewById(R.id.sp_navigation_level)).getSelectedItem().toString(); // ͨ���ȼ�
        		String section_high = ((EditText) findViewById(R.id.et_section_high)).getText().toString(); // ���н����
        		String deck_profile_grade = ((EditText) findViewById(R.id.et_deck_profile_grade)).getText().toString(); // ��������
        		
        		// �����ԭʼ���ݣ�ִ���޸Ĳ���
        		if (cursor.moveToFirst()) {
        			String setValue = "bridge_span='" + bridge_span + "',longest_span='" + longest_span + "',total_len='" + total_len  + "',bridge_wide='" + bridge_wide 
        					+ "',full_wide='" + full_wide + "',clear_wide='" + clear_wide + "',bridge_high='" + bridge_high + "',high_limit='" + high_limit
        					+ "',building_time='" + building_time + "',navigation_level='" + navigation_level + "',section_high='" + section_high + "',deck_profile_grade='" + deck_profile_grade + "',flag='0'";
        			
        			// �޸�����
        			int flag = db.updateData("structure", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0)
            			Toast.makeText(StructureActivity.this, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(StructureActivity.this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(StructureActivity.this, PartsActivity.class);
            			intent.putExtra("toNextId", bg_id); // ����idֵ
            			intent.putExtra("toNext", "toNextBg"); // ���������ת
                		startActivity(intent);
            		}
        		}
        		else { // û����ִ�в������
        			String key = "bg_id, bridge_span, longest_span, total_len, bridge_wide, full_wide, clear_wide,"
        					+ "bridge_high, high_limit, building_time, navigation_level, section_high, deck_profile_grade, flag";
            		
            		String values = "'" + bg_id + "','" + bridge_span + "','" + longest_span + "','" + total_len + "','"
            				+ bridge_wide + "','" + full_wide + "','" + clear_wide + "','" + bridge_high + "','" + high_limit + "','"
            				+ building_time + "','" + navigation_level + "','" + section_high + "','" + deck_profile_grade + "','0'";
            		
            		// ��������
        			int flag = db.insertData("structure", key, values);
            		
            		if (flag == 0)
            			Toast.makeText(StructureActivity.this, "���ʧ��", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(StructureActivity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(StructureActivity.this, PartsActivity.class);
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
