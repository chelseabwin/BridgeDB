package com.qjs.bridgedb.disease.subfragment.sub2;

import java.io.FileNotFoundException;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;

import android.content.ContentResolver;
import android.content.Intent;
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

public class sub2OtherFragment extends Fragment {
	private RadioGroup rgFeature,rg1,rg2,rg3,rg4;
	private RadioButton rg1Jam;
	private RadioButton rg2Damage;
	private RadioButton rg3Breakage;
	private RadioButton rg4Defect;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnCamera,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // 保存本地图片地址
	private DbOperation db = null;
	private String optionStr = null; // 标签名
	private String tableName = null; // 待查询表名
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100; // 图片库反馈码
    private static final int CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE = 200; // 照相机反馈码
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_down_page4, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // 病害名称		

		rg1 = (RadioGroup) rootView.findViewById(R.id.rg); // 病害特征单选框-河床
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2); // 病害特征单选框-调治构造物
		rg3 = (RadioGroup) rootView.findViewById(R.id.rg3); // 病害特征单选框-翼墙、耳墙
		rg4 = (RadioGroup) rootView.findViewById(R.id.rg4); // 病害特征单选框-锥坡、护坡
		
		addContent = (EditText) rootView.findViewById(R.id.tv_add_content); // 病害描述
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // 病害图片按钮
		btnCamera = (Button) rootView.findViewById(R.id.btn_camera); // 打开照相机按钮
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // 病害图片
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_submit); // 提交按钮
		
		db = new DbOperation(this.getActivity());
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			if (args.getString("BED") != null) {
				rgFeature = rg1;
				rg1.setVisibility(View.VISIBLE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg1Jam = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_jam); // 堵塞
				
				optionStr = "BED";
				diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("BED") + ")");
				tableName = "disease_bed";
			}
			else if (args.getString("REGSTRUC") != null) {
				rgFeature = rg2;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.VISIBLE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg2Damage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_damage); // 损坏
				
				optionStr = "REGSTRUC";
				diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("REGSTRUC") + ")");
				tableName = "disease_regstruc";
			}
			else if (args.getString("WINGWALL") != null) {
				rgFeature = rg3;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.VISIBLE);
				rg4.setVisibility(View.GONE);
				
				rg3Breakage = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_breakage); // 破损
				
				optionStr = "WINGWALL";
				diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("WINGWALL") + ")");
				tableName = "disease_wingwall";
			}
			else {
				rgFeature = rg4;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.VISIBLE);
				
				rg4Defect = (RadioButton) rootView.findViewById(R.id.rg4_rbtn_defect); // 缺陷
				
				if (args.getString("CONSLOPE") != null) {
					optionStr = "CONSLOPE";
					diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("CONSLOPE") + ")");
					tableName = "disease_conslope";
				}
				else if (args.getString("PROSLOPE") != null) {
					optionStr = "PROSLOPE";
					diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("PROSLOPE") + ")");
					tableName = "disease_proslope";
				}				
			}
			
			final String itemName = args.getString("ITEM_NAME");
			final String bgCode = args.getString(optionStr);
			final String bgId = args.getString("BRIDGE_ID");
			
			// 设置病害特征监听
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb, rootView, bgCode, bgId, itemName); // 设置病害特征选择
				}				
			});
			
			if (optionStr == "BED")
				rg1Jam.setChecked(true); // 设置“堵塞”默认选中
			else if (optionStr == "REGSTRUC")
				rg2Damage.setChecked(true); // 设置“损坏”默认选中
			else if (optionStr == "WINGWALL")
				rg3Breakage.setChecked(true); // 设置“破损”默认选中
			else if (optionStr == "CONSLOPE" || optionStr == "PROSLOPE")
				rg4Defect.setChecked(true); // 设置“缺陷”默认选中			
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
	 * */
	private void setDiseaseFeature(RadioButton rb, View rootView, String bgCode, String bgId, String itemName) {
		
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
                getRootFragment().startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}			
		});
		
		// 打开照相机
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 拍照后返回本画面
				getRootFragment().startActivityForResult(intent, CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE);
			}
		});
		
		final View rv = rootView;
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
				
				String key = "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag";
				String values = "'" + bg_id + "','" + parts_id + "','" + item_name + "','" + rg_feature + "','" + add_content + "','" + disease_image + "','0'";
    			
    			int flag = db.insertData(tableName, key, values);
    			
    			if (flag == 0)
        			Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_SHORT).show();
    			else
    				Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();				
				
				// 刷新页面
				Bundle bd = new Bundle();
				bd.putString(optionStr, parts_id);
				bd.putString("BRIDGE_ID", bg_id);
				bd.putString("ITEM_NAME", item_name);
				
				// 创建Fragment对象
				Fragment frag = new sub2OtherFragment();
				
				// 向Fragment传入参数
				frag.setArguments(bd);
				// 使用fragment替换bridge_detail_container容器当前显示的Fragment
				getFragmentManager().beginTransaction()
					.replace(R.id.down_detail_container, frag)
					.commit();
			}			
		});		
	}
	
	/**
	 * 得到根Fragment
	 * 
	 * @return
	 */	 
	public Fragment getRootFragment() {
		Fragment fragment = getParentFragment();
		while (fragment.getParentFragment() != null) {
			fragment = fragment.getParentFragment();
		}	  
		return fragment;
	}
}
