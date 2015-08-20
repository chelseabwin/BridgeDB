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
	private TextView girder; // ����
	private TextView wetJoint; // ʪ�ӷ�
	private TextView support; // ֧��
	
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
			// ����id�����ϲ����ع�������
			Cursor cursor1 = db.queryData("*", "load_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
			
			if (cursor1.moveToFirst()) 
			{
				girder.setTextSize(20);
				girder.setText("\t����:\t");
				// ����ϲ����ع������
				String[] girderCodes = cursor1.getString(cursor1.getColumnIndex("load_nums")).replace("\n", "").split("; ");
				// ����ɵ�����ַ������飬���鳤��ΪgirderCodes�ĳ���
				SpannableString[] ss = new SpannableString[girderCodes.length];
				for (int i = 0; i < girderCodes.length; i++) 
				{
					// ���ϲ����ع�����Ÿ�ֵ��������,��ȥ�����з�
					ss[i] = new SpannableString(girderCodes[i]);
					// �����ϲ����ع�����ŵ���¼�
					ss[i].setSpan(new ClickableSpan()
					{
						@Override
						public void onClick(View widget) 
						{
							// TODO Auto-generated method stub
							System.out.println(widget);
							
						}
						
					}, 0, girderCodes[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					
					girder.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
					girder.append(ss[i]); // �������ӵ�TextView��
					girder.append("\t\t");					
					girder.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�
				}
			}
			
			// ����id�����ϲ�һ�㹹������
			Cursor cursor2 = db.queryData("*", "general_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
			
			if (cursor2.moveToFirst()) 
			{
				wetJoint.setText(cursor2.getString(cursor2.getColumnIndex("general_nums")));
			}
			
			// ����id�����ϲ�һ�㹹������
			Cursor cursor3 = db.queryData("*", "support_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
			
			if (cursor3.moveToFirst()) 
			{
				support.setText(cursor3.getString(cursor3.getColumnIndex("support_nums")));
			}
		}
		
		return rootView;
	}
}
