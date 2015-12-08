package com.qjs.bridgedb.disease.subfragment.sub2;

import java.io.FileNotFoundException;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.DiseaseDetailFragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

public class abutmentFragment extends Fragment {
	private RadioGroup rgFeature,rg1,rg2,rg3;
	private Spinner spOtherDisease;
	private RadioButton rg1PeelOff;
	private RadioButton rg2Breakage;
	private RadioButton rg3Scouring;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // 保存本地图片地址
	private DbOperation db = null;
	private String optionStr = null; // 标签名
	private String tableName = null; // 待查询表名
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_down_page3, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // 病害名称		

		rg1 = (RadioGroup) rootView.findViewById(R.id.rg); // 病害特征单选框-桥台身
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2); // 病害特征单选框-桥台帽
		rg3 = (RadioGroup) rootView.findViewById(R.id.rg3); // 病害特征单选框-墩台基础
		
		addContent = (EditText) rootView.findViewById(R.id.tv_down1_add_content); // 病害描述
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // 病害图片按钮
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // 病害图片
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_down1_submit); // 提交按钮
		
		db = new DbOperation(this.getActivity());
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			if (args.getString("ATBODY") != null) {
				rgFeature = rg1;
				rg1.setVisibility(View.VISIBLE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				
				rg1PeelOff = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_peel_off); // 剥落
				spOtherDisease = (Spinner) rootView.findViewById(R.id.rg1_sp_other_disease); // 其他病害下拉列表
				
				optionStr = "ATBODY";
				diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("ATBODY") + ")");
				tableName = "disease_atbody";
			}
			else if (args.getString("ATCAPPING") != null) {
				rgFeature = rg2;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.VISIBLE);
				rg3.setVisibility(View.GONE);
				
				rg2Breakage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_breakage); // 破损
				
				optionStr = "ATCAPPING";
				diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("ATCAPPING") + ")");
				tableName = "disease_atcapping";
			}
			else if (args.getString("PA") != null) {
				rgFeature = rg3;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.VISIBLE);
				
				rg3Scouring = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_scouring); // 冲刷、淘空
								
				optionStr = "PA";
				diseaseDescription.setText("病害描述：" + args.getString("ITEM_NAME") + "(" + args.getString("PA") + ")");
				tableName = "disease_pa";
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
			
			if (optionStr == "ATBODY")
				rg1PeelOff.setChecked(true); // 设置“剥落”默认选中
			else if (optionStr == "ATCAPPING")
				rg2Breakage.setChecked(true); // 设置“破损”默认选中
			else if (optionStr == "PA")
				rg3Scouring.setChecked(true); // 设置“冲刷、淘空”默认选中
		}		
		return rootView;		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.getActivity();
		if (resultCode == FragmentActivity.RESULT_OK) {
            uri = data.getData();
            if (uri != null) {
	            ContentResolver cr = this.getActivity().getContentResolver();
	            try {  
	                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
	                // 将Bitmap设定到ImageView
	                ivImage.setImageBitmap(bitmap);
	            } catch (FileNotFoundException e) {}
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
                // 使用Intent.ACTION_GET_CONTENT这个Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 取得相片后返回本画面
                new DiseaseDetailFragment().getRootFragment().startActivityForResult(intent, 1);
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
				String sp_otherDisease = "0"; // 其他病害-病害信息
				String add_content = addContent.getText().toString(); // 病害描述
				String disease_image = null; // 本地图片地址
				if (uri != null)
					disease_image = uri.toString();
				if ("其他病害".equals(rbFeature.getText()))
					sp_otherDisease = spOtherDisease.getSelectedItem().toString(); // 其他病害-病害信息
				
				String key = null;
				String values = null;
				if (tableName == "disease_atbody") {
					key = "bg_id, parts_id, item_name, rg_feature, sp_otherDisease, add_content, disease_image, flag";
        			values = "'" + bg_id + "','" + parts_id + "','" + item_name + "','" + rg_feature + "','" + sp_otherDisease + "','" + add_content + "','" + disease_image + "','0'";						
				}
				else {
					key = "bg_id, parts_id, item_name, rg_feature, add_content, disease_image, flag";
        			values = "'" + bg_id + "','" + parts_id + "','" + item_name + "','" + rg_feature + "','" + add_content + "','" + disease_image + "','0'";
				}
    			
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
				Fragment frag = new abutmentFragment();
				
				// 向Fragment传入参数
				frag.setArguments(bd);
				// 使用fragment替换bridge_detail_container容器当前显示的Fragment
				getFragmentManager().beginTransaction()
					.replace(R.id.down_detail_container, frag)
					.commit();
			}			
		});		
	}
}
