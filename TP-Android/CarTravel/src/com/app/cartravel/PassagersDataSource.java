package com.app.cartravel;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.cartravel.classes.Passagers;
import com.app.cartravel.utilitaire.DatabaseHelper;

public class PassagersDataSource {
	private DatabaseHelper m_Helper;
	private SQLiteDatabase m_Db;

	public PassagersDataSource(Context p_Context) {
		m_Helper = new DatabaseHelper(p_Context);
	}

	public void open() {
		m_Db = this.m_Helper.getWritableDatabase();
	}

	public void close() {
		m_Db.close();
	}
	
	public String insert(Passagers p_Passagers) {
		ContentValues row = passagersToContentValues(p_Passagers);
		String newId = String.valueOf((int) m_Db.insert(
				DatabaseHelper.TABLE_PARCOUR_PASSAGER, null, row));
		return newId;
	}

	public void update(Passagers p_Passagers) {
		ContentValues row = passagersToContentValues(p_Passagers);
		m_Db.update(DatabaseHelper.TABLE_PARCOUR_PASSAGER, row,
				DatabaseHelper.COL_ID_PARCOUR + "='" + p_Passagers.getdParcours() + "'", null);
	}

	public void delete(int p_Id) {
		m_Db.delete(DatabaseHelper.TABLE_PARCOUR_PASSAGER,
				DatabaseHelper.COL_ID_PARCOUR + "=" + p_Id, null);
	}

	public void removeAll() {
		m_Db.delete(DatabaseHelper.TABLE_PARCOURS, null, null);
	}

	public Passagers getPassager(String p_IdParcour) {
		Passagers p = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_PARCOURS, null,
				DatabaseHelper.COL_ID_PARCOUR + "='" + p_IdParcour + "'", null, null,
				null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			p = cursorToPassagers(c);
		}
		return p;
	}

	public List<Passagers> getAllPassagers() {
		List<Passagers> passagers = new ArrayList<Passagers>();
		Cursor c = m_Db.query(DatabaseHelper.TABLE_PARCOURS, null, null, null,
				null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Passagers p = cursorToPassagers(c);
			passagers.add(p);
			c.moveToNext();
		}
		return passagers;
	}

	private ContentValues passagersToContentValues(Passagers p_Passagers) {
		ContentValues row = new ContentValues();
		row.put(DatabaseHelper.COL_ID_PARCOUR, p_Passagers.getdParcours());
		row.put(DatabaseHelper.COL_ID_PASSAGER, p_Passagers.getPassager());
		row.put(DatabaseHelper.COL_NBR_PASSAGER, p_Passagers.getNbrPassagers());

		return row;
	}

	private Passagers cursorToPassagers(Cursor c) {
		Passagers p = new Passagers(
				c.getString(c.getColumnIndex(DatabaseHelper.COL_ID_PARCOUR)),
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_ID_PASSAGER)),
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_NBR_PASSAGER)));
		return p;
	}
}
