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
		
		down_rg = (RadioGroup) rootView.findViewById(R.id.down_rg); // 属性复选框		
		spanNum = (TextView) rootView.findViewById(R.id.tv_span_num); // 跨号
		spanDetail = (TextView) rootView.findViewById(R.id.tv_span_detail); // 编号
		downDetail = (FrameLayout) rootView.findViewById(R.id.down_detail_container); // 病害容器
		
		final Bundle args = getArguments();
		if (args != null) {
			down_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if (spanDetail.getVisibility() != View.GONE)
						spanDetail.setVisibility(View.GONE); // 将编号详情设为不可见
					if (downDetail.getVisibility() != View.GONE)
						downDetail.setVisibility(View.GONE); // 将病害详情设为不可见
					
					spanNum.setTextSize(25);
					spanNum.setText("墩号:\t");
					
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setSpanNum(rb, args.getInt("BRIDGE_ID")); // 设置跨号
				}				
			});
		}		
		return rootView;
	}
	
	/** 设置跨号
	 * rb: 病害选择button
	 * bgId: 桥梁id
	 * */
	private void setSpanNum(RadioButton rb, int bgId) {
		String tableName,fieldName,option;
		
		if ("桥墩".equals(rb.getText())) {
			tableName = "pier_detail"; // 待查询的数据库表
			fieldName = "pier_nums"; // 表中字段
			option = "PIER"; // 选择项（桥墩、盖梁、系梁...）
		}
		else if ("盖梁".equals(rb.getText())) {
			tableName = "pier_detail";
			fieldName = "bent_cap_nums";
			option = "BENTCAP";
		}
		else if ("系梁".equals(rb.getText())) {
			tableName = "pier_detail";
			fieldName = "tie_beam_nums";
			option = "TIEBEAM";
		}
		else if ("桥台身".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "abutment_num";
			option = "ATBODY";
		}
		else if ("桥台帽".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "abutment_num";
			option = "ATCAPPING";			
		}
		else if ("墩台基础".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "pa_num";
			option = "PA";
		}
		else if ("河床".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "bed_num";
			option = "BED";			
		}
		else if ("调治构造物".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "reg_structure";
			option = "REGSTRUC";			
		}
		else if ("翼墙、耳墙".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "wing_wall";
			option = "WINGWALL";			
		}
		else if ("锥坡".equals(rb.getText())) {
			tableName = "parts1";
			fieldName = "conical_slope";
			option = "CONSLOPE";			
		}
		else {
			tableName = "parts1";
			fieldName = "protection_slope";
			option = "PROSLOPE";			
		}
		// 根据id查找数据
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'");
		
		if (cursor.moveToFirst()) {
			if (tableName == "pier_detail") {
				if (fieldName == "pier_nums") { // 如果是桥墩
					if (spanNum.getVisibility() != View.VISIBLE)
						spanNum.setVisibility(View.VISIBLE); // 将墩号设为可见
					// 拆分墩号
					String[] lineCodes = cursor.getString(cursor.getColumnIndex(fieldName)).split("\n");
					// 拆分桥墩编号
					String[] itemCodes = cursor.getString(cursor.getColumnIndex(fieldName)).replace("\n", "").split("; ");
					// 设置数组长度为lineCodes的长度
					SpannableString[] ss = new SpannableString[lineCodes.length];
					final String optionStr = option;
					final String[] itemNum = itemCodes;
					final int bg_id = bgId;
					
					for (int i = 0; i < lineCodes.length; i++) {
						// 将墩号赋值到该数组
						ss[i] = new SpannableString(lineCodes[i].split("-")[0]); // 定义可点击的字符串数组
						final String num = i + 1 + ""; // 设置选中的墩号
						// 设置墩号点击事件
						ss[i].setSpan(new ClickableSpan() {
							
							@Override
							public void onClick(View widget) {
								// TODO Auto-generated method stub
								setSpanDetail(itemNum, optionStr, num, bg_id); // 设置编号详情
							}
						}, 0, ss[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						
						spanNum.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
						spanNum.append(ss[i]); // 将跨号添加到TextView中
						spanNum.append("\t\t");					
						spanNum.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
					}
				}
				else { // 处理盖梁、系梁
					String[] itemCodes = cursor.getString(cursor.getColumnIndex(fieldName)).split(", ");
					if (spanNum.getVisibility() != View.GONE)
						spanNum.setVisibility(View.GONE); // 将编号详情设为不可见
					setSpanDetail(itemCodes, option, bgId); // 设置编号详情
				}
			}
			else {
				String itemNum = cursor.getString(cursor.getColumnIndex(fieldName));
				if (itemNum != null) {
					String[] itemCodes;
					if (option == "WINGWALL" || option == "CONSLOPE") { // 处理翼墙、耳墙和锥坡
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
						spanNum.setVisibility(View.GONE); // 将墩号设为不可见
					setSpanDetail(itemCodes, option, bgId); // 设置编号详情
				}				
			}			
		}		
	}
	
	/** 设置编号详情
	 * ItemCodes: 编号数组
	 * option: 选择项（桥墩）
	 * num: 选择墩号
	 * bgId: 桥梁id
	 * */
	private void setSpanDetail(String[] ItemCodes, String option, String num, int bgId) {
		// 如果编号详情不可见，设为可见
		if (spanDetail.getVisibility() != View.VISIBLE)
			spanDetail.setVisibility(View.VISIBLE);
	
		spanDetail.setTextSize(25);
		if (option.equals("PIER"))			
			spanDetail.setText("桥墩:\t");
		
		final String optionStr = option;
		final int bg_id = bgId;
		
		// 定义可点击的字符串数组，数组长度为ItemCodes的长度
		SpannableString[] ss = new SpannableString[ItemCodes.length];
		
		for (int j = 0; j < ItemCodes.length; j++) {
			if (ItemCodes[j].split("-")[0].equals(num)) {
				final String str = ItemCodes[j];
				// 将编号赋值到该数组
				ss[j] = new SpannableString(ItemCodes[j]);
				// 设置编号点击事件
				ss[j].setSpan(new ClickableSpan() {

					@Override
					public void onClick(View widget) {
						// TODO Auto-generated method stub
						setDiseaseDetail(optionStr, str, bg_id); // 设置病害详情
					}														
				}, 0, ss[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				spanDetail.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
				spanDetail.append(ss[j]); // 将编号添加到TextView中
				spanDetail.append("\t\t");					
				spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
			}
		}		
	}
	
	/** 设置编号详情（重载）
	 * ItemCodes: 编号数组
	 * option: 选择项（盖梁、系梁...）
	 * bgId: 桥梁id
	 * */
	private void setSpanDetail(String[] ItemCodes, String option,int bgId) {
		// 如果编号详情不可见，设为可见
		if (spanDetail.getVisibility() != View.VISIBLE)
			spanDetail.setVisibility(View.VISIBLE);
	
		spanDetail.setTextSize(25);
		if (option.equals("BENTCAP"))
			spanDetail.setText("盖梁:\t");
		else if (option.equals("TIEBEAM"))
			spanDetail.setText("系梁:\t");
		else if (option.equals("ATBODY"))
			spanDetail.setText("桥台身:\t");
		else if (option.equals("ATCAPPING"))
			spanDetail.setText("桥台帽:\t");
		else if (option.equals("PA"))
			spanDetail.setText("墩台基础:\t");
		else if (option.equals("BED"))
			spanDetail.setText("河床:\t");		
		else if (option.equals("REGSTRUC"))
			spanDetail.setText("调治构造物:\t");
		else if (option.equals("WINGWALL"))
			spanDetail.setText("翼墙、耳墙:\t");
		else if (option.equals("CONSLOPE"))
			spanDetail.setText("锥坡:\t");
		else
			spanDetail.setText("护坡:\t");
		
		final String optionStr = option;
		final int bg_id = bgId;
		
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
					// TODO Auto-generated method stub
					setDiseaseDetail(optionStr, str, bg_id); // 设置病害详情
				}														
			}, 0, ss[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			spanDetail.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
			spanDetail.append(ss[j]); // 将编号添加到TextView中
			spanDetail.append("\t\t");					
			spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
		}		
	}
	
	/** 设置病害详情
	 * girderCodes: 编号数组
	 * option: 选择项（主梁、湿接缝、支座）
	 * Item: 选择跨号
	 * bgId: 桥梁id
	 * */
	private void setDiseaseDetail(String optionStr, String Item, int bdId) {
		// 如果病害详情不可见，设为可见
		if (downDetail.getVisibility() != View.VISIBLE)
			downDetail.setVisibility(View.VISIBLE);
		Bundle bd = new Bundle();
		bd.putString(optionStr, Item);
		bd.putInt("BRIDGE_ID", bdId);
		
		// 创建Fragment对象
		Fragment frag = new Fragment();
		
		if (optionStr.equals("PIER"))
			frag = new pierFragment();
		else if (optionStr.equals("BENTCAP") || optionStr.equals("TIEBEAM"))
			frag = new beamFragment();
		else if (optionStr.equals("ATBODY") || optionStr.equals("ATCAPPING") || optionStr.equals("PA"))
			frag = new abutmentFragment();
//		else
//			frag = new supportFragment();
		
		// 向Fragment传入参数
		frag.setArguments(bd);
		// 使用fragment替换bridge_detail_container容器当前显示的Fragment
		getChildFragmentManager().beginTransaction()
			.replace(R.id.down_detail_container, frag)
			.commit();
	}
}
