package com.app.cartravel.utilitaire;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.cartravel.classes.Parcours;

public class ParcourDataSource {
	private DatabaseHelper m_Helper;
	private SQLiteDatabase m_Db;

	public ParcourDataSource(Context p_Context) {
		m_Helper = new DatabaseHelper(p_Context);
	}

	public void open() {
		m_Db = this.m_Helper.getWritableDatabase();
	}

	public void close() {
		m_Db.close();
	}

	public float insert(Parcours p_Parcour) {
		ContentValues row = parcourToContentValues(p_Parcour);
		float newId = (float) m_Db.insert(DatabaseHelper.TABLE_PARCOURS, null, row);
		p_Parcour.setId(newId);
		return newId;
	}

	public void update(Parcours p_Parcour) {
		ContentValues row = parcourToContentValues(p_Parcour);
		m_Db.update(DatabaseHelper.TABLE_PARCOURS, row,
				DatabaseHelper.COL_ID_PARCOUR + "=" + p_Parcour.getId(), null);
	}

	public void delete(int p_Id) {
		m_Db.delete(DatabaseHelper.TABLE_PARCOURS, DatabaseHelper.COL_ID_PARCOUR
				+ "=" + p_Id, null);
	}

	public void removeAll() {
		m_Db.delete(DatabaseHelper.TABLE_PARCOURS, null, null);
	}

	public Parcours getParcours(int p_IdParcour) {
		Parcours p = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_PARCOURS, null,
				DatabaseHelper.COL_ID_PARCOUR + "=" + p_IdParcour, null, null,
				null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			p = cursorToParcour(c);
		}
		return p;
	}

	public List<Parcours> getAllParcours() {
		List<Parcours> parcours = new ArrayList<Parcours>();
		Cursor c = m_Db.query(DatabaseHelper.TABLE_PARCOURS, null, null, null,
				null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Parcours p = cursorToParcour(c);
			parcours.add(p);
			c.moveToNext();
		}
		return parcours;
	}

	private ContentValues parcourToContentValues(Parcours p_Parcour) {
		ContentValues row = new ContentValues();
		row.put(DatabaseHelper.COL_ID_PARCOUR, p_Parcour.getId());
		row.put(DatabaseHelper.COL_ID_PROPRIETAIRE,
				p_Parcour.getIdProprietaire());
		row.put(DatabaseHelper.COL_ID_CONDUCTEUR, p_Parcour.getIdConducteur());
		row.put(DatabaseHelper.COL_JOUR, p_Parcour.getJour());
		row.put(DatabaseHelper.COL_HEURE, p_Parcour.getHeure());
		row.put(DatabaseHelper.COL_TYPE_PARCOUR, p_Parcour.getRepetitif() ? 0
				: 1);
		row.put(DatabaseHelper.COL_NBR_PLACE_DISPO, p_Parcour.getNbPlaceDispo());
		row.put(DatabaseHelper.COL_NBR_PLACE_PRISE, p_Parcour.getNbPlacePrise());
		row.put(DatabaseHelper.COL_DISTANCE_SUP_MAX,
				p_Parcour.getDistanceSupMax());
		row.put(DatabaseHelper.COL_NO_CIVIQUE_DEP, p_Parcour.getNumCiviqueDep());
		row.put(DatabaseHelper.COL_RUE_DEP, p_Parcour.getRueDep());
		row.put(DatabaseHelper.COL_VILLE_DEP, p_Parcour.getVilleDep());
		row.put(DatabaseHelper.COL_CODE_POSTAL_DEP,
				p_Parcour.getCodePostalDep());
		row.put(DatabaseHelper.COL_NO_CIVIQUE_ARR, p_Parcour.getNumCiviqueArr());
		row.put(DatabaseHelper.COL_RUE_ARR, p_Parcour.getRueArr());
		row.put(DatabaseHelper.COL_VILLE_ARR, p_Parcour.getVilleArr());
		row.put(DatabaseHelper.COL_CODE_POSTAL_ARR,
				p_Parcour.getCodePostalArr());

		return row;
	}

	private Parcours cursorToParcour(Cursor c) {
		Parcours p = new Parcours(
				c.getFloat(c.getColumnIndex(DatabaseHelper.COL_ID_PARCOUR)),
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_ID_PROPRIETAIRE)),
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_ID_CONDUCTEUR)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_JOUR)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_HEURE)),
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_TYPE_PARCOUR)) == 1 ? true
						: false,
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_NBR_PLACE_DISPO)),
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_NBR_PLACE_PRISE)),
				c.getFloat(c
						.getColumnIndex(DatabaseHelper.COL_DISTANCE_SUP_MAX)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_NO_CIVIQUE_DEP)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_RUE_DEP)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_VILLE_DEP)),
				c.getString(c
						.getColumnIndex(DatabaseHelper.COL_CODE_POSTAL_DEP)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_NO_CIVIQUE_ARR)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_RUE_ARR)), c
						.getString(c
								.getColumnIndex(DatabaseHelper.COL_VILLE_ARR)),
				c.getString(c
						.getColumnIndex(DatabaseHelper.COL_CODE_POSTAL_ARR)));
		return p;
	}
}
