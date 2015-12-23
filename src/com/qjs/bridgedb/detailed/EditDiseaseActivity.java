package com.qjs.bridgedb.detailed;

import com.qjs.bridgedb.R;
import com.qjs.bridgedb.disease.subfragment.sub1.girderEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub1.supportEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub1.wetJointEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.abutmentEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.beamEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.pierEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub2.sub2OtherEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub3.deckEditFragment;
import com.qjs.bridgedb.disease.subfragment.sub3.sub3OtherEditFragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class EditDiseaseActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_disease_edit_dlg);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		Bundle bd = new Bundle();
		bd.putString("BRIDGE_ID", bundle.getString("BRIDGE_ID"));
		bd.putInt("ITEM_ID", bundle.getInt("ITEM_ID"));
		bd.putString("ACROSS_NUM", bundle.getString("ACROSS_NUM"));
		bd.putString("ITEM_NAME", bundle.getString("ITEM_NAME"));
		
		int tableId = bundle.getInt("TABLE_ID");
		Fragment frt = null;
		
		switch (tableId) {
		// 上部
		case 0:
			frt = new girderEditFragment();
			break;
		case 1:
			frt = new wetJointEditFragment();
			break;
		case 2:
			frt = new supportEditFragment();
			break;
		// 下部
		case 3:
			frt = new pierEditFragment();
			break;
		case 4:
		case 5:
			frt = new beamEditFragment();
			break;
		case 6:
		case 7:
		case 8:
			frt = new abutmentEditFragment();
			break;
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
			frt = new sub2OtherEditFragment();
			break;
		// 桥面系
		case 14:
		case 15:
			frt = new deckEditFragment();
			break;
		case 16:
		case 17:
			frt = new sub3OtherEditFragment();
			break;
		case 18:
		case 19:
			frt = new deckEditFragment();
			break;
		default:
			break;
		}
		
		frt.setArguments(bd);
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.edit_disease_dlg, frt)
			.commit();
	}
	
	/**
	 * 根据绝对路径获取图片
	 * 
	 * @return
	 */
	public static final Bitmap getBitmap(String fileName) {
		Bitmap bitmap = null;
		try {
			Options options = new Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(fileName, options);
			options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
					(double) options.outWidth / 1024f,
					(double) options.outHeight / 1024f)));
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(fileName, options);			
		} catch (OutOfMemoryError error) {
			error.printStackTrace();			
		}		
		return bitmap;		
	}
	
	/**
	 * Android4.4 之后获取图片路径的方法
	 * 
	 * @return
	 */
	public static String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {

	        // Return the remote address
	        if (isGooglePhotosUri(uri))
	            return uri.getLastPathSegment();

	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
}
