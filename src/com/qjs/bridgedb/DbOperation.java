package com.qjs.bridgedb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class DbOperation {
	
	private DatabaseHelper dbHelper;
	
	public DbOperation(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	/** ��������
	 * 
	 * @param table ���������
	 * @param key ���������
	 * @param values �������ֵ
	 * @return ����ɹ�����������id��ʧ�ܷ���0
	 */
	public int insertData(String table, String key, String values) {		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "insert into " + table + "(" + key + ") values(" + values + ")";
		
		Log.i("-- insert --", sql);
		
		db.beginTransaction();
		try {
			db.execSQL(sql);
			db.setTransactionSuccessful();
		}
		catch(Exception e) {
			return 0;
		}
		finally {
			db.endTransaction();
		}
		
		// ��ȡ���²��������
		Cursor cursor = db.rawQuery("select max(id) from " + table, null);
		
		cursor.moveToFirst(); // ���α��ƶ�����һ������
        int bg_id = cursor.getInt(0); // ��ȡ�²������ݵ�id
		
		cursor.close();
		db.close();
		return bg_id;
	}
	
	/** �޸�����
	 * 
	 * @param table ���޸ı���
	 * @param setValue �޸Ĳ���
	 * @param where �޸�λ��
	 * @return �޸ĳɹ�����1��ʧ�ܷ���0
	 */
	public int updateData(String table, String setValue, String where) {
		String sql = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (where == null) {
			sql = "update " + table + " set " + setValue;
		}
		else {
			sql = "update " + table + " set " + setValue + " where " + where;
		}
		
		Log.i("-- update --", sql);
		
		db.beginTransaction();
		try {
			db.execSQL(sql);
			db.setTransactionSuccessful();
		}
		catch(Exception e) {
			return 0;
		}
		finally {
			db.endTransaction();
		}
		
		db.close();
		return 1;
	}
	
	/** ��ѯ����
	 * 
	 * @param result ��ѯ���
	 * @param table ����ѯ����
	 * @param where ��ѯλ��
	 * @return ���ز�ѯ���
	 */
	public Cursor queryData(String result, String table, String where) {
		String sql = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (where == null) {
			sql = "select " + result + " from " + table;
		}
		else  {
			sql = "select " + result + " from " + table + " where " + where;
		}
		
		Log.i("-- query --", sql);
		
		Cursor cursor;
		
		db.beginTransaction();
		try {
			cursor = db.rawQuery(sql, null);
			db.setTransactionSuccessful();
		}
		catch(Exception e) {
			return null;
		}
		finally {
			db.endTransaction();
		}
		
		return cursor;
	}
	
	/** ɾ������
	 * 
	 * @param table ���������
	 * @param where ɾ��λ��
	 * @return ɾ���ɹ�����1��ʧ�ܷ���0
	 */
	public int deleteData(String table, String where) {		
		String sql = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (where == null) {
			sql = "delete from " + table;
		}
		else  {
			sql = "delete from " + table + " where " + where;
		}
		
		Log.i("-- delete --", sql);
		
		db.beginTransaction();
		try {
			db.execSQL(sql);
			db.setTransactionSuccessful();
		}
		catch(Exception e) {
			return 0;
		}
		finally {
			db.endTransaction();
		}
		
		db.close();
		return 1;
	}
	
	/**
	 * ����ֵ, ����spinnerĬ��ѡ��
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
		SpinnerAdapter apsAdapter = spinner.getAdapter(); // �õ�SpinnerAdapter����
	    int k = apsAdapter.getCount();
		for(int i = 0; i < k; i++) {
			if(value.equals(apsAdapter.getItem(i).toString())) {
				spinner.setSelection(i,true);// Ĭ��ѡ����
				break;
			}
		}
	}
}
