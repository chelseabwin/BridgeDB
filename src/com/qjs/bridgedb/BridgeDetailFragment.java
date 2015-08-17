package com.qjs.bridgedb;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BridgeDetailFragment extends Fragment
{	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	// ��д�÷������÷������ص�View����ΪFragment��ʾ�����
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{		
		// ����/res/layout/Ŀ¼�µ�fragment_bridge_detail.xml�����ļ�
		View rootView = inflater.inflate(R.layout.fragment_bridge_detail, container, false);
		Bundle args = getArguments();
		if (args != null)
		{
			// ��ʾtitle����
			((TextView) rootView.findViewById(R.id.tilte)).setText(args.getString("title"));
			// ��book_desc�ı�����ʾbook�����desc����
			//((TextView) rootView.findViewById(R.id.book_desc)).setText(book.desc);	
		}
		return rootView;
	}
}
