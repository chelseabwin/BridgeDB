package com.qjs.bridgedb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SyncService extends IntentService {    
    final String IP_ADDRESS = "192.168.1.130";
    final int POST = 30000;
    
    private Handler handler; // 在IntentService中显示Toast

	public SyncService() {
		super("SyncService");
	}
	
	@Override  
    public void onCreate() {  
        super.onCreate();
        handler = new Handler();  
    }

	@Override
	protected void onHandleIntent(Intent intent) {		
		SQLiteDatabase sqldb = openOrCreateDatabase("bgdb.db", Context.MODE_PRIVATE, null);
		Cursor cursor = sqldb.rawQuery("select name from sqlite_master where type='table' order by name", null);
		
		DbOperation db = new DbOperation(this);
		JSONArray jsonDb = new JSONArray(); // 用于存储所有数据
		HashMap<String, Cursor> hm = new HashMap<String, Cursor>(); // 创建一个HashMap用于同步后修改这些cursor的状态(flag)
		
		while(cursor.moveToNext()) {			   
			//遍历出表名			   
			String tableName = cursor.getString(0);			
			Cursor cur = db.queryData("*", tableName, "flag='0' or flag='2'"); // 找出所有flag等于“0”或“2”的行
			if (cur != null) {
				hm.put(tableName, cur); // 保存该cursor
				
				JSONObject jsonTable = new JSONObject(); // 用于存储某张表的数据
				JSONArray jsonRow = new JSONArray(); // 用于存储某一行的数据
				
				String[] columnNames = cur.getColumnNames();
				
				while (cur.moveToNext()) {
					try {
						JSONObject jsonField = new JSONObject(); // 用于存储某个字段的数据
						for (int i = 1; i < columnNames.length; i++) { // 从"1"开始，去掉“id”					
							jsonField.put(columnNames[i], cur.getString(cur.getColumnIndex(columnNames[i])));							
						}
						jsonRow.put(jsonField);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				try {
					jsonTable.put(cursor.getString(0), jsonRow); // 向表对象里面添加包含某行的数据
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonDb.put(jsonTable); // 整合所有表
			}
		}
		String bridgeData = jsonDb.toString();
		
		try {
			// 建立客户端socket连接，指定服务器位置及端口
            Socket socket = new Socket(IP_ADDRESS, POST);
            // 得到socket读写流
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            // 输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            // 利用流按照一定的操作，对socket进行读写操作
            pw.write(bridgeData);
            pw.flush();
            socket.shutdownOutput();
            
            //接收服务器的相应
            String reply = null;
            while(!((reply = br.readLine()) == null)){
                if (reply.equals("finish")) {
                	handler.post(new Runnable() {
                        @Override
                        public void run() {
                        	Toast.makeText(getApplicationContext(), "数据同步完成！", Toast.LENGTH_SHORT).show();
                        }
                    });
                	
                	// 将已经同步完成的数据的flag修改为“1”
                	Iterator<Entry<String, Cursor>> iter = hm.entrySet().iterator();
                	while (iter.hasNext()) {
                		Entry<String, Cursor> entry = iter.next();
                		String tableName = (String) entry.getKey();
                		Cursor cur2 = (Cursor) entry.getValue();
                		
                		if(cur2.moveToFirst()) {
	                		do {
	                			int flag = db.updateData(tableName, "flag='1'", null);
	                			if (flag == 0)
	                				Log.w("-- flag_status --", "修改状态失败");
	                			else
	                				Log.i("-- flag_status --", "修改状态成功");
							} while (cur2.moveToNext());
                		}
                	}
				}
            }
            
            // 关闭资源  
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
