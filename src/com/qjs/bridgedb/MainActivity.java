package com.qjs.bridgedb;

import com.qjs.bridgedb.collection.BaseActivity;
import com.qjs.bridgedb.disease.DiseaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 桥梁基本数据按钮跳转
        Button base_btn = (Button)findViewById(R.id.bridge_base);
        base_btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(MainActivity.this, BaseActivity.class);
        		intent.putExtra("toNext", "toNextBg"); // 传递添加跳转
        		startActivity(intent);
        	}
        });
        
        // 病害信息按钮跳转
        Button disease_btn = (Button)findViewById(R.id.disease_info);
        disease_btn.setOnClickListener(new OnClickListener() {

        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(MainActivity.this, DiseaseActivity.class);
        		startActivity(intent);
        	}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
