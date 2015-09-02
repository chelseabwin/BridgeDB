package com.qjs.bridgedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {	
	static String dbName = "bgdb.db";
	static int dbVersion = 1; 
	
	// ʶ���1
	final String CREATE_TABLE_BASE1 = "create table if not exists base1 (id integer primary key autoincrement,"
			+ "bridge_code, bridge_name, path_num, path_name, path_type, rode_grade, order_num,"
			+ "location, center_stake, custody_unit, across_name, across_type, bridge_nature, flag)";
	
	// ʶ���2
	final String CREATE_TABLE_BASE2 = "create table if not exists base2 (id integer primary key autoincrement,"
			+ "bg_id, bridge_classify, design_load, bridge_use, bridge_status,"
			+ "material_code, bridge_panel, stress_pattern, support_type, bridge_type, flag)";
	
	// ʶ���3
	final String CREATE_TABLE_BASE3 = "create table if not exists base3 (id integer primary key autoincrement,"
			+ "bg_id, pier_material, section_form, pier_type, abutment_material, abutment_type,"
			+ "pier_abutment_material, pier_abutment_base, deck_type, joint_type, flag)";
	
	// �ṹ��
	final String CREATE_TABLE_STRU = "create table if not exists structure (id integer primary key autoincrement,"			
			+ "bg_id, bridge_span, longest_span, total_len, bridge_wide, full_wide, clear_wide,"
			+ "bridge_high, high_limit, building_time, navigation_level, section_high, deck_profile_grade, flag)";
	
	// ������1
	final String CREATE_TABLE_PARTS1 = "create table if not exists parts1 (id integer primary key autoincrement,"
			+ "bg_id, wing_wall, conical_slope, protection_slope, pier_detail,"
			+ "abutment_num, pa_num, bed_num, reg_structure, flag)";
	
	// ������2
	final String CREATE_TABLE_PARTS2 = "create table if not exists parts2 (id integer primary key autoincrement,"
			+ "bg_id, load_detail, general_detail, support_detail, deck_num, joint_num,"
			+ "sidewalk, guardrail, drainage_system, illuminated_sign, flag)";
	
	// �Ŷ����
	final String CREATE_TABLE_PIER_ADD = "create table if not exists pier_add (id integer primary key autoincrement,"
			+ "bg_id, start_pier, end_pier, per_pier, bent_cap, tie_beam, tap, flag)";
	
	// �Ŷ���Ϣ
	final String CREATE_TABLE_PIER_DETAIL = "create table if not exists pier_detail (id integer primary key autoincrement,"
			+ "bg_id, pier_details, pier_nums, bent_cap_nums, tie_beam_nums, flag)";
	
	// �ϲ����ع������
	final String CRETAE_TABLE_LOAD_ADD = "create table if not exists load_add (id integer primary key autoincrement,"
			+ "bg_id, start_load, end_load, per_load, tap, flag)";
	
	// �ϲ����ع�����Ϣ
	final String CREATE_TABLE_LOAD_DETAIL = "create table if not exists load_detail (id integer primary key autoincrement,"
			+ "bg_id, load_details, load_nums, flag)";
	
	// �ϲ�һ�㹹�����
	final String CRETAE_TABLE_GENERAL_ADD = "create table if not exists general_add (id integer primary key autoincrement,"
			+ "bg_id, start_general, end_general, per_general, tap, flag)";
	
	// �ϲ�һ�㹹����Ϣ
	final String CREATE_TABLE_GENERAL_DETAIL = "create table if not exists general_detail (id integer primary key autoincrement,"
			+ "bg_id, general_details, general_nums, flag)";
	
	// ֧�����
	final String CRETAE_TABLE_SUPPORT_ADD = "create table if not exists support_add (id integer primary key autoincrement,"
			+ "bg_id, start_support, end_support, raw_num, support_num, tap, flag)";
	
	// ֧����Ϣ
	final String CREATE_TABLE_SUPPORT_DETAIL = "create table if not exists support_detail (id integer primary key autoincrement,"
			+ "bg_id, support_details, support_nums, flag)";
	
	// ����-�ϲ��ṹ-������Ϣ
	final String CREATE_TABLE_DISEASE_GIRDER = "create table if not exists disease_girder (id integer primary key autoincrement,"
			+ "bg_id, girder_id, rg_feature, rg_fissure, sp_otherDisease, l1_start, l1_end, l1_area, l2_start,"
			+ "l2_length, l2_width,rg_location, add_content, disease_image, flag)";
	
	public DatabaseHelper(Context context) {
		super(context, dbName, null, dbVersion);
		// TODO Auto-generated constructor stub		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_BASE1);
		db.execSQL(CREATE_TABLE_BASE2);
		db.execSQL(CREATE_TABLE_BASE3);
		db.execSQL(CREATE_TABLE_STRU);
		db.execSQL(CREATE_TABLE_PARTS1);
		db.execSQL(CREATE_TABLE_PARTS2);
		
		db.execSQL(CREATE_TABLE_PIER_ADD);
		db.execSQL(CREATE_TABLE_PIER_DETAIL);
		
		db.execSQL(CRETAE_TABLE_LOAD_ADD);
		db.execSQL(CREATE_TABLE_LOAD_DETAIL);
		
		db.execSQL(CRETAE_TABLE_GENERAL_ADD);
		db.execSQL(CREATE_TABLE_GENERAL_DETAIL);
		
		db.execSQL(CRETAE_TABLE_SUPPORT_ADD);
		db.execSQL(CREATE_TABLE_SUPPORT_DETAIL);
		
		db.execSQL(CREATE_TABLE_DISEASE_GIRDER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
