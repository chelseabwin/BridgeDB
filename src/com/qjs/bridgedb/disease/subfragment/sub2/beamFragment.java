package com.qjs.bridgedb.disease.subfragment.sub2;

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

public class beamFragment extends Fragment {
	private RadioGroup rgFeature;
	private RadioButton voidsPits;
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
		final View rootView = inflater.inflate(R.layout.fragment_down_page2, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // ��������
		
		rgFeature = (RadioGroup) rootView.findViewById(R.id.rg); // ����������ѡ��
		
		voidsPits = (RadioButton) rootView.findViewById(R.id.rbtn_voids_pits); // ��������
		
		addContent = (EditText) rootView.findViewById(R.id.tv_down1_add_content); // ��������
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // ����ͼƬ��ť
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // ����ͼƬ
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_down1_submit); // �ύ��ť
		
		db = new DbOperation(this.getActivity());
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			if (args.getString("BENTCAP") != null) {
				optionStr = "BENTCAP";
				diseaseDescription.setText("����������" + args.getString("ITEM_NAME") + "(" + args.getString("BENTCAP") + ")");
				tableName = "disease_bentcap";
			}
			else if (args.getString("TIEBEAM") != null) {
				optionStr = "TIEBEAM";
				diseaseDescription.setText("����������" + args.getString("ITEM_NAME") + "(" + args.getString("TIEBEAM") + ")");
				tableName = "disease_tiebeam";
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
			voidsPits.setChecked(true); // ���á��������桱Ĭ��ѡ��			
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
				Fragment frag = new beamFragment();
				
				// ��Fragment�������
				frag.setArguments(bd);
				// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
				getFragmentManager().beginTransaction()
					.replace(R.id.down_detail_container, frag)
					.commit();
			}			
		});		
	}
}
