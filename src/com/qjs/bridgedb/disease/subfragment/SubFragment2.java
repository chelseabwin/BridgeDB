package com.qjs.bridgedb.disease.subfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SubFragment2 extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setTextSize(25);
		tv.setText("下部结构");
		tv.setGravity(Gravity.CENTER);
		return tv;
	}
}
