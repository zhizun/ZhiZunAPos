package com.ch.epw.db;

import java.util.ArrayList;
import java.util.List;

import com.zhizun.pos.bean.PhoneContactBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil {
	private static DBUtil mInstance;
	private Context mContext;
	private SQLHelper mSQLHelp;
	private SQLiteDatabase mSQLiteDatabase;

	private DBUtil(Context context) {
		mContext = context;
		mSQLHelp = new SQLHelper(context);
		mSQLiteDatabase = mSQLHelp.getWritableDatabase();
	}
	/**
	 * 初始化数据库操作DBUtil类
	 */
	public static DBUtil getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DBUtil(context);
		}
		return mInstance;
	}
	/**
	 * 关闭数据库
	 */
	public void close() {
		mSQLHelp.close();
		mSQLHelp = null;
		mSQLiteDatabase.close();
		mSQLiteDatabase = null;
		mInstance = null;
	}

	/**
	 * 添加数据
	 */
	public void insertData(ContentValues values) {
		mSQLiteDatabase.insert(SQLHelper.TABLE_CHANNEL, null, values);
	}

	/**
	 * 更新数据
	 * 
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 */
	public void updateData(ContentValues values, String whereClause,
			String[] whereArgs) {
		mSQLiteDatabase.update(SQLHelper.TABLE_CHANNEL, values, whereClause,
				whereArgs);
	}

	/**
	 * 删除数据
	 * 
	 * @param whereClause
	 * @param whereArgs
	 */
	public void deleteData(String whereClause, String[] whereArgs) {
		mSQLiteDatabase
				.delete(SQLHelper.TABLE_CHANNEL, whereClause, whereArgs);
	}

	/**
	 * 查询数据
	 * 
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor selectData(String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		Cursor cursor = mSQLiteDatabase.query(SQLHelper.TABLE_CHANNEL,columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;
	}
	
	/**
	 * 手机联系人，增加
	 */
	public void insertConstantData(List<PhoneContactBean> PhoneContact) {
		if (PhoneContact.size()>0) {
		for (int i = 0; i < PhoneContact.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("desplayName", PhoneContact.get(i).getDesplayName());
			values.put("phoneNum", PhoneContact.get(i).getPhoneNum());
			mSQLiteDatabase.insert(SQLHelper.TABLE_PHONE_CONTACT, null, values);
		}
		close();
		}
	}
	/**
	 * 手机联系人，删除
	 * @param phone
	 */
	public void deleteConstant(String phone){
		mSQLiteDatabase.delete(SQLHelper.TABLE_PHONE_CONTACT, "phoneNum" + "=?", new String[]{String.valueOf(phone)});
	}
	/**
	 * 手机联系人，查询
	 */
	public List<PhoneContactBean> findById() {
		ArrayList<PhoneContactBean> pe = new ArrayList<PhoneContactBean>();  
		String sql="select * from contact";
		Cursor cursor =mSQLiteDatabase.rawQuery(sql,null);
      while (cursor.moveToNext()) {
    	  PhoneContactBean phoneContactBean=new PhoneContactBean();
    	  String desplayName = cursor.getString(cursor.getColumnIndex("desplayName"));
    	  String phoneNum = cursor.getString(cursor.getColumnIndex("phoneNum"));
    	  phoneContactBean.setDesplayName(desplayName);
    	  phoneContactBean.setPhoneNum(phoneNum);
    	  pe.add(phoneContactBean);
      }  
      close();
      return pe;
		
	}  
	
	
}