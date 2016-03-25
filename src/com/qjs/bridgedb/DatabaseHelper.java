package com.qjs.bridgedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {	
	static String dbName = "bgdb.db";
	static int dbVersion = 1; 
	
	// 识别表1
	final String CREATE_TABLE_BASE1 = "create table if not exists base1 (id integer primary key autoincrement,"
			+ "bridge_code, bridge_name, path_num, path_name, path_type, rode_grade, order_num,"
			+ "location, center_stake, custody_unit, across_name, across_type, bridge_nature, flag)";
	
	// 识别表2
	final String CREATE_TABLE_BASE2 = "create table if not exists base2 (id integer primary key autoincrement,"
			+ "bg_id, bridge_classify, design_load, bridge_use, bridge_status,"
			+ "material_code, bridge_panel, stress_pattern, support_type, bridge_type, flag)";
	
	// 识别表3
	final String CREATE_TABLE_BASE3 = "create table if not exists base3 (id integer primary key autoincrement,"
			+ "bg_id, pier_material, section_form, pier_type, section_shape, abutment_material, abutment_type,"
			+ "pier_abutment_material, pier_abutment_base, deck_type, joint_type, flag)";
	
	// 结构表
	final String CREATE_TABLE_STRU = "create table if not exists structure (id integer primary key autoincrement,"			
			+ "bg_id, bridge_span, longest_span, total_len, bridge_wide, full_wide, clear_wide,"
			+ "bridge_high, high_limit, building_time, navigation_level, section_high, deck_profile_grade, flag)";
	
	// 部件表1
	final String CREATE_TABLE_PARTS1 = "create table if not exists parts1 (id integer primary key autoincrement,"
			+ "bg_id, wing_wall, conical_slope, protection_slope, pier_detail,"
			+ "abutment_num, pa_num, bed_num, reg_structure, flag)";
	
	// 部件表2
	final String CREATE_TABLE_PARTS2 = "create table if not exists parts2 (id integer primary key autoincrement,"
			+ "bg_id, load_detail, general_detail, support_detail, deck_num, joint_num,"
			+ "sidewalk, guardrail, drainage_system, illuminated_sign, flag)";
	
	// 桥墩添加
	final String CREATE_TABLE_PIER_ADD = "create table if not exists pier_add (id integer primary key autoincrement,"
			+ "bg_id, start_pier, end_pier, per_pier, bent_cap, tie_beam, tap, flag)";
	
	// 桥墩信息
	final String CREATE_TABLE_PIER_DETAIL = "create table if not exists pier_detail (id integer primary key autoincrement,"
			+ "bg_id, pier_details, pier_nums, bent_cap_nums, tie_beam_nums, flag)";
	
	// 上部承重构件添加
	final String CRETAE_TABLE_LOAD_ADD = "create table if not exists load_add (id integer primary key autoincrement,"
			+ "bg_id, start_load, end_load, per_load, tap, flag)";
	
	// 上部承重构件信息
	final String CREATE_TABLE_LOAD_DETAIL = "create table if not exists load_detail (id integer primary key autoincrement,"
			+ "bg_id, load_details, load_nums, flag)";
	
	// 上部一般构件添加
	final String CRETAE_TABLE_GENERAL_ADD = "create table if not exists general_add (id integer primary key autoincrement,"
			+ "bg_id, start_general, end_general, per_general, tap, flag)";
	
	// 上部一般构件信息
	final String CREATE_TABLE_GENERAL_DETAIL = "create table if not exists general_detail (id integer primary key autoincrement,"
			+ "bg_id, general_details, general_nums, flag)";
	
	// 支座添加
	final String CRETAE_TABLE_SUPPORT_ADD = "create table if not exists support_add (id integer primary key autoincrement,"
			+ "bg_id, start_support, end_support, raw_num, support_num, tap, flag)";
	
	// 支座信息
	final String CREATE_TABLE_SUPPORT_DETAIL = "create table if not exists support_detail (id integer primary key autoincrement,"
			+ "bg_id, support_details, support_nums, flag)";
	
	// 病害-上部结构-主梁信息
	final String CREATE_TABLE_DISEASE_GIRDER = "create table if not exists disease_girder (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, rg_fissure, sp_otherDisease, start, end, area, length, width,"
			+ "side_start, side_end, side_length, side_width, rg_location, add_content, disease_image, flag)";
	
	// 病害-上部结构-湿接缝信息
	final String CREATE_TABLE_DISEASE_WETJOINT = "create table if not exists disease_wetjoint (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, rg_fissure, sp_otherDisease, l1_start, l1_end, l1_area, l2_start,"
			+ "l2_length, l2_width, add_content, disease_image, flag)";
	
	// 病害-上部结构-支座信息
	final String CREATE_TABLE_DISEASE_SUPPORT = "create table if not exists disease_support (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-下部结构-桥墩信息
	final String CREATE_TABLE_DISEASE_PIER = "create table if not exists disease_pier (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, rg_fissure, sp_otherDisease, l1_start, l1_end, l1_area, l2_start,"
			+ "l2_length, l2_width, add_content, disease_image, flag)";
	
	// 病害-下部结构-盖梁
	final String CREATE_TABLE_DISEASE_BENTCAP = "create table if not exists disease_bentcap (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-下部结构-系梁
	final String CREATE_TABLE_DISEASE_TIEBEAM = "create table if not exists disease_tiebeam (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-下部结构-桥台身
	final String CREATE_TABLE_DISEASE_ATBODY = "create table if not exists disease_atbody (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, sp_otherDisease, add_content, disease_image, flag)";
	
	// 病害-下部结构-桥台帽
	final String CREATE_TABLE_DISEASE_ATCAPPING = "create table if not exists disease_atcapping (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-下部结构-墩台基础
	final String CREATE_TABLE_DISEASE_PA = "create table if not exists disease_pa (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";	
	
	// 病害-下部结构-河床
	final String CREATE_TABLE_DISEASE_BED = "create table if not exists disease_bed (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-下部结构-调治构造物
	final String CREATE_TABLE_DISEASE_REGSTRUC = "create table if not exists disease_regstruc (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-下部结构-翼墙、耳墙
	final String CREATE_TABLE_DISEASE_WINGWALL = "create table if not exists disease_wingwall (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-下部结构-锥坡
	final String CREATE_TABLE_DISEASE_CONSLOPE = "create table if not exists disease_conslope (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-下部结构-护坡
	final String CREATE_TABLE_DISEASE_PROSLOPE = "create table if not exists disease_proslope (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-桥面系-桥面铺装
	final String CREATE_TABLE_DISEASE_DECK = "create table if not exists disease_deck (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-桥面系-伸缩缝
	final String CREATE_TABLE_DISEASE_JOINT = "create table if not exists disease_joint (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-桥面系-人行道
	final String CREATE_TABLE_DISEASE_SIDEWALK = "create table if not exists disease_sidewalk (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-桥面系-栏杆、护栏
	final String CREATE_TABLE_DISEASE_FENCE = "create table if not exists disease_fence (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-桥面系-防排水系统
	final String CREATE_TABLE_DISEASE_WATERTIGHT = "create table if not exists disease_watertight (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	// 病害-桥面系-照明、标志
	final String CREATE_TABLE_DISEASE_LIGHTING = "create table if not exists disease_lighting (id integer primary key autoincrement,"
			+ "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag)";
	
	public DatabaseHelper(Context context) {
		super(context, dbName, null, dbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
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
		db.execSQL(CREATE_TABLE_DISEASE_WETJOINT);
		db.execSQL(CREATE_TABLE_DISEASE_SUPPORT);
		
		db.execSQL(CREATE_TABLE_DISEASE_PIER);
		db.execSQL(CREATE_TABLE_DISEASE_BENTCAP);
		db.execSQL(CREATE_TABLE_DISEASE_TIEBEAM);
		db.execSQL(CREATE_TABLE_DISEASE_ATBODY);
		db.execSQL(CREATE_TABLE_DISEASE_ATCAPPING);
		db.execSQL(CREATE_TABLE_DISEASE_PA);
		db.execSQL(CREATE_TABLE_DISEASE_BED);
		db.execSQL(CREATE_TABLE_DISEASE_REGSTRUC);
		db.execSQL(CREATE_TABLE_DISEASE_WINGWALL);
		db.execSQL(CREATE_TABLE_DISEASE_CONSLOPE);
		db.execSQL(CREATE_TABLE_DISEASE_PROSLOPE);
		
		db.execSQL(CREATE_TABLE_DISEASE_DECK);
		db.execSQL(CREATE_TABLE_DISEASE_JOINT);
		db.execSQL(CREATE_TABLE_DISEASE_SIDEWALK);
		db.execSQL(CREATE_TABLE_DISEASE_FENCE);
		db.execSQL(CREATE_TABLE_DISEASE_WATERTIGHT);
		db.execSQL(CREATE_TABLE_DISEASE_LIGHTING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
