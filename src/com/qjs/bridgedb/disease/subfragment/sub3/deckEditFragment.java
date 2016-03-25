package com.qjs.bridgedb.disease.subfragment.sub3;

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
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class deckEditFragment extends Fragment {
	private RadioGroup rgFeature,rg1,rg2,rg3,rg4;
	private RadioButton rg1Track,rg1Bleeding,rg1Breakage,rg1Fissure,rg1OtherDisease;
	private RadioButton rg2Irregularity,rg2Anchorage,rg2Breakage,rg2Failure,rg2OtherDisease;
	private RadioButton rg3Impeded,rg3Flaw,rg3OtherDisease;
	private RadioButton rg4Dirty,rg4LackOfLighting,rg4SignMissing,rg4OtherDisease;
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
		final View rootView = inflater.inflate(R.layout.fragment_deck_page1, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // 病害名称		

		rg1 = (RadioGroup) rootView.findViewById(R.id.rg); // 病害特征单选框-桥面铺装
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2); // 病害特征单选框-伸缩缝
		rg3 = (RadioGroup) rootView.findViewById(R.id.rg3); // 病害特征单选框-防排水系统
		rg4 = (RadioGroup) rootView.findViewById(R.id.rg4); // 病害特征单选框-照明、标志
		
		addContent = (EditText) rootView.findViewById(R.id.tv_add_content); // 病害描述
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // 病害图片按钮
		btnCamera = (Button) rootView.findViewById(R.id.btn_camera); // 打开照相机按钮
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // 病害图片
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_submit); // 提交按钮
		
		Bundle args = getArguments();
		
		if (args != null) {
			diseaseDescription.setTextSize(25);
			diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("ACROSS_NUM") + ")");
			if (args.getString("ITEM_NAME").equals("桥面铺装")) {
				rgFeature = rg1;
				rg1.setVisibility(View.VISIBLE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg1Track = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_track); // 变形（车辙、拥包、高低不平等）
				rg1Bleeding = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_bleeding); // 泛油
				rg1Breakage = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_breakage); // 破损
				rg1Fissure = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_fissure); // 裂缝
				rg1OtherDisease = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_other_disease); // 其他病害
				
				optionStr = "DECK";
				tableName = "disease_deck";
			}
			else if (args.getString("ITEM_NAME").equals("伸缩缝装置")) {
				rgFeature = rg2;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.VISIBLE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg2Irregularity = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_irregularity); // 凹凸不平
				rg2Anchorage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_anchorage); // 锚固区缺陷
				rg2Breakage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_breakage); // 破损
				rg2Failure = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_failure); // 失效
				rg2OtherDisease = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_other_disease); // 其他病害
				
				optionStr = "JOINT";
				tableName = "disease_joint";
			}
			else if (args.getString("ITEM_NAME").equals("防排水系统")) {
				rgFeature = rg3;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.VISIBLE);
				rg4.setVisibility(View.GONE);
				
				rg3Impeded = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_impeded); // 排水不畅
				rg3Flaw = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_flaw); // 排水管、引水槽缺陷
				rg3OtherDisease = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_other_disease); // 其他病害
				
				optionStr = "WATERTIGHT";
				tableName = "disease_watertight";
			}
			else {
				rgFeature = rg4;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.VISIBLE);
				
				rg4Dirty = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_dirty); // 污损或破坏
				rg4LackOfLighting = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_lack_of_lighting); // 照明设施缺失
				rg4SignMissing = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_sign_missing); // 标志脱落、缺失
				rg4OtherDisease = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_other_disease); // 其他病害
				
				optionStr = "LIGHTING";
				tableName = "disease_lighting";
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
			
			if (optionStr == "DECK")
				rg1Track.setChecked(true); // 设置“变形（车辙、拥包、高低不平等）”默认选中
			else if (optionStr == "JOINT")
				rg2Irregularity.setChecked(true); // 设置“凹凸不平”默认选中
			else if (optionStr == "WATERTIGHT")
				rg3Impeded.setChecked(true); // 设置“排水不畅”默认选中
			else if (optionStr == "LIGHTING")
				rg4Dirty.setChecked(true); // 设置“污损或破坏”默认选中
			
			db = new DbOperation(this.getActivity());
			cursor = db.queryData("*", tableName, "id=" + itemId + " and bg_id='" + bgId + "'" + " and parts_id='" + bgCode + "'"); // 查找表中是否有对应的数据
			
			
			// 如果有则填入相应数据
			if (cursor.moveToFirst()) {
				String rg_feature = cursor.getString(cursor.getColumnIndex("rg_feature"));

				if (optionStr == "DECK") {
					if ((rg1Track.getText().toString()).equals(rg_feature))
						rg1Track.setChecked(true);
					if ((rg1Bleeding.getText().toString()).equals(rg_feature))
						rg1Bleeding.setChecked(true);
					if ((rg1Breakage.getText().toString()).equals(rg_feature))
						rg1Breakage.setChecked(true);
					if ((rg1Fissure.getText().toString()).equals(rg_feature))
						rg1Fissure.setChecked(true);
					if ((rg1OtherDisease.getText().toString()).equals(rg_feature))
						rg1OtherDisease.setChecked(true);
				}
				else if (optionStr == "JOINT") {
					if ((rg2Irregularity.getText().toString()).equals(rg_feature))
						rg2Irregularity.setChecked(true);
					if ((rg2Anchorage.getText().toString()).equals(rg_feature))
						rg2Anchorage.setChecked(true);
					if ((rg2Breakage.getText().toString()).equals(rg_feature))
						rg2Breakage.setChecked(true);
					if ((rg2Failure.getText().toString()).equals(rg_feature))
						rg2Failure.setChecked(true);
					if ((rg2OtherDisease.getText().toString()).equals(rg_feature))
						rg2OtherDisease.setChecked(true);
				}
				else if (optionStr == "WATERTIGHT") {
					if ((rg3Impeded.getText().toString()).equals(rg_feature))
						rg3Impeded.setChecked(true);
					if ((rg3Flaw.getText().toString()).equals(rg_feature))
						rg3Flaw.setChecked(true);
					if ((rg3OtherDisease.getText().toString()).equals(rg_feature))
						rg3OtherDisease.setChecked(true);
				}
				else if (optionStr == "LIGHTING") {
					if ((rg4Dirty.getText().toString()).equals(rg_feature))
						rg4Dirty.setChecked(true);
					if ((rg4LackOfLighting.getText().toString()).equals(rg_feature))
						rg4LackOfLighting.setChecked(true);
					if ((rg4SignMissing.getText().toString()).equals(rg_feature))
						rg4SignMissing.setChecked(true);
					if ((rg4OtherDisease.getText().toString()).equals(rg_feature))
						rg4OtherDisease.setChecked(true);
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
				String add_content = addContent.getText().toString(); // 病害描述
				String disease_image = null; // 本地图片地址
				if (uri != null)
					disease_image = uri.toString();
				
				String sql = "item_name='" + item_name + "',rg_feature='" + rg_feature + "',add_content='" + add_content + "',disease_image='" + disease_image + "',flag='2'";
				
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
