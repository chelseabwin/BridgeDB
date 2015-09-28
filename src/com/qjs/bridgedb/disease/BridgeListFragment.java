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

public class BridgeListFragment extends ListFragment {
	private Callbacks mCallbacks;
	private ArrayList<String> data = new ArrayList<String>();
	String bg_code = "";
	
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
			bg_code = cursor.getString(cursor.getColumnIndex("bridge_code"));
			String bg_name = cursor.getString(cursor.getColumnIndex("bridge_name"));
			data.add(++index + ". " + bg_name);
		}
		
		// �����б�
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				data));
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
		// ����mCallbacks��onItemSelected����
		mCallbacks.onItemSelected(bg_code);
	}
}
