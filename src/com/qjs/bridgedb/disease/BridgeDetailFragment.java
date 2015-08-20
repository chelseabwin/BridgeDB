package com.qjs.bridgedb.disease;

import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.subfragment.SubFragment1;
import com.qjs.bridgedb.disease.subfragment.SubFragment2;
import com.qjs.bridgedb.disease.subfragment.SubFragment3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BridgeDetailFragment extends Fragment
{	 
    private FragmentTabHost mTabHost; // 定义FragmentTabHost对象  
    
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
		View rootView = inflater.inflate(R.layout.fragment_bridge_detail, null);
		
		mTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		
		mTabHost.addTab(mTabHost.newTabSpec("sub1").setIndicator("上部结构"),
				SubFragment1.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("sub2").setIndicator("下部结构"),
				SubFragment2.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("sub3").setIndicator("桥面系"),
				SubFragment3.class, null);
		
		
		
		
//		Bundle args = getArguments();
//		
//		if (args != null)
//		{
//			DbOperation db = new DbOperation(this.getActivity());
//			// 根据id查找数据
//			Cursor cursor = db.queryData("*", "base1", "id=" + args.getInt("BRIDGE_ID"));
//			
//			if (cursor.moveToFirst()) 
//			{
//				// 显示title属性
//				//((TextView) rootView.findViewById(R.id.tilte)).setText(cursor.getString(cursor.getColumnIndex("bridge_name")));
//				// 让book_desc文本框显示book对象的desc属性
//				//((TextView) rootView.findViewById(R.id.book_desc)).setText(book.desc);
//			}
//		}
		return rootView;
	}
}
