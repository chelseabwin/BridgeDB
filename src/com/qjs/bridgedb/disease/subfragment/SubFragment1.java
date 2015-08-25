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
				girder.setText("����:\t");
				// ����ϲ����ع������
				String[] girderCodes = cursor1.getString(cursor1.getColumnIndex("load_nums")).replace("\n", "").split("; ");
				// ����ɵ�����ַ������飬���鳤��ΪgirderCodes�ĳ���
				SpannableString[] ss = new SpannableString[girderCodes.length];
				for (int i = 0; i < girderCodes.length; i++) 
				{
					final String str = girderCodes[i];
					// ���ϲ����ع�����Ÿ�ֵ��������,��ȥ�����з�
					ss[i] = new SpannableString(girderCodes[i]);
					// �����ϲ����ع�����ŵ���¼�
					ss[i].setSpan(new ClickableSpan()
					{						
						@Override
						public void onClick(View widget) 
						{							
							// TODO Auto-generated method stub
							Bundle bd = new Bundle();
							bd.putString("GIRDER", str);
							
							// ����BridgeDetailFragment����
							girderFragment gf = new girderFragment();
							// ��Fragment�������
							gf.setArguments(bd);
							// ʹ��fragment�滻bridge_detail_container������ǰ��ʾ��Fragment
							getChildFragmentManager().beginTransaction()
								.replace(R.id.upper_detail_container, gf)
								.commit();
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
				wetJoint.setTextSize(20);
				wetJoint.setText("ʪ�ӷ�:\t");
				// ����ϲ�һ�㹹�����
				String[] wetJointCodes = cursor2.getString(cursor2.getColumnIndex("general_nums")).replace("\n", "").split("; ");
				// ����ɵ�����ַ������飬���鳤��ΪwetJointCodes�ĳ���
				SpannableString[] ss = new SpannableString[wetJointCodes.length];
				for (int i = 0; i < wetJointCodes.length; i++) 
				{
					// ���ϲ�һ�㹹����Ÿ�ֵ��������,��ȥ�����з�
					ss[i] = new SpannableString(wetJointCodes[i]);
					// �����ϲ�һ�㹹����ŵ���¼�
					ss[i].setSpan(new ClickableSpan()
					{
						@Override
						public void onClick(View widget) 
						{
							// TODO Auto-generated method stub
							System.out.println(widget);
							
						}
						
					}, 0, wetJointCodes[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					
					wetJoint.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
					wetJoint.append(ss[i]); // �������ӵ�TextView��
					wetJoint.append("\t\t");					
					wetJoint.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�					
				}
			}
			
			// ����id����֧������
			Cursor cursor3 = db.queryData("*", "support_detail", "bg_id='" + args.getInt("BRIDGE_ID") + "'");
			
			if (cursor3.moveToFirst()) 
			{
				support.setTextSize(20);
				support.setText("֧��:\t");
				// ���֧�����
				String[] supportCodes = cursor3.getString(cursor3.getColumnIndex("support_nums")).replace("\n", "").split("; ");
				// ����ɵ�����ַ������飬���鳤��ΪsupportCodes�ĳ���
				SpannableString[] ss = new SpannableString[supportCodes.length];
				for (int i = 0; i < supportCodes.length; i++) 
				{
					// ��֧����Ÿ�ֵ��������,��ȥ�����з�
					ss[i] = new SpannableString(supportCodes[i]);
					// ����֧����ŵ���¼�
					ss[i].setSpan(new ClickableSpan()
					{
						@Override
						public void onClick(View widget) 
						{
							// TODO Auto-generated method stub
							System.out.println(widget);
							
						}
						
					}, 0, supportCodes[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					
					support.setHighlightColor(Color.TRANSPARENT); // ���õ�������ɫΪ͸���������һֱ���ָ���
					support.append(ss[i]); // �������ӵ�TextView��
					support.append("\t\t");					
					support.setMovementMethod(LinkMovementMethod.getInstance()); // ��ʼ��Ӧ����¼�					
				}
			}
		}
		
		return rootView;
	}
}
