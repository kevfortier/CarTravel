package com.app.cartravel.utilitaire;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.database.DatabaseUtilsCompat;

import com.app.cartravel.classes.Utilisateurs;

public class UtilisateurDataSource {
	private DatabaseHelper m_Helper;
	private SQLiteDatabase m_Db;

	public UtilisateurDataSource(Context p_Context) {
		m_Helper = new DatabaseHelper(p_Context);
	}

	public void open() {
		m_Db = this.m_Helper.getWritableDatabase();
	}

	public void close() {
		m_Db.close();
	}

	public int insert(Utilisateurs p_Utilisateur) {
		ContentValues row = utilisateurToContentValues(p_Utilisateur);
		int newId = (int) m_Db.insert(DatabaseHelper.TABLE_UTILISATEUR, null,
				row);
		p_Utilisateur.setId(newId);
		return newId;
	}

	public void update(Utilisateurs p_Utilisateur) {
		ContentValues row = utilisateurToContentValues(p_Utilisateur);
		m_Db.update(DatabaseHelper.TABLE_UTILISATEUR, row,
				DatabaseHelper.COL_ID_USER + "=" + p_Utilisateur.getId(), null);
	}

	public void delete(int p_Id) {
		m_Db.delete(DatabaseHelper.TABLE_UTILISATEUR, DatabaseHelper.COL_ID_USER
				+ "=" + p_Id, null);
	}

	public void removeAll() {
		m_Db.delete(DatabaseHelper.TABLE_UTILISATEUR, null, null);
	}

	public Utilisateurs getUtilisateur(int p_Id) {
		Utilisateurs u = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null,
				DatabaseHelper.COL_ID_USER + "=" + p_Id, null, null, null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			u = cursorToUtilisateur(c);
		}
		return u;
	}

	public Utilisateurs getUtilisateur(String p_Username) {
		Utilisateurs u = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null,
				DatabaseHelper.COL_COURRIEL + "='" + p_Username + "'", null,
				null, null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			u = cursorToUtilisateur(c);
		}
		return u;
	}

	public Utilisateurs getConnectedUtilisateur() {
		Utilisateurs u = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null,
				DatabaseHelper.COL_ESTCONNECTE + "=1", null, null, null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			u = cursorToUtilisateur(c);
		}
		return u;
	}

	public Utilisateurs getDernierConnecte() {
		Utilisateurs u = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null,
				DatabaseHelper.COL_DERNIERCONNECTE + "=1", null, null, null,
				null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			u = cursorToUtilisateur(c);
		}
		return u;
	}

	/**
	 * Met à jour le dernier utilisateur connecté
	 * 
	 * @param nouveau
	 */
	public void modifierDernierConnecte(Utilisateurs nouveau) {
		Utilisateurs dernierConnecte = getDernierConnecte();
		if (dernierConnecte != null) {
			// dernierConnecte.setDernierConnecte(false);
			update(dernierConnecte);
		}
		// nouveau.setDernierConnecte(true);
		update(nouveau);

	}

	public List<Utilisateurs> getAllUtilisateurs() {
		List<Utilisateurs> utilisateurs = new ArrayList<Utilisateurs>();
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null, null,
				null, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Utilisateurs u = cursorToUtilisateur(c);
			utilisateurs.add(u);
			c.moveToNext();
		}
		return utilisateurs;
	}

	private ContentValues utilisateurToContentValues(Utilisateurs p_Utilisateur) {
		ContentValues row = new ContentValues();
		row.put(DatabaseHelper.COL_COURRIEL, p_Utilisateur.getCourriel());
		row.put(DatabaseHelper.COL_PSEUDO, p_Utilisateur.getPseudo());
		row.put(DatabaseHelper.COL_MOTDEPASSE, p_Utilisateur.getMotDePasse());
		row.put(DatabaseHelper.COL_ESTCONNECTE,
				p_Utilisateur.getEstConnecte() ? 1 : 0);
		// row.put(DatabaseHelper.COL_DERNIERCONNECTE,
		// p_Utilisateur.getDernierConnecte() ? 1:0);
		return row;
	}

	private Utilisateurs cursorToUtilisateur(Cursor c) {
		Utilisateurs r = new Utilisateurs(
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_ID_USER)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_COURRIEL)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_PSEUDO)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_MOTDEPASSE)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_NO_CIVIQUE)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_RUE)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_VILLE)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_CODE_POSTAL)),
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_VOITURE)) == 1 ? true : false,
				c.getFloat(c.getColumnIndex(DatabaseHelper.COL_RATING_COND)),
				c.getFloat(c.getColumnIndex(DatabaseHelper.COL_RATING_PASS)),
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_ESTCONNECTE)) == 1 ? true
						: false,
				c.getInt(c.getColumnIndex(DatabaseHelper.COL_DERNIERCONNECTE)) == 1 ? true
						: false, new ArrayList<String>());
		return r;
	}
}
