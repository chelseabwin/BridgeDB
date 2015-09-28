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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class beamFragment extends Fragment {
	private RadioGroup rgFeature;
	private RadioButton voidsPits,leakageTendon,cavitation,corrosion,carbonation,fissure;
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
		final View rootView = inflater.inflate(R.layout.fragment_down_page2, container, false);
		diseaseDescription = (TextView) rootView.findViewById(R.id.tv_disease_description); // 病害名称
		
		rgFeature = (RadioGroup) rootView.findViewById(R.id.rg); // 病害特征单选框
		
		voidsPits = (RadioButton) rootView.findViewById(R.id.rbtn_voids_pits); // 蜂窝麻面
		leakageTendon = (RadioButton) rootView.findViewById(R.id.rbtn_leakage_tendon); // 剥落露筋
		cavitation = (RadioButton) rootView.findViewById(R.id.rbtn_cavitation); // 空洞孔洞
		corrosion = (RadioButton) rootView.findViewById(R.id.rbtn_corrosion); // 钢筋锈蚀
		carbonation = (RadioButton) rootView.findViewById(R.id.rbtn_carbonation); // 混凝土碳化、腐蚀
		fissure = (RadioButton) rootView.findViewById(R.id.rbtn_fissure); // 裂缝
		
		addContent = (EditText) rootView.findViewById(R.id.tv_down1_add_content); // 病害描述
		
		btnImage = (Button) rootView.findViewById(R.id.btn_image); // 病害图片按钮
		ivImage = (ImageView) rootView.findViewById(R.id.iv_image); // 病害图片
		
		btnSubmit = (Button) rootView.findViewById(R.id.btn_down1_submit); // 提交按钮
		
		Bundle args = getArguments();
		if (args != null) {
			diseaseDescription.setTextSize(25);
			if (args.getString("BENTCAP") != null) {
				optionStr = "BENTCAP";
				diseaseDescription.setText("病害描述：盖梁(" + args.getString("BENTCAP") + ")");
				tableName = "disease_bentcap";
			}
			else if (args.getString("TIEBEAM") != null) {
				optionStr = "TIEBEAM";
				diseaseDescription.setText("病害描述：系梁(" + args.getString("TIEBEAM") + ")");
				tableName = "disease_tiebeam";
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
			voidsPits.setChecked(true); // 设置“蜂窝麻面”默认选中
			
			db = new DbOperation(this.getActivity());
			cursor = db.queryData("*", tableName, "bg_id='" + bgId + "'" + " and parts_id='" + bgCode + "'"); // 查找表中是否有对应的数据
			
			// 如果有则填入相应数据
			if (cursor.moveToFirst()) {
				String rg_feature = cursor.getString(cursor.getColumnIndex("rg_feature"));
				
				if ((voidsPits.getText().toString()).equals(rg_feature))
					voidsPits.setChecked(true);
				if ((leakageTendon.getText().toString()).equals(rg_feature))
					leakageTendon.setChecked(true);
				if ((cavitation.getText().toString()).equals(rg_feature))
					cavitation.setChecked(true);
				if ((corrosion.getText().toString()).equals(rg_feature))
					corrosion.setChecked(true);
				if ((carbonation.getText().toString()).equals(rg_feature))
					carbonation.setChecked(true);
				if ((fissure.getText().toString()).equals(rg_feature))
					fissure.setChecked(true);
				
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
				Fragment frag = new beamFragment();
				
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
