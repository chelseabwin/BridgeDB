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
import android.widget.TextView;

public class SubFragment1 extends Fragment
{
	private TextView girder; // 主梁
	private TextView wetJoint; // 湿接缝
	private TextView support; // 支座
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_tab_page1, container, false);
		
		girder = (TextView) rootView.findViewById(R.id.tv_girder);
		wetJoint = (TextView) rootView.findViewById(R.id.tv_wet_joint);
		support = (TextView) rootView.findViewById(R.id.tv_support);
		
		Bundle args = getArguments();
		if (args != null)
		{
			DbOperation db = new DbOperation(this.getActivity());
			// 根据id查找上部承重构件数据
			Cursor cursor1 = db.queryData("*", "load_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
			
			if (cursor1.moveToFirst()) 
			{
				girder.setTextSize(20);
				girder.setText("主梁:\t");
				// 拆分上部承重构件编号
				String[] girderCodes = cursor1.getString(cursor1.getColumnIndex("load_nums")).replace("\n", "").split("; ");
				// 定义可点击的字符串数组，数组长度为girderCodes的长度
				SpannableString[] ss = new SpannableString[girderCodes.length];
				for (int i = 0; i < girderCodes.length; i++) 
				{
					final String str = girderCodes[i];
					// 将上部承重构件编号赋值到该数组,并去掉换行符
					ss[i] = new SpannableString(girderCodes[i]);
					// 设置上部承重构件编号点击事件
					ss[i].setSpan(new ClickableSpan()
					{						
						@Override
						public void onClick(View widget) 
						{							
							// TODO Auto-generated method stub
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
						
					}, 0, girderCodes[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					
					girder.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
					girder.append(ss[i]); // 将编号添加到TextView中
					girder.append("\t\t");					
					girder.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件
				}
			}
			
			// 根据id查找上部一般构件数据
			Cursor cursor2 = db.queryData("*", "general_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
			
			if (cursor2.moveToFirst()) 
			{
				wetJoint.setTextSize(20);
				wetJoint.setText("湿接缝:\t");
				// 拆分上部一般构件编号
				String[] wetJointCodes = cursor2.getString(cursor2.getColumnIndex("general_nums")).replace("\n", "").split("; ");
				// 定义可点击的字符串数组，数组长度为wetJointCodes的长度
				SpannableString[] ss = new SpannableString[wetJointCodes.length];
				for (int i = 0; i < wetJointCodes.length; i++) 
				{
					// 将上部一般构件编号赋值到该数组,并去掉换行符
					ss[i] = new SpannableString(wetJointCodes[i]);
					// 设置上部一般构件编号点击事件
					ss[i].setSpan(new ClickableSpan()
					{
						@Override
						public void onClick(View widget) 
						{
							// TODO Auto-generated method stub
							System.out.println(widget);
							
						}
						
					}, 0, wetJointCodes[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					
					wetJoint.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
					wetJoint.append(ss[i]); // 将编号添加到TextView中
					wetJoint.append("\t\t");					
					wetJoint.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件					
				}
			}
			
			// 根据id查找支座数据
			Cursor cursor3 = db.queryData("*", "support_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
			
			if (cursor3.moveToFirst()) 
			{
				support.setTextSize(20);
				support.setText("支座:\t");
				// 拆分支座编号
				String[] supportCodes = cursor3.getString(cursor3.getColumnIndex("support_nums")).replace("\n", "").split("; ");
				// 定义可点击的字符串数组，数组长度为supportCodes的长度
				SpannableString[] ss = new SpannableString[supportCodes.length];
				for (int i = 0; i < supportCodes.length; i++) 
				{
					// 将支座编号赋值到该数组,并去掉换行符
					ss[i] = new SpannableString(supportCodes[i]);
					// 设置支座编号点击事件
					ss[i].setSpan(new ClickableSpan()
					{
						@Override
						public void onClick(View widget) 
						{
							// TODO Auto-generated method stub
							System.out.println(widget);
							
						}
						
					}, 0, supportCodes[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					
					support.setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明，否则会一直出现高亮
					support.append(ss[i]); // 将编号添加到TextView中
					support.append("\t\t");					
					support.setMovementMethod(LinkMovementMethod.getInstance()); // 开始响应点击事件					
				}
			}
		}
		
		return rootView;
	}
}
