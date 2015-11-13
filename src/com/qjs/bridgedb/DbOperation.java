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

	/** 插入数据
	 * 
	 * @param table 待插入表名
	 * @param key 插入参数名
	 * @param values 插入参数值
	 * @return 插入成功返回新数据id，失败返回0
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
		
		// 获取最新插入的数据
		Cursor cursor = db.rawQuery("select max(id) from " + table, null);
		
		cursor.moveToFirst(); // 将游标移动到第一条数据
        int bg_id = cursor.getInt(0); // 获取新插入数据的id
		
		cursor.close();
		db.close();
		return bg_id;
	}
	
	/** 修改数据
	 * 
	 * @param table 待修改表名
	 * @param setValue 修改参数
	 * @param where 修改位置
	 * @return 修改成功返回1，失败返回0
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
	
	/** 查询数据
	 * 
	 * @param result 查询结果
	 * @param table 待查询表名
	 * @param where 查询位置
	 * @return 返回查询结果
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
	
	/** 删除数据
	 * 
	 * @param table 待插入表名
	 * @param where 删除位置
	 * @return 删除成功返回1，失败返回0
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
	 * 根据值, 设置spinner默认选中
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
		SpinnerAdapter apsAdapter = spinner.getAdapter(); // 得到SpinnerAdapter对象
	    int k = apsAdapter.getCount();
		for(int i = 0; i < k; i++) {
			if(value.equals(apsAdapter.getItem(i).toString())) {
				spinner.setSelection(i,true);// 默认选中项
				break;
			}
		}
	}
}
