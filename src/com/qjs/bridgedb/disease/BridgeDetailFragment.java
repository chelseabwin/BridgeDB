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

public class BridgeDetailFragment extends Fragment {	 
    private FragmentTabHost mTabHost; // ����FragmentTabHost����  
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// ��д�÷������÷������ص�View����ΪFragment��ʾ�����
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		// ����/res/layout/Ŀ¼�µ�fragment_bridge_detail.xml�����ļ�
		View rootView = inflater.inflate(R.layout.fragment_bridge_detail, null);
		
		mTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		
		// ���ñ�ǩҳ���Լ�bundle����
		mTabHost.addTab(mTabHost.newTabSpec("sub1").setIndicator("�ϲ��ṹ"),
				SubFragment1.class, getArguments());

		mTabHost.addTab(mTabHost.newTabSpec("sub2").setIndicator("�²��ṹ"),
				SubFragment2.class, getArguments());

		mTabHost.addTab(mTabHost.newTabSpec("sub3").setIndicator("����ϵ"),
				SubFragment3.class, getArguments());
		
		return rootView;
	}
}
