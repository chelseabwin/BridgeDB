package com.qjs.bridgedb.disease.subfragment.sub2;

import java.io.FileNotFoundException;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class otherFragment extends Fragment {
	private RadioGroup rgFeature,rg1,rg2,rg3,rg4;
	private RadioButton rg1Jam,rg1Washing,rg1Riverbed;
	private RadioButton rg2Damage,rg2Transformation;
	private RadioButton rg3Breakage,rg3Offset,rg3Bulging,rg3Fissure;
	private RadioButton rg4Defect,rg4Washing;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // ���汾��ͼƬ��ַ
	private DbOperation db = null;
	private Cursor cursor = null;
	private String optionStr = null; // ��ǩ��
	private String tableName = null; // ����ѯ����
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_down_page4, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // ��������		

		rg1 = (RadioGroup) rootView.findViewById(R.id.rg); // ����������ѡ��-�Ӵ�
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2); // ����������ѡ��-���ι�����
		rg3 = (RadioGroup) rootView.findViewById(R.id.rg3); // ����������ѡ��-��ǽ����ǽ
		rg4 = (RadioGroup) rootView.findViewById(R.id.rg4); // ����������ѡ��-׶�¡�����
		
		addContent = (EditText) rootView.findViewById(R.id.tv_down1_add_content); // ��������
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // ����ͼƬ��ť
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // ����ͼƬ
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_down1_submit); // �ύ��ť
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			if (args.getString("BED") != null) {
				rgFeature = rg1;
				rg1.setVisibility(View.VISIBLE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg1Jam = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_jam); // ����
				rg1Washing = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_washing); // ��ˢ
				rg1Riverbed = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_riverbed); // �Ӵ���Ǩ
				
				optionStr = "BED";
				diseaseDescription.setText("�����������Ӵ�(" + args.getString("BED") + ")");
				tableName = "disease_bed";
			}
			else if (args.getString("REGSTRUC") != null) {
				rgFeature = rg2;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.VISIBLE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg2Damage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_damage); // ��
				rg2Transformation = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_transformation); // ��ˢ������
				
				optionStr = "REGSTRUC";
				diseaseDescription.setText("�������������ι�����(" + args.getString("REGSTRUC") + ")");
				tableName = "disease_regstruc";
			}
			else if (args.getString("WINGWALL") != null) {
				rgFeature = rg3;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.VISIBLE);
				rg4.setVisibility(View.GONE);
				
				rg3Breakage = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_breakage); // ����
				rg3Offset = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_offset); // λ��
				rg3Bulging = (RadioButton) rootView.findViewById(R.id.rg3_bulging); // �Ķǡ������ɶ�
				rg3Fissure = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_fissure); // �ѷ�
				
				optionStr = "WINGWALL";
				diseaseDescription.setText("������������ǽ����ǽ(" + args.getString("WINGWALL") + ")");
				tableName = "disease_wingwall";
			}
			else {
				rgFeature = rg4;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.VISIBLE);
				
				rg4Defect = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_defect); // ȱ��
				rg4Washing = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_washing); // ��ˢ
				
				if (args.getString("CONSLOPE") != null) {
					optionStr = "CONSLOPE";
					diseaseDescription.setText("����������׶��(" + args.getString("CONSLOPE") + ")");
					tableName = "disease_conslope";
				}
				else if (args.getString("PROSLOPE") != null) {
					optionStr = "PROSLOPE";
					diseaseDescription.setText("��������������(" + args.getString("PROSLOPE") + ")");
					tableName = "disease_proslope";
				}				
			}
			
			final String bgCode = args.getString(optionStr);
			final int bgId = args.getInt("BRIDGE_ID");
			
			// ���ò�����������
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub					
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb,rootView,bgCode,bgId); // ���ò�������ѡ��
				}				
			});
			
			if (optionStr == "BED")
				rg1Jam.setChecked(true); // ���á�������Ĭ��ѡ��
			else if (optionStr == "REGSTRUC")
				rg2Damage.setChecked(true); // ���á��𻵡�Ĭ��ѡ��
			else if (optionStr == "WINGWALL")
				rg3Breakage.setChecked(true); // ���á�����Ĭ��ѡ��
			else if (optionStr == "CONSLOPE" || optionStr == "PROSLOPE")
				rg4Defect.setChecked(true); // ���á�ȱ�ݡ�Ĭ��ѡ��
			
			db = new DbOperation(this.getActivity());
			cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'" + " and parts_id='" + bgCode + "'"); // ���ұ����Ƿ��ж�Ӧ������
			
			
			// �������������Ӧ����
			if (cursor.moveToFirst()) {
				String rg_feature = cursor.getString(cursor.getColumnIndex("rg_feature"));

				if (optionStr == "BED") {
					if ((rg1Jam.getText().toString()).equals(rg_feature))
						rg1Jam.setChecked(true);
					if ((rg1Washing.getText().toString()).equals(rg_feature))
						rg1Washing.setChecked(true);
					if ((rg1Riverbed.getText().toString()).equals(rg_feature))
						rg1Riverbed.setChecked(true);
				}
				else if (optionStr == "REGSTRUC") {
					if ((rg2Damage.getText().toString()).equals(rg_feature))
						rg2Damage.setChecked(true);
					if ((rg2Transformation.getText().toString()).equals(rg_feature))
						rg2Transformation.setChecked(true);
				}
				else if (optionStr == "WINGWALL") {
					if ((rg3Breakage.getText().toString()).equals(rg_feature))
						rg3Breakage.setChecked(true);
					if ((rg3Offset.getText().toString()).equals(rg_feature))
						rg3Offset.setChecked(true);
					if ((rg3Bulging.getText().toString()).equals(rg_feature))
						rg3Bulging.setChecked(true);
					if ((rg3Fissure.getText().toString()).equals(rg_feature))
						rg3Fissure.setChecked(true);
				}
				else if (optionStr == "CONSLOPE" || optionStr == "PROSLOPE") {
					if ((rg4Defect.getText().toString()).equals(rg_feature))
						rg4Defect.setChecked(true);
					if ((rg4Washing.getText().toString()).equals(rg_feature))
						rg4Washing.setChecked(true);
				}
				
				addContent.setText(cursor.getString(cursor.getColumnIndex("add_content")));
				
				uri = Uri.parse(cursor.getString(cursor.getColumnIndex("disease_image")));
				if (uri != null) {
					try {  
		                Bitmap bitmap = BitmapFactory.decodeStream(this.getActivity().getContentResolver().openInputStream(uri));  
		                // ��Bitmap�趨��ImageView
		                ivImage.setImageBitmap(bitmap); // ����ͼƬ
		            } catch (FileNotFoundException e) {}
				}
			}
		}		
		return rootView;		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.getActivity();
		if (resultCode == FragmentActivity.RESULT_OK) {
            uri = data.getData();
            if (uri != null) {
	            ContentResolver cr = this.getActivity().getContentResolver();
	            try {  
	                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
	                // ��Bitmap�趨��ImageView
	                ivImage.setImageBitmap(bitmap);
	            } catch (FileNotFoundException e) {}
            }
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }
	
	/** ���ò�������ѡ��
	 * rb: ����ѡ��button
	 * rootView: ҳ��view
	 * bgCode: �����������
	 * bgId:����id
	 * */
	private void setDiseaseFeature(RadioButton rb, View rootView, String bgCode, int bgId) {
		// ѡ�񲡺�ͼƬ����
		btnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                // ����Pictures����Type�趨Ϊimage
                intent.setType("image/*");
                // ʹ��Intent.ACTION_GET_CONTENT���Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // ȡ����Ƭ�󷵻ر�����
                getRootFragment().startActivityForResult(intent, 1);
			}			
		});
		
		final View rv = rootView;
		final String parts_id = bgCode;
		final int bg_id = bgId;
		
		// �ύ��ť����
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RadioButton rbFeature = (RadioButton) rv.findViewById(rgFeature.getCheckedRadioButtonId());
				
				String rg_feature = rbFeature.getText().toString(); // ��������
				String add_content = addContent.getText().toString(); // ��������
				String disease_image = null; // ����ͼƬ��ַ
				if (uri != null)
					disease_image = uri.toString();
				
				int flag1 = 0;
				int flag2 = 0;
				
				if (cursor.moveToFirst()) { // ��������޸�
					String sql = "rg_feature='" + rg_feature + "',add_content='" + add_content + "',disease_image='" + disease_image + "',flag='0'";
    				
    				flag1 = db.updateData(tableName, sql, "bg_id='" + bg_id + "'" + " and parts_id='" + parts_id + "'");
    				
    				if (flag1 == 0)
            			Toast.makeText(getActivity(), "�޸�ʧ��", Toast.LENGTH_SHORT).show();
        			else
        				Toast.makeText(getActivity(), "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
				}
				else { // û�������
					String key = "bg_id, parts_id, rg_feature, add_content, disease_image, flag";
					String values = "'" + bg_id + "','" + parts_id + "','" + rg_feature + "','" + add_content + "','" + disease_image + "','0'";
        			
        			flag2 = db.insertData(tableName, key, values);
        			
        			if (flag2 == 0)
            			Toast.makeText(getActivity(), "���ʧ��", Toast.LENGTH_SHORT).show();
        			else
        				Toast.makeText(getActivity(), "��ӳɹ�", Toast.LENGTH_SHORT).show();
				}
				cursor.close();
				
				// ˢ��ҳ��
				Bundle bd = new Bundle();
				bd.putString(optionStr, parts_id);
				bd.putInt("BRIDGE_ID", bg_id);
				
				// ����Fragment����
				Fragment frag = new otherFragment();
				
				// ��Fragment�������
				frag.setArguments(bd);
				// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
				getFragmentManager().beginTransaction()
					.replace(R.id.down_detail_container, frag)
					.commit();
			}			
		});		
	}
	
	/**
	 * �õ���Fragment
	 * 
	 * @return
	 */	 
	private Fragment getRootFragment() {
		Fragment fragment = getParentFragment();	  
		while (fragment.getParentFragment() != null) {
			fragment = fragment.getParentFragment();
		}	  
		return fragment;
	}
}
