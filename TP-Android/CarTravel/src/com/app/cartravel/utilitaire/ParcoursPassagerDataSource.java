package com.app.cartravel.utilitaire;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.cartravel.classes.ParcoursPassager;

public class ParcoursPassagerDataSource {

	private DatabaseHelper m_Helper;
	private SQLiteDatabase m_Db;

	public ParcoursPassagerDataSource(Context p_Context) {
		m_Helper = new DatabaseHelper(p_Context);
	}

	public void open() {
		m_Db = this.m_Helper.getWritableDatabase();
	}

	public void close() {
		m_Db.close();
	}

	public String insert(ParcoursPassager p_ParcourPassager) {
		ContentValues row = parcourPassagerToContentValues(p_ParcourPassager);
		String newId = String.valueOf((int) m_Db.insert(
				DatabaseHelper.TABLE_PARCOURS_PASSAGER, null, row));
		p_ParcourPassager.SetIdParcoursPassager(newId);
		return newId;
	}

	public void update(ParcoursPassager p_ParcourPassager) {
		ContentValues row = parcourPassagerToContentValues(p_ParcourPassager);
		m_Db.update(DatabaseHelper.TABLE_PARCOURS_PASSAGER, row,
				DatabaseHelper.COL_ID_PARCOURS_PASSAGER + "='"
						+ p_ParcourPassager.getIdParcoursPassager() + "'", null);
	}

	public void delete(String p_Id) {
		m_Db.delete(DatabaseHelper.TABLE_PARCOURS_PASSAGER,
				DatabaseHelper.COL_ID_PARCOURS_PASSAGER + "='" + p_Id + "'",
				null);
	}

	public void removeAll() {
		m_Db.delete(DatabaseHelper.TABLE_PARCOURS_PASSAGER, null, null);
	}

	public ParcoursPassager getParcoursPassager(String p_IdParcourPassager) {
		ParcoursPassager p = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_PARCOURS_PASSAGER, null,
				DatabaseHelper.COL_ID_PARCOURS_PASSAGER + "='"
						+ p_IdParcourPassager + "'", null, null, null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			p = cursorToParcourPassager(c);
		}
		return p;
	}

	public List<ParcoursPassager> getAllParcoursPassager() {
		List<ParcoursPassager> parcoursPassager = new ArrayList<ParcoursPassager>();
		Cursor c = m_Db.query(DatabaseHelper.TABLE_PARCOURS, null, null, null,
				null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			ParcoursPassager pP = cursorToParcourPassager(c);
			parcoursPassager.add(pP);
			c.moveToNext();
		}
		return parcoursPassager;
	}

	private ContentValues parcourPassagerToContentValues(
			ParcoursPassager p_ParcourPassager) {
		ContentValues row = new ContentValues();
		row.put(DatabaseHelper.COL_ID_PARCOURS_PASSAGER,
				p_ParcourPassager.getIdParcoursPassager());
		if (p_ParcourPassager.getListIdPassager() != null) {
			row.put(DatabaseHelper.COL_ID_PASSAGERS,
					((ArrayList<String>) p_ParcourPassager.getListIdPassager())
							.toString());
		}
		row.put(DatabaseHelper.COL_NBR_PASSAGERS,
				p_ParcourPassager.getNbrPassagers());
		return row;
	}

	private ParcoursPassager cursorToParcourPassager(Cursor c) {
		ParcoursPassager pP = new ParcoursPassager();
		pP.SetIdParcoursPassager(c.getString(c
				.getColumnIndex(DatabaseHelper.COL_ID_PARCOURS_PASSAGER)));
		pP.setListIdPassager(Util.ParseStringToList(
				c.getString(c.getColumnIndex(DatabaseHelper.COL_ID_PASSAGERS)),
				", "));
		pP.setNbrPassagers(c.getInt(c
				.getColumnIndex(DatabaseHelper.COL_NBR_PASSAGERS)));
		;
		return pP;
	}
}
