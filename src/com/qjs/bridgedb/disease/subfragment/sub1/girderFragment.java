package com.qjs.bridgedb.disease.subfragment.sub1;

import com.qjs.bridgedb.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class girderFragment extends Fragment
{
	private TextView diseaseDescription; // ��������
	private RadioGroup rg,rg2;
	private Spinner otherDisease;
	private LinearLayout ll1,ll2;
	private RadioButton rbtn_fissure1;
	
	String diseaseAttribute = "���ѡ�����"; // ��������
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_upper_page1, container, false);
		
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description);
		rg = (RadioGroup) rootView.findViewById(R.id.rg);
		otherDisease = (Spinner) rootView.findViewById(R.id.sp_other_disease);
		ll1 = (LinearLayout) rootView.findViewById(R.id.up1_ll_1);
		ll2 = (LinearLayout) rootView.findViewById(R.id.up1_ll_2);
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2);
		rbtn_fissure1 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure1);
		
		Bundle args = getArguments();
		if (args != null)
		{
			diseaseDescription.setTextSize(20);
			diseaseDescription.setText("��������������(" + args.getString("GIRDER") + ")");
			
			rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) 
				{
					// TODO Auto-generated method stub
					switch (checkedId)
					{
						case 2131362001:
							diseaseAttribute = "���ѡ�����";
							otherDisease.setVisibility(View.GONE); // ���ء��������ı���
							
							rg2.setVisibility(View.GONE); // �����ѷ�����
							
							// ��ʾ����ѡ��
							ll1.setVisibility(View.VISIBLE);
							ll2.setVisibility(View.GONE);
							break;
						case 2131362002:
							diseaseAttribute = "���䡢����";
							otherDisease.setVisibility(View.GONE); // ���ء��������ı���
							
							rg2.setVisibility(View.GONE); // �����ѷ�����
							
							// ��ʾ����ѡ��
							ll1.setVisibility(View.VISIBLE);
							ll2.setVisibility(View.GONE);
							break;
						case 2131362003:
							diseaseAttribute = "�ն����׶�";
							otherDisease.setVisibility(View.GONE); // ���ء��������ı���
							
							rg2.setVisibility(View.GONE); // �����ѷ�����
							
							// ��ʾ����ѡ��
							ll1.setVisibility(View.VISIBLE);
							ll2.setVisibility(View.GONE);
							break;
						case 2131362004:
							diseaseAttribute = "�ѷ�";
							otherDisease.setVisibility(View.GONE); // ���ء��������ı���							
							
							rg2.setVisibility(View.VISIBLE); // ��ʾ�ѷ�����
							rbtn_fissure1.setChecked(true); // �����ѷ����͵�һ��ѡ��
							
							// ��ʾ����ѡ��
							ll1.setVisibility(View.GONE);
							ll2.setVisibility(View.VISIBLE);
							
							rg2.setOnCheckedChangeListener(new OnCheckedChangeListener() 
							{
								@Override
								public void onCheckedChanged(RadioGroup group, int checkedId) 
								{
									// TODO Auto-generated method stub
									if (checkedId == 2131362012) 
									{
										// ��ʾ����ѡ��
										ll1.setVisibility(View.VISIBLE);
										ll2.setVisibility(View.GONE);										
									}
									else 
									{
										// ��ʾ����ѡ��
										ll1.setVisibility(View.GONE);
										ll2.setVisibility(View.VISIBLE);
									}
								}								
							});
							break;
						case 2131362005:
							diseaseAttribute = "����";
							otherDisease.setVisibility(View.VISIBLE); // ��ʾ���������ı���
							
							rg2.setVisibility(View.GONE); // �����ѷ�����
							
							// ��ʾ����ѡ��
							ll1.setVisibility(View.VISIBLE);
							ll2.setVisibility(View.GONE);
							break;					
					}					
				}				
			});
			

			//diseaseAttribute = otherDisease.getSelectedItem().toString();
			
		}
		
		return rootView;		
	}
}
