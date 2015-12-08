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
		
		deck_rg = (RadioGroup) rootView.findViewById(R.id.deck_rg); // 属性复选框
		spanDetail = (TextView) rootView.findViewById(R.id.tv_span_detail); // 编号
		deckDetail = (FrameLayout) rootView.findViewById(R.id.deck_detail_container); // 病害容器
		
		final Bundle args = getArguments();
		if (args != null) {
			deck_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (spanDetail.getVisibility() != View.GONE)
						spanDetail.setVisibility(View.GONE); // 将编号详情设为不可见
					if (deckDetail.getVisibility() != View.GONE)
						deckDetail.setVisibility(View.GONE); // 将病害详情设为不可见
					
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setSpanNum(rb, args.getString("BRIDGE_ID")); // 设置跨号
				}				
			});
		}		
		return rootView;
	}
	
	/** 设置跨号
	 * rb: 病害选择button
	 * bgId: 桥梁id
	 * */
	private void setSpanNum(RadioButton rb, String bgId) {
		String tableName,fieldName,option;
		
		if ("桥面铺装".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "deck_num";
			option = "DECK";
		}
		else if ("伸缩缝装置".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "joint_num";
			option = "JOINT";			
		}
		else if ("人行道".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "sidewalk";
			option = "SIDEWALK";
		}
		else if ("栏杆、护栏".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "guardrail";
			option = "FENCE";			
		}
		else if ("防排水系统".equals(rb.getText())) {
			tableName = "parts2";
			fieldName = "drainage_system";
			option = "WATERTIGHT";			
		}
		else {
			tableName = "parts2";
			fieldName = "illuminated_sign";
			option = "LIGHTING";			
		}
		// 根据id查找数据
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'");
		
		if (cursor.moveToFirst()) {
			String itemNum = cursor.getString(cursor.getColumnIndex(fieldName));
			if (itemNum != null) {
				String[] itemCodes;
				if (option == "SIDEWALK" || option == "FENCE") { // 处理翼墙、耳墙和锥坡
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
				setSpanDetail(itemCodes, option, rb.getText().toString(), bgId); // 设置编号详情
			}
		}		
	}
	
	/** 设置编号详情
	 * ItemCodes: 编号数组
	 * option: 选择项
	 * itemName: 选择项名称
	 * bgId: 桥梁id
	 * */
	private void setSpanDetail(String[] ItemCodes, String option, String itemName, String bgId) {
		// 如果编号详情不可见，设为可见
		if (spanDetail.getVisibility() != View.VISIBLE)
			spanDetail.setVisibility(View.VISIBLE);
	
		spanDetail.setTextSize(25);
		if (option.equals("DECK"))
			spanDetail.setText("桥面铺装:\t");
		else if (option.equals("JOINT"))
			spanDetail.setText("伸缩缝装置:\t");
		else if (option.equals("SIDEWALK"))
			spanDetail.setText("人行道:\t");
		else if (option.equals("FENCE"))
			spanDetail.setText("栏杆、护栏:\t");
		else if (option.equals("WATERTIGHT"))
			spanDetail.setText("防排水系统:\t");
		else
			spanDetail.setText("照明、标志:\t");
		
		final String optionStr = option;
		final String itemNameStr = itemName;
		final String bg_id = bgId;
		
		// 定义可点击的字符串数组，数组长度为ItemCodes的长度
		SpannableString[] ss = new SpannableString[ItemCodes.length];
		
		for (int j = 0; j < ItemCodes.length; j++) {
			final String str = ItemCodes[j];
			// 将编号赋值到该数组
			ss[j] = new SpannableString(ItemCodes[j]);
			// 设置编号点击事件
			ss[j].setSpan(new ClickableSpan() {

				@Override
				public void onClick(View widget) {
					setDiseaseDetail(optionStr, itemNameStr, str, bg_id); // 设置病害详情
				}														
			}, 0, ss[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			spanDetail.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
			spanDetail.append(ss[j]); // 将编号添加到TextView中
			spanDetail.append("\t\t");					
			spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
		}		
	}
	
	/** 设置病害详情
	 * ItemName: 选择项名称
	 * option: 选择项
	 * Item: 选择跨号
	 * bgId: 桥梁id
	 * */
	private void setDiseaseDetail(String optionStr, String ItemName, String Item, String bdId) {
		// 如果病害详情不可见，设为可见
		if (deckDetail.getVisibility() != View.VISIBLE)
			deckDetail.setVisibility(View.VISIBLE);
		Bundle bd = new Bundle();
		bd.putString("ITEM_NAME", ItemName);
		bd.putString(optionStr, Item);
		bd.putString("BRIDGE_ID", bdId);
		
		// 创建Fragment对象
		Fragment frag = new Fragment();
		
		if (optionStr.equals("DECK") || optionStr.equals("JOINT") || optionStr.equals("WATERTIGHT") || optionStr.equals("LIGHTING"))
			frag = new deckFragment();
		else
			frag = new sub3OtherFragment();
		
		// 向Fragment传入参数
		frag.setArguments(bd);
		// 使用fragment替换bridge_detail_container容器当前显示的Fragment
		getChildFragmentManager().beginTransaction()
			.replace(R.id.deck_detail_container, frag)
			.commit();
	}
}
