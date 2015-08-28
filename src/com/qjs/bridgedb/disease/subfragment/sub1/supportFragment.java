package com.qjs.bridgedb.disease.subfragment.sub1;

import com.qjs.bridgedb.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class supportFragment extends Fragment {
	private TextView diseaseDescription;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_upper_page3, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // ��������
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			diseaseDescription.setText("����������֧��(" + args.getString("SUPPORT") + ")");
			
//			// ���ò�����������
//			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//				@Override
//				public void onCheckedChanged(RadioGroup group, int checkedId) {
//					// TODO Auto-generated method stub					
//					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
//					setDiseaseFeature(rb); // ���ò�������ѡ��
//				}				
//			});
//			voidsPits.setChecked(true); // ���á��������桱Ĭ��ѡ��
		}		
		return rootView;		
	}
}
