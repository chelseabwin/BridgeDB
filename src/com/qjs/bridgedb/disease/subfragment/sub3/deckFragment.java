package com.qjs.bridgedb.disease.subfragment.sub3;

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

public class deckFragment extends Fragment {
	private RadioGroup rgFeature,rg1,rg2,rg3,rg4;
	private RadioButton rg1Track,rg1Bleeding,rg1Breakage,rg1Fissure;
	private RadioButton rg2Irregularity,rg2Anchorage,rg2Breakage,rg2Failure;
	private RadioButton rg3Impeded,rg3Flaw;
	private RadioButton rg4Dirty,rg4LackOfLighting,rg4SignMissing;
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
		final View rootView = inflater.inflate(R.layout.fragment_deck_page1, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // ��������		

		rg1 = (RadioGroup) rootView.findViewById(R.id.rg); // ����������ѡ��-������װ
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2); // ����������ѡ��-������
		rg3 = (RadioGroup) rootView.findViewById(R.id.rg3); // ����������ѡ��-����ˮϵͳ
		rg4 = (RadioGroup) rootView.findViewById(R.id.rg4); // ����������ѡ��-��������־
		
		addContent = (EditText) rootView.findViewById(R.id.tv_deck1_add_content); // ��������
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // ����ͼƬ��ť
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // ����ͼƬ
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_deck1_submit); // �ύ��ť
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			if (args.getString("DECK") != null) {
				rgFeature = rg1;
				rg1.setVisibility(View.VISIBLE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg1Track = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_track); // ���Σ����ޡ�ӵ�����ߵͲ�ƽ�ȣ�
				rg1Bleeding = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_bleeding); // ����
				rg1Breakage = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_breakage); // ����
				rg1Fissure = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_fissure); // �ѷ�
				
				optionStr = "DECK";
				diseaseDescription.setText("����������������װ(" + args.getString("DECK") + ")");
				tableName = "disease_deck";
			}
			else if (args.getString("JOINT") != null) {
				rgFeature = rg2;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.VISIBLE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg2Irregularity = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_irregularity); // ��͹��ƽ
				rg2Anchorage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_anchorage); // ê����ȱ��
				rg2Breakage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_breakage); // ����
				rg2Failure = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_failure); // ʧЧ
				
				optionStr = "JOINT";
				diseaseDescription.setText("����������������װ��(" + args.getString("JOINT") + ")");
				tableName = "disease_joint";
			}
			else if (args.getString("WATERTIGHT") != null) {
				rgFeature = rg3;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.VISIBLE);
				rg4.setVisibility(View.GONE);
				
				rg3Impeded = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_impeded); // ��ˮ����
				rg3Flaw = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_flaw); // ��ˮ�ܡ���ˮ��ȱ��
				
				optionStr = "WATERTIGHT";
				diseaseDescription.setText("��������������ˮϵͳ(" + args.getString("WATERTIGHT") + ")");
				tableName = "disease_watertight";
			}
			else {
				rgFeature = rg4;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.VISIBLE);
				
				rg4Dirty = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_dirty); // ������ƻ�
				rg4LackOfLighting = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_lack_of_lighting); // ������ʩȱʧ
				rg4SignMissing = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_sign_missing); // ��־���䡢ȱʧ
				
				optionStr = "LIGHTING";
				diseaseDescription.setText("������������������־(" + args.getString("LIGHTING") + ")");
				tableName = "disease_lighting";
			}
			
			final String bgCode = args.getString(optionStr);
			final String bgId = args.getString("BRIDGE_ID");
			
			// ���ò�����������
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub					
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb,rootView,bgCode,bgId); // ���ò�������ѡ��
				}				
			});
			
			if (optionStr == "DECK")
				rg1Track.setChecked(true); // ���á����Σ����ޡ�ӵ�����ߵͲ�ƽ�ȣ���Ĭ��ѡ��
			else if (optionStr == "JOINT")
				rg2Irregularity.setChecked(true); // ���á���͹��ƽ��Ĭ��ѡ��
			else if (optionStr == "WATERTIGHT")
				rg3Impeded.setChecked(true); // ���á���ˮ������Ĭ��ѡ��
			else if (optionStr == "LIGHTING")
				rg4Dirty.setChecked(true); // ���á�������ƻ���Ĭ��ѡ��
			
			db = new DbOperation(this.getActivity());
			cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'" + " and parts_id='" + bgCode + "'"); // ���ұ����Ƿ��ж�Ӧ������
			
			
			// �������������Ӧ����
			if (cursor.moveToFirst()) {
				String rg_feature = cursor.getString(cursor.getColumnIndex("rg_feature"));

				if (optionStr == "DECK") {
					if ((rg1Track.getText().toString()).equals(rg_feature))
						rg1Track.setChecked(true);
					if ((rg1Bleeding.getText().toString()).equals(rg_feature))
						rg1Bleeding.setChecked(true);
					if ((rg1Breakage.getText().toString()).equals(rg_feature))
						rg1Breakage.setChecked(true);
					if ((rg1Fissure.getText().toString()).equals(rg_feature))
						rg1Fissure.setChecked(true);
				}
				else if (optionStr == "JOINT") {
					if ((rg2Irregularity.getText().toString()).equals(rg_feature))
						rg2Irregularity.setChecked(true);
					if ((rg2Anchorage.getText().toString()).equals(rg_feature))
						rg2Anchorage.setChecked(true);
					if ((rg2Breakage.getText().toString()).equals(rg_feature))
						rg2Breakage.setChecked(true);
					if ((rg2Failure.getText().toString()).equals(rg_feature))
						rg2Failure.setChecked(true);
				}
				else if (optionStr == "WATERTIGHT") {
					if ((rg3Impeded.getText().toString()).equals(rg_feature))
						rg3Impeded.setChecked(true);
					if ((rg3Flaw.getText().toString()).equals(rg_feature))
						rg3Flaw.setChecked(true);
				}
				else if (optionStr == "LIGHTING") {
					if ((rg4Dirty.getText().toString()).equals(rg_feature))
						rg4Dirty.setChecked(true);
					if ((rg4LackOfLighting.getText().toString()).equals(rg_feature))
						rg4LackOfLighting.setChecked(true);
					if ((rg4SignMissing.getText().toString()).equals(rg_feature))
						rg4SignMissing.setChecked(true);
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
	private void setDiseaseFeature(RadioButton rb, View rootView, String bgCode, String bgId) {
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
		final String bg_id = bgId;
		
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
				bd.putString("BRIDGE_ID", bg_id);
				
				// ����Fragment����
				Fragment frag = new deckFragment();
				
				// ��Fragment�������
				frag.setArguments(bd);
				// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
				getFragmentManager().beginTransaction()
					.replace(R.id.deck_detail_container, frag)
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
