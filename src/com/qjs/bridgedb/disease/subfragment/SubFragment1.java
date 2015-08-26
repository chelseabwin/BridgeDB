package com.qjs.bridgedb.disease.subfragment;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.subfragment.sub1.girderFragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SubFragment1 extends Fragment {
	private RadioGroup up_rg;
	private RadioButton girder, wetJoint, support;
	private TextView spanNum, spanDetail;
	private FrameLayout upperDetail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tab_page1, container, false);
		
		up_rg = (RadioGroup) rootView.findViewById(R.id.up_rg); // ���Ը�ѡ��		
		spanNum = (TextView) rootView.findViewById(R.id.tv_span_num); // ���
		spanDetail = (TextView) rootView.findViewById(R.id.tv_span_detail); // ���
		upperDetail = (FrameLayout) rootView.findViewById(R.id.upper_detail_container); // ��������
		
		girder = (RadioButton) rootView.findViewById(R.id.rbtn_girder);
		wetJoint = (RadioButton) rootView.findViewById(R.id.rbtn_wet_joint);
		support = (RadioButton) rootView.findViewById(R.id.rbtn_support);
		
		final DbOperation db = new DbOperation(this.getActivity());
		
		final Bundle args = getArguments();
		if (args != null) {
			up_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					spanDetail.setVisibility(View.GONE);
					upperDetail.setVisibility(View.GONE);
					
					spanNum.setTextSize(20);
					spanNum.setText("���:\t");
					
					Cursor cursor = null;
					// ѡ��������
					if (checkedId == girder.getId()) {
						// ����id�����ϲ����ع�������
						cursor = db.queryData("*", "load_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
						
						if (cursor.moveToFirst()) {
							// ��ֿ��
							String[] gCodes = cursor.getString(cursor.getColumnIndex("load_nums")).split("\n");
							// ����ϲ����ع������
							final String[] girderCodes = cursor.getString(cursor.getColumnIndex("load_nums")).replace("\n", "").split("; ");
							
							// �������鳤��ΪgCodes�ĳ���
							SpannableString[] ss1 = new SpannableString[gCodes.length];
							
							for (int i = 0; i < gCodes.length; i++) {
								// ����Ÿ�ֵ��������
								ss1[i] = new SpannableString(gCodes[i].split("-")[0]); // ����ɵ�����ַ�������
								final String num = i + 1 + "";
								// �����ϲ����ع�����ŵ���¼�
								ss1[i].setSpan(new ClickableSpan() {
									
									@Override
									public void onClick(View widget) {
										// TODO Auto-generated method stub
										spanDetail.setVisibility(View.VISIBLE);
										spanDetail.setTextSize(20);
										spanDetail.setText("����:\t");
										// ����ɵ�����ַ������飬���鳤��ΪgirderCodes�ĳ���
										SpannableString[] ss2 = new SpannableString[girderCodes.length];
										
										for (int j = 0; j < girderCodes.length; j++) {
											if (girderCodes[j].split("-")[0].equals(num)) {
												final String str = girderCodes[j];
												// ��������Ÿ�ֵ��������
												ss2[j] = new SpannableString(girderCodes[j]);
												// ���ϲ����ع�����Ÿ�ֵ��������
												ss2[j] = new SpannableString(girderCodes[j]);
												// �����ϲ����ع�����ŵ���¼�
												ss2[j].setSpan(new ClickableSpan() {

													@Override
													public void onClick(View widget) {
														// TODO Auto-generated method stub
														upperDetail.setVisibility(View.VISIBLE);
														Bundle bd = new Bundle();
														bd.putString("GIRDER", str);
														
														// ����BridgeDetailFragment����
														girderFragment gf = new girderFragment();
														// ��Fragment�������
														gf.setArguments(bd);
														// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
														getChildFragmentManager().beginTransaction()
															.replace(R.id.upper_detail_container, gf)
															.commit();
													}														
												}, 0, ss2[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
												
												spanDetail.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
												spanDetail.append(ss2[j]); // �������ӵ�TextView��
												spanDetail.append("\t\t");					
												spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�
											}
										}
									}
								}, 0, ss1[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								
								spanNum.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
								spanNum.append(ss1[i]); // �������ӵ�TextView��
								spanNum.append("\t\t");					
								spanNum.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�
							}
						}						
					}
					// ѡ��ʪ�ӷ족
					else if (checkedId == wetJoint.getId()) {
						// ����id�����ϲ�һ�㹹������
						cursor = db.queryData("*", "general_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
						
						if (cursor.moveToFirst()) {
							// ��ֿ��
							String[] gCodes = cursor.getString(cursor.getColumnIndex("general_nums")).split("\n");
							// ����ϲ�һ�㹹�����
							final String[] wetJointCodes = cursor.getString(cursor.getColumnIndex("general_nums")).replace("\n", "").split("; ");
							
							// �������鳤��ΪgCodes�ĳ���
							SpannableString[] ss1 = new SpannableString[gCodes.length];
							
							for (int i = 0; i < gCodes.length; i++) {
								// ����Ÿ�ֵ��������
								ss1[i] = new SpannableString(gCodes[i].split("-")[0]); // ����ɵ�����ַ�������
								final String num = i + 1 + "";
								// �����ϲ����ع�����ŵ���¼�
								ss1[i].setSpan(new ClickableSpan() {
									
									@Override
									public void onClick(View widget) {
										// TODO Auto-generated method stub
										spanDetail.setVisibility(View.VISIBLE);
										spanDetail.setTextSize(20);
										spanDetail.setText("ʪ�ӷ�:\t");
										// ����ɵ�����ַ������飬���鳤��ΪgirderCodes�ĳ���
										SpannableString[] ss2 = new SpannableString[wetJointCodes.length];
										
										for (int j = 0; j < wetJointCodes.length; j++) {
											if (wetJointCodes[j].split("-")[0].equals(num)) {
												final String str = wetJointCodes[j];
												// ��������Ÿ�ֵ��������
												ss2[j] = new SpannableString(wetJointCodes[j]);
												// ���ϲ����ع�����Ÿ�ֵ��������
												ss2[j] = new SpannableString(wetJointCodes[j]);
												// �����ϲ����ع�����ŵ���¼�
												ss2[j].setSpan(new ClickableSpan() {

													@Override
													public void onClick(View widget) {
														// TODO Auto-generated method stub
														//upperDetail.setVisibility(View.VISIBLE);
//														Bundle bd = new Bundle();
//														bd.putString("WETJOINT", str);
//														
//														// ����BridgeDetailFragment����
//														girderFragment gf = new girderFragment();
//														// ��Fragment�������
//														gf.setArguments(bd);
//														// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
//														getChildFragmentManager().beginTransaction()
//															.replace(R.id.upper_detail_container, gf)
//															.commit();
													}														
												}, 0, ss2[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
												
												spanDetail.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
												spanDetail.append(ss2[j]); // �������ӵ�TextView��
												spanDetail.append("\t\t");					
												spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�
											}
										}
									}
								}, 0, ss1[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								
								spanNum.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
								spanNum.append(ss1[i]); // �������ӵ�TextView��
								spanNum.append("\t\t");					
								spanNum.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�
							}
						}						
					}
					// ѡ��֧����
					else {
						// ����id�����ϲ�һ�㹹������
						cursor = db.queryData("*", "support_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
						
						if (cursor.moveToFirst()) {
							// ��ֿ��
							String[] gCodes = cursor.getString(cursor.getColumnIndex("support_nums")).split("\n");
							// ���֧�����
							final String[] supportCodes = cursor.getString(cursor.getColumnIndex("support_nums")).replace("\n", "").split("; ");
							
							// �������鳤��ΪgCodes�ĳ���
							SpannableString[] ss1 = new SpannableString[gCodes.length];
							
							for (int i = 0; i < gCodes.length; i++) {
								// ����Ÿ�ֵ��������
								ss1[i] = new SpannableString(gCodes[i].split("-")[0]); // ����ɵ�����ַ�������
								final String num = i + 1 + "";
								// �����ϲ����ع�����ŵ���¼�
								ss1[i].setSpan(new ClickableSpan() {
									
									@Override
									public void onClick(View widget) {
										// TODO Auto-generated method stub
										spanDetail.setVisibility(View.VISIBLE);
										spanDetail.setTextSize(20);
										spanDetail.setText("֧��:\t");
										// ����ɵ�����ַ������飬���鳤��ΪgirderCodes�ĳ���
										SpannableString[] ss2 = new SpannableString[supportCodes.length];
										
										for (int j = 0; j < supportCodes.length; j++) {
											if (supportCodes[j].split("-")[0].equals(num)) {
												final String str = supportCodes[j];
												// ��������Ÿ�ֵ��������
												ss2[j] = new SpannableString(supportCodes[j]);
												// ���ϲ����ع�����Ÿ�ֵ��������
												ss2[j] = new SpannableString(supportCodes[j]);
												// �����ϲ����ع�����ŵ���¼�
												ss2[j].setSpan(new ClickableSpan() {

													@Override
													public void onClick(View widget) {
														// TODO Auto-generated method stub
														//upperDetail.setVisibility(View.VISIBLE);
//														Bundle bd = new Bundle();
//														bd.putString("SUPPORT", str);
//														
//														// ����BridgeDetailFragment����
//														girderFragment gf = new girderFragment();
//														// ��Fragment�������
//														gf.setArguments(bd);
//														// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
//														getChildFragmentManager().beginTransaction()
//															.replace(R.id.upper_detail_container, gf)
//															.commit();
													}														
												}, 0, ss2[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
												
												spanDetail.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
												spanDetail.append(ss2[j]); // �������ӵ�TextView��
												spanDetail.append("\t\t");					
												spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�
											}
										}
									}
								}, 0, ss1[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								
								spanNum.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
								spanNum.append(ss1[i]); // �������ӵ�TextView��
								spanNum.append("\t\t");					
								spanNum.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�
							}
						}						
					}
				}				
			});
		}
		
		return rootView;
	}
}
