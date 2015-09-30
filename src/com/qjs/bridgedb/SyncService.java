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
    
    private Handler handler; // ��IntentService����ʾToast

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
		JSONArray jsonDb = new JSONArray(); // ���ڴ洢��������
		HashMap<String, Cursor> hm = new HashMap<String, Cursor>(); // ����һ��HashMap����ͬ�����޸���Щcursor��״̬(flag)
		
		while(cursor.moveToNext()) {			   
			//����������			   
			String tableName = cursor.getString(0);			
			Cursor cur = db.queryData("*", tableName, "flag='0' or flag='2'"); // �ҳ�����flag���ڡ�0����2������
			if (cur != null) {
				hm.put(tableName, cur); // �����cursor
				
				JSONObject jsonTable = new JSONObject(); // ���ڴ洢ĳ�ű������
				JSONArray jsonRow = new JSONArray(); // ���ڴ洢ĳһ�е�����
				
				String[] columnNames = cur.getColumnNames();
				
				while (cur.moveToNext()) {
					try {
						JSONObject jsonField = new JSONObject(); // ���ڴ洢ĳ���ֶε�����
						for (int i = 1; i < columnNames.length; i++) { // ��"1"��ʼ��ȥ����id��					
							jsonField.put(columnNames[i], cur.getString(cur.getColumnIndex(columnNames[i])));							
						}
						jsonRow.put(jsonField);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				try {
					jsonTable.put(cursor.getString(0), jsonRow); // ������������Ӱ���ĳ�е�����
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonDb.put(jsonTable); // �������б�
			}
		}
		String bridgeData = jsonDb.toString();
		
		try {
			// �����ͻ���socket���ӣ�ָ��������λ�ü��˿�
            Socket socket = new Socket(IP_ADDRESS, POST);
            // �õ�socket��д��
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            // ������
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            // ����������һ���Ĳ�������socket���ж�д����
            pw.write(bridgeData);
            pw.flush();
            socket.shutdownOutput();
            
            //���շ���������Ӧ
            String reply = null;
            while(!((reply = br.readLine()) == null)){
                if (reply.equals("finish")) {
                	handler.post(new Runnable() {
                        @Override
                        public void run() {
                        	Toast.makeText(getApplicationContext(), "����ͬ����ɣ�", Toast.LENGTH_SHORT).show();
                        }
                    });
                	
                	// ���Ѿ�ͬ����ɵ����ݵ�flag�޸�Ϊ��1��
                	Iterator<Entry<String, Cursor>> iter = hm.entrySet().iterator();
                	while (iter.hasNext()) {
                		Entry<String, Cursor> entry = iter.next();
                		String tableName = (String) entry.getKey();
                		Cursor cur2 = (Cursor) entry.getValue();
                		
                		if(cur2.moveToFirst()) {
	                		do {
	                			int flag = db.updateData(tableName, "flag='1'", null);
	                			if (flag == 0)
	                				Log.w("-- flag_status --", "�޸�״̬ʧ��");
	                			else
	                				Log.i("-- flag_status --", "�޸�״̬�ɹ�");
							} while (cur2.moveToNext());
                		}
                	}
				}
            }
            
            // �ر���Դ  
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
