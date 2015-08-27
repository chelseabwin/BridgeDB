package com.qjs.bridgedb.disease.subfragment.sub1;

import com.qjs.bridgedb.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
	private TextView diseaseDescription,addContent;
	private Button diseaseSubmit;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_upper_page1, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // ��������
		
		rgFeature = (RadioGroup) rootView.findViewById(R.id.rg); // ����������ѡ��
		rgFissure = (RadioGroup) rootView.findViewById(R.id.rg2); // �ѷ쵥ѡ��
		rgLocation = (RadioGroup) rootView.findViewById(R.id.rg3); // ����λ�õ�ѡ��
		
		spOtherDisease = (Spinner) rootView.findViewById(R.id.sp_other_disease); // �������������б�
		
		local1 = (LinearLayout) rootView.findViewById(R.id.up1_ll_1); // λ����Ϣ�������ѷ�������ѷ죩
		local2 = (LinearLayout) rootView.findViewById(R.id.up1_ll_2); // �����ѷ�λ����Ϣ
		
		voidsPits = (RadioButton) rootView.findViewById(R.id.rbtn_voids_pits); // ��������
		fissure1 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure1); // �����ѷ�
		fissure5 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure5); // ���ѷ�
		
		addContent = (TextView) rootView.findViewById(R.id.tv_up1_add_content); // ���������ı���
		diseaseSubmit = (Button) rootView.findViewById(R.id.btn_up1_submit); // �ύ��ť		
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(20);
			diseaseDescription.setText("��������������(" + args.getString("GIRDER") + ")");
			
			// ���ò�����������
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub					
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb); // ���ò�������ѡ��
				}				
			});
			voidsPits.setChecked(true); // ���á��������桱Ĭ��ѡ��
		}		
		return rootView;		
	}
	
	/** ���ò�������ѡ��
	 * rb: ����ѡ��button
	 * */
	private void setDiseaseFeature(RadioButton rb) {
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
					// TODO Auto-generated method stub
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
			if ("��������".equals(rb.getText()))
				spOtherDisease.setVisibility(View.VISIBLE); // ��ʾ�����������������б�
			else
				spOtherDisease.setVisibility(View.GONE); // ���ء����������������б�
			
			if (local1.getVisibility() != View.VISIBLE)
				local1.setVisibility(View.VISIBLE); // ���λ����Ϣ1���ɼ�����Ϊ�ɼ�
			if (local2.getVisibility() != View.GONE)
				local2.setVisibility(View.GONE); // ���λ����Ϣ2�ɼ�����Ϊ���ɼ�
			if (rgFissure.getVisibility() != View.GONE)
				rgFissure.setVisibility(View.GONE); // ����ѷ쵥ѡ��ɼ�����Ϊ���ɼ�
		}
		
		if (rgLocation.getVisibility() != View.VISIBLE)
			rgLocation.setVisibility(View.VISIBLE); // �������λ�õ�ѡ�򲻿ɼ�����Ϊ�ɼ�
		if (addContent.getVisibility() != View.VISIBLE)
			addContent.setVisibility(View.VISIBLE); // ������������ı��򲻿ɼ�����Ϊ�ɼ�
		if (diseaseSubmit.getVisibility() != View.VISIBLE)
			diseaseSubmit.setVisibility(View.VISIBLE); // ����ύ��ť���ɼ�����Ϊ�ɼ�
		
		//diseaseAttribute = otherDisease.getSelectedItem().toString();
		
		
	}
}
