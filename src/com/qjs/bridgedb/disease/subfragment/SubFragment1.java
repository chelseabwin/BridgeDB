package com.qjs.bridgedb.disease.subfragment;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.subfragment.sub1.girderFragment;
import com.qjs.bridgedb.disease.subfragment.sub1.wetJointFragment;
import com.qjs.bridgedb.disease.subfragment.sub1.supportFragment;

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
	private TextView spanNum, spanDetail;
	private FrameLayout upperDetail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_tab_page1, container, false);
		
		up_rg = (RadioGroup) rootView.findViewById(R.id.up_rg); // ���Ը�ѡ��		
		spanNum = (TextView) rootView.findViewById(R.id.tv_span_num); // ���
		spanDetail = (TextView) rootView.findViewById(R.id.tv_span_detail); // ���
		upperDetail = (FrameLayout) rootView.findViewById(R.id.upper_detail_container); // ��������
		
		final Bundle args = getArguments();
		if (args != null) {
			up_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if (spanDetail.getVisibility() != View.GONE)
						spanDetail.setVisibility(View.GONE); // �����������Ϊ���ɼ�
					if (upperDetail.getVisibility() != View.GONE)
						upperDetail.setVisibility(View.GONE); // ������������Ϊ���ɼ�
					
					spanNum.setTextSize(25);
					spanNum.setText("���:\t");
					
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setSpanNum(rb, args.getString("BRIDGE_ID")); // ���ÿ��
				}				
			});
		}		
		return rootView;
	}
	
	/** ���ÿ��
	 * rb: ����ѡ��button
	 * bgId: ����id
	 * */
	private void setSpanNum(RadioButton rb, String bgId) {
		String tableName,fieldName,option;
		
		if ("����".equals(rb.getText())) {
			tableName = "load_detail"; // ����ѯ�����ݿ��
			fieldName = "load_nums"; // �����ֶ�
			option = "GIRDER"; // ѡ���������ʪ�ӷ졢֧����
		}
		else if ("ʪ�ӷ�".equals(rb.getText())) {
			tableName = "general_detail";
			fieldName = "general_nums";
			option = "WETJOINT";
		}
		else {
			tableName = "support_detail";
			fieldName = "support_nums";
			option = "SUPPORT";			
		}
		// ����id��������
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'");
		
		if (cursor.moveToFirst()) {
			// ��ֿ��
			String[] lineCodes = cursor.getString(cursor.getColumnIndex(fieldName)).split("\n");
			// ����ϲ����ع������
			String[] itemCodes = cursor.getString(cursor.getColumnIndex(fieldName)).replace("\n", "").split("; ");
			// �������鳤��ΪlineCodes�ĳ���
			SpannableString[] ss = new SpannableString[lineCodes.length];
			final String optionStr = option;
			final String[] itemNum = itemCodes;
			final String bg_id = bgId;
			
			for (int i = 0; i < lineCodes.length; i++) {
				// ����Ÿ�ֵ��������
				ss[i] = new SpannableString(lineCodes[i].split("-")[0]); // ����ɵ�����ַ�������
				final String num = i + 1 + ""; // ����ѡ�еĿ��
				// ���ÿ�ŵ���¼�
				ss[i].setSpan(new ClickableSpan() {
					
					@Override
					public void onClick(View widget) {
						// TODO Auto-generated method stub
						setSpanDetail(itemNum, optionStr, num, bg_id); // ���ñ������
					}
				}, 0, ss[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				spanNum.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
				spanNum.append(ss[i]); // �������ӵ�TextView��
				spanNum.append("\t\t");					
				spanNum.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�
			}
		}		
	}
	
	/** ���ñ������
	 * ItemCodes: �������
	 * option: ѡ���������ʪ�ӷ졢֧����
	 * num: ѡ����
	 * bgId: ����id
	 * */
	private void setSpanDetail(String[] ItemCodes, String option, String num, String bgId) {
		// ���������鲻�ɼ�����Ϊ�ɼ�
		if (spanDetail.getVisibility() != View.VISIBLE)
			spanDetail.setVisibility(View.VISIBLE);
	
		spanDetail.setTextSize(25);
		if (option.equals("GIRDER"))			
			spanDetail.setText("����:\t");
		else if (option.equals("WETJOINT"))
			spanDetail.setText("ʪ�ӷ�:\t");
		else
			spanDetail.setText("֧��:\t");
		
		final String optionStr = option;
		final String bg_id = bgId;
		
		// ����ɵ�����ַ������飬���鳤��ΪItemCodes�ĳ���
		SpannableString[] ss = new SpannableString[ItemCodes.length];
		
		for (int j = 0; j < ItemCodes.length; j++) {
			if (ItemCodes[j].split("-")[0].equals(num)) {
				final String str = ItemCodes[j];
				// ����Ÿ�ֵ��������
				ss[j] = new SpannableString(ItemCodes[j]);
				// ���ñ�ŵ���¼�
				ss[j].setSpan(new ClickableSpan() {

					@Override
					public void onClick(View widget) {
						// TODO Auto-generated method stub
						setDiseaseDetail(optionStr, str, bg_id); // ���ò�������
					}														
				}, 0, ss[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				spanDetail.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
				spanDetail.append(ss[j]); // �������ӵ�TextView��
				spanDetail.append("\t\t");					
				spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�
			}
		}		
	}
	
	/** ���ò�������
	 * girderCodes: �������
	 * option: ѡ���������ʪ�ӷ졢֧����
	 * Item: ѡ����
	 * bgId: ����id
	 * */
	private void setDiseaseDetail(String optionStr, String Item, String bdId) {
		// ����������鲻�ɼ�����Ϊ�ɼ�
		if (upperDetail.getVisibility() != View.VISIBLE)
			upperDetail.setVisibility(View.VISIBLE);
		Bundle bd = new Bundle();
		bd.putString(optionStr, Item);
		bd.putString("BRIDGE_ID", bdId);
		
		// ����Fragment����
		Fragment frag = new Fragment();
		
		if (optionStr.equals("GIRDER"))
			frag = new girderFragment();
		else if (optionStr.equals("WETJOINT"))
			frag = new wetJointFragment();
		else
			frag = new supportFragment();
		
		// ��Fragment�������
		frag.setArguments(bd);
		// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
		getChildFragmentManager().beginTransaction()
			.replace(R.id.upper_detail_container, frag)
			.commit();
	}
}
