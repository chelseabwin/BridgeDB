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
	String bg_id; // ����id
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base3);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		final String fromPrev = bundle.getString("toNext"); // ������һҳ����ת
		final String fromNext = bundle.getString("toPrev"); // ������һҳ����ת
		
		final DbOperation db = new DbOperation(Base3Activity.this);
		
		if (fromPrev != null)
			bg_id = bundle.getString("toNextId"); // ��ȡ����һҳ�洫�ݹ�����id
		else if (fromNext != null)
			bg_id = bundle.getString("toPrevId"); // ��ȡ����һҳ�洫�ݹ�����id
		
		// ����id��������
		final Cursor cursor = db.queryData("*", "base3", "bg_id='" + bg_id + "'");
		
		// �����ԭʼ���ݣ���ԭʼ���������ı���
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
		
		// ��һ��
        Button b3_last_btn = (Button)findViewById(R.id.b3_last_btn);
        b3_last_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(Base3Activity.this, Base2Activity.class);
        		intent.putExtra("toPrevId", bg_id); // ������һҳ��id
        		intent.putExtra("toPrev", "toPrevBg"); // ��ת��һҳ��ʶ
        		startActivity(intent);
        	}
        });
        
        // ��һ��
        Button b3_next_btn = (Button)findViewById(R.id.b3_next_btn);
        b3_next_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {        		
        		// ��ȡ����
        		String pier_material = ((Spinner) findViewById(R.id.sp_pier_material)).getSelectedItem().toString(); // �Ŷղ���
        		String section_form = ((Spinner) findViewById(R.id.sp_section_form)).getSelectedItem().toString(); // �Ŷս�����ʽ
        		String pier_type = ((Spinner) findViewById(R.id.sp_pier_type)).getSelectedItem().toString(); // �Ŷ�����
        		
        		String abutment_material = ((Spinner) findViewById(R.id.sp_abutment_material)).getSelectedItem().toString(); // ��̨����
        		String abutment_type = ((Spinner) findViewById(R.id.sp_abutment_type)).getSelectedItem().toString(); // ��̨����
        		String pier_abutment_material = ((Spinner) findViewById(R.id.sp_pier_abutment_material)).getSelectedItem().toString(); // ��̨��������
        		String pier_abutment_base = ((Spinner) findViewById(R.id.sp_pier_abutment_base)).getSelectedItem().toString(); // ��̨����
        		
        		String deck_type = ((Spinner) findViewById(R.id.sp_deck_type)).getSelectedItem().toString(); // ������װ����
        		String joint_type = ((Spinner) findViewById(R.id.sp_joint_type)).getSelectedItem().toString(); // ����������
        		
        		// �����ԭʼ���ݣ�ִ���޸Ĳ���
        		if (cursor.moveToFirst()) {
        			String setValue = "pier_material='" + pier_material + "',section_form='" + section_form + "',pier_type='" + pier_type 
        					+ "',abutment_material='" + abutment_material + "',abutment_type='" + abutment_type + "',pier_abutment_material='" + pier_abutment_material
        					+ "',pier_abutment_base='" + pier_abutment_base + "',deck_type='" + deck_type + "',joint_type='" + joint_type + "',flag='0'";
        			
        			// �޸�����
        			int flag = db.updateData("base3", setValue, "bg_id='" + bg_id + "'");
        			
        			if (flag == 0)
            			Toast.makeText(Base3Activity.this, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(Base3Activity.this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Base3Activity.this, StructureActivity.class);
            			intent.putExtra("toNextId", bg_id); // ����idֵ
            			intent.putExtra("toNext", "toNextBg"); // ���������ת
                		startActivity(intent);
            		}
        		}
        		else { // û����ִ�в������
        			String key = "bg_id, pier_material, section_form, pier_type, abutment_material, abutment_type,"
        					+ "pier_abutment_material, pier_abutment_base, deck_type, joint_type, flag";
            		
            		String values = "'" + bg_id + "','" + pier_material + "','" + section_form + "','" + pier_type + "','" + abutment_material + "','"
            				+ abutment_type + "','" + pier_abutment_material + "','" + pier_abutment_base + "','" + deck_type + "','" + joint_type + "','0'";
            		
            		// ��������
        			int flag = db.insertData("base3", key, values);
            		
            		if (flag == 0)
            			Toast.makeText(Base3Activity.this, "���ʧ��", Toast.LENGTH_SHORT).show();
            		else {
            			Toast.makeText(Base3Activity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
            			
            			Intent intent = new Intent(Base3Activity.this, StructureActivity.class);
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
