package com.qjs.bridgedb;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BridgeListFragment extends ListFragment
{
	private Callbacks mCallbacks;
	private ArrayList<String> data = new ArrayList<String>();
	
	// ͨ���ص��ӿڽ���fragment������activity����
	public interface Callbacks 
	{
		public void onItemSelected(Integer id);
	}
		
	// fragment����ʱ���ص�
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Bundle args = getArguments();
		
		if (args != null) 
		{
			Set<String> keySet = args.keySet();
			
			for (String key : keySet) 
			{
				data.add(key + ". " + args.getString(key));
			}		
		}
		// �����б�
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				data));
	}
	
	// fragment����ӵ�activityʱ���ص�
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		// ���Activityû��ʵ��Callbacks�ӿڣ��׳��쳣
		if (!(activity instanceof Callbacks))
		{
			throw new IllegalStateException("BridgeListFragment���ڵ�Activity����ʵ��Callbacks�ӿ�!");
		}
		// �Ѹ�Activity����Callbacks����
		mCallbacks = (Callbacks)activity;		
	}
	
	// fragment��ɾ��ʱ�ص�
	@Override
	public void onDetach() 
	{
		super.onDetach();
		// ��mCallbacks��Ϊnull
		mCallbacks = null;		
	}
	
	// ����ĳ�б���ʱ�ص�
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) 
	{
		super.onListItemClick(listView, view, position, id);
		String item = listView.getItemAtPosition(position).toString();
		// ����mCallbacks��onItemSelected����
		mCallbacks.onItemSelected(Integer.valueOf(item.split(". ")[0]).intValue());
	}
}
