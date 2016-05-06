package com.qjs.bridgedb.disease.subfragment.sub1;

import java.io.FileNotFoundException;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;

import android.content.ContentResolver;
import android.content.Intent;
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

public class girderFragment extends Fragment {
	private RadioGroup rgFeature,rgFissure,rgLocation;
	private Spinner spOtherDisease;
	private LinearLayout local1,local2,local3;
	private RadioButton voidsPits;
	private RadioButton fissure1,fissure5,location1,location2;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnCamera,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // ���汾��ͼƬ��ַ
	private DbOperation db = null;
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100; // ͼƬ�ⷴ����
    private static final int CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE = 200; // �����������
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_upper_page1, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // ��������
		
		rgFeature = (RadioGroup) rootView.findViewById(R.id.rg); // ����������ѡ��
		rgFissure = (RadioGroup) rootView.findViewById(R.id.rg2); // �ѷ쵥ѡ��
		rgLocation = (RadioGroup) rootView.findViewById(R.id.rg3); // λ�õ�ѡ��
		
		spOtherDisease = (Spinner) rootView.findViewById(R.id.sp_other_disease); // �������������б�
		
		local1 = (LinearLayout) rootView.findViewById(R.id.up1_ll_1); // λ����Ϣ�������ѷ�������ѷ죩
		local2 = (LinearLayout) rootView.findViewById(R.id.up1_ll_2); // �����ѷ�λ����Ϣ
		local3 = (LinearLayout) rootView.findViewById(R.id.up1_ll_3); // �����ѷ�
		
		voidsPits = (RadioButton) rootView.findViewById(R.id.rbtn_voids_pits); // ��������
		
		fissure1 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure1); // �����ѷ�
		fissure5 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure5); // ���ѷ�
		
		location1 = (RadioButton) rootView.findViewById(R.id.rbtn_location1); // �����
		location2 = (RadioButton) rootView.findViewById(R.id.rbtn_location2); // �����
		
		addContent = (EditText) rootView.findViewById(R.id.tv_add_content); // ��������
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // ����ͼƬ��ť
		btnCamera = (Button) rootView.findViewById(R.id.btn_camera); // ���������ť
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // ����ͼƬ
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_submit); // �ύ��ť
		
		db = new DbOperation(this.getActivity());
		
		Bundle args = getArguments();		
		if (args != null) {
			diseaseDescription.setTextSize(25);
			diseaseDescription.setText("����������" + args.getString("ITEM_NAME") + "(" + args.getString("GIRDER") + ")");
			
			final String itemName = args.getString("ITEM_NAME");
			final String bgCode = args.getString("GIRDER");
			final String bgId = args.getString("BRIDGE_ID");
			
			// ���ò�����������
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb, rootView, bgCode, bgId, itemName); // ���ò�������ѡ��
				}				
			});
			voidsPits.setChecked(true); // ���á��������桱Ĭ��ѡ��		
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
	 * */
	private void setDiseaseFeature(RadioButton rb, View rootView, String bgCode, String bgId, String itemName) {
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
			
			rgLocation.setOnCheckedChangeListener(new OnCheckedChangeListener() { // �����ѷ����
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (checkedId == location1.getId() || checkedId == location2.getId()) {
						local3.setVisibility(View.VISIBLE);						
					}
					else {
						local3.setVisibility(View.GONE);
					}					
				}
			});
			location1.setChecked(true); // ���á�����塱Ĭ��ѡ��
		}
		else {
			if ("��������".equals(rb.getText()))
				spOtherDisease.setVisibility(View.VISIBLE); // ��ʾ�����������������б�
			else
				spOtherDisease.setVisibility(View.GONE); // ���ء����������������б�
			
			if (local1.getVisibility() != View.VISIBLE)
				local1.setVisibility(View.VISIBLE); // ���λ����Ϣ1���ɼ�����Ϊ�ɼ�
			if (local2.getVisibility() != View.GONE)
				local2.setVisibility(View.GONE); // ���λ����Ϣ2�ɼ�����Ϊ���ɼ�
			if (local3.getVisibility() != View.GONE)
				local3.setVisibility(View.GONE); // ���λ����Ϣ3�ɼ�����Ϊ���ɼ�
			if (rgFissure.getVisibility() != View.GONE)
				rgFissure.setVisibility(View.GONE); // ����ѷ쵥ѡ��ɼ�����Ϊ���ɼ�
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
                getRootFragment().startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}			
		});
		
		// �������
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// ���պ󷵻ر�����
				getRootFragment().startActivityForResult(intent, CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE);
			}
		});
		
		final View rv = rootView;
		final String parts_id = bgCode;
		final String bg_id = bgId;
		final String item_name = itemName;
		
		// �ύ��ť����
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				RadioButton rbFeature = (RadioButton) rv.findViewById(rgFeature.getCheckedRadioButtonId());
				RadioButton rbLocation = (RadioButton) rv.findViewById(rgLocation.getCheckedRadioButtonId());
				
				String rg_feature = rbFeature.getText().toString(); // ��������
				String rg_fissure = "0"; // �ѷ�
				String sp_otherDisease = "0"; // ��������-������Ϣ
				String start = "0"; // ��ʼλ�� 
				String end = "0"; // ����λ��
				String area = "0"; // ���
				String length = "0"; // ����
				String width = "0";	// ���
				String side_start = "0"; // ����ʼλ�� 
				String side_end = "0"; // �������λ��
				String side_length = "0"; // ���򳤶�
				String side_width = "0";	// ������
				String rg_location = rbLocation.getText().toString(); // λ��
				String add_content = addContent.getText().toString(); // ��������				
				String disease_image = null; // ����ͼƬ��ַ
				if (uri != null)
					disease_image = uri.toString();
				
				if ("�ѷ�".equals(rbFeature.getText())) {
					RadioButton rbFissure = (RadioButton) rv.findViewById(rgFissure.getCheckedRadioButtonId());
					rg_fissure = rbFissure.getText().toString(); // �ѷ�
					
					if ("���ѷ�".equals(rbFissure.getText())) {
						start = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc2)).getText().toString();
						end = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc3)).getText().toString();
						area = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc4)).getText().toString();
					}
					else {
						start = ((EditText) rv.findViewById(R.id.up1_ll2_dis_desc2)).getText().toString();
						end = ((EditText) rv.findViewById(R.id.up1_ll2_dis_desc3)).getText().toString();
						length = ((EditText) rv.findViewById(R.id.up1_ll2_dis_desc4)).getText().toString();
						width = ((EditText) rv.findViewById(R.id.up1_ll2_dis_desc5)).getText().toString();
					}
					
					if ("�����".equals(rbLocation.getText()) || "�����".equals(rbLocation.getText())) {
						local3.setVisibility(View.VISIBLE);
						side_start = ((EditText) rv.findViewById(R.id.up1_ll3_dis_desc2)).getText().toString();
						side_end = ((EditText) rv.findViewById(R.id.up1_ll3_dis_desc3)).getText().toString();
						side_length = ((EditText) rv.findViewById(R.id.up1_ll3_dis_desc4)).getText().toString();
						side_width = ((EditText) rv.findViewById(R.id.up1_ll3_dis_desc5)).getText().toString();
					}
				}
				else {
					if ("��������".equals(rbFeature.getText()))
						sp_otherDisease = spOtherDisease.getSelectedItem().toString(); // ��������-������Ϣ
					
					start = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc2)).getText().toString();
					end = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc3)).getText().toString();
					area = ((EditText) rv.findViewById(R.id.up1_ll1_dis_desc4)).getText().toString();
				}
				
				String key = "bg_id, parts_id, item_name, rg_feature, rg_fissure, sp_otherDisease, start, end, area, length, width,"
						+ "side_start, side_end, side_length, side_width, rg_location, add_content, disease_image, flag";
    			String values = "'" + bg_id + "','" + parts_id + "','" + item_name + "','" + rg_feature + "','" + rg_fissure + "','"
    					 + sp_otherDisease + "','" + start + "','" + end + "','" + area + "','" + length + "','" + width + "','"
    					 + side_start + "','" + side_end + "','" + side_length + "','" + side_width + "','" + rg_location + "','" 
    					 + add_content + "','" + disease_image + "','0'";
    			
    			int flag = db.insertData("disease_girder", key, values);
    			
    			if (flag == 0)
        			Toast.makeText(getActivity(), "���ʧ��", Toast.LENGTH_SHORT).show();
    			else
    				Toast.makeText(getActivity(), "��ӳɹ�", Toast.LENGTH_SHORT).show();
				
				// ˢ��ҳ��
				Bundle bd = new Bundle();
				bd.putString("GIRDER", parts_id);
				bd.putString("BRIDGE_ID", bg_id);
				bd.putString("ITEM_NAME", item_name);
				
				// ����Fragment����
				Fragment frag = new girderFragment();
				
				// ��Fragment�������
				frag.setArguments(bd);
				// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
				getFragmentManager().beginTransaction()
					.replace(R.id.upper_detail_container, frag)
					.commit();
			}			
		});
	}
	
	/**
	 * �õ���Fragment
	 * 
	 * @return
	 */	 
	public Fragment getRootFragment() {
		Fragment fragment = getParentFragment();
		while (fragment.getParentFragment() != null) {
			fragment = fragment.getParentFragment();
		}	  
		return fragment;
	}
}
