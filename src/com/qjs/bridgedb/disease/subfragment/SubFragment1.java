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
			if (bgType.charAt(1) == '1') { // 板桥
				up_rg1.setVisibility(View.GONE);
				up_rg2.setVisibility(View.VISIBLE);
				up_rg3.setVisibility(View.GONE);
				up_rg4.setVisibility(View.GONE);
				up_rg5.setVisibility(View.GONE);
				up_rg = up_rg2;
			}
			else if (bgType.charAt(1) == '2') { // 梁桥
				up_rg1.setVisibility(View.VISIBLE);
				up_rg2.setVisibility(View.GONE);
				up_rg3.setVisibility(View.GONE);
				up_rg4.setVisibility(View.GONE);
				up_rg5.setVisibility(View.GONE);
				up_rg = up_rg1;
			}
			else if (bgType.charAt(1) == '4') { // 拱桥
				if (bgType.charAt(3) == '1' || bgType.charAt(3) == '2' 
						|| bgType.charAt(3) == '3' || bgType.charAt(3) == '4') { // 板拱、肋拱、箱形拱、双曲拱
					up_rg1.setVisibility(View.GONE);
					up_rg2.setVisibility(View.GONE);
					up_rg3.setVisibility(View.VISIBLE);
					up_rg4.setVisibility(View.GONE);
					up_rg5.setVisibility(View.GONE);
					up_rg = up_rg3;
				}
				else if (bgType.charAt(3) == '5' || bgType.charAt(3) == '6') { // 桁架拱、刚架拱
					up_rg1.setVisibility(View.GONE);
					up_rg2.setVisibility(View.GONE);
					up_rg3.setVisibility(View.GONE);
					up_rg4.setVisibility(View.VISIBLE);
					up_rg5.setVisibility(View.GONE);
					up_rg = up_rg4;
				}
			}
			else { // 其他
				up_rg1.setVisibility(View.GONE);
				up_rg2.setVisibility(View.GONE);
				up_rg3.setVisibility(View.GONE);
				up_rg4.setVisibility(View.GONE);
				up_rg5.setVisibility(View.VISIBLE);
				up_rg = up_rg5;
			}
		}
		
		spanNum = (TextView) rootView.findViewById(R.id.tv_span_num); // 跨号
		spanDetail = (TextView) rootView.findViewById(R.id.tv_span_detail); // 编号
		upperDetail = (FrameLayout) rootView.findViewById(R.id.upper_detail_container); // 病害容器		
		
		if (args != null) {
			up_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (spanDetail.getVisibility() != View.GONE)
						spanDetail.setVisibility(View.GONE); // 将编号详情设为不可见
					if (upperDetail.getVisibility() != View.GONE)
						upperDetail.setVisibility(View.GONE); // 将病害详情设为不可见
					
					spanNum.setTextSize(25);
					spanNum.setText("跨号:\t");
					
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
		
		if ("主梁".equals(rb.getText()) || "空心板".equals(rb.getText()) || "主拱圈".equals(rb.getText()) 
				|| "钢、桁架拱片".equals(rb.getText()) || "上部承重构件".equals(rb.getText())) {
			tableName = "load_detail"; // 待查询的数据库表
			fieldName = "load_nums"; // 表中字段
			option = "GIRDER"; // 选择项
		}
		else if ("湿接缝".equals(rb.getText()) || "横隔板".equals(rb.getText()) || "铰缝".equals(rb.getText()) 
				|| "拱上结构".equals(rb.getText()) || "横向联结系".equals(rb.getText()) || "上部一般构件".equals(rb.getText())) {
			tableName = "general_detail";
			fieldName = "general_nums";
			option = "WETJOINT";
		}
		else {
			tableName = "support_detail";
			fieldName = "support_nums";
			option = "SUPPORT";			
		}
		// 根据id查找数据
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'");
		
		if (cursor.moveToFirst()) {
			// 拆分跨号
			String[] lineCodes = cursor.getString(cursor.getColumnIndex(fieldName)).split("\n");
			// 拆分上部承重构件编号
			String[] itemCodes = cursor.getString(cursor.getColumnIndex(fieldName)).replace("\n", "").split("; ");
			// 设置数组长度为lineCodes的长度
			SpannableString[] ss = new SpannableString[lineCodes.length];
			final String optionStr = option;
			final String itemName = rb.getText().toString();
			final String[] itemNum = itemCodes;
			final String bg_id = bgId;
			
			for (int i = 0; i < lineCodes.length; i++) {
				// 将跨号赋值到该数组
				ss[i] = new SpannableString(lineCodes[i].split("-")[0]); // 定义可点击的字符串数组
				
				String tap = ""; 
				if (tableName == "general_detail" || tableName == "support_detail") {
					tap = i + ""; // 设置选中的跨号
				}
				else {
					tap = i + 1 + ""; // 设置选中的跨号
				}
				final String num = tap;
				
				// 设置跨号点击事件
				ss[i].setSpan(new ClickableSpan() {
					
					@Override
					public void onClick(View widget) {
						setSpanDetail(itemNum, optionStr, itemName, num, bg_id); // 设置编号详情
					}
				}, 0, ss[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				spanNum.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
				spanNum.append(ss[i]); // 将跨号添加到TextView中
				spanNum.append("\t\t");					
				spanNum.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
			}
		}		
	}
	
	/** 设置编号详情
	 * ItemCodes: 编号数组
	 * option: 选择项
	 * itemName: 选择项名称
	 * num: 选择跨号
	 * bgId: 桥梁id
	 * */
	private void setSpanDetail(String[] ItemCodes, String option, String itemName, String num, String bgId) {
		// 如果编号详情不可见，设为可见
		if (spanDetail.getVisibility() != View.VISIBLE)
			spanDetail.setVisibility(View.VISIBLE);
	
		spanDetail.setTextSize(25);
		spanDetail.setText(itemName + ":\t");
		
		final String optionStr = option;
		final String itemNameStr = itemName;
		final String bg_id = bgId;
		
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
						setDiseaseDetail(optionStr, itemNameStr, str, bg_id); // 设置病害详情
					}														
				}, 0, ss[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				spanDetail.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
				spanDetail.append(ss[j]); // 将编号添加到TextView中
				spanDetail.append("\t\t");					
				spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
			}
		}		
	}
	
	/** 设置病害详情
	 * girderCodes: 编号数组
	 * option: 选择项
	 * ItemName: 选择项名称
	 * Item: 选择跨号
	 * bgId: 桥梁id
	 * */
	private void setDiseaseDetail(String optionStr, String ItemName, String ItemNum, String bdId) {
		// 如果病害详情不可见，设为可见
		if (upperDetail.getVisibility() != View.VISIBLE)
			upperDetail.setVisibility(View.VISIBLE);
		Bundle bd = new Bundle();
		bd.putString("ITEM_NAME", ItemName);
		bd.putString(optionStr, ItemNum);
		bd.putString("BRIDGE_ID", bdId);
		
		// 创建Fragment对象
		Fragment frag = new Fragment();
		
		if (optionStr.equals("GIRDER"))
			frag = new girderFragment();
		else if (optionStr.equals("WETJOINT"))
			frag = new wetJointFragment();
		else
			frag = new supportFragment();
		
		// 向Fragment传入参数
		frag.setArguments(bd);
		// 使用fragment替换bridge_detail_container容器当前显示的Fragment
		getChildFragmentManager().beginTransaction()
			.replace(R.id.upper_detail_container, frag)
			.commit();
	}
}
