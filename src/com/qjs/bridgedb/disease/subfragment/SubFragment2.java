package com.qjs.bridgedb.disease.subfragment;

import java.util.ArrayList;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.subfragment.sub2.abutmentFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.beamFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.pierFragment;

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

public class SubFragment2 extends Fragment {
	private RadioGroup down_rg;
	private TextView spanNum, spanDetail;
	private FrameLayout downDetail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_tab_page2, container, false);
		
		down_rg = (RadioGroup) rootView.findViewById(R.id.down_rg); // ���Ը�ѡ��		
		spanNum = (TextView) rootView.findViewById(R.id.tv_span_num); // ���
		spanDetail = (TextView) rootView.findViewById(R.id.tv_span_detail); // ���
		downDetail = (FrameLayout) rootView.findViewById(R.id.down_detail_container); // ��������
		
		final Bundle args = getArguments();
		if (args != null) {
			down_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if (spanDetail.getVisibility() != View.GONE)
						spanDetail.setVisibility(View.GONE); // �����������Ϊ���ɼ�
					if (downDetail.getVisibility() != View.GONE)
						downDetail.setVisibility(View.GONE); // ������������Ϊ���ɼ�
					
					spanNum.setTextSize(25);
					spanNum.setText("�պ�:\t");
					
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setSpanNum(rb, args.getInt("BRIDGE_ID")); // ���ÿ��
				}				
			});
		}		
		return rootView;
	}
	
	/** ���ÿ��
	 * rb: ����ѡ��button
	 * bgId: ����id
	 * */
	private void setSpanNum(RadioButton rb, int bgId) {
		String tableName,fieldName,option;
		
		if ("�Ŷ�".equals(rb.getText())) {
			tableName = "pier_detail"; // ����ѯ�����ݿ��
			fieldName = "pier_nums"; // �����ֶ�
			option = "PIER"; // ѡ����Ŷա�������ϵ��...��
		}
		else if ("����".equals(rb.getText())) {
			tableName = "pier_detail";
			fieldName = "bent_cap_nums";
			option = "BENTCAP";
		}
		else if ("ϵ��".equals(rb.getText())) {
			tableName = "pier_detail";
			fieldName = "tie_beam_nums";
			option = "TIEBEAM";
		}
		else if ("��̨��".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "abutment_num";
			option = "ATBODY";
		}
		else if ("��̨ñ".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "abutment_num";
			option = "ATCAPPING";			
		}
		else if ("��̨����".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "pa_num";
			option = "PA";
		}
		else if ("�Ӵ�".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "bed_num";
			option = "BED";			
		}
		else if ("���ι�����".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "reg_structure";
			option = "REGSTRUC";			
		}
		else if ("��ǽ����ǽ".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "wing_wall";
			option = "WINGWALL";			
		}
		else if ("׶��".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "conical_slope";
			option = "CONSLOPE";			
		}
		else {
			tableName = "parts1";
			fieldName = "protection_slope";
			option = "PROSLOPE";			
		}
		// ����id��������
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'");
		
		if (cursor.moveToFirst()) {
			if (tableName == "pier_detail") {
				if (fieldName == "pier_nums") { // ������Ŷ�
					if (spanNum.getVisibility() != View.VISIBLE)
						spanNum.setVisibility(View.VISIBLE); // ���պ���Ϊ�ɼ�
					// ��ֶպ�
					String[] lineCodes = cursor.getString(cursor.getColumnIndex(fieldName)).split("\n");
					// ����Ŷձ��
					String[] itemCodes = cursor.getString(cursor.getColumnIndex(fieldName)).replace("\n", "").split("; ");
					// �������鳤��ΪlineCodes�ĳ���
					SpannableString[] ss = new SpannableString[lineCodes.length];
					final String optionStr = option;
					final String[] itemNum = itemCodes;
					final int bg_id = bgId;
					
					for (int i = 0; i < lineCodes.length; i++) {
						// ���պŸ�ֵ��������
						ss[i] = new SpannableString(lineCodes[i].split("-")[0]); // ����ɵ�����ַ�������
						final String num = i + 1 + ""; // ����ѡ�еĶպ�
						// ���öպŵ���¼�
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
				else { // ���������ϵ��
					String[] itemCodes = cursor.getString(cursor.getColumnIndex(fieldName)).split(", ");
					if (spanNum.getVisibility() != View.GONE)
						spanNum.setVisibility(View.GONE); // �����������Ϊ���ɼ�
					setSpanDetail(itemCodes, option, bgId); // ���ñ������
				}
			}
			else {
				String itemNum = cursor.getString(cursor.getColumnIndex(fieldName));
				if (itemNum != null) {
					String[] itemCodes;
					if (option == "WINGWALL" || option == "CONSLOPE") { // ������ǽ����ǽ��׶��
						char[] ch = itemNum.toCharArray();
						ArrayList<String> al = new ArrayList<String>();
						
						if (ch[0] == '1')
							al.add("L0");
						if (ch[1] == '1')
							al.add("R0");
						if (ch[2] == '1')
							al.add("L1");
						if (ch[3] == '1')
							al.add("R1");
						
						itemCodes = (String[])al.toArray(new String[al.size()]);
					}
					else {
						int num = Integer.valueOf(itemNum).intValue();
						itemCodes = new String[num];
						for (int i = 0; i < num; i++) {
							itemCodes[i] = String.valueOf(i+1);
						}
					}
					
					if (spanNum.getVisibility() != View.GONE)
						spanNum.setVisibility(View.GONE); // ���պ���Ϊ���ɼ�
					setSpanDetail(itemCodes, option, bgId); // ���ñ������
				}				
			}			
		}		
	}
	
	/** ���ñ������
	 * ItemCodes: �������
	 * option: ѡ����Ŷգ�
	 * num: ѡ��պ�
	 * bgId: ����id
	 * */
	private void setSpanDetail(String[] ItemCodes, String option, String num, int bgId) {
		// ���������鲻�ɼ�����Ϊ�ɼ�
		if (spanDetail.getVisibility() != View.VISIBLE)
			spanDetail.setVisibility(View.VISIBLE);
	
		spanDetail.setTextSize(25);
		if (option.equals("PIER"))			
			spanDetail.setText("�Ŷ�:\t");
		
		final String optionStr = option;
		final int bg_id = bgId;
		
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
	
	/** ���ñ�����飨���أ�
	 * ItemCodes: �������
	 * option: ѡ���������ϵ��...��
	 * bgId: ����id
	 * */
	private void setSpanDetail(String[] ItemCodes, String option,int bgId) {
		// ���������鲻�ɼ�����Ϊ�ɼ�
		if (spanDetail.getVisibility() != View.VISIBLE)
			spanDetail.setVisibility(View.VISIBLE);
	
		spanDetail.setTextSize(25);
		if (option.equals("BENTCAP"))
			spanDetail.setText("����:\t");
		else if (option.equals("TIEBEAM"))
			spanDetail.setText("ϵ��:\t");
		else if (option.equals("ATBODY"))
			spanDetail.setText("��̨��:\t");
		else if (option.equals("ATCAPPING"))
			spanDetail.setText("��̨ñ:\t");
		else if (option.equals("PA"))
			spanDetail.setText("��̨����:\t");
		else if (option.equals("BED"))
			spanDetail.setText("�Ӵ�:\t");		
		else if (option.equals("REGSTRUC"))
			spanDetail.setText("���ι�����:\t");
		else if (option.equals("WINGWALL"))
			spanDetail.setText("��ǽ����ǽ:\t");
		else if (option.equals("CONSLOPE"))
			spanDetail.setText("׶��:\t");
		else
			spanDetail.setText("����:\t");
		
		final String optionStr = option;
		final int bg_id = bgId;
		
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
	
	/** ���ò�������
	 * girderCodes: �������
	 * option: ѡ���������ʪ�ӷ졢֧����
	 * Item: ѡ����
	 * bgId: ����id
	 * */
	private void setDiseaseDetail(String optionStr, String Item, int bdId) {
		// ����������鲻�ɼ�����Ϊ�ɼ�
		if (downDetail.getVisibility() != View.VISIBLE)
			downDetail.setVisibility(View.VISIBLE);
		Bundle bd = new Bundle();
		bd.putString(optionStr, Item);
		bd.putInt("BRIDGE_ID", bdId);
		
		// ����Fragment����
		Fragment frag = new Fragment();
		
		if (optionStr.equals("PIER"))
			frag = new pierFragment();
		else if (optionStr.equals("BENTCAP") || optionStr.equals("TIEBEAM"))
			frag = new beamFragment();
		else if (optionStr.equals("ATBODY") || optionStr.equals("ATCAPPING") || optionStr.equals("PA"))
			frag = new abutmentFragment();
//		else
//			frag = new supportFragment();
		
		// ��Fragment�������
		frag.setArguments(bd);
		// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
		getChildFragmentManager().beginTransaction()
			.replace(R.id.down_detail_container, frag)
			.commit();
	}
}
