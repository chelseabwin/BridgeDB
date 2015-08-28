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
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // 病害名称
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			diseaseDescription.setText("病害描述：支座(" + args.getString("SUPPORT") + ")");
			
//			// 设置病害特征监听
//			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//				@Override
//				public void onCheckedChanged(RadioGroup group, int checkedId) {
//					// TODO Auto-generated method stub					
//					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
//					setDiseaseFeature(rb); // 设置病害特征选择
//				}				
//			});
//			voidsPits.setChecked(true); // 设置“蜂窝麻面”默认选中
		}		
		return rootView;		
	}
}
