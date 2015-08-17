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

	// 重写该方法，该方法返回的View将作为Fragment显示的组件
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{		
		// 加载/res/layout/目录下的fragment_bridge_detail.xml布局文件
		View rootView = inflater.inflate(R.layout.fragment_bridge_detail, container, false);
		Bundle args = getArguments();
		if (args != null)
		{
			// 显示title属性
			((TextView) rootView.findViewById(R.id.tilte)).setText(args.getString("title"));
			// 让book_desc文本框显示book对象的desc属性
			//((TextView) rootView.findViewById(R.id.book_desc)).setText(book.desc);	
		}
		return rootView;
	}
}
