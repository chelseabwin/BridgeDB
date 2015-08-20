package com.qjs.bridgedb.disease;

import java.util.ArrayList;

import com.qjs.bridgedb.DbOperation;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BridgeListFragment extends ListFragment
{
	private Callbacks mCallbacks;
	private ArrayList<String> data = new ArrayList<String>();
	
	// 通过回调接口将此fragment与所在activity交互
	public interface Callbacks 
	{
		public void onItemSelected(Integer id);
	}
		
	// fragment创建时被回调
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = db.queryData("*", "base1", null); // 获取base1表中数据
		
		while (cursor.moveToNext()) 
		{
			String bg_id = cursor.getString(cursor.getColumnIndex("id"));
			String bg_name = cursor.getString(cursor.getColumnIndex("bridge_name"));
			data.add(bg_id + ". " + bg_name);
		}
		
		// 设置列表
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				data));
	}
	
	// fragment被添加到activity时被回调
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		// 如果Activity没有实现Callbacks接口，抛出异常
		if (!(activity instanceof Callbacks))
		{
			throw new IllegalStateException("BridgeListFragment所在的Activity必须实现Callbacks接口!");
		}
		// 把该Activity当成Callbacks对象
		mCallbacks = (Callbacks)activity;		
	}
	
	// fragment被删除时回调
	@Override
	public void onDetach() 
	{
		super.onDetach();
		// 将mCallbacks赋为null
		mCallbacks = null;		
	}
	
	// 单击某列表项时回调
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) 
	{
		super.onListItemClick(listView, view, position, id);
		String item = listView.getItemAtPosition(position).toString();
		// 激发mCallbacks的onItemSelected方法
		mCallbacks.onItemSelected(Integer.valueOf(item.split(". ")[0]).intValue());
	}
}
