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
	String bg_id; // ����id
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parts2);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // ������һҳ����ת
		final String fromNext = bundle.getString("toPrev"); // ������һҳ����ת
		
		final DbOperation db = new DbOperation(Parts2Activity.this);
		
		if (fromPrev != null)
			bg_id = bundle.getString("toNextId"); // ��ȡ����һҳ�洫�ݹ�����id
		else if (fromNext != null)
			bg_id = bundle.getString("toPrevId"); // ��ȡ����һҳ�洫�ݹ�����id
		
		// ����id��������
		final Cursor cursor = db.queryData("*", "parts2", "bg_id='" + bg_id + "'");
		
		// �����ԭʼ���ݣ���ԭʼ���������ı���
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
		
		EditText load_detail = (EditText) findViewById(R.id.et_load_detail); // �ϲ����ع�������
		EditText general_detail = (EditText) findViewById(R.id.et_general_detail); // �ϲ�һ�㹹������
		EditText support_detail = (EditText) findViewById(R.id.et_support_detail); // ֧������
		
		// ����ϲ����ع����ı��򴥷��¼�
		load_detail.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					loadShowView(v);
				}
				return true;
			}
		});
		
		// ����ϲ�һ�㹹���ı��򴥷��¼�
		general_detail.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					generalShowView(v);
				}
				return true;
			}
		});
		
		// ���֧���ı��򴥷��¼�
		support_detail.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					supportShowView(v);
				}
				return true;
			}
		});
		
		// ��һ��
        Button pa2_last_btn = (Button)findViewById(R.id.pa2_last_btn);
        pa2_last_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(Parts2Activity.this, PartsActivity.class);
        		intent.putExtra("toPrevId", bg_id); // ������һҳ��id
        		intent.putExtra("toPrev", "toPrevBg"); // ��ת��һҳ��ʶ
        		startActivity(intent);
    			finish();
        	}
        });
        
        // ��һ��
        Button pa2_finish_btn = (Button)findViewById(R.id.pa2_finish_btn);
        pa2_finish_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {        		
        		// ���е���ѡ��
        		CheckBox sidewalk_l = (CheckBox) findViewById(R.id.sidewalk_l);
        		CheckBox sidewalk_r = (CheckBox) findViewById(R.id.sidewalk_r);
        		
        		// ���ˡ�������ѡ��
        		CheckBox guardrail_l = (CheckBox) findViewById(R.id.guardrail_l);
        		CheckBox guardrail_r = (CheckBox) findViewById(R.id.guardrail_r);
        		
        		String sidewalk = ""; // ���е�
        		String guardrail = ""; // ���ˡ�����
        		
        		// ���е�����ֵ
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
        		
        		// ���ˡ���������ֵ
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
        		
        		String deck_num = ((EditText) findViewById(R.id.et_deck_num)).getText().toString(); // ������װ����
        		String joint_num = ((EditText) findViewById(R.id.et_joint_num)).getText().toString(); // ������װ�ø���
        		String drainage_system = ((EditText) findViewById(R.id.et_drainage_system)).getText().toString(); // ��ˮϵͳ����
        		String illuminated_sign = ((EditText) findViewById(R.id.et_illuminated_sign)).getText().toString(); // ��������־����
        		
        		// ���Ϊ�գ���ֵΪ��0��
        		if (deck_num.equals(""))
        			deck_num = "0";
        		if (joint_num.equals(""))
        			joint_num = "0";
        		if (drainage_system.equals(""))
        			drainage_system = "0";
        		if (illuminated_sign.equals(""))
        			illuminated_sign = "0";
        		
        		String load_detail_id = "0"; // �ϲ����ع���id
        		String general_detail_id = "0"; // �ϲ�һ�㹹��id
        		String support_detail_id = "0"; // ֧��id
        		
        		// �����ԭʼ���ݣ�ִ���޸Ĳ���
        		if (cursor.moveToFirst()) {
        			String setValue = "load_detail='" + load_detail_id + "',general_detail='" + general_detail_id + "',support_detail='" + support_detail_id 
        					+ "',deck_num='" + deck_num + "',joint_num='" + joint_num + "',sidewalk='" + sidewalk + "',guardrail='" + guardrail 
        					+ "',drainage_system='" + drainage_system + "',illuminated_sign='" + illuminated_sign + "',flag='2'";
        			
        			// �޸�����
        			int flag = db.updateData("parts2", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0)
            			Toast.makeText(Parts2Activity.this, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(Parts2Activity.this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Parts2Activity.this, MainActivity.class);
                		startActivity(intent);
            		}
        		}
        		else { // û����ִ�в������
        			String key = "bg_id, load_detail, general_detail, support_detail, deck_num, joint_num,"
        					+ "sidewalk, guardrail, drainage_system, illuminated_sign, flag";
            		
            		String values = "'" + bg_id + "','" + load_detail_id + "','" + general_detail_id + "','" + support_detail_id + "','" + deck_num + "','"
            				+ joint_num + "','" + sidewalk + "','" + guardrail + "','" + drainage_system + "','" + illuminated_sign + "','0'";
            		
            		// ��������
        			int flag = db.insertData("parts2", key, values);
            		
            		if (flag == 0)
            			Toast.makeText(Parts2Activity.this, "���ʧ��", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(Parts2Activity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Parts2Activity.this, MainActivity.class);
                		startActivity(intent);
            		}
        		}
    			finish();        		
        	}
        });
	}
	
	// �ϲ����ع�����Ϣչʾ������
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
		
		// �ж��Ƿ����ϲ����ع�������
		if (load_details.length() == 0 && load_nums.length() == 0) {
			arr = new String[] {"�����ϲ����ع�����Ϣ"};
		}
		else {
			arr = new String[] {
					"�ϲ����ع������飺\n" + load_details, 
					"�ϲ����ع�����ţ�\n" + load_nums};
		}
		 
		final View v = source;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);			
		builder.setTitle("�ϲ����ع�����Ϣ����") // ���ñ���
			// �����б���
			.setAdapter(new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr), null)
			// ������Ӱ�ť
			.setPositiveButton("���", new DialogInterface.OnClickListener () {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					loadDetailView(v);
				}				
			})
			// ���÷��ذ�ť
			.setNegativeButton("����", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}				
			})
			// ������հ�ť
			.setNeutralButton("���", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DbOperation db = new DbOperation(Parts2Activity.this);
					int flag = db.deleteData("load_add", null);
					int flag1 = db.deleteData("load_detail", null);
					
					if (flag == 1 && flag1 == 1)
            			Toast.makeText(Parts2Activity.this, "�ϲ����ع�����Ϣɾ���ɹ�", Toast.LENGTH_SHORT).show();
            		else
            			Toast.makeText(Parts2Activity.this, "�ϲ����ع�����Ϣɾ��ʧ��", Toast.LENGTH_SHORT).show();
				}
			})
			.create()
			.show();			
	}
	
	// �½��ϲ����ع�����Ϣ������
	public void loadDetailView(View source) {
		// װ��/res/layout/load_general_detail.xml���沼��
		final TableLayout loadForm = (TableLayout)getLayoutInflater()
			.inflate( R.layout.load_general_detail, null);		
		new AlertDialog.Builder(this)
			// ���öԻ���ı���
			.setTitle("�½��ϲ����ع�����Ϣ")
			// ���öԻ�����ʾ��View����
			.setView(loadForm)
			// Ϊ�Ի�������һ����ȷ������ť
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String start_load = ((EditText) loadForm.findViewById(R.id.et_start_stride)).getText().toString(); // ��ʼ���
					String end_load = ((EditText) loadForm.findViewById(R.id.et_end_stride)).getText().toString(); // ��ֹ���
					String per_load = ((EditText) loadForm.findViewById(R.id.et_per_stride_num)).getText().toString(); // ÿ�����
					
					String tap = "0"; // ��ű�־λ
					
					// ����id��������
	        		DbOperation db = new DbOperation(Parts2Activity.this);
	        		// ����д����Ϊ���ֱ��MAX�Ļ�����һ���ռ�¼
	        		Cursor cursor_la = db.queryData("*", "load_add", "bg_id='" + bg_id + "' and id = (select max(id) from load_add where bg_id='" + bg_id + "')"); // �����ſ������Ϣ
					
	        		// ���ƿ��
	        		if (cursor_la.moveToFirst()) {
	        			if (!cursor_la.getString(cursor_la.getColumnIndex("tap")).equals("")) {
	        				tap = cursor_la.getString(cursor_la.getColumnIndex("tap"));
	        			}
	        		}
	        		
	        		// �ǿ���֤
	        		if (start_load.length() != 0 && end_load.length() != 0 && per_load.length() != 0) {
	        			// �����֤
	        			if (Integer.parseInt(end_load) < Integer.parseInt(start_load)) {
		        			Toast.makeText(Parts2Activity.this, "��ֹ��Ų���С����ʼ���", Toast.LENGTH_SHORT).show();
		        		}
		        		else if(Integer.parseInt(start_load) <= Integer.parseInt(tap)) {
		        			Toast.makeText(Parts2Activity.this, "��ʼ��������" + tap, Toast.LENGTH_SHORT).show();
		        		}
		        		else {
		        			// ��֤ͨ������tapֵ
		        			tap = end_load;
		        			
		        			// ��ʼ�������ݲ���
		        			String key = "bg_id, start_load, end_load, per_load, tap, flag";
		            		
		            		String values = "'" + bg_id + "','" + start_load + "','" + end_load + "','" + per_load + "','" + tap + "','0'";
		            		
		            		// ��������
		            		int flag1 = db.insertData("load_add", key, values);
		            		
		            		if (flag1 == 0) {
		            			Toast.makeText(Parts2Activity.this, "������ʧ��", Toast.LENGTH_SHORT).show();
		            		}
		            		else {
		            			String load_details = "";
		            			String load_nums = "";
		            			
		            			Cursor cursor = db.queryData("*", "load_detail", "bg_id='" + bg_id + "'");
		            			if (cursor.moveToFirst()) {
		            				load_details = cursor.getString(cursor.getColumnIndex("load_details"));
		            				load_nums = cursor.getString(cursor.getColumnIndex("load_nums"));
		            			}
		            			
		            			// �����ſ�ϸ��
		            			load_details += "��" + start_load + "�絽" + end_load + "�磬ÿ��" + per_load + "��\n";
		            			
		            			for (int i = Integer.parseInt(start_load); i <= Integer.parseInt(end_load); i++) {
		            				for (int j = 1; j <= Integer.parseInt(per_load); j++) {
		            					load_nums += i + "-" + j + "; "; // �����ſ���                    			
		            				}
		            				load_nums += "\n";
		            			}
		            			
		            			int flag2 = 0;
		            			
		            			// �����ԭʼ���ݣ�ִ���޸Ĳ���
		            			if (cursor.moveToFirst()) {
		            				String setValue = "load_details='" + load_details + "',load_nums='" + load_nums + "',flag='2'";
		            				
		            				flag2 = db.updateData("load_detail", setValue, "bg_id='" + bg_id + "'");
		            			}
		            			else { // û����ִ�в������
		            				String detail_key = "bg_id, load_details, load_nums, flag";
			            			String detail_values = "'" + bg_id + "','" + load_details + "','" + load_nums + "','0'";
			            			
			            			flag2 = db.insertData("load_detail", detail_key, detail_values);
		            			}
		            			
		            			if (flag2 == 0)
		                			Toast.makeText(Parts2Activity.this, "�ſ�ϸ�����ʧ��", Toast.LENGTH_SHORT).show();
		            			else
		            				Toast.makeText(Parts2Activity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
		            		}
		        		}
	        		}
	        		else
	        			Toast.makeText(Parts2Activity.this, "��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
				}
			})
			// Ϊ�Ի�������һ����ȡ������ť
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			// ����������ʾ�Ի���
			.create()
			.show();
	}
	
	// �ϲ�һ�㹹����Ϣչʾ������
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
		
		// �ж��Ƿ����ϲ�һ�㹹������
		if (general_details.length() == 0 && general_nums.length() == 0) {
			arr = new String[] {"�����ϲ�һ�㹹����Ϣ"};
		}
		else {
			arr = new String[] {
					"�ϲ�һ�㹹�����飺\n" + general_details, 
					"�ϲ�һ�㹹����ţ�\n" + general_nums};
		}
		 
		final View v = source;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);			
		builder.setTitle("�ϲ�һ�㹹����Ϣ����") // ���ñ���
			// �����б���
			.setAdapter(new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr), null)
			// ������Ӱ�ť
			.setPositiveButton("���", new DialogInterface.OnClickListener () {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					generalDetailView(v);
				}				
			})
			// ���÷��ذ�ť
			.setNegativeButton("����", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}				
			})
			// ������հ�ť
			.setNeutralButton("���", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DbOperation db = new DbOperation(Parts2Activity.this);
					int flag = db.deleteData("general_add", null);
					int flag1 = db.deleteData("general_detail", null);
					
					if (flag == 1 && flag1 == 1)
            			Toast.makeText(Parts2Activity.this, "�ϲ�һ�㹹����Ϣɾ���ɹ�", Toast.LENGTH_SHORT).show();
            		else
            			Toast.makeText(Parts2Activity.this, "�ϲ�һ�㹹����Ϣɾ��ʧ��", Toast.LENGTH_SHORT).show();
				}
			})
			.create()
			.show();			
	}
	
	// �½��ϲ�һ�㹹����Ϣ������
	public void generalDetailView(View source) {
		// װ��/res/layout/load_general_detail.xml���沼��
		final TableLayout generalForm = (TableLayout)getLayoutInflater().inflate(R.layout.load_general_detail, null);
		
		new AlertDialog.Builder(this)
			// ���öԻ���ı���
			.setTitle("�½��ϲ�һ�㹹����Ϣ")
			// ���öԻ�����ʾ��View����
			.setView(generalForm)
			// Ϊ�Ի�������һ����ȷ������ť
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String start_general = ((EditText) generalForm.findViewById(R.id.et_start_stride)).getText().toString(); // ��ʼ���
					String end_general = ((EditText) generalForm.findViewById(R.id.et_end_stride)).getText().toString(); // ��ֹ���
					String per_general = ((EditText) generalForm.findViewById(R.id.et_per_stride_num)).getText().toString(); // ÿ�����
					
					String tap = "0"; // ��ű�־λ
					
					// ����id��������
	        		DbOperation db = new DbOperation(Parts2Activity.this);
	        		// ����д����Ϊ���ֱ��MAX�Ļ�����һ���ռ�¼
	        		Cursor cursor_ga = db.queryData("*", "general_add", "bg_id='" + bg_id + "' and id = (select max(id) from general_add where bg_id='" + bg_id + "')"); // �����ſ������Ϣ
					
	        		// ���ƿ��
	        		if (cursor_ga.moveToFirst()) {
	        			if (!cursor_ga.getString(cursor_ga.getColumnIndex("tap")).equals("")) {
	        				tap = cursor_ga.getString(cursor_ga.getColumnIndex("tap"));
	        			}
	        		}
	        		
	        		// �ǿ���֤
	        		if (start_general.length() != 0 && end_general.length() != 0 && per_general.length() != 0) {
	        			// �����֤
	        			if (Integer.parseInt(end_general) < Integer.parseInt(start_general)) {
		        			Toast.makeText(Parts2Activity.this, "��ֹ��Ų���С����ʼ���", Toast.LENGTH_SHORT).show();
		        		}
		        		else if(Integer.parseInt(start_general) <= Integer.parseInt(tap) && tap != "0") {
		        			Toast.makeText(Parts2Activity.this, "��ʼ��������" + tap, Toast.LENGTH_SHORT).show();
		        		}
		        		else {
		        			// ��֤ͨ������tapֵ
		        			tap = end_general;
		        			
		        			// ��ʼ�������ݲ���
		        			String key = "bg_id, start_general, end_general, per_general, tap, flag";
		            		
		            		String values = "'" + bg_id + "','" + start_general + "','" + end_general + "','" + per_general + "','" + tap + "','0'";
		            		
		            		// ��������
		            		int flag1 = db.insertData("general_add", key, values);
		            		
		            		if (flag1 == 0) {
		            			Toast.makeText(Parts2Activity.this, "������ʧ��", Toast.LENGTH_SHORT).show();
		            		}
		            		else {
		            			String general_details = "";
		            			String general_nums = "";
		            			
		            			Cursor cursor = db.queryData("*", "general_detail", "bg_id='" + bg_id + "'");
		            			if (cursor.moveToFirst()) {
		            				general_details = cursor.getString(cursor.getColumnIndex("general_details"));
		            				general_nums = cursor.getString(cursor.getColumnIndex("general_nums"));
		            			}
		            			
		            			// �����ſ�ϸ��
		            			general_details += "��" + start_general + "�絽" + end_general + "�磬ÿ��" + per_general + "��\n";
		            			
		            			for (int i = Integer.parseInt(start_general); i <= Integer.parseInt(end_general); i++) {
		            				for (int j = 1; j <= Integer.parseInt(per_general); j++) {
		            					general_nums += i + "-" + j + "; "; // �����ſ���                    			
		            				}
		            				general_nums += "\n";
		            			}
		            			
		            			int flag2 = 0;
		            			
		            			// �����ԭʼ���ݣ�ִ���޸Ĳ���
		            			if (cursor.moveToFirst()) {
		            				String setValue = "general_details='" + general_details + "',general_nums='" + general_nums + "',flag='2'";
		            				
		            				flag2 = db.updateData("general_detail", setValue, "bg_id='" + bg_id + "'");
		            			}
		            			else { // û����ִ�в������
		            				String detail_key = "bg_id, general_details, general_nums, flag";
			            			String detail_values = "'" + bg_id + "','" + general_details + "','" + general_nums + "','0'";
			            			
			            			flag2 = db.insertData("general_detail", detail_key, detail_values);
		            			}
		            			
		            			if (flag2 == 0)
		                			Toast.makeText(Parts2Activity.this, "�ſ�ϸ�����ʧ��", Toast.LENGTH_SHORT).show();
		            			else
		            				Toast.makeText(Parts2Activity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
		            		}
		        		}
	        		}
	        		else
	        			Toast.makeText(Parts2Activity.this, "��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
				}
			})
			// Ϊ�Ի�������һ����ȡ������ť
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			// ����������ʾ�Ի���
			.create()
			.show();
	}
	
	// ֧����Ϣչʾ������
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
		
		// �ж��Ƿ���֧������
		if (support_details.length() == 0 && support_nums.length() == 0) {
			arr = new String[] {"����֧����Ϣ"};
		}
		else {
			arr = new String[] {
					"֧�����飺\n" + support_details, 
					"֧����ţ�\n" + support_nums};
		}
		 
		final View v = source;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);			
		builder.setTitle("֧����Ϣ����") // ���ñ���
			// �����б���
			.setAdapter(new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr), null)
			// ������Ӱ�ť
			.setPositiveButton("���", new DialogInterface.OnClickListener () {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					supportDetailView(v);
				}				
			})
			// ���÷��ذ�ť
			.setNegativeButton("����", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}				
			})
			// ������հ�ť
			.setNeutralButton("���", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DbOperation db = new DbOperation(Parts2Activity.this);
					int flag = db.deleteData("support_add", null);
					int flag1 = db.deleteData("support_detail", null);
					
					if (flag == 1 && flag1 == 1)
            			Toast.makeText(Parts2Activity.this, "֧����Ϣɾ���ɹ�", Toast.LENGTH_SHORT).show();
            		else
            			Toast.makeText(Parts2Activity.this, "֧����Ϣɾ��ʧ��", Toast.LENGTH_SHORT).show();
				}
			})
			.create()
			.show();			
	}
	
	// �½�֧����Ϣ������
	public void supportDetailView(View source) {
		// װ��/res/layout/support_detail.xml���沼��
		final TableLayout supportForm = (TableLayout)getLayoutInflater().inflate(R.layout.support_detail, null);
		
		new AlertDialog.Builder(this)
			// ���öԻ���ı���
			.setTitle("�½�֧����Ϣ")
			// ���öԻ�����ʾ��View����
			.setView(supportForm)
			// Ϊ�Ի�������һ����ȷ������ť
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String start_support = ((EditText) supportForm.findViewById(R.id.et_start_spt)).getText().toString(); // ��ʼ�պ�
					String end_support = ((EditText) supportForm.findViewById(R.id.et_end_spt)).getText().toString(); // ��ֹ�պ�
					String raw_num = ((EditText) supportForm.findViewById(R.id.et_per_row_num)).getText().toString(); // ÿ������
					String support_num = ((EditText) supportForm.findViewById(R.id.et_per_spt_num)).getText().toString(); // ÿ��֧����
					
					String tap = "0"; // ��ű�־λ
					
					// ����id��������
	        		DbOperation db = new DbOperation(Parts2Activity.this);
	        		// ����д����Ϊ���ֱ��MAX�Ļ�����һ���ռ�¼
	        		Cursor cursor_sa = db.queryData("*", "support_add", "bg_id='" + bg_id + "' and id = (select max(id) from support_add where bg_id='" + bg_id + "')"); // ����֧�������Ϣ
					
	        		// ���ƶգ�̨����
	        		if (cursor_sa.moveToFirst()) {
	        			if (!cursor_sa.getString(cursor_sa.getColumnIndex("tap")).equals("")) {
	        				tap = cursor_sa.getString(cursor_sa.getColumnIndex("tap"));
	        			}
	        		}
	        		
	        		// �ǿ���֤
	        		if (start_support.length() != 0 && end_support.length() != 0 && raw_num.length() != 0 && support_num.length() != 0) {
	        			// �գ�̨������֤
	        			if (Integer.parseInt(end_support) < Integer.parseInt(start_support)) {
		        			Toast.makeText(Parts2Activity.this, "��ֹ�գ�̨���Ų���С����ʼ�գ�̨����", Toast.LENGTH_SHORT).show();
		        		}
		        		else if(Integer.parseInt(start_support) <= Integer.parseInt(tap) && tap != "0") {
		        			Toast.makeText(Parts2Activity.this, "��ʼ�գ�̨���������" + tap, Toast.LENGTH_SHORT).show();
		        		}
		        		else {
		        			// ��֤ͨ������tapֵ
		        			tap = end_support;
		        			
		        			// ��ʼ�������ݲ���
		        			String key = "bg_id, start_support, end_support, raw_num, support_num, tap, flag";
		            		
		            		String values = "'" + bg_id + "','" + start_support + "','" + end_support + "','" + raw_num + "','" + support_num + "','" + tap + "','0'";
		            		
		            		// ��������
		            		int flag1 = db.insertData("support_add", key, values);
		            		
		            		if (flag1 == 0) {
		            			Toast.makeText(Parts2Activity.this, "�գ�̨�������ʧ��", Toast.LENGTH_SHORT).show();
		            		}
		            		else {
		            			String support_details = "";
		            			String support_nums = "";
		            			
		            			Cursor cursor = db.queryData("*", "support_detail", "bg_id='" + bg_id + "'");
		            			if (cursor.moveToFirst()) {
		            				support_details = cursor.getString(cursor.getColumnIndex("support_details"));
		            				support_nums = cursor.getString(cursor.getColumnIndex("support_nums"));
		            			}
		            			
		            			// ����֧��ϸ��
		            			support_details += "��" + start_support + "��̨��" + end_support + "��̨��ÿ��̨" + raw_num + "�ţ�ÿ��" + support_num + "��\n";
		            			
		            			for (int i = Integer.parseInt(start_support); i <= Integer.parseInt(end_support); i++) {
		            				for (int j = 1; j <= Integer.parseInt(raw_num); j++) {
		            					for (int k = 1; k <= Integer.parseInt(support_num); k++) {
		            						support_nums += i + "-" + j + "-" + k + "; "; // ����֧�����
		            					}		            					
		            				}
		            				support_nums += "\n";
		            			}
		            			
		            			int flag2 = 0;
		            			
		            			// �����ԭʼ���ݣ�ִ���޸Ĳ���
		            			if (cursor.moveToFirst()) {
		            				String setValue = "support_details='" + support_details + "',support_nums='" + support_nums + "',flag='2'";
		            				
		            				flag2 = db.updateData("support_detail", setValue, "bg_id='" + bg_id + "'");
		            			}
		            			else { // û����ִ�в������
		            				String detail_key = "bg_id, support_details, support_nums, flag";
			            			String detail_values = "'" + bg_id + "','" + support_details + "','" + support_nums + "','0'";
			            			
			            			flag2 = db.insertData("support_detail", detail_key, detail_values);
		            			}
		            			
		            			if (flag2 == 0)
		                			Toast.makeText(Parts2Activity.this, "֧��ϸ�����ʧ��", Toast.LENGTH_SHORT).show();
		            			else
		            				Toast.makeText(Parts2Activity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
		            		}
		        		}
	        		}
	        		else
	        			Toast.makeText(Parts2Activity.this, "�գ�̨���Ų���Ϊ��", Toast.LENGTH_SHORT).show();
				}
			})
			// Ϊ�Ի�������һ����ȡ������ť
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			// ����������ʾ�Ի���
			.create()
			.show();
	}
    
    @Override
    public void onBackPressed() {
    	// ���÷��ؼ������û�ʹ�ð�ť�������Ƴ�����������
    	Toast.makeText(this, "���ؼ��ѽ��ã���ʹ�� ����һ���� ��ť����", Toast.LENGTH_SHORT).show();
    	return;
    }
}
