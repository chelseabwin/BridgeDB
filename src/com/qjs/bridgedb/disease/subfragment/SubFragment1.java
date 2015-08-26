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
		
		up_rg = (RadioGroup) rootView.findViewById(R.id.up_rg); // 属性复选框		
		spanNum = (TextView) rootView.findViewById(R.id.tv_span_num); // 跨号
		spanDetail = (TextView) rootView.findViewById(R.id.tv_span_detail); // 编号
		upperDetail = (FrameLayout) rootView.findViewById(R.id.upper_detail_container); // 病害容器
		
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
					spanNum.setText("跨号:\t");
					
					Cursor cursor = null;
					// 选择“主梁”
					if (checkedId == girder.getId()) {
						// 根据id查找上部承重构件数据
						cursor = db.queryData("*", "load_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
						
						if (cursor.moveToFirst()) {
							// 拆分跨号
							String[] gCodes = cursor.getString(cursor.getColumnIndex("load_nums")).split("\n");
							// 拆分上部承重构件编号
							final String[] girderCodes = cursor.getString(cursor.getColumnIndex("load_nums")).replace("\n", "").split("; ");
							
							// 设置数组长度为gCodes的长度
							SpannableString[] ss1 = new SpannableString[gCodes.length];
							
							for (int i = 0; i < gCodes.length; i++) {
								// 将跨号赋值到该数组
								ss1[i] = new SpannableString(gCodes[i].split("-")[0]); // 定义可点击的字符串数组
								final String num = i + 1 + "";
								// 设置上部承重构件编号点击事件
								ss1[i].setSpan(new ClickableSpan() {
									
									@Override
									public void onClick(View widget) {
										// TODO Auto-generated method stub
										spanDetail.setVisibility(View.VISIBLE);
										spanDetail.setTextSize(20);
										spanDetail.setText("主梁:\t");
										// 定义可点击的字符串数组，数组长度为girderCodes的长度
										SpannableString[] ss2 = new SpannableString[girderCodes.length];
										
										for (int j = 0; j < girderCodes.length; j++) {
											if (girderCodes[j].split("-")[0].equals(num)) {
												final String str = girderCodes[j];
												// 将主梁编号赋值到该数组
												ss2[j] = new SpannableString(girderCodes[j]);
												// 将上部承重构件编号赋值到该数组
												ss2[j] = new SpannableString(girderCodes[j]);
												// 设置上部承重构件编号点击事件
												ss2[j].setSpan(new ClickableSpan() {

													@Override
													public void onClick(View widget) {
														// TODO Auto-generated method stub
														upperDetail.setVisibility(View.VISIBLE);
														Bundle bd = new Bundle();
														bd.putString("GIRDER", str);
														
														// 创建BridgeDetailFragment对象
														girderFragment gf = new girderFragment();
														// 向Fragment传入参数
														gf.setArguments(bd);
														// 使用fragment替换bridge_detail_container容器当前显示的Fragment
														getChildFragmentManager().beginTransaction()
															.replace(R.id.upper_detail_container, gf)
															.commit();
													}														
												}, 0, ss2[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
												
												spanDetail.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
												spanDetail.append(ss2[j]); // 将编号添加到TextView中
												spanDetail.append("\t\t");					
												spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
											}
										}
									}
								}, 0, ss1[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								
								spanNum.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
								spanNum.append(ss1[i]); // 将编号添加到TextView中
								spanNum.append("\t\t");					
								spanNum.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
							}
						}						
					}
					// 选择“湿接缝”
					else if (checkedId == wetJoint.getId()) {
						// 根据id查找上部一般构件数据
						cursor = db.queryData("*", "general_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
						
						if (cursor.moveToFirst()) {
							// 拆分跨号
							String[] gCodes = cursor.getString(cursor.getColumnIndex("general_nums")).split("\n");
							// 拆分上部一般构件编号
							final String[] wetJointCodes = cursor.getString(cursor.getColumnIndex("general_nums")).replace("\n", "").split("; ");
							
							// 设置数组长度为gCodes的长度
							SpannableString[] ss1 = new SpannableString[gCodes.length];
							
							for (int i = 0; i < gCodes.length; i++) {
								// 将跨号赋值到该数组
								ss1[i] = new SpannableString(gCodes[i].split("-")[0]); // 定义可点击的字符串数组
								final String num = i + 1 + "";
								// 设置上部承重构件编号点击事件
								ss1[i].setSpan(new ClickableSpan() {
									
									@Override
									public void onClick(View widget) {
										// TODO Auto-generated method stub
										spanDetail.setVisibility(View.VISIBLE);
										spanDetail.setTextSize(20);
										spanDetail.setText("湿接缝:\t");
										// 定义可点击的字符串数组，数组长度为girderCodes的长度
										SpannableString[] ss2 = new SpannableString[wetJointCodes.length];
										
										for (int j = 0; j < wetJointCodes.length; j++) {
											if (wetJointCodes[j].split("-")[0].equals(num)) {
												final String str = wetJointCodes[j];
												// 将主梁编号赋值到该数组
												ss2[j] = new SpannableString(wetJointCodes[j]);
												// 将上部承重构件编号赋值到该数组
												ss2[j] = new SpannableString(wetJointCodes[j]);
												// 设置上部承重构件编号点击事件
												ss2[j].setSpan(new ClickableSpan() {

													@Override
													public void onClick(View widget) {
														// TODO Auto-generated method stub
														//upperDetail.setVisibility(View.VISIBLE);
//														Bundle bd = new Bundle();
//														bd.putString("WETJOINT", str);
//														
//														// 创建BridgeDetailFragment对象
//														girderFragment gf = new girderFragment();
//														// 向Fragment传入参数
//														gf.setArguments(bd);
//														// 使用fragment替换bridge_detail_container容器当前显示的Fragment
//														getChildFragmentManager().beginTransaction()
//															.replace(R.id.upper_detail_container, gf)
//															.commit();
													}														
												}, 0, ss2[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
												
												spanDetail.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
												spanDetail.append(ss2[j]); // 将编号添加到TextView中
												spanDetail.append("\t\t");					
												spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
											}
										}
									}
								}, 0, ss1[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								
								spanNum.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
								spanNum.append(ss1[i]); // 将编号添加到TextView中
								spanNum.append("\t\t");					
								spanNum.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
							}
						}						
					}
					// 选择“支座”
					else {
						// 根据id查找上部一般构件数据
						cursor = db.queryData("*", "support_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
						
						if (cursor.moveToFirst()) {
							// 拆分跨号
							String[] gCodes = cursor.getString(cursor.getColumnIndex("support_nums")).split("\n");
							// 拆分支座编号
							final String[] supportCodes = cursor.getString(cursor.getColumnIndex("support_nums")).replace("\n", "").split("; ");
							
							// 设置数组长度为gCodes的长度
							SpannableString[] ss1 = new SpannableString[gCodes.length];
							
							for (int i = 0; i < gCodes.length; i++) {
								// 将跨号赋值到该数组
								ss1[i] = new SpannableString(gCodes[i].split("-")[0]); // 定义可点击的字符串数组
								final String num = i + 1 + "";
								// 设置上部承重构件编号点击事件
								ss1[i].setSpan(new ClickableSpan() {
									
									@Override
									public void onClick(View widget) {
										// TODO Auto-generated method stub
										spanDetail.setVisibility(View.VISIBLE);
										spanDetail.setTextSize(20);
										spanDetail.setText("支座:\t");
										// 定义可点击的字符串数组，数组长度为girderCodes的长度
										SpannableString[] ss2 = new SpannableString[supportCodes.length];
										
										for (int j = 0; j < supportCodes.length; j++) {
											if (supportCodes[j].split("-")[0].equals(num)) {
												final String str = supportCodes[j];
												// 将主梁编号赋值到该数组
												ss2[j] = new SpannableString(supportCodes[j]);
												// 将上部承重构件编号赋值到该数组
												ss2[j] = new SpannableString(supportCodes[j]);
												// 设置上部承重构件编号点击事件
												ss2[j].setSpan(new ClickableSpan() {

													@Override
													public void onClick(View widget) {
														// TODO Auto-generated method stub
														//upperDetail.setVisibility(View.VISIBLE);
//														Bundle bd = new Bundle();
//														bd.putString("SUPPORT", str);
//														
//														// 创建BridgeDetailFragment对象
//														girderFragment gf = new girderFragment();
//														// 向Fragment传入参数
//														gf.setArguments(bd);
//														// 使用fragment替换bridge_detail_container容器当前显示的Fragment
//														getChildFragmentManager().beginTransaction()
//															.replace(R.id.upper_detail_container, gf)
//															.commit();
													}														
												}, 0, ss2[j].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
												
												spanDetail.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
												spanDetail.append(ss2[j]); // 将编号添加到TextView中
												spanDetail.append("\t\t");					
												spanDetail.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
											}
										}
									}
								}, 0, ss1[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								
								spanNum.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
								spanNum.append(ss1[i]); // 将编号添加到TextView中
								spanNum.append("\t\t");					
								spanNum.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
							}
						}						
					}
				}				
			});
		}
		
		return rootView;
	}
}
