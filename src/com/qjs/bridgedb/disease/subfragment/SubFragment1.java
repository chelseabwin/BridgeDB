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
	private RadioGroup up_rg, up_rg1, up_rg2, up_rg3, up_rg4, up_rg5;
	private TextView spanNum, spanDetail;
	private FrameLayout upperDetail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_tab_page1, container, false);
		
		up_rg1 = (RadioGroup) rootView.findViewById(R.id.up_rg);
		up_rg2 = (RadioGroup) rootView.findViewById(R.id.up_rg2);
		up_rg3 = (RadioGroup) rootView.findViewById(R.id.up_rg3);
		up_rg4 = (RadioGroup) rootView.findViewById(R.id.up_rg4);
		up_rg5 = (RadioGroup) rootView.findViewById(R.id.up_rg5);
		
		String bgId = null;
		final Bundle args = getArguments();
		if (args != null)
			bgId = args.getString("BRIDGE_ID");		
		
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = db.queryData("*", "base2", "bg_id='" + bgId + "'");
		
		if (cursor.moveToFirst()) {
			String bgType = cursor.getString(cursor.getColumnIndex("bridge_type"));
			if (bgType.charAt(1) == '1') { // ����
				up_rg1.setVisibility(View.GONE);
				up_rg2.setVisibility(View.VISIBLE);
				up_rg3.setVisibility(View.GONE);
				up_rg4.setVisibility(View.GONE);
				up_rg5.setVisibility(View.GONE);
				up_rg = up_rg2;
			}
			else if (bgType.charAt(1) == '2') { // ����
				up_rg1.setVisibility(View.VISIBLE);
				up_rg2.setVisibility(View.GONE);
				up_rg3.setVisibility(View.GONE);
				up_rg4.setVisibility(View.GONE);
				up_rg5.setVisibility(View.GONE);
				up_rg = up_rg1;
			}
			else if (bgType.charAt(1) == '4') { // ����
				if (bgType.charAt(3) == '1' || bgType.charAt(3) == '2' 
						|| bgType.charAt(3) == '3' || bgType.charAt(3) == '4') { // �幰���߹������ι���˫����
					up_rg1.setVisibility(View.GONE);
					up_rg2.setVisibility(View.GONE);
					up_rg3.setVisibility(View.VISIBLE);
					up_rg4.setVisibility(View.GONE);
					up_rg5.setVisibility(View.GONE);
					up_rg = up_rg3;
				}
				else if (bgType.charAt(3) == '5' || bgType.charAt(3) == '6') { // ��ܹ����ռܹ�
					up_rg1.setVisibility(View.GONE);
					up_rg2.setVisibility(View.GONE);
					up_rg3.setVisibility(View.GONE);
					up_rg4.setVisibility(View.VISIBLE);
					up_rg5.setVisibility(View.GONE);
					up_rg = up_rg4;
				}
			}
			else { // ����
				up_rg1.setVisibility(View.GONE);
				up_rg2.setVisibility(View.GONE);
				up_rg3.setVisibility(View.GONE);
				up_rg4.setVisibility(View.GONE);
				up_rg5.setVisibility(View.VISIBLE);
				up_rg = up_rg5;
			}
		}
		
		spanNum = (TextView) rootView.findViewById(R.id.tv_span_num); // ���
		spanDetail = (TextView) rootView.findViewById(R.id.tv_span_detail); // ���
		upperDetail = (FrameLayout) rootView.findViewById(R.id.upper_detail_container); // ��������		
		
		if (args != null) {
			up_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
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
		
		if ("����".equals(rb.getText()) || "���İ�".equals(rb.getText()) || "����Ȧ".equals(rb.getText()) 
				|| "�֡���ܹ�Ƭ".equals(rb.getText()) || "�ϲ����ع���".equals(rb.getText())) {
			tableName = "load_detail"; // ����ѯ�����ݿ��
			fieldName = "load_nums"; // �����ֶ�
			option = "GIRDER"; // ѡ����
		}
		else if ("ʪ�ӷ�".equals(rb.getText()) || "�����".equals(rb.getText()) || "�·�".equals(rb.getText()) 
				|| "���Ͻṹ".equals(rb.getText()) || "��������ϵ".equals(rb.getText()) || "�ϲ�һ�㹹��".equals(rb.getText())) {
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
			final String itemName = rb.getText().toString();
			final String[] itemNum = itemCodes;
			final String bg_id = bgId;
			
			for (int i = 0; i < lineCodes.length; i++) {
				// ����Ÿ�ֵ��������
				ss[i] = new SpannableString(lineCodes[i].split("-")[0]); // ����ɵ�����ַ�������
				
				String tap = ""; 
				if (tableName == "general_detail" || tableName == "support_detail") {
					tap = i + ""; // ����ѡ�еĿ��
				}
				else {
					tap = i + 1 + ""; // ����ѡ�еĿ��
				}
				final String num = tap;
				
				// ���ÿ�ŵ���¼�
				ss[i].setSpan(new ClickableSpan() {
					
					@Override
					public void onClick(View widget) {
						setSpanDetail(itemNum, optionStr, itemName, num, bg_id); // ���ñ������
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
	 * option: ѡ����
	 * itemName: ѡ��������
	 * num: ѡ����
	 * bgId: ����id
	 * */
	private void setSpanDetail(String[] ItemCodes, String option, String itemName, String num, String bgId) {
		// ���������鲻�ɼ�����Ϊ�ɼ�
		if (spanDetail.getVisibility() != View.VISIBLE)
			spanDetail.setVisibility(View.VISIBLE);
	
		spanDetail.setTextSize(25);
		spanDetail.setText(itemName + ":\t");
		
		final String optionStr = option;
		final String itemNameStr = itemName;
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
						setDiseaseDetail(optionStr, itemNameStr, str, bg_id); // ���ò�������
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
	 * option: ѡ����
	 * ItemName: ѡ��������
	 * Item: ѡ����
	 * bgId: ����id
	 * */
	private void setDiseaseDetail(String optionStr, String ItemName, String ItemNum, String bdId) {
		// ����������鲻�ɼ�����Ϊ�ɼ�
		if (upperDetail.getVisibility() != View.VISIBLE)
			upperDetail.setVisibility(View.VISIBLE);
		Bundle bd = new Bundle();
		bd.putString("ITEM_NAME", ItemName);
		bd.putString(optionStr, ItemNum);
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
