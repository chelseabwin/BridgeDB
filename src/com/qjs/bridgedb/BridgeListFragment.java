package com.qjs.bridgedb;

import java.util.ArrayList;

import com.qjs.bridgedb.DbOperation;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class BridgeListFragment extends ListFragment {
	private Callbacks mCallbacks;
	public ArrayList<String> data = new ArrayList<String>();
	private ArrayList<String> bg_code = new ArrayList<String>();
	private MyArrayAdapter myArrayAdapter;
	
	// ͨ���ص��ӿڽ���fragment������activity����
	public interface Callbacks {
		public void onItemSelected(String bgCode);
	}
		
	// fragment����ʱ���ص�
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DbOperation db = new DbOperation(this.getActivity());
		Cursor cursor = db.queryData("*", "base1", null); // ��ȡbase1��������
		int index = 0;
		
		while (cursor.moveToNext()) {
			bg_code.add(cursor.getString(cursor.getColumnIndex("bridge_code")));
			String bg_name = cursor.getString(cursor.getColumnIndex("bridge_name"));
			data.add(++index + ". " + bg_name);
		}
		
		myArrayAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				data);
		
		// �����б�
		setListAdapter(myArrayAdapter);
	}
	
	// fragment����ӵ�activityʱ���ص�
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// ���Activityû��ʵ��Callbacks�ӿڣ��׳��쳣
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("BridgeListFragment���ڵ�Activity����ʵ��Callbacks�ӿ�!");
		}
		// �Ѹ�Activity����Callbacks����
		mCallbacks = (Callbacks)activity;		
	}
	
	// fragment��ɾ��ʱ�ص�
	@Override
	public void onDetach() {
		super.onDetach();
		// ��mCallbacks��Ϊnull
		mCallbacks = null;		
	}
	
	// ����ĳ�б���ʱ�ص�
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		
		myArrayAdapter.setSelectItem(position);  
		myArrayAdapter.notifyDataSetInvalidated();
		
		// ����mCallbacks��onItemSelected����
		mCallbacks.onItemSelected(bg_code.get(position));
	}
}
