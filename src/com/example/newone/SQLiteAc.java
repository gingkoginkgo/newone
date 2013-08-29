package com.example.newone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Handler;

public class SQLiteAc extends SQLiteOpenHelper {
	private static final String dbName = "todos";

	public SQLiteAc(Context context) {
		super(context, dbName, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String DATABASE_CREATE_TABLE = "create table todos("
				+ "num INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
				+ "desc VARCHAR," + "sTime VARCHAR," + "dLine VARCHAR,"
				+ "pri FLOAT," + "action VARCHAR," + "tPlace VARCHAR" + ")";
		db.execSQL(DATABASE_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public void incert(String desc, String sTime, String dLine, float pri,
			String action, String tPlace) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("desc", desc);
		values.put("sTime", sTime);
		values.put("dLine", dLine);
		values.put("pri", String.valueOf(pri));
		values.put("action", action);
		values.put("tPlace", tPlace);
		db.insert(dbName, null, values);
	}

	public void delete(int num) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(dbName, "num = " + num, null);
	}

	public Cursor getdatas() {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = { "num", "desc", "sTime", "dLine", "pri", "action",
				"tPlace" };
		Cursor cursor = db.query(dbName, columns, null, null, null, null, "num"
				+ " DESC");
		return cursor;
	}

}
