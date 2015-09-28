package com.qjs.bridgedb.disease.subfragment.sub3;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class deckFragment extends Fragment {
	private RadioGroup rgFeature,rg1,rg2,rg3,rg4;
	private RadioButton rg1Track,rg1Bleeding,rg1Breakage,rg1Fissure;
	private RadioButton rg2Irregularity,rg2Anchorage,rg2Breakage,rg2Failure;
	private RadioButton rg3Impeded,rg3Flaw;
	private RadioButton rg4Dirty,rg4LackOfLighting,rg4SignMissing;
	private TextView diseaseDescription;
	private EditText addContent;
	private Button btnImage,btnSubmit;
	private ImageView ivImage;
	private Uri uri = null; // 保存本地图片地址
	private DbOperation db = null;
	private Cursor cursor = null;
	private String optionStr = null; // 标签名
	private String tableName = null; // 待查询表名
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_deck_page1, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // 病害名称		

		rg1 = (RadioGroup) rootView.findViewById(R.id.rg); // 病害特征单选框-桥面铺装
		rg2 = (RadioGroup) rootView.findViewById(R.id.rg2); // 病害特征单选框-伸缩缝
		rg3 = (RadioGroup) rootView.findViewById(R.id.rg3); // 病害特征单选框-防排水系统
		rg4 = (RadioGroup) rootView.findViewById(R.id.rg4); // 病害特征单选框-照明、标志
		
		addContent = (EditText) rootView.findViewById(R.id.tv_deck1_add_content); // 病害描述
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // 病害图片按钮
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // 病害图片
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_deck1_submit); // 提交按钮
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			if (args.getString("DECK") != null) {
				rgFeature = rg1;
				rg1.setVisibility(View.VISIBLE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg1Track = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_track); // 变形（车辙、拥包、高低不平等）
				rg1Bleeding = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_bleeding); // 泛油
				rg1Breakage = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_breakage); // 破损
				rg1Fissure = (RadioButton) rootView.findViewById(R.id.rg1_rbtn_fissure); // 裂缝
				
				optionStr = "DECK";
				diseaseDescription.setText("病害描述：桥面铺装(" + args.getString("DECK") + ")");
				tableName = "disease_deck";
			}
			else if (args.getString("JOINT") != null) {
				rgFeature = rg2;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.VISIBLE);
				rg3.setVisibility(View.GONE);
				rg4.setVisibility(View.GONE);
				
				rg2Irregularity = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_irregularity); // 凹凸不平
				rg2Anchorage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_anchorage); // 锚固区缺陷
				rg2Breakage = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_breakage); // 破损
				rg2Failure = (RadioButton) rootView.findViewById(R.id.rg2_rbtn_failure); // 失效
				
				optionStr = "JOINT";
				diseaseDescription.setText("病害描述：伸缩缝装置(" + args.getString("JOINT") + ")");
				tableName = "disease_joint";
			}
			else if (args.getString("WATERTIGHT") != null) {
				rgFeature = rg3;
				rg1.setVisibility(View.GONE);
				rg2.setVisibility(View.GONE);
				rg3.setVisibility(View.VISIBLE);
				rg4.setVisibility(View.GONE);
				
				rg3Impeded = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_impeded); // 排水不畅
				rg3Flaw = (RadioButton) rootView.findViewById(R.id.rg3_rbtn_flaw); // 排水管、引水槽缺陷
				
				optionStr = "WATERTIGHT";
				diseaseDescription.setText("病害描述：防排水系统(" + args.getString("WATERTIGHT") + ")");
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
				
				optionStr = "LIGHTING";
				diseaseDescription.setText("病害描述：照明、标志(" + args.getString("LIGHTING") + ")");
				tableName = "disease_lighting";
			}
			
			final String bgCode = args.getString(optionStr);
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
			
			if (optionStr == "DECK")
				rg1Track.setChecked(true); // 设置“变形（车辙、拥包、高低不平等）”默认选中
			else if (optionStr == "JOINT")
				rg2Irregularity.setChecked(true); // 设置“凹凸不平”默认选中
			else if (optionStr == "WATERTIGHT")
				rg3Impeded.setChecked(true); // 设置“排水不畅”默认选中
			else if (optionStr == "LIGHTING")
				rg4Dirty.setChecked(true); // 设置“污损或破坏”默认选中
			
			db = new DbOperation(this.getActivity());
			cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'" + " and parts_id='" + bgCode + "'"); // 查找表中是否有对应的数据
			
			
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
				}
				else if (optionStr == "WATERTIGHT") {
					if ((rg3Impeded.getText().toString()).equals(rg_feature))
						rg3Impeded.setChecked(true);
					if ((rg3Flaw.getText().toString()).equals(rg_feature))
						rg3Flaw.setChecked(true);
				}
				else if (optionStr == "LIGHTING") {
					if ((rg4Dirty.getText().toString()).equals(rg_feature))
						rg4Dirty.setChecked(true);
					if ((rg4LackOfLighting.getText().toString()).equals(rg_feature))
						rg4LackOfLighting.setChecked(true);
					if ((rg4SignMissing.getText().toString()).equals(rg_feature))
						rg4SignMissing.setChecked(true);
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
				String add_content = addContent.getText().toString(); // 病害描述
				String disease_image = null; // 本地图片地址
				if (uri != null)
					disease_image = uri.toString();
				
				int flag1 = 0;
				int flag2 = 0;
				
				if (cursor.moveToFirst()) { // 如果有则修改
					String sql = "rg_feature='" + rg_feature + "',add_content='" + add_content + "',disease_image='" + disease_image + "',flag='0'";
    				
    				flag1 = db.updateData(tableName, sql, "bg_id='" + bg_id + "'" + " and parts_id='" + parts_id + "'");
    				
    				if (flag1 == 0)
            			Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
        			else
        				Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
				}
				else { // 没有则插入
					String key = "bg_id, parts_id, rg_feature, add_content, disease_image, flag";
					String values = "'" + bg_id + "','" + parts_id + "','" + rg_feature + "','" + add_content + "','" + disease_image + "','0'";
        			
        			flag2 = db.insertData(tableName, key, values);
        			
        			if (flag2 == 0)
            			Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_SHORT).show();
        			else
        				Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
				}
				cursor.close();
				
				// 刷新页面
				Bundle bd = new Bundle();
				bd.putString(optionStr, parts_id);
				bd.putString("BRIDGE_ID", bg_id);
				
				// 创建Fragment对象
				Fragment frag = new deckFragment();
				
				// 向Fragment传入参数
				frag.setArguments(bd);
				// 使用fragment替换bridge_detail_container容器当前显示的Fragment
				getFragmentManager().beginTransaction()
					.replace(R.id.deck_detail_container, frag)
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
