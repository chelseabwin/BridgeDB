package com.qjs.bridgedb.disease.subfragment;

import java.util.ArrayList;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.subfragment.sub3.deckFragment;
import com.qjs.bridgedb.disease.subfragment.sub3.sub3OtherFragment;

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

public class SubFragment3 extends Fragment {
	private RadioGroup deck_rg;
	private TextView spanDetail;
	private FrameLayout deckDetail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_tab_page3, container, false);
		
		deck_rg = (RadioGroup) rootView.findViewById(R.id.deck_rg); // ���Ը�ѡ��
		spanDetail = (TextView) rootView.findViewById(R.id.tv_span_detail); // ���
		deckDetail = (FrameLayout) rootView.findViewById(R.id.deck_detail_container); // ��������
		
		final Bundle args = getArguments();
		if (args != null) {
			deck_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (spanDetail.getVisibility() != View.GONE)
						spanDetail.setVisibility(View.GONE); // �����������Ϊ���ɼ�
					if (deckDetail.getVisibility() != View.GONE)
						deckDetail.setVisibility(View.GONE); // ������������Ϊ���ɼ�
					
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
		
		if ("������װ".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "deck_num";
			option = "DECK";
		}
		else if ("������װ��".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "joint_num";
			option = "JOINT";			
		}
		else if ("���е�".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "sidewalk";
			option = "SIDEWALK";
		}
		else if ("���ˡ�����".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "guardrail";
			option = "FENCE";			
		}
		else if ("����ˮϵͳ".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "drainage_system";
			option = "WATERTIGHT";			
		}
		else {
			tableName = "parts2";
			fieldName = "illuminated_sign";
			option = "LIGHTING";			
		}
		// ����id��������
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'");
		
		if (cursor.moveToFirst()) {
			String itemNum = cursor.getString(cursor.getColumnIndex(fieldName));
			if (itemNum != null) {
				String[] itemCodes;
				if (option == "SIDEWALK" || option == "FENCE") { // ������ǽ����ǽ��׶��
					char[] ch = itemNum.toCharArray();
					ArrayList<String> al = new ArrayList<String>();
					
					if (ch[0] == '1')
						al.add("L");
					if (ch[1] == '1')
						al.add("R");
					
					itemCodes = (String[])al.toArray(new String[al.size()]);
				}
				else {
					int num = Integer.valueOf(itemNum).intValue();
					itemCodes = new String[num];
					for (int i = 0; i < num; i++) {
						itemCodes[i] = String.valueOf(i+1);
					}
				}
				setSpanDetail(itemCodes, option, rb.getText().toString(), bgId); // ���ñ������
			}
		}		
	}
	
	/** ���ñ������
	 * ItemCodes: �������
	 * option: ѡ����
	 * itemName: ѡ��������
	 * bgId: ����id
	 * */
	private void setSpanDetail(String[] ItemCodes, String option, String itemName, String bgId) {
		// ���������鲻�ɼ�����Ϊ�ɼ�
		if (spanDetail.getVisibility() != View.VISIBLE)
			spanDetail.setVisibility(View.VISIBLE);
	
		spanDetail.setTextSize(25);
		if (option.equals("DECK"))
			spanDetail.setText("������װ:\t");
		else if (option.equals("JOINT"))
			spanDetail.setText("������װ��:\t");
		else if (option.equals("SIDEWALK"))
			spanDetail.setText("���е�:\t");
		else if (option.equals("FENCE"))
			spanDetail.setText("���ˡ�����:\t");
		else if (option.equals("WATERTIGHT"))
			spanDetail.setText("����ˮϵͳ:\t");
		else
			spanDetail.setText("��������־:\t");
		
		final String optionStr = option;
		final String itemNameStr = itemName;
		final String bg_id = bgId;
		
		// ����ɵ�����ַ������飬���鳤��ΪItemCodes�ĳ���
		SpannableString[] ss = new SpannableString[ItemCodes.length];
		
		for (int j = 0; j < ItemCodes.length; j++) {
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
	
	/** ���ò�������
	 * ItemName: ѡ��������
	 * option: ѡ����
	 * Item: ѡ����
	 * bgId: ����id
	 * */
	private void setDiseaseDetail(String optionStr, String ItemName, String Item, String bdId) {
		// ����������鲻�ɼ�����Ϊ�ɼ�
		if (deckDetail.getVisibility() != View.VISIBLE)
			deckDetail.setVisibility(View.VISIBLE);
		Bundle bd = new Bundle();
		bd.putString("ITEM_NAME", ItemName);
		bd.putString(optionStr, Item);
		bd.putString("BRIDGE_ID", bdId);
		
		// ����Fragment����
		Fragment frag = new Fragment();
		
		if (optionStr.equals("DECK") || optionStr.equals("JOINT") || optionStr.equals("WATERTIGHT") || optionStr.equals("LIGHTING"))
			frag = new deckFragment();
		else
			frag = new sub3OtherFragment();
		
		// ��Fragment�������
		frag.setArguments(bd);
		// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
		getChildFragmentManager().beginTransaction()
			.replace(R.id.deck_detail_container, frag)
			.commit();
	}
}
