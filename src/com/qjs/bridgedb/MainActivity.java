package com.qjs.bridgedb;

import java.util.Timer;
import java.util.TimerTask;

import com.qjs.bridgedb.collection.BaseActivity;
import com.qjs.bridgedb.detailed.BaseDetailedActivity;
import com.qjs.bridgedb.disease.DiseaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

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
        		finish();
        	}
        });
        
        // 病害信息按钮跳转
        Button disease_btn = (Button)findViewById(R.id.disease_info);
        disease_btn.setOnClickListener(new OnClickListener() {

        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(MainActivity.this, DiseaseActivity.class);
        		startActivity(intent);
    			finish();
        	}
        });
        
        // 查看桥梁基本数据按钮跳转
        Button detailed_base = (Button)findViewById(R.id.detailed_base);
        detailed_base.setOnClickListener(new OnClickListener() {

        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(MainActivity.this, BaseDetailedActivity.class);
        		startActivity(intent);
    			finish();
        	}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {  
        case R.id.data_sync:
        	Toast.makeText(this, "开始数据同步", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SyncService.class);
            startService(intent);
            break;
        }
    	return super.onOptionsItemSelected(item);
    }
    
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK) {
    		exitBy2Click(); //调用双击退出函数
    	}     
    	return false;
    }
    
    /**
     * 双击退出函数
     */    
    private static Boolean isExit = false;
     
    private void exitBy2Click() {     
    	Timer tExit = null;     
    	if (isExit == false) {     
    		isExit = true; // 准备退出     
    		Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();     
    		tExit = new Timer();     
    		tExit.schedule(new TimerTask() {
    			
    			@Override      
    			public void run() {
    				isExit = false; // 取消退出      
    			}     
    		}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
    	}
    	else {     
    		finish();
    		System.exit(0);
    	}
    }
}
