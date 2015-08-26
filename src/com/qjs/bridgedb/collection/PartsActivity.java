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
	int bg_id; // ����id
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parts);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // ������һҳ����ת
		final String fromNext = bundle.getString("toPrev"); // ������һҳ����ת
		
		final DbOperation db = new DbOperation(PartsActivity.this);
		
		if (fromPrev != null) {
			bg_id = bundle.getInt("toNextId"); // ��ȡ����һҳ�洫�ݹ�����id
		}
		else if (fromNext != null) {
			bg_id = bundle.getInt("toPrevId"); // ��ȡ����һҳ�洫�ݹ�����id
		}
		
		// ����id��������
		final Cursor cursor = db.queryData("*", "parts1", "bg_id='" + bg_id + "'");
		
		// �����ԭʼ���ݣ���ԭʼ���������ı���
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
		
		final EditText pier_detail = (EditText) findViewById(R.id.et_pier_detail); // �Ŷ�����
		
		// ����Ŷ��ı��򴥷��¼�
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
		
		// ��һ��
        Button pa_last_btn = (Button)findViewById(R.id.pa_last_btn);
        pa_last_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(PartsActivity.this, StructureActivity.class);
        		intent.putExtra("toPrevId", bg_id); // ������һҳ��id
        		intent.putExtra("toPrev", "toPrevBg"); // ��ת��һҳ��ʶ
        		startActivity(intent);
        	}
        });
        
        // ��һ��
        Button pa_next_btn = (Button)findViewById(R.id.pa_next_btn);
        pa_next_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// ��ǽ����ǽ��ѡ��
        		CheckBox wall_l0 = (CheckBox) findViewById(R.id.wall_l0);
        		CheckBox wall_r0 = (CheckBox) findViewById(R.id.wall_r0);
        		CheckBox wall_l1 = (CheckBox) findViewById(R.id.wall_l1);
        		CheckBox wall_r1 = (CheckBox) findViewById(R.id.wall_r1);
        		
        		// ׶�¸�ѡ��
        		CheckBox slope_l0 = (CheckBox) findViewById(R.id.slope_l0);
        		CheckBox slope_r0 = (CheckBox) findViewById(R.id.slope_r0);
        		CheckBox slope_l1 = (CheckBox) findViewById(R.id.slope_l1);
        		CheckBox slope_r1 = (CheckBox) findViewById(R.id.slope_r1);
        		
        		String wing_wall = ""; // ��ǽ����ǽ
        		String conical_slope = ""; // ׶��
        		
        		// ��ǽ����ǽ����ֵ
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
        		
        		// ׶������ֵ
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
        		
        		String protection_slope = ((EditText) findViewById(R.id.et_protection_slope)).getText().toString(); // ���¸���
        		String abutment_num = ((EditText) findViewById(R.id.et_abutment_num)).getText().toString(); // ��̨����
        		String pa_num = ((EditText) findViewById(R.id.et_pa_num)).getText().toString(); // ��̨��������
        		String bed_num = ((EditText) findViewById(R.id.et_bed_num)).getText().toString(); // �Ӵ�����
        		String reg_structure = ((EditText) findViewById(R.id.et_reg_structure)).getText().toString(); // ���ι��������
        		
        		String pier_detail_id = "0"; // �Ŷ�id
        		
        		// �����ԭʼ���ݣ�ִ���޸Ĳ���
        		if (cursor.moveToFirst()) {
        			String setValue = "wing_wall='" + wing_wall + "',conical_slope='" + conical_slope + "',protection_slope='" + protection_slope 
        					+ "',pier_detail='" + pier_detail_id + "',abutment_num='" + abutment_num + "',pa_num='" + pa_num
        					+ "',bed_num='" + bed_num + "',reg_structure='" + reg_structure + "'";
        			
        			// �޸�����
        			int flag = db.updateData("parts1", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0) {
            			Toast.makeText(PartsActivity.this, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
            		}
            		else {
            			Toast.makeText(PartsActivity.this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(PartsActivity.this, Parts2Activity.class);
            			intent.putExtra("toNextId", bg_id); // ����idֵ
            			intent.putExtra("toNext", "toNextBg"); // ���������ת
                		startActivity(intent);
            		}
        		}
        		else { // û����ִ�в������
        			String key = "bg_id, wing_wall, conical_slope, protection_slope, pier_detail,"
        					+ "abutment_num, pa_num, bed_num, reg_structure";
            		
            		String values = "'" + bg_id + "','" + wing_wall + "','" + conical_slope + "','" + protection_slope + "','" + pier_detail_id + "','"
            				+ abutment_num + "','" + pa_num + "','" + bed_num + "','" + reg_structure + "'";
            		
            		// ��������
        			int flag = db.insertData("parts1", key, values);
            		
            		if (flag == 0) {
            			Toast.makeText(PartsActivity.this, "���ʧ��", Toast.LENGTH_SHORT).show();
            		}
            		else {
            			Toast.makeText(PartsActivity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(PartsActivity.this, Parts2Activity.class);
            			intent.putExtra("toNextId", bg_id); // ����idֵ
            			intent.putExtra("toNext", "toNextBg"); // ���������ת
                		startActivity(intent);
            		}
        		}
        	}
        });
	}
	
	// �Ŷ���Ϣչʾ������
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
		
		// �ж��Ƿ����Ŷ�����
		if (pier_details.length() == 0 && pier_nums.length() == 0 && bent_cap_nums.length() == 0 && tie_beam_nums.length() == 0) {
			arr = new String[] {"�����Ŷ���Ϣ"};
		}
		else  {
			arr = new String[] {
					"�Ŷ����飺\n" + pier_details, 
					"�Ŷձ�ţ�\n" + pier_nums,
					"�����ţ�\n" + bent_cap_nums, 
					"ϵ���ţ�\n" + tie_beam_nums};
		}
		 
		final View v = source;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);			
		builder.setTitle("�Ŷ���Ϣ����") // ���ñ���
			// �����б���
			.setAdapter(new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr), null)
			// ������Ӱ�ť
			.setPositiveButton("���", new DialogInterface.OnClickListener () {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					pierDetailView(v);
				}				
			})
			// ���÷��ذ�ť
			.setNegativeButton("����", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}				
			})
			.create()
			.show();			
	}
	
	// �½��Ŷ���Ϣ������
	public void pierDetailView (View source) {
		// װ��/res/layout/pier_detail.xml���沼��
		final TableLayout pierForm = (TableLayout) getLayoutInflater().inflate(R.layout.pier_detail, null);		
		
		new AlertDialog.Builder(this)
			// ���öԻ���ı���
			.setTitle("�½��Ŷ���Ϣ")
			// ���öԻ�����ʾ��View����
			.setView(pierForm)
			// Ϊ�Ի�������һ����ȷ������ť
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					String start_pier = ((EditText) pierForm.findViewById(R.id.et_start_pier)).getText().toString(); // ��ʼ�Ŷպ�
					String end_pier = ((EditText) pierForm.findViewById(R.id.et_end_pier)).getText().toString(); // ��ֹ�Ŷպ�
					String per_pier = ((EditText) pierForm.findViewById(R.id.et_per_pier_num)).getText().toString(); // ÿ���Ŷն�����
					
					CheckBox ck_bent_cap = (CheckBox) pierForm.findViewById(R.id.ck_bent_cap);
	        		CheckBox ck_tie_beam = (CheckBox) pierForm.findViewById(R.id.ck_tie_beam);
	        		
	        		String bent_cap = ""; // ����
	        		String tie_beam = ""; // ϵ��
	        		String tap = "0"; // �պű�־λ
	        		
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
	        		
	        		// ����id��������
	        		DbOperation db = new DbOperation(PartsActivity.this);
	        		// ����д����Ϊ���ֱ��MAX�Ļ�����һ���ռ�¼
	        		Cursor cursor_pa = db.queryData("*", "pier_add", "bg_id='" + bg_id + "' and id = (select max(id) from pier_add where bg_id='" + bg_id + "')"); // �����Ŷ������Ϣ
	        		
	        		// ���ƶպ�
	        		if (cursor_pa.moveToFirst()) {
	        			if (!cursor_pa.getString(cursor_pa.getColumnIndex("tap")).equals("")) {
	        				tap = cursor_pa.getString(cursor_pa.getColumnIndex("tap"));
	        			}
	        		}
	        		
	        		// �ǿ���֤
	        		if (start_pier.length() != 0 && end_pier.length() != 0 && per_pier.length() != 0) {
	        			// �պ���֤
	        			if (Integer.parseInt(end_pier) < Integer.parseInt(start_pier)) {
		        			Toast.makeText(PartsActivity.this, "��ֹ�ŶպŲ���С����ʼ�Ŷպ�", Toast.LENGTH_SHORT).show();
		        		}
		        		else if(Integer.parseInt(start_pier) <= Integer.parseInt(tap) && tap != "0") {
		        			Toast.makeText(PartsActivity.this, "��ʼ�Ŷպ������" + tap, Toast.LENGTH_SHORT).show();
		        		}
		        		else {
		        			// ��֤ͨ������tapֵ
		        			tap = end_pier;
		        			
		        			// ��ʼ�������ݲ���
		        			String key = "bg_id, start_pier, end_pier, per_pier, bent_cap, tie_beam, tap";
		            		
		            		String values = "'" + bg_id + "','" + start_pier + "','" + end_pier + "','" + per_pier + "','"
		            				+ bent_cap + "','" + tie_beam + "','" + tap + "'";
		            		
		            		// ��������
		            		int flag1 = db.insertData("pier_add", key, values);
		            		
		            		if (flag1 == 0) {
		            			Toast.makeText(PartsActivity.this, "�Ŷձ�����ʧ��", Toast.LENGTH_SHORT).show();
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
		            			
		            			String bc = (bent_cap == "1")?"�и�����":"�޸�����";
		            			String tb = (tie_beam == "1")?"��ϵ��\n":"��ϵ��\n";
		            			
		            			// �����Ŷ�ϸ��
		            			pier_details += "��" + start_pier + "�յ�" + end_pier + "�գ�ÿ��" + per_pier + "������" + bc + tb;
		            			
		            			for(int i = Integer.parseInt(start_pier); i <= Integer.parseInt(end_pier); i++) {
		            				for (int j = 1; j <= Integer.parseInt(per_pier); j++) {
		            					pier_nums += i + "-" + j + "; "; // �����Ŷձ��                    			
		            				}
		            				pier_nums += "\n";
		            				
		            				if (bent_cap == "1") {
		            					bent_cap_nums += i + ", "; // ���ø������
		            				}
		            				
		            				if (tie_beam == "1") {
		            					tie_beam_nums += i + ", "; // ����ϵ�����
		            				}
		            			}
		            			
		            			int flag2 = 0;
		            			
		            			// �����ԭʼ���ݣ�ִ���޸Ĳ���
		            			if (cursor.moveToFirst()) {
		            				String setValue = "pier_details='" + pier_details + "',pier_nums='" + pier_nums + "',bent_cap_nums='" + bent_cap_nums 
		                					+ "',tie_beam_nums='" + tie_beam_nums + "'";
		            				
		            				flag2 = db.updateData("pier_detail", setValue, "bg_id='" + bg_id + "'");
		            			}
		            			else { // û����ִ�в������
		            				String detail_key = "bg_id, pier_details, pier_nums, bent_cap_nums, tie_beam_nums";
			            			String detail_values = "'" + bg_id + "','" + pier_details + "','" + pier_nums + "','" + bent_cap_nums + "','" + tie_beam_nums + "'";
			            			
			            			flag2 = db.insertData("pier_detail", detail_key, detail_values);
		            			}
		            			
		            			if (flag2 == 0) {
		                			Toast.makeText(PartsActivity.this, "�Ŷ�ϸ�����ʧ��", Toast.LENGTH_SHORT).show();
		                		}
		            			else {
		            				Toast.makeText(PartsActivity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
		            			}
		            		}
		        		}
	        		}
	        		else {
	        			Toast.makeText(PartsActivity.this, "�պŲ���Ϊ��", Toast.LENGTH_SHORT).show();
	        		}
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
