package com.qjs.bridgedb.disease.subfragment;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;

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
				girder.setText("\t主梁:\t");
				// 拆分上部承重构件编号
				String[] girderCodes = cursor1.getString(cursor1.getColumnIndex("load_nums")).replace("\n", "").split("; ");
				// 定义可点击的字符串数组，数组长度为girderCodes的长度
				SpannableString[] ss = new SpannableString[girderCodes.length];
				for (int i = 0; i < girderCodes.length; i++) 
				{
					// 将上部承重构件编号赋值到该数组,并去掉换行符
					ss[i] = new SpannableString(girderCodes[i]);
					// 设置上部承重构件编号点击事件
					ss[i].setSpan(new ClickableSpan()
					{
						@Override
						public void onClick(View widget) 
						{
							// TODO Auto-generated method stub
							System.out.println(widget);
							
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
				wetJoint.setText(cursor2.getString(cursor2.getColumnIndex("general_nums")));
			}
			
			// 根据id查找上部一般构件数据
			Cursor cursor3 = db.queryData("*", "support_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
			
			if (cursor3.moveToFirst()) 
			{
				support.setText(cursor3.getString(cursor3.getColumnIndex("support_nums")));
			}
		}
		
		return rootView;
	}
}
