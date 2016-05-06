package com.qjs.bridgedb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class WelcomeActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);        
     
        // µã»÷Í¼Æ¬Ìø×ª
        findViewById(R.id.welcomr_image).setOnClickListener(new OnClickListener() {        	
        	@Override		
        	public void onClick(View v) {			
        		Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);    		
        		startActivity(intent);    		
        		finish();		
        	}	
        });        
    }
}
