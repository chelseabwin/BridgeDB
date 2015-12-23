package com.qjs.bridgedb.disease.subfragment.sub2;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class abutmentEditFragment extends Fragment {
	private RadioGroup rgFeature,rg1,rg2,rg3;
	private Spinner spOtherDisease;
	private RadioButton rg1PeelOff,rg1Abrasion,rg1Fissure,rg1MasonryDefects,rg1VehicleJump,rg1Drainage,rg1OtherDisease;
	private RadioButton rg2Breakage,rg2Carbonation,rg2Fissure,rg2Cavitation;
	private RadioButton rg3Scouring,rg3LeakageTendon,rg3Washout,rg3BottomPaving,rg3Sedimentation,rg3SlipTilt,rg3Fissure;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnCamera,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // ���汾��ͼƬ��ַ
	private DbOperation db = null;
	private Cursor cursor = null;
	private String optionStr = null; // ��ǩ��
	private String tableName = null; // ����ѯ����
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100; // ͼƬ�ⷴ����
    private static final int CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE = 200; // �����������
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_down_page3, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // ��������		

		rg1 = (RadioGroup) rootView.findViewById(R.id.rg); // ����������ѡ��-��̨��
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2); // ����������ѡ��-��̨ñ
		rg3 = (RadioGroup) rootView.findViewById(R.id.rg3); // ����������ѡ��-��̨����
		
		addContent = (EditText) rootView.findViewById(R.id.tv_add_content); // ��������
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // ����ͼƬ��ť
		btnCamera = (Button) rootView.findViewById(R.id.btn_camera); // ���������ť
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // ����ͼƬ
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_submit); // �ύ��ť
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			diseaseDescription.setText("����������" + args.getString("ITEM_NAME") + "(" + args.getString("ACROSS_NUM") + ")");
			if (args.getString("ITEM_NAME").equals("��̨��")) {
				rgFeature = rg1;
				rg1.setVisibility(View.VISIBLE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				
				rg1PeelOff = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_peel_off); // ����
				rg1Abrasion = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_abrasion); // ĥ��
				rg1Fissure = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_fissure); // �ѷ�
				rg1MasonryDefects = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_masonry_defects); // �ع�����ȱ��
				rg1VehicleJump = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_vehicle_jump); // ��̨����
				rg1Drainage = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_drainage); // ̨����ˮ
				rg1OtherDisease = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_other_disease); // ��������
				spOtherDisease = (Spinner) rootView.findViewById(R.id.rg1_sp_other_disease); // �������������б�
				
				optionStr = "ATBODY";
				tableName = "disease_atbody";
			}
			else if (args.getString("ITEM_NAME").equals("��̨ñ")) {
				rgFeature = rg2;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.VISIBLE);
				rg3.setVisibility(View.GONE);
				
				rg2Breakage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_breakage); // ����
				rg2Carbonation = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_carbonation); // ������̼������ʴ
				rg2Fissure = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_fissure); // �ѷ�
				rg2Cavitation = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_cavitation); // �ն����׶�
				
				optionStr = "ATCAPPING";
				tableName = "disease_atcapping";
			}
			else {
				rgFeature = rg3;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.VISIBLE);
				
				rg3Scouring = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_scouring); // ��ˢ���Կ�
				rg3LeakageTendon = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_leakage_tendon); // ���䡢¶��
				rg3Washout = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_washout); // ��ʴ
				rg3BottomPaving = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_bottom_paving); // �ӵ�����
				rg3Sedimentation = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_sedimentation); // ����
				rg3SlipTilt = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_slip_tilt); // ���ƺ���б
				rg3Fissure = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_fissure); // �ѷ�
				
				optionStr = "PA";
				tableName = "disease_pa";
			}
			
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
			
			if (optionStr == "ATBODY")
				rg1PeelOff.setChecked(true); // ���á����䡱Ĭ��ѡ��
			else if (optionStr == "ATCAPPING")
				rg2Breakage.setChecked(true); // ���á�����Ĭ��ѡ��
			else if (optionStr == "PA")
				rg3Scouring.setChecked(true); // ���á���ˢ���Կա�Ĭ��ѡ��
			
			db = new DbOperation(this.getActivity());
			cursor = db.queryData("*", tableName, "id=" + itemId + " and bg_id='" + bgId + "'" + " and parts_id='" + bgCode + "'"); // ���ұ����Ƿ��ж�Ӧ������
			
			
			// �������������Ӧ����
			if (cursor.moveToFirst()) {
				String rg_feature = cursor.getString(cursor.getColumnIndex("rg_feature"));

				if (optionStr == "ATBODY") {
					if ((rg1PeelOff.getText().toString()).equals(rg_feature))
						rg1PeelOff.setChecked(true);
					if ((rg1Abrasion.getText().toString()).equals(rg_feature))
						rg1Abrasion.setChecked(true);
					if ((rg1Fissure.getText().toString()).equals(rg_feature))
						rg1Fissure.setChecked(true);
					if ((rg1MasonryDefects.getText().toString()).equals(rg_feature))
						rg1MasonryDefects.setChecked(true);
					if ((rg1VehicleJump.getText().toString()).equals(rg_feature))
						rg1VehicleJump.setChecked(true);
					if ((rg1Drainage.getText().toString()).equals(rg_feature))
						rg1Drainage.setChecked(true);
					if ((rg1OtherDisease.getText().toString()).equals(rg_feature)) { // ��������
						rg1OtherDisease.setChecked(true);
						DbOperation.setSpinnerItemSelectedByValue(spOtherDisease, cursor.getString(cursor.getColumnIndex("sp_otherDisease")));						
					}					
				}
				else if (optionStr == "ATCAPPING") {
					if ((rg2Breakage.getText().toString()).equals(rg_feature))
						rg2Breakage.setChecked(true);
					if ((rg2Carbonation.getText().toString()).equals(rg_feature))
						rg2Carbonation.setChecked(true);
					if ((rg2Fissure.getText().toString()).equals(rg_feature))
						rg2Fissure.setChecked(true);
					if ((rg2Cavitation.getText().toString()).equals(rg_feature))
						rg2Cavitation.setChecked(true);					
				}
				else if (optionStr == "PA") {
					if ((rg3Scouring.getText().toString()).equals(rg_feature))
						rg3Scouring.setChecked(true);
					if ((rg3LeakageTendon.getText().toString()).equals(rg_feature))
						rg3LeakageTendon.setChecked(true);
					if ((rg3Washout.getText().toString()).equals(rg_feature))
						rg3Washout.setChecked(true);
					if ((rg3BottomPaving.getText().toString()).equals(rg_feature))
						rg3BottomPaving.setChecked(true);
					if ((rg3Sedimentation.getText().toString()).equals(rg_feature))
						rg3Sedimentation.setChecked(true);
					if ((rg3SlipTilt.getText().toString()).equals(rg_feature))
						rg3SlipTilt.setChecked(true);
					if ((rg3Fissure.getText().toString()).equals(rg_feature))
						rg3Fissure.setChecked(true);
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
		if (tableName == "disease_atbody") {
			if ("��������".equals(rb.getText()))
				spOtherDisease.setVisibility(View.VISIBLE); // ��ʾ�����������������б�
			else
				spOtherDisease.setVisibility(View.GONE); // ���ء����������������б�			
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
				String sp_otherDisease = "0"; // ��������-������Ϣ
				String add_content = addContent.getText().toString(); // ��������
				String disease_image = null; // ����ͼƬ��ַ
				if (uri != null)
					disease_image = uri.toString();
				if ("��������".equals(rbFeature.getText()))
					sp_otherDisease = spOtherDisease.getSelectedItem().toString(); // ��������-������Ϣ
				
				String sql = null;
				if (tableName == "disease_atbody")
					sql = "item_name='" + item_name + "',rg_feature='" + rg_feature + "',sp_otherDisease='" + sp_otherDisease + "',add_content='" + add_content + "',disease_image='" + disease_image + "',flag='2'";
				else
					sql = "item_name='" + item_name + "',rg_feature='" + rg_feature + "',add_content='" + add_content + "',disease_image='" + disease_image + "',flag='2'";
				
				int flag = db.updateData(tableName, sql, "id=" + item_id + " and bg_id='" + bg_id + "'" + " and parts_id='" + parts_id + "'");
				
				if (flag == 0)
        			Toast.makeText(getActivity(), "�޸�ʧ��", Toast.LENGTH_SHORT).show();
    			else
    				Toast.makeText(getActivity(), "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
				
				getActivity().finish();
			}			
		});		
	}
}
