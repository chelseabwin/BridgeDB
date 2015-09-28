package com.qjs.bridgedb.disease.subfragment.sub2;

import java.io.FileNotFoundException;

import com.qjs.bridgedb.DbOperation;
import com.qjs.bridgedb.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class pierFragment extends Fragment {
	private RadioGroup rgFeature,rgFissure;
	private Spinner spOtherDisease;
	private LinearLayout local1,local2;
	private RadioButton voidsPits,leakageTendon,cavitation,masonryDefects,fissure,otherDisease;
	private RadioButton fissure1,fissure2,fissure3,fissure4;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // 保存本地图片地址
	private DbOperation db = null;
	private Cursor cursor = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_down_page1, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // 病害名称
		
		rgFeature = (RadioGroup) rootView.findViewById(R.id.rg); // 病害特征单选框
		rgFissure = (RadioGroup) rootView.findViewById(R.id.rg2); // 裂缝单选框
		
		spOtherDisease = (Spinner) rootView.findViewById(R.id.sp_other_disease); // 其他病害下拉列表
		
		local1 = (LinearLayout) rootView.findViewById(R.id.down1_ll_1); // 位置信息（含网裂缝除其他裂缝）
		local2 = (LinearLayout) rootView.findViewById(R.id.down1_ll_2); // 其他裂缝位置信息
		
		voidsPits = (RadioButton) rootView.findViewById(R.id.rbtn_voids_pits); // 蜂窝麻面
		leakageTendon = (RadioButton) rootView.findViewById(R.id.rbtn_leakage_tendon); // 剥落露筋
		cavitation = (RadioButton) rootView.findViewById(R.id.rbtn_cavitation); // 空洞孔洞
		masonryDefects = (RadioButton) rootView.findViewById(R.id.rbtn_masonry_defects); // 圬工砌体缺陷
		fissure = (RadioButton) rootView.findViewById(R.id.rbtn_fissure); // 裂缝
		otherDisease = (RadioButton) rootView.findViewById(R.id.rbtn_other_disease); // 其他病害
		
		fissure1 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure1); // 竖向裂缝
		fissure2 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure2); // 斜向裂缝
		fissure3 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure3); // 水平裂缝
		fissure4 = (RadioButton) rootView.findViewById(R.id.rbtn_fissure4); // 网裂缝
		
		addContent = (EditText) rootView.findViewById(R.id.tv_down1_add_content); // 病害描述
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // 病害图片按钮
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // 病害图片
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_down1_submit); // 提交按钮
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			diseaseDescription.setText("病害描述：桥墩(" + args.getString("PIER") + ")");
			
			final String bgCode = args.getString("PIER");
			final String bgId = args.getString("BRIDGE_ID");
			
			// 设置病害特征监听
			rgFeature.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub					
					RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
					setDiseaseFeature(rb,rootView,bgCode,bgId); // 设置病害特征选择
				}				
			});
			voidsPits.setChecked(true); // 设置“蜂窝麻面”默认选中
			
			db = new DbOperation(this.getActivity());
			cursor = db.queryData("*", "disease_pier", "bg_id='" + bgId + "'" + " and parts_id='" + bgCode + "'"); // 查找disease_pier表中是否有对应的数据
			
			// 如果有则填入相应数据
			if (cursor.moveToFirst()) {
				String rg_feature = cursor.getString(cursor.getColumnIndex("rg_feature"));
				String rg_fissure = cursor.getString(cursor.getColumnIndex("rg_fissure"));
				
				if ((fissure.getText().toString()).equals(rg_feature)) { // 如果是裂缝
					fissure.setChecked(true);
					
					if ((fissure4.getText().toString()).equals(rg_fissure)) { // 如果是网裂缝
						fissure4.setChecked(true);
						
						((EditText) rootView.findViewById(R.id.down1_ll1_dis_desc2)).setText(cursor.getString(cursor.getColumnIndex("l1_start")));
						((EditText) rootView.findViewById(R.id.down1_ll1_dis_desc3)).setText(cursor.getString(cursor.getColumnIndex("l1_end")));
						((EditText) rootView.findViewById(R.id.down1_ll1_dis_desc4)).setText(cursor.getString(cursor.getColumnIndex("l1_area")));
					}
					else { // 其他裂缝
						if ((fissure1.getText().toString()).equals(rg_fissure))
							fissure1.setChecked(true);
						if ((fissure2.getText().toString()).equals(rg_fissure))
							fissure2.setChecked(true);
						if ((fissure3.getText().toString()).equals(rg_fissure))
							fissure3.setChecked(true);
						
						((EditText) rootView.findViewById(R.id.down1_ll2_dis_desc2)).setText(cursor.getString(cursor.getColumnIndex("l2_start")));
						((EditText) rootView.findViewById(R.id.down1_ll2_dis_desc3)).setText(cursor.getString(cursor.getColumnIndex("l2_length")));
						((EditText) rootView.findViewById(R.id.down1_ll2_dis_desc4)).setText(cursor.getString(cursor.getColumnIndex("l2_width")));
					}
				}
				else { // 除裂缝以外的病害
					if ((voidsPits.getText().toString()).equals(rg_feature))
						voidsPits.setChecked(true);
					if ((leakageTendon.getText().toString()).equals(rg_feature))
						leakageTendon.setChecked(true);
					if ((cavitation.getText().toString()).equals(rg_feature))
						cavitation.setChecked(true);
					if ((masonryDefects.getText().toString()).equals(rg_feature))
						masonryDefects.setChecked(true);
					if ((otherDisease.getText().toString()).equals(rg_feature)) { // 其他病害
						otherDisease.setChecked(true);
						DbOperation.setSpinnerItemSelectedByValue(spOtherDisease, cursor.getString(cursor.getColumnIndex("sp_otherDisease")));						
					}
					((EditText) rootView.findViewById(R.id.down1_ll1_dis_desc2)).setText(cursor.getString(cursor.getColumnIndex("l1_start")));
					((EditText) rootView.findViewById(R.id.down1_ll1_dis_desc3)).setText(cursor.getString(cursor.getColumnIndex("l1_end")));
					((EditText) rootView.findViewById(R.id.down1_ll1_dis_desc4)).setText(cursor.getString(cursor.getColumnIndex("l1_area")));					
				}
				
				addContent.setText(cursor.getString(cursor.getColumnIndex("add_content")));
				
				uri = Uri.parse(cursor.getString(cursor.getColumnIndex("disease_image")));
				if (uri != null) {
					try {  
		                Bitmap bitmap = BitmapFactory.decodeStream(this.getActivity().getContentResolver().openInputStream(uri));  
		                // 将Bitmap设定到ImageView
		                ivImage.setImageBitmap(bitmap); // 设置图片
		            } catch (FileNotFoundException e) {}
				}
			}
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
	 * */
	private void setDiseaseFeature(RadioButton rb, View rootView, String bgCode, String bgId) {
		if ("裂缝".equals(rb.getText())) {
			if (spOtherDisease.getVisibility() != View.GONE)			
				spOtherDisease.setVisibility(View.GONE); // 隐藏“其他病害”下拉列表
			if (rgFissure.getVisibility() != View.VISIBLE)
				rgFissure.setVisibility(View.VISIBLE); // 如果裂缝单选框不可见，设为可见
			rgFissure.clearCheck(); // 清空裂缝单选框选择
			
			//设置裂缝单选框点击监听
			rgFissure.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if (checkedId == fissure4.getId()) {
						// 显示位置信息1
						local1.setVisibility(View.VISIBLE);
						local2.setVisibility(View.GONE);
					}
					else {
						// 显示位置信息2
						local1.setVisibility(View.GONE);
						local2.setVisibility(View.VISIBLE);
					}
				}								
			});
			fissure1.setChecked(true); // 设置“竖向裂缝”默认选中
		}
		else {
			if ("其他病害".equals(rb.getText()))
				spOtherDisease.setVisibility(View.VISIBLE); // 显示“其他病害”下拉列表
			else
				spOtherDisease.setVisibility(View.GONE); // 隐藏“其他病害”下拉列表
			
			if (local1.getVisibility() != View.VISIBLE)
				local1.setVisibility(View.VISIBLE); // 如果位置信息1不可见，设为可见
			if (local2.getVisibility() != View.GONE)
				local2.setVisibility(View.GONE); // 如果位置信息2可见，设为不可见
			if (rgFissure.getVisibility() != View.GONE)
				rgFissure.setVisibility(View.GONE); // 如果裂缝单选框可见，设为不可见
		}
		
		// 选择病害图片监听
		btnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                // 开启Pictures画面Type设定为image
                intent.setType("image/*");
                // 使用Intent.ACTION_GET_CONTENT这个Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 取得相片后返回本画面
                getRootFragment().startActivityForResult(intent, 1);
			}			
		});
		
		final View rv = rootView;
		final String parts_id = bgCode;
		final String bg_id = bgId;
		
		// 提交按钮监听
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RadioButton rbFeature = (RadioButton) rv.findViewById(rgFeature.getCheckedRadioButtonId());
				
				String rg_feature = rbFeature.getText().toString(); // 病害特征
				String rg_fissure = "0"; // 裂缝
				String sp_otherDisease = "0"; // 其他病害-病害信息
				String l1_start = "0"; // l1开始位置 
				String l1_end = "0"; // l1结束位置
				String l1_area = "0"; // l1面积
				String l2_start = "0"; // l2开始位置
				String l2_length = "0"; // l2长度
				String l2_width = "0";	// l2宽度
				String add_content = addContent.getText().toString(); // 病害描述
				String disease_image = null; // 本地图片地址
				if (uri != null)
					disease_image = uri.toString();
				
				if ("裂缝".equals(rbFeature.getText())) {
					RadioButton rbFissure = (RadioButton) rv.findViewById(rgFissure.getCheckedRadioButtonId());
					rg_fissure = rbFissure.getText().toString(); // 裂缝
					
					if ("网裂缝".equals(rbFissure.getText())) {
						l1_start = ((EditText) rv.findViewById(R.id.down1_ll1_dis_desc2)).getText().toString();
						l1_end = ((EditText) rv.findViewById(R.id.down1_ll1_dis_desc3)).getText().toString();
						l1_area = ((EditText) rv.findViewById(R.id.down1_ll1_dis_desc4)).getText().toString();
					}
					else {
						l2_start = ((EditText) rv.findViewById(R.id.down1_ll2_dis_desc2)).getText().toString();
						l2_length = ((EditText) rv.findViewById(R.id.down1_ll2_dis_desc3)).getText().toString();
						l2_width = ((EditText) rv.findViewById(R.id.down1_ll2_dis_desc4)).getText().toString();						
					}
				}
				else {
					if ("其他病害".equals(rbFeature.getText()))
						sp_otherDisease = spOtherDisease.getSelectedItem().toString(); // 其他病害-病害信息
					
					l1_start = ((EditText) rv.findViewById(R.id.down1_ll1_dis_desc2)).getText().toString();
					l1_end = ((EditText) rv.findViewById(R.id.down1_ll1_dis_desc3)).getText().toString();
					l1_area = ((EditText) rv.findViewById(R.id.down1_ll1_dis_desc4)).getText().toString();
				}
				
				int flag1 = 0;
				int flag2 = 0;
				
				if (cursor.moveToFirst()) { // 如果有则修改
					String sql = "rg_feature='" + rg_feature + "',rg_fissure='" + rg_fissure + "',sp_otherDisease='" + sp_otherDisease
							+ "',l1_start='" + l1_start + "',l1_end='" + l1_end + "',l1_area='" + l1_area + "',l2_start='" + l2_start
							+ "',l2_length='" + l2_length + "',l2_width='" + l2_width + "',add_content='" + add_content
							+ "',disease_image='" + disease_image + "',flag='0'";
    				
    				flag1 = db.updateData("disease_pier", sql, "bg_id='" + bg_id + "'" + " and parts_id='" + parts_id + "'");
    				
    				if (flag1 == 0)
            			Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
        			else
        				Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
				}
				else { // 没有则插入
					String key = "bg_id, parts_id, rg_feature, rg_fissure, sp_otherDisease, l1_start, l1_end, l1_area, l2_start,"
							+ "l2_length, l2_width, add_content, disease_image, flag";
        			String values = "'" + bg_id + "','" + parts_id + "','" + rg_feature + "','" + rg_fissure + "','" + sp_otherDisease + "','"
        					 + l1_start + "','" + l1_end + "','" + l1_area + "','" + l2_start + "','" + l2_length + "','" + l2_width + "','"
        					 + add_content + "','" + disease_image + "','0'";
        			
        			flag2 = db.insertData("disease_pier", key, values);
        			
        			if (flag2 == 0)
            			Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_SHORT).show();
        			else
        				Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
				}
				cursor.close();
				
				// 刷新页面
				Bundle bd = new Bundle();
				bd.putString("PIER", parts_id);
				bd.putString("BRIDGE_ID", bg_id);
				
				// 创建Fragment对象
				Fragment frag = new pierFragment();
				
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
	private Fragment getRootFragment() {
		Fragment fragment = getParentFragment();	  
		while (fragment.getParentFragment() != null) {
			fragment = fragment.getParentFragment();
		}	  
		return fragment;
	}
}
