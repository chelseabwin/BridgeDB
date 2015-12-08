package com.qjs.bridgedb.disease.subfragment.sub3;

import java.io.FileNotFoundException;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.DiseaseDetailFragment;

import android.content.ContentResolver;
import android.content.Intent;
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

public class sub3OtherFragment extends Fragment {
	private RadioGroup rgFeature,rg1,rg2;
	private RadioButton rg1Breakage;
	private RadioButton rg2CrackUp;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // ���汾��ͼƬ��ַ
	private DbOperation db = null;
	private String optionStr = null; // ��ǩ��
	private String tableName = null; // ����ѯ����
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_deck_page2, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // ��������		

		rg1 = (RadioGroup) rootView.findViewById(R.id.rg); // ����������ѡ��-���е�
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2); // ����������ѡ��-���ˡ�����
		
		addContent = (EditText) rootView.findViewById(R.id.tv_deck1_add_content); // ��������
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // ����ͼƬ��ť
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // ����ͼƬ
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_deck1_submit); // �ύ��ť
		
		db = new DbOperation(this.getActivity());
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			if (args.getString("SIDEWALK") != null) {
				rgFeature = rg1;
				rg1.setVisibility(View.VISIBLE);
				rg2.setVisibility(View.GONE);
				
				rg1Breakage = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_breakage); // ����
				
				optionStr = "SIDEWALK";
				diseaseDescription.setText("����������" + args.getString("ITEM_NAME") + "(" + args.getString("SIDEWALK") + ")");
				tableName = "disease_sidewalk";
			}
			else if (args.getString("FENCE") != null) {
				rgFeature = rg2;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.VISIBLE);
				
				rg2CrackUp = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_crack_up); // ײ����ȱʧ
				
				optionStr = "FENCE";
				diseaseDescription.setText("����������" + args.getString("ITEM_NAME") + "(" + args.getString("FENCE") + ")");
				tableName = "disease_fence";
			}
			
			final String itemName = args.getString("ITEM_NAME");
			final String bgCode = args.getString(optionStr);
			final String bgId = args.getString("BRIDGE_ID");
			
			// ���ò�����������
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb, rootView, bgCode, bgId, itemName); // ���ò�������ѡ��
				}				
			});
			
			if (optionStr == "SIDEWALK")
				rg1Breakage.setChecked(true); // ���á�����Ĭ��ѡ��
			else if (optionStr == "FENCE")
				rg2CrackUp.setChecked(true); // ���á�ײ����ȱʧ��Ĭ��ѡ��
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
	 * itemName:ѡ��������
	 * */
	private void setDiseaseFeature(RadioButton rb, View rootView, String bgCode, String bgId, String itemName) {
		// ѡ�񲡺�ͼƬ����
		btnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                // ����Pictures����Type�趨Ϊimage
                intent.setType("image/*");
                // ʹ��Intent.ACTION_GET_CONTENT���Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // ȡ����Ƭ�󷵻ر�����
                new DiseaseDetailFragment().getRootFragment().startActivityForResult(intent, 1);
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
				
				String rg_feature = rbFeature.getText().toString(); // ��������
				String add_content = addContent.getText().toString(); // ��������
				String disease_image = null; // ����ͼƬ��ַ
				if (uri != null)
					disease_image = uri.toString();
				
				String key = "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag";
				String values = "'" + bg_id + "','" + parts_id + "','" + item_name + "','" + rg_feature + "','" + add_content + "','" + disease_image + "','0'";
    			
    			int flag = db.insertData(tableName, key, values);
    			
    			if (flag == 0)
        			Toast.makeText(getActivity(), "���ʧ��", Toast.LENGTH_SHORT).show();
    			else
    				Toast.makeText(getActivity(), "��ӳɹ�", Toast.LENGTH_SHORT).show();
				
				// ˢ��ҳ��
				Bundle bd = new Bundle();
				bd.putString(optionStr, parts_id);
				bd.putString("BRIDGE_ID", bg_id);
				bd.putString("ITEM_NAME", item_name);
				
				// ����Fragment����
				Fragment frag = new sub3OtherFragment();
				
				// ��Fragment�������
				frag.setArguments(bd);
				// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
				getFragmentManager().beginTransaction()
					.replace(R.id.deck_detail_container, frag)
					.commit();
			}			
		});		
	}
}
