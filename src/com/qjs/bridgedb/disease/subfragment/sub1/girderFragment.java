package com.qjs.bridgedb.disease.subfragment.sub1;

import java.io.FileNotFoundException;

import com.qjs.bridgedb.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class girderFragment extends Fragment {
	private RadioGroup rgFeature,rgFissure,rgLocation;
	private Spinner spOtherDisease;
	private LinearLayout local1,local2;
	private RadioButton voidsPits,fissure1,fissure5;
	private TextView diseaseDescription;
	private Button btnImage,btnSubmit;
	private ImageView ivImage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_upper_page1_2, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // 病害名称
		
		rgFeature = (RadioGroup) rootView.findViewById(R.id.rg); // 病害特征单选框
		rgFissure = (RadioGroup) rootView.findViewById(R.id.rg2); // 裂缝单选框
		rgLocation = (RadioGroup) rootView.findViewById(R.id.rg3); // 位置单选框
		
		spOtherDisease = (Spinner) rootView.findViewById(R.id.sp_other_disease); // 其他病害下拉列表
		
		local1 = (LinearLayout) rootView.findViewById(R.id.up1_ll_1); // 位置信息（含网裂缝除其他裂缝）
		local2 = (LinearLayout) rootView.findViewById(R.id.up1_ll_2); // 其他裂缝位置信息
		
		voidsPits = (RadioButton) rootView.findViewById(R.id.rbtn_voids_pits); // 蜂窝麻面
		fissure1 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure1); // 竖向裂缝
		fissure5 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure5); // 网裂缝
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // 病害图片按钮
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // 病害图片
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_up1_submit); // 提交按钮
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			diseaseDescription.setText("病害描述：主梁(" + args.getString("GIRDER") + ")");
			
			// 设置病害特征监听
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub					
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb,rootView); // 设置病害特征选择
				}				
			});
			voidsPits.setChecked(true); // 设置“蜂窝麻面”默认选中
		}		
		return rootView;		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.getActivity();
		if (resultCode == FragmentActivity.RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getActivity().getContentResolver();
            try {  
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
                // 将Bitmap设定到ImageView
                ivImage.setImageBitmap(bitmap);  
            } catch (FileNotFoundException e) {  
                Log.e("Exception", e.getMessage(),e);  
            }  
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }
	
	/** 设置病害特征选择
	 * rb: 病害选择button
	 * rootView: 页面view
	 * */
	private void setDiseaseFeature(RadioButton rb, View rootView) {
		if ("裂缝".equals(rb.getText())) {
			if (spOtherDisease.getVisibility() != View.GONE)			
				spOtherDisease.setVisibility(View.GONE); // 隐藏“其他病害”下拉列表
			if (rgFissure.getVisibility() != View.VISIBLE)
				rgFissure.setVisibility(View.VISIBLE); // 如果裂缝单选框不可见，设为可见
			rgFissure.clearCheck(); // 清空裂缝单选框选择
			
			//设置裂缝单选框点击监听
			rgFissure.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if (checkedId == fissure5.getId()) {
						// 显示位置信息1
						local1.setVisibility(View.VISIBLE);
						local2.setVisibility(View.GONE);
					}
					else {
						// 显示位置信息2
						local1.setVisibility(View.GONE);
						local2.setVisibility(View.VISIBLE);
					}
				}								
			});
			fissure1.setChecked(true); // 设置“竖向裂缝”默认选中
		}
		else {
			if ("其他病害".equals(rb.getText()))
				spOtherDisease.setVisibility(View.VISIBLE); // 显示“其他病害”下拉列表
			else
				spOtherDisease.setVisibility(View.GONE); // 隐藏“其他病害”下拉列表
			
			if (local1.getVisibility() != View.VISIBLE)
				local1.setVisibility(View.VISIBLE); // 如果位置信息1不可见，设为可见
			if (local2.getVisibility() != View.GONE)
				local2.setVisibility(View.GONE); // 如果位置信息2可见，设为不可见
			if (rgFissure.getVisibility() != View.GONE)
				rgFissure.setVisibility(View.GONE); // 如果裂缝单选框可见，设为不可见
		}
		
		// 选择病害图片监听
		btnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                // 开启Pictures画面Type设定为image
                intent.setType("image/*");
                // 使用Intent.ACTION_GET_CONTENT这个Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 取得相片后返回本画面
                getRootFragment().startActivityForResult(intent, 1);
			}			
		});
		
		final View rv = rootView;
		
		// 提交按钮监听
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RadioButton rbFeature = (RadioButton) rv.findViewById(rgFeature.getCheckedRadioButtonId());
				RadioButton rbFissure = (RadioButton) rv.findViewById(rgFissure.getCheckedRadioButtonId());
				RadioButton rbLocation = (RadioButton) rv.findViewById(rgLocation.getCheckedRadioButtonId());
				
				String rg_feature = rbFeature.getText().toString(); // 病害特征
				// 待加判断，小心空指针！！！
				String rg_fissure = rbFissure.getText().toString(); // 裂缝
				String rg_location = rbLocation.getText().toString(); // 位置
				
				System.out.println(rg_feature + "," + rg_fissure + "," + rg_location);
				
			}			
		});
		
		//diseaseAttribute = otherDisease.getSelectedItem().toString();
		
		
	}
	
	/**
	 * 得到根Fragment
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
