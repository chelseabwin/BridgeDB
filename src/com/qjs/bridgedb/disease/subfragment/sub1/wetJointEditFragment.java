package com.qjs.bridgedb.disease.subfragment.sub1;

import java.io.FileNotFoundException;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.detailed.EditDiseaseActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class wetJointEditFragment extends Fragment {
	private RadioGroup rgFeature,rgFissure;
	private Spinner spOtherDisease;
	private LinearLayout local1,local2;
	private RadioButton voidsPits,peelingOffAngle,cavitation,fissure,otherDisease;
	private RadioButton fissure1,fissure2,fissure3,fissure4,fissure5;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnCamera,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // ���汾��ͼƬ��ַ
	private DbOperation db = null;
	private Cursor cursor = null;
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100; // ͼƬ�ⷴ����
    private static final int CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE = 200; // �����������
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_upper_page2, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // ��������
		
		rgFeature = (RadioGroup) rootView.findViewById(R.id.rg); // ����������ѡ��
		rgFissure = (RadioGroup) rootView.findViewById(R.id.rg2); // �ѷ쵥ѡ��
		
		spOtherDisease = (Spinner) rootView.findViewById(R.id.sp_other_disease); // �������������б�
		
		local1 = (LinearLayout) rootView.findViewById(R.id.up1_ll_1); // λ����Ϣ�������ѷ�������ѷ죩
		local2 = (LinearLayout) rootView.findViewById(R.id.up1_ll_2); // �����ѷ�λ����Ϣ
		
		voidsPits = (RadioButton) rootView.findViewById(R.id.rbtn_voids_pits); // ��������
		peelingOffAngle = (RadioButton) rootView.findViewById(R.id.rbtn_peeling_off_angle); // �������
		cavitation = (RadioButton) rootView.findViewById(R.id.rbtn_cavitation); // �ն��׶�
		fissure = (RadioButton) rootView.findViewById(R.id.rbtn_fissure); // �ѷ�
		otherDisease = (RadioButton) rootView.findViewById(R.id.rbtn_other_disease); // ��������
		
		fissure1 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure1); // �����ѷ�
		fissure2 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure2); // �����ѷ�
		fissure3 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure3); // б���ѷ�
		fissure4 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure4); // �����ѷ�
		fissure5 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure5); // ���ѷ�
		
		addContent = (EditText) rootView.findViewById(R.id.tv_add_content); // ��������
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // ����ͼƬ��ť
		btnCamera = (Button) rootView.findViewById(R.id.btn_camera); // ���������ť
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // ����ͼƬ
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_submit); // �ύ��ť
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			diseaseDescription.setText("����������" + args.getString("ITEM_NAME") + "(" + args.getString("ACROSS_NUM") + ")");
			
			final String itemName = args.getString("ITEM_NAME");
			final int itemId = args.getInt("ITEM_ID");
			final String bgId = args.getString("BRIDGE_ID");
			final String bgCode = args.getString("ACROSS_NUM");
			
			// ���ò�����������
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb, rootView, bgCode, bgId, itemName, itemId); // ���ò�������ѡ��
				}				
			});
			voidsPits.setChecked(true); // ���á��������桱Ĭ��ѡ��
			
			db = new DbOperation(this.getActivity());
			cursor = db.queryData("*", "disease_wetjoint", "id=" + itemId + " and bg_id='" + bgId + "'" + " and parts_id='" + bgCode + "'"); // ����disease_wetjoint�����Ƿ��ж�Ӧ������
			
			// �������������Ӧ����
			if (cursor.moveToFirst()) {
				String rg_feature = cursor.getString(cursor.getColumnIndex("rg_feature"));
				String rg_fissure = cursor.getString(cursor.getColumnIndex("rg_fissure"));
				
				if ((fissure.getText().toString()).equals(rg_feature)) { // ������ѷ�
					fissure.setChecked(true);
					
					if ((fissure5.getText().toString()).equals(rg_fissure)) { // ��������ѷ�
						fissure5.setChecked(true);
						
						((EditText) rootView.findViewById(R.id.up1_ll1_dis_desc2)).setText(cursor.getString(cursor.getColumnIndex("l1_start")));
						((EditText) rootView.findViewById(R.id.up1_ll1_dis_desc3)).setText(cursor.getString(cursor.getColumnIndex("l1_end")));
						((EditText) rootView.findViewById(R.id.up1_ll1_dis_desc4)).setText(cursor.getString(cursor.getColumnIndex("l1_area")));
					}
					else { // �����ѷ�
						if ((fissure1.getText().toString()).equals(rg_fissure))
							fissure1.setChecked(true);
						if ((fissure2.getText().toString()).equals(rg_fissure))
							fissure2.setChecked(true);
						if ((fissure3.getText().toString()).equals(rg_fissure))
							fissure3.setChecked(true);
						if ((fissure4.getText().toString()).equals(rg_fissure))
							fissure4.setChecked(true);
						
						((EditText) rootView.findViewById(R.id.up1_ll2_dis_desc2)).setText(cursor.getString(cursor.getColumnIndex("l2_start")));
						((EditText) rootView.findViewById(R.id.up1_ll2_dis_desc3)).setText(cursor.getString(cursor.getColumnIndex("l2_length")));
						((EditText) rootView.findViewById(R.id.up1_ll2_dis_desc4)).setText(cursor.getString(cursor.getColumnIndex("l2_width")));
					}
				}
				else { // ���ѷ�����Ĳ���
					if ((voidsPits.getText().toString()).equals(rg_feature))
						voidsPits.setChecked(true);
					if ((peelingOffAngle.getText().toString()).equals(rg_feature))
						peelingOffAngle.setChecked(true);
					if ((cavitation.getText().toString()).equals(rg_feature))
						cavitation.setChecked(true);
					if ((otherDisease.getText().toString()).equals(rg_feature)) { // ��������
						otherDisease.setChecked(true);
						DbOperation.setSpinnerItemSelectedByValue(spOtherDisease, cursor.getString(cursor.getColumnIndex("sp_otherDisease")));						
					}
					((EditText) rootView.findViewById(R.id.up1_ll1_dis_desc2)).setText(cursor.getString(cursor.getColumnIndex("l1_start")));
					((EditText) rootView.findViewById(R.id.up1_ll1_dis_desc3)).setText(cursor.getString(cursor.getColumnIndex("l1_end")));
					((EditText) rootView.findViewById(R.id.up1_ll1_dis_desc4)).setText(cursor.getString(cursor.getColumnIndex("l1_area")));					
				}
				
				addContent.setText(cursor.getString(cursor.getColumnIndex("add_content")));
				
				uri = Uri.parse(cursor.getString(cursor.getColumnIndex("disease_image")));
				if (uri != null) {					
					String mFileName = EditDiseaseActivity.getPath(this.getActivity().getApplicationContext(), uri); // ��ȡͼƬ����·��
					Bitmap bitmap = EditDiseaseActivity.getBitmap(mFileName); // ���ݾ���·���ҵ�ͼƬ
					ivImage.setImageBitmap(bitmap); // ����ͼƬ
				}
			}
		}		
		return rootView;		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.getActivity();		
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) { // ����ͼƬ��
			if (resultCode == FragmentActivity.RESULT_OK) {
	            uri = data.getData();
	            if (uri != null) {
		            ContentResolver cr = this.getActivity().getContentResolver();
		            try {  
		                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
		                // ��Bitmap�趨��ImageView
		                ivImage.setImageBitmap(bitmap);
		            } catch (FileNotFoundException e) {
		            	Log.e("Exception", e.getMessage(),e);
		            }
	            }
	        }
		}
		else if (requestCode == CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE) { // ���������
			if (resultCode == FragmentActivity.RESULT_OK) {
				if (data != null) {
					uri = data.getData();
                    if (data.hasExtra("data")) {
                    	Bitmap bitmap = data.getParcelableExtra("data");
						// ��Bitmap�趨��ImageView
						ivImage.setImageBitmap(bitmap);
                    }
                }
			}			
		}		
        super.onActivityResult(requestCode, resultCode, data);  
    }
	
	/** ���ò�������ѡ��
	 * rb: ����ѡ��button
	 * rootView: ҳ��view
	 * bgCode: �����������
	 * bgId:����id
	 * itemName:ѡ��������
	 * itemId:ѡ����id
	 * */
	private void setDiseaseFeature(RadioButton rb, View rootView, String bgCode, String bgId, String itemName, int itemId) {
		if ("�ѷ�".equals(rb.getText())) {
			if (spOtherDisease.getVisibility() != View.GONE)			
				spOtherDisease.setVisibility(View.GONE); // ���ء����������������б�
			if (rgFissure.getVisibility() != View.VISIBLE)
				rgFissure.setVisibility(View.VISIBLE); // ����ѷ쵥ѡ�򲻿ɼ�����Ϊ�ɼ�
			rgFissure.clearCheck(); // ����ѷ쵥ѡ��ѡ��
			
			//�����ѷ쵥ѡ��������
			rgFissure.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (checkedId == fissure5.getId()) {
						// ��ʾλ����Ϣ1
						local1.setVisibility(View.VISIBLE);
						local2.setVisibility(View.GONE);
					}
					else {
						// ��ʾλ����Ϣ2
						local1.setVisibility(View.GONE);
						local2.setVisibility(View.VISIBLE);
					}
				}								
			});
			fissure1.setChecked(true); // ���á������ѷ족Ĭ��ѡ��
		}
		else {			
			if (local1.getVisibility() != View.VISIBLE)
				local1.setVisibility(View.VISIBLE); // ���λ����Ϣ1���ɼ�����Ϊ�ɼ�
			if (local2.getVisibility() != View.GONE)
				local2.setVisibility(View.GONE); // ���λ����Ϣ2�ɼ�����Ϊ���ɼ�
			if (rgFissure.getVisibility() != View.GONE)
				rgFissure.setVisibility(View.GONE); // ����ѷ쵥ѡ��ɼ�����Ϊ���ɼ�
			
			if ("��������".equals(rb.getText())) {
				spOtherDisease.setVisibility(View.VISIBLE); // ��ʾ�����������������б�
				local1.setVisibility(View.GONE);
				local2.setVisibility(View.GONE);
			}
			else {
				spOtherDisease.setVisibility(View.GONE); // ���ء����������������б�
				local1.setVisibility(View.VISIBLE);
				local2.setVisibility(View.GONE);
			}
		}
		
		// ѡ�񲡺�ͼƬ����
		btnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                // ����Pictures����Type�趨Ϊimage
                intent.setType("image/*");
                // ʹ��Intent.ACTION_GET_CONTENT / ACTION_OPEN_DOCUMENT���Action
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                // ȡ����Ƭ�󷵻ر�����
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}			
		});
		
		// �������
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// ���պ󷵻ر�����
				startActivityForResult(intent, CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE);
			}
		});
		
		final View rv = rootView;
		final int item_id = itemId;
		final String parts_id = bgCode;
		final String bg_id = bgId;
		final String item_name = itemName;
		
		// �ύ��ť����
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				RadioButton rbFeature = (RadioButton) rv.findViewById(rgFeature.getCheckedRadioButtonId());
				
				String rg_feature = rbFeature.getText().toString(); // ��������
				String rg_fissure = "0"; // �ѷ�
				String sp_otherDisease = "0"; // ��������-������Ϣ
				String l1_start = "0"; // l1��ʼλ�� 
				String l1_end = "0"; // l1����λ��
				String l1_area = "0"; // l1���
				String l2_start = "0"; // l2��ʼλ��
				String l2_length = "0"; // l2����
				String l2_width = "0";	// l2���
				String add_content = addContent.getText().toString(); // ��������
				String disease_image = null; // ����ͼƬ��ַ
				if (uri != null)
					disease_image = uri.toString();
				
				if ("�ѷ�".equals(rbFeature.getText())) {
					RadioButton rbFissure = (RadioButton) rv.findViewById(rgFissure.getCheckedRadioButtonId());
					rg_fissure = rbFissure.getText().toString(); // �ѷ�
					
					if ("���ѷ�".equals(rbFissure.getText())) {
						l1_start = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc2)).getText().toString();
						l1_end = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc3)).getText().toString();
						l1_area = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc4)).getText().toString();
					}
					else {
						l2_start = ((EditText) rv.findViewById(R.id.up1_ll2_dis_desc2)).getText().toString();
						l2_length = ((EditText) rv.findViewById(R.id.up1_ll2_dis_desc3)).getText().toString();
						l2_width = ((EditText) rv.findViewById(R.id.up1_ll2_dis_desc4)).getText().toString();						
					}
				}
				else {
					if ("��������".equals(rbFeature.getText()))
						sp_otherDisease = spOtherDisease.getSelectedItem().toString(); // ��������-������Ϣ
					
					l1_start = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc2)).getText().toString();
					l1_end = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc3)).getText().toString();
					l1_area = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc4)).getText().toString();
				}
				
				String sql = "item_name='" + item_name + "',rg_feature='" + rg_feature + "',rg_fissure='" + rg_fissure + "',sp_otherDisease='" + sp_otherDisease
						+ "',l1_start='" + l1_start + "',l1_end='" + l1_end + "',l1_area='" + l1_area + "',l2_start='" + l2_start
						+ "',l2_length='" + l2_length + "',l2_width='" + l2_width + "',add_content='" + add_content
						+ "',disease_image='" + disease_image + "',flag='2'";
				
				int flag = db.updateData("disease_wetjoint", sql, "id=" + item_id + " and bg_id='" + bg_id + "'" + " and parts_id='" + parts_id + "'");
				
				if (flag == 0)
        			Toast.makeText(getActivity(), "�޸�ʧ��", Toast.LENGTH_SHORT).show();
    			else
    				Toast.makeText(getActivity(), "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
				
				getActivity().finish();
			}			
		});		
	}
}
