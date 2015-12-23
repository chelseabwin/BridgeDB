package com.qjs.bridgedb.disease.subfragment.sub2;

import java.io.FileNotFoundException;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.detailed.EditDiseaseActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class abutmentEditFragment extends Fragment {
	private RadioGroup rgFeature,rg1,rg2,rg3;
	private Spinner spOtherDisease;
	private RadioButton rg1PeelOff,rg1Abrasion,rg1Fissure,rg1MasonryDefects,rg1VehicleJump,rg1Drainage,rg1OtherDisease;
	private RadioButton rg2Breakage,rg2Carbonation,rg2Fissure,rg2Cavitation;
	private RadioButton rg3Scouring,rg3LeakageTendon,rg3Washout,rg3BottomPaving,rg3Sedimentation,rg3SlipTilt,rg3Fissure;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnCamera,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // 保存本地图片地址
	private DbOperation db = null;
	private Cursor cursor = null;
	private String optionStr = null; // 标签名
	private String tableName = null; // 待查询表名
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100; // 图片库反馈码
    private static final int CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE = 200; // 照相机反馈码
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_down_page3, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // 病害名称		

		rg1 = (RadioGroup) rootView.findViewById(R.id.rg); // 病害特征单选框-桥台身
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2); // 病害特征单选框-桥台帽
		rg3 = (RadioGroup) rootView.findViewById(R.id.rg3); // 病害特征单选框-墩台基础
		
		addContent = (EditText) rootView.findViewById(R.id.tv_add_content); // 病害描述
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // 病害图片按钮
		btnCamera = (Button) rootView.findViewById(R.id.btn_camera); // 打开照相机按钮
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // 病害图片
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_submit); // 提交按钮
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("ACROSS_NUM") + ")");
			if (args.getString("ITEM_NAME").equals("桥台身")) {
				rgFeature = rg1;
				rg1.setVisibility(View.VISIBLE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				
				rg1PeelOff = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_peel_off); // 剥落
				rg1Abrasion = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_abrasion); // 磨损
				rg1Fissure = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_fissure); // 裂缝
				rg1MasonryDefects = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_masonry_defects); // 圬工砌体缺陷
				rg1VehicleJump = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_vehicle_jump); // 桥台跳车
				rg1Drainage = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_drainage); // 台背排水
				rg1OtherDisease = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_other_disease); // 其他病害
				spOtherDisease = (Spinner) rootView.findViewById(R.id.rg1_sp_other_disease); // 其他病害下拉列表
				
				optionStr = "ATBODY";
				tableName = "disease_atbody";
			}
			else if (args.getString("ITEM_NAME").equals("桥台帽")) {
				rgFeature = rg2;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.VISIBLE);
				rg3.setVisibility(View.GONE);
				
				rg2Breakage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_breakage); // 破损
				rg2Carbonation = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_carbonation); // 混凝土碳化、腐蚀
				rg2Fissure = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_fissure); // 裂缝
				rg2Cavitation = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_cavitation); // 空洞、孔洞
				
				optionStr = "ATCAPPING";
				tableName = "disease_atcapping";
			}
			else {
				rgFeature = rg3;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.VISIBLE);
				
				rg3Scouring = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_scouring); // 冲刷、淘空
				rg3LeakageTendon = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_leakage_tendon); // 剥落、露筋
				rg3Washout = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_washout); // 冲蚀
				rg3BottomPaving = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_bottom_paving); // 河底铺砌
				rg3Sedimentation = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_sedimentation); // 沉降
				rg3SlipTilt = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_slip_tilt); // 滑移和倾斜
				rg3Fissure = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_fissure); // 裂缝
				
				optionStr = "PA";
				tableName = "disease_pa";
			}
			
			final String itemName = args.getString("ITEM_NAME");			
			final int itemId = args.getInt("ITEM_ID");
			final String bgId = args.getString("BRIDGE_ID");
			final String bgCode = args.getString("ACROSS_NUM");
			
			// 设置病害特征监听
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb, rootView, bgCode, bgId, itemName, itemId); // 设置病害特征选择
				}				
			});
			
			if (optionStr == "ATBODY")
				rg1PeelOff.setChecked(true); // 设置“剥落”默认选中
			else if (optionStr == "ATCAPPING")
				rg2Breakage.setChecked(true); // 设置“破损”默认选中
			else if (optionStr == "PA")
				rg3Scouring.setChecked(true); // 设置“冲刷、淘空”默认选中
			
			db = new DbOperation(this.getActivity());
			cursor = db.queryData("*", tableName, "id=" + itemId + " and bg_id='" + bgId + "'" + " and parts_id='" + bgCode + "'"); // 查找表中是否有对应的数据
			
			
			// 如果有则填入相应数据
			if (cursor.moveToFirst()) {
				String rg_feature = cursor.getString(cursor.getColumnIndex("rg_feature"));

				if (optionStr == "ATBODY") {
					if ((rg1PeelOff.getText().toString()).equals(rg_feature))
						rg1PeelOff.setChecked(true);
					if ((rg1Abrasion.getText().toString()).equals(rg_feature))
						rg1Abrasion.setChecked(true);
					if ((rg1Fissure.getText().toString()).equals(rg_feature))
						rg1Fissure.setChecked(true);
					if ((rg1MasonryDefects.getText().toString()).equals(rg_feature))
						rg1MasonryDefects.setChecked(true);
					if ((rg1VehicleJump.getText().toString()).equals(rg_feature))
						rg1VehicleJump.setChecked(true);
					if ((rg1Drainage.getText().toString()).equals(rg_feature))
						rg1Drainage.setChecked(true);
					if ((rg1OtherDisease.getText().toString()).equals(rg_feature)) { // 其他病害
						rg1OtherDisease.setChecked(true);
						DbOperation.setSpinnerItemSelectedByValue(spOtherDisease, cursor.getString(cursor.getColumnIndex("sp_otherDisease")));						
					}					
				}
				else if (optionStr == "ATCAPPING") {
					if ((rg2Breakage.getText().toString()).equals(rg_feature))
						rg2Breakage.setChecked(true);
					if ((rg2Carbonation.getText().toString()).equals(rg_feature))
						rg2Carbonation.setChecked(true);
					if ((rg2Fissure.getText().toString()).equals(rg_feature))
						rg2Fissure.setChecked(true);
					if ((rg2Cavitation.getText().toString()).equals(rg_feature))
						rg2Cavitation.setChecked(true);					
				}
				else if (optionStr == "PA") {
					if ((rg3Scouring.getText().toString()).equals(rg_feature))
						rg3Scouring.setChecked(true);
					if ((rg3LeakageTendon.getText().toString()).equals(rg_feature))
						rg3LeakageTendon.setChecked(true);
					if ((rg3Washout.getText().toString()).equals(rg_feature))
						rg3Washout.setChecked(true);
					if ((rg3BottomPaving.getText().toString()).equals(rg_feature))
						rg3BottomPaving.setChecked(true);
					if ((rg3Sedimentation.getText().toString()).equals(rg_feature))
						rg3Sedimentation.setChecked(true);
					if ((rg3SlipTilt.getText().toString()).equals(rg_feature))
						rg3SlipTilt.setChecked(true);
					if ((rg3Fissure.getText().toString()).equals(rg_feature))
						rg3Fissure.setChecked(true);
				}
				
				addContent.setText(cursor.getString(cursor.getColumnIndex("add_content")));
				
				uri = Uri.parse(cursor.getString(cursor.getColumnIndex("disease_image")));
				if (uri != null) {					
					String mFileName = EditDiseaseActivity.getPath(this.getActivity().getApplicationContext(), uri); // 获取图片绝对路径
					Bitmap bitmap = EditDiseaseActivity.getBitmap(mFileName); // 根据绝对路径找到图片
					ivImage.setImageBitmap(bitmap); // 设置图片
				}
			}
		}		
		return rootView;		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.getActivity();		
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) { // 来自图片库
			if (resultCode == FragmentActivity.RESULT_OK) {
	            uri = data.getData();
	            if (uri != null) {
		            ContentResolver cr = this.getActivity().getContentResolver();
		            try {  
		                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
		                // 将Bitmap设定到ImageView
		                ivImage.setImageBitmap(bitmap);
		            } catch (FileNotFoundException e) {
		            	Log.e("Exception", e.getMessage(),e);
		            }
	            }
	        }
		}
		else if (requestCode == CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE) { // 来自照相机
			if (resultCode == FragmentActivity.RESULT_OK) {
				if (data != null) {
					uri = data.getData();
                    if (data.hasExtra("data")) {
                    	Bitmap bitmap = data.getParcelableExtra("data");
						// 将Bitmap设定到ImageView
						ivImage.setImageBitmap(bitmap);
                    }
                }
			}			
		}		
        super.onActivityResult(requestCode, resultCode, data);  
    }
	
	/** 设置病害特征选择
	 * rb: 病害选择button
	 * rootView: 页面view
	 * bgCode: 桥梁部件编号
	 * bgId:桥梁id
	 * itemName:选择项名称
	 * itemId:选择项id
	 * */
	private void setDiseaseFeature(RadioButton rb, View rootView, String bgCode, String bgId, String itemName, int itemId) {
		if (tableName == "disease_atbody") {
			if ("其他病害".equals(rb.getText()))
				spOtherDisease.setVisibility(View.VISIBLE); // 显示“其他病害”下拉列表
			else
				spOtherDisease.setVisibility(View.GONE); // 隐藏“其他病害”下拉列表			
		}
		
		// 选择病害图片监听
		btnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                // 开启Pictures画面Type设定为image
                intent.setType("image/*");
                // 使用Intent.ACTION_GET_CONTENT / ACTION_OPEN_DOCUMENT这个Action
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                // 取得相片后返回本画面
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}			
		});
		
		// 打开照相机
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 拍照后返回本画面
				startActivityForResult(intent, CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE);
			}
		});
		
		final View rv = rootView;
		final int item_id = itemId;
		final String parts_id = bgCode;
		final String bg_id = bgId;
		final String item_name = itemName;
		
		// 提交按钮监听
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				RadioButton rbFeature = (RadioButton) rv.findViewById(rgFeature.getCheckedRadioButtonId());
				
				String rg_feature = rbFeature.getText().toString(); // 病害特征
				String sp_otherDisease = "0"; // 其他病害-病害信息
				String add_content = addContent.getText().toString(); // 病害描述
				String disease_image = null; // 本地图片地址
				if (uri != null)
					disease_image = uri.toString();
				if ("其他病害".equals(rbFeature.getText()))
					sp_otherDisease = spOtherDisease.getSelectedItem().toString(); // 其他病害-病害信息
				
				String sql = null;
				if (tableName == "disease_atbody")
					sql = "item_name='" + item_name + "',rg_feature='" + rg_feature + "',sp_otherDisease='" + sp_otherDisease + "',add_content='" + add_content + "',disease_image='" + disease_image + "',flag='2'";
				else
					sql = "item_name='" + item_name + "',rg_feature='" + rg_feature + "',add_content='" + add_content + "',disease_image='" + disease_image + "',flag='2'";
				
				int flag = db.updateData(tableName, sql, "id=" + item_id + " and bg_id='" + bg_id + "'" + " and parts_id='" + parts_id + "'");
				
				if (flag == 0)
        			Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
    			else
    				Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
				
				getActivity().finish();
			}			
		});		
	}
}
